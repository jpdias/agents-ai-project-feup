package Players;

import Common.Information;
import Common.Utilities;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import java.util.Arrays;
import Run.*;

class infoDummy {
    public static int[][] pontuation = new int[Run.experts.size()][Information.Categories.length];
    private static int numberOfAgents = Run.experts.size();
    public static int iteration = 0;
    public static AID[] experts;
    public static void initarray(){
        //System.out.println(numberOfAgents + "*" + Information.Categories.length);
        for (int agent = 0; agent < numberOfAgents; agent ++)
            for (int cat = 0; cat < Information.Categories.length; cat++)
                pontuation[agent][cat] = 0;
    }
    public static int lastAgent;
    public static int lastCategory;
}


class PlayerDummy extends SimpleBehaviour
{

    public PlayerDummy(Agent a) {
        super(a);
        infoDummy.experts=Utilities.searchDF(a,"expert");

    }

    public void action()
    {

        ACLMessage msg = myAgent.blockingReceive();
        if (msg!=null) {

           // System.out.println( " - " + myAgent.getLocalName() + "Question: " + msg.getContent());
            String[] data = msg.getContent().split(",");
            String category = data[0];
            //String question = data[1];
            String[] solv = {data[2],data[3],data[4],data[5]};

            int cat = Arrays.asList(Information.Categories).indexOf(category);

            if(infoDummy.iteration!=0) {
                if (data[6] != null) {
                    //System.out.println(cat +" - "+ Players.infoDummy.lastAgent + " - " + Players.infoDummy.pontuation.length);
                    if (Boolean.parseBoolean(data[6]))
                        infoDummy.pontuation[infoDummy.lastAgent][infoDummy.lastCategory] += 1;
                    else
                        infoDummy.pontuation[infoDummy.lastAgent][infoDummy.lastCategory] -= 1;
                }
                /*try {
                    for (int[] arr : infoDummy.pontuation) {
                        System.out.println(Arrays.toString(arr));
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }*/
            }
            int pos;

            ACLMessage expertop = new ACLMessage(ACLMessage.REQUEST);
            expertop.setContent(msg.getContent());

            //Random randomGenerator = new Random();
            //int index = randomGenerator.nextInt(Run.Run.expertsName.size());
            int majorpontuation = -1000;
            int currentAgent = 0;
            for(int j =0; j< infoDummy.experts.length; j++){
                if(infoDummy.pontuation[j][cat]> majorpontuation){
                    majorpontuation = infoDummy.pontuation[j][cat];
                    currentAgent = j;
                }
            }
            String agentname = infoDummy.experts[currentAgent].getLocalName();
            infoDummy.lastAgent = currentAgent;
            infoDummy.lastCategory = cat;

            System.out.println(agentname);
            AID expert = new AID(agentname, AID.ISLOCALNAME);
            expertop.addReceiver(expert);
            expertop.setConversationId(String.valueOf(currentAgent));
            myAgent.send(expertop);

            infoDummy.iteration++;

            ACLMessage response =  myAgent.blockingReceive();
            pos = Integer.parseInt(response.getContent());

            ACLMessage reply = msg.createReply();
            reply.setPerformative( ACLMessage.INFORM );
            reply.setContent(pos+"|"+solv[pos]);
            myAgent.send(reply);
        }

    }

    private boolean finished = false;
    public  boolean done() {  return finished;  }

} //End class B1