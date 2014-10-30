package Experts;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Random;

/**
 * Created by JP on 30/10/2014.
 */
class RandomAgent extends SimpleBehaviour
{
    public RandomAgent(Agent a) {
        super(a);
    }

    public void action()
    {

        ACLMessage msg = myAgent.blockingReceive();
        if (msg!=null) {

            Random randomGenerator = new Random();
            int answer = randomGenerator.nextInt(4);
            ACLMessage reply = msg.createReply();
            //System.out.println( " ---> " + myAgent.getLocalName() + " / Answer: " + answer);
            reply.setPerformative( ACLMessage.INFORM );
            reply.setContent(Integer.toString(answer));
            myAgent.send(reply);
        }

    }

    private boolean finished = false;
    public  boolean done() {  return finished;  }

}
