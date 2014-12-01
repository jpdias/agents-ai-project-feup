package Experts;

import Common.Information;
import Common.Question;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;

class ArtsBehavior extends SimpleBehaviour
{
    public ArtsBehavior(Agent a) {
        super(a);
    }

    public void action()
    {

        ACLMessage msg = myAgent.blockingReceive();
        if (msg!=null) {


            int sol = 3;
            String[] question =  msg.getContent().split(",");
            ArrayList<Question> history = Information.getAllQuestion("Arts");

            for (Question aHistory : history) {
                if (aHistory.getQuestion().equals(question[1])) {
                    sol = aHistory.getSolution();
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
