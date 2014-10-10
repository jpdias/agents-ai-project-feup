import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;
import java.sql.*;

// classe do agente
public class PingPong extends Agent {

    public Connection connect(){
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    }

    // classe do behaviour
   class PingPongBehaviour extends SimpleBehaviour {

      private int n = 0;

      // construtor do behaviour
      public PingPongBehaviour(Agent a) {
         super(a);
      }

      // m�todo action
      public void action() {
         ACLMessage msg = blockingReceive();

         if(msg.getPerformative() == ACLMessage.INFORM) {
            System.out.println(++n + " " + getLocalName() + ": recebi " + msg.getContent());
            Connection db =connect();
             Statement stmt = null;
             try {
                 stmt = db.createStatement();
                 ResultSet rs = stmt.executeQuery( "SELECT * FROM Arts;" );
                 while ( rs.next() ) {

                     String[] answers = {rs.getString("A"),rs.getString("B"),rs.getString("C"),rs.getString("D")};
                     Question question = new Question(QuestionType.ARTS,
                             rs.getInt("ID"),
                             rs.getString("Question"),
                             answers,
                             rs.getString("Answer")
                             );
                     System.out.println(question.toString());
                 }
                 rs.close();
                 stmt.close();
                 db.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
            // cria resposta
            ACLMessage reply = msg.createReply();
            // preenche conte�do da mensagem
            if(msg.getContent().equals("ping"))
               reply.setContent("pong");
            else reply.setContent("ping");
            // envia mensagem
            send(reply);
         }
      }

      // m�todo done
      public boolean done() {
         return n==1;
      }

   }   // fim da classe PingPongBehaviour


   // m�todo setup
   protected void setup() {

      String tipo = "";
      // obt�m argumentos
      Object[] args = getArguments();
      if(args != null && args.length > 0) {
         tipo = (String) args[0];
      } else {
         System.out.println("Nao especificou o tipo");
      }
      
      // regista agente no DF
      DFAgentDescription dfd = new DFAgentDescription();
      dfd.setName(getAID());
      ServiceDescription sd = new ServiceDescription();
      sd.setName(getName());
      sd.setType("Agente " + tipo);
      dfd.addServices(sd);
      try {
         DFService.register(this, dfd);
      } catch(FIPAException e) {
         e.printStackTrace();
      }

      // cria behaviour
      PingPongBehaviour b = new PingPongBehaviour(this);
      addBehaviour(b);
	  
      // toma a iniciativa se for agente "pong"
      if(tipo.equals("pong")) {
         // pesquisa DF por agentes "ping"
         DFAgentDescription template = new DFAgentDescription();
         ServiceDescription sd1 = new ServiceDescription();
         sd1.setType("Agente ping");
         template.addServices(sd1);
         try {
            DFAgentDescription[] result = DFService.search(this, template);
            // envia mensagem "pong" inicial a todos os agentes "ping"
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            for(int i=0; i<result.length; ++i)
               msg.addReceiver(result[i].getName());
            msg.setContent("pong");
            send(msg);
         } catch(FIPAException e) { e.printStackTrace(); }
      }

   }   // fim do metodo setup

   // m�todo takeDown
   protected void takeDown() {
      // retira registo no DF
      try {
         DFService.deregister(this);  
      } catch(FIPAException e) {
         e.printStackTrace();
      }
   }

}   // fim da classe PingPong

