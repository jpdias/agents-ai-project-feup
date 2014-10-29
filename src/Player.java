import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;


public class Player extends Agent
{

    protected void setup()
    {
        addBehaviour(new Answer(this));
    }
}

class Answer extends SimpleBehaviour
{
    public Answer(Agent a) {
        super(a);
    }

    public void action()
    {

        ACLMessage msg = myAgent.blockingReceive();
        if (msg!=null) {
            //System.out.println( " - " + myAgent.getLocalName() + " Question: " + msg.getContent());
            String[] data = msg.getContent().split(",");
            String category = data[0];
            String question = data[1];
            String[] solv = {data[2],data[3],data[4],data[5]};
            int pos = 0;

            ACLMessage expertop = new ACLMessage(ACLMessage.INFORM);
            expertop.setContent(msg.getContent());
            expertop.addReceiver(new AID( "expert1", AID.ISLOCALNAME) );

            myAgent.send(expertop);

            ACLMessage response =  myAgent.blockingReceive();
            pos = Integer.parseInt(response.getContent());

            ACLMessage reply = msg.createReply();
            reply.setPerformative( ACLMessage.INFORM );
            reply.setContent(pos+"-"+solv[pos]);
            myAgent.send(reply);
        }

    }

    private boolean finished = false;
    public  boolean done() {  return finished;  }

} //End class B1