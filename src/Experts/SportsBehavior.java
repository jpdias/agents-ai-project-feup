package Experts;

import Common.Information;
import Common.Question;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by JP on 30/10/2014.
 */
class SportsBehavior extends SimpleBehaviour
{
    public SportsBehavior(Agent a) {
        super(a);
    }

    public void action()
    {

        ACLMessage msg = myAgent.blockingReceive();
        if (msg!=null) {

            Random randomGenerator = new Random();
            int sol = randomGenerator.nextInt(4);
            String[] question =  msg.getContent().split(",");
            ArrayList<Question> history = Information.getAllQuestion("Sports");

            for(int i = 0; i <history.size();i++){
                if( history.get(i).getQuestion().equals(question[1])){
                    sol = history.get(i).getSolution();
                }
            }

            ACLMessage reply = msg.createReply();

            reply.setPerformative( ACLMessage.INFORM );
            reply.setContent(Integer.toString(sol));
            myAgent.send(reply);
        }

    }

    private boolean finished = false;
    public  boolean done() {  return finished;  }

}
