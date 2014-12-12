package Experts;

import Common.Information;
import Common.Question;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by JP on 31/10/2014.
 */
public class CommonBehavior extends SimpleBehaviour
{
    public CommonBehavior(Agent a) {
        super(a);
    }

    public void action()
    {

        ACLMessage msg = myAgent.blockingReceive();
        if (msg!=null) {

            Random randomGenerator = new Random();
            int sol = randomGenerator.nextInt(4);
            String[] question =  msg.getContent().split(",");
            ArrayList<Question> history = new ArrayList<Question>();
            for(int k=0; k<Information.Categories.length;k++) {
                history.addAll(Information.getHalfQuestion(Information.Categories[k]));
            }

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
