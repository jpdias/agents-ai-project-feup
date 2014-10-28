import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import java.util.Random;


public class Master extends Agent
{
    protected void setup()
    {   // First set-up answering behaviour
        addBehaviour( new Ask( this ) );
    }
}

class Ask extends SimpleBehaviour {


    public Ask(Agent a) {
        super(a);
    }

    private int n = 0;

    public void action() {

        //Choose random category
        /*
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(Information.Categories.length);
        String cat = Information.Categories[index];
        */
        Question current = Information.getQuestion("Arts");
        ACLMessage msg;
        msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent(current.makeQuestion());
        System.out.println("Question: "+current.makeQuestion());
        msg.addReceiver(new AID( "player", AID.ISLOCALNAME) );
        myAgent.send(msg);


        block();
        ACLMessage response=  myAgent.receive();
        if (response!=null){
            System.out.println( "Player answer:" +  response.getContent() + " -> from: " +  response.getSender().getName() );
            n++;
        }

    }

    public boolean done() {
        return n==3;
    }
}