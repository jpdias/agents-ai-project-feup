package Players;

import Common.Information;
import Common.Utilities;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import java.util.Arrays;

class infoDummy {
    public static AID[] experts;
    public static int[][] pontuation;
    public static void initarray(){
        pontuation = new int[experts.length][Information.Categories.length];
        int numberOfAgents = experts.length;
        System.out.println(numberOfAgents + "*" + Information.Categories.length);
        for (int agent = 0; agent < numberOfAgents; agent ++)
            for (int cat = 0; cat < Information.Categories.length; cat++)
                pontuation[agent][cat] = 0;
    }

}


class PlayerDummy extends SimpleBehaviour
{

    public PlayerDummy(Agent a) {
        super(a);
        infoDummy.experts=Utilities.searchDF(a,"expert");
        infoDummy.initarray();
    }

    public void action()
    {

        ACLMessage msg = myAgent.blockingReceive();
        if (msg!=null) {


            String[] data = msg.getContent().split(",");
            String category = data[0];

            String[] solv = {data[2],data[3],data[4],data[5]};

            int cat = Arrays.asList(Information.Categories).indexOf(category);


            int pos;

            ACLMessage expertop = new ACLMessage(ACLMessage.REQUEST);
            expertop.setContent(msg.getContent());


            int majorpontuation = -1000;
            int currentAgent = 0;
            for(int j =0; j< infoDummy.experts.length; j++){
                if(infoDummy.pontuation[j][cat]> majorpontuation){
                    majorpontuation = infoDummy.pontuation[j][cat];
                    currentAgent = j;
                }
            }
            String agentname = infoDummy.experts[currentAgent].getLocalName();//selected expert

            //System.out.println(agentname);
            AID expert = new AID(agentname, AID.ISLOCALNAME);
            expertop.addReceiver(expert);
            expertop.setConversationId(String.valueOf(currentAgent));
            myAgent.send(expertop);


            ACLMessage response =  myAgent.blockingReceive();
            pos = Integer.parseInt(response.getContent());

            ACLMessage reply = msg.createReply();
            reply.setPerformative( ACLMessage.INFORM );
            reply.setContent(pos+"|"+solv[pos]);
            myAgent.send(reply);

            ACLMessage lastQuestionSol =  myAgent.blockingReceive();

            if (lastQuestionSol.getContent()!= null) {
                if (lastQuestionSol.getContent().equals("true")){

                    infoDummy.pontuation[currentAgent][cat] += 1;
                }
                else
                    infoDummy.pontuation[currentAgent][cat] -= 1;
            }
        }

    }

    private boolean finished = false;
    public  boolean done() {  return finished;  }

} //End class B1