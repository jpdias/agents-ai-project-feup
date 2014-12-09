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
public class VariableBehavior extends SimpleBehaviour
{

    int count = 0, random=0;

    public VariableBehavior(Agent a) {
        super(a);
        Random randomGenerator = new Random();
        random = randomGenerator.nextInt(50);
    }

    public void action()
    {

        ACLMessage msg = myAgent.blockingReceive();
        if (msg!=null) {
            count++;
            Random randomGenerator = new Random();
            int sol = randomGenerator.nextInt(4);
            String[] question =  msg.getContent().split(",");
            if(count<=random) {
                ArrayList<Question> history = new ArrayList<Question>();
                for (int k = 0; k < Information.Categories.length; k++)
                    history.addAll(Information.getAllQuestion(Information.Categories[k]));

                for (int i = 0; i < history.size(); i++) {
                    if (history.get(i).getQuestion().equals(question[1])) {
                        sol = history.get(i).getSolution();
                    }
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
