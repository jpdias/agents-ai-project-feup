package Players.Beta;

import Common.Information;
import Common.Utilities;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.util.Arrays;


class infoBeta {
    public static AID[] experts;
    public static int[][] res;
    public static double[]reputation;
    public static int numberOfAgents=0;
    public static int cat=0;

    public static void initarray(){
        res = new int[experts.length][2];
        reputation = new double[experts.length];
        numberOfAgents = experts.length;
    }
}


public class PlayerBeta extends SimpleBehaviour
{
    private String agent;
    private int cat;

    public PlayerBeta(Agent a) {
        super(a);
        agent=a.getLocalName();
        infoBeta.experts=Utilities.searchDF(a,"expert");
        infoBeta.initarray();
    }

    public double getExpectedValue(){

        double expectedValue=0;
        int total_rights=0, total_wrongs=0;
        for(int j =0; j< infoBeta.experts.length; j++){
            total_rights += infoBeta.res[j][0];
            total_wrongs += infoBeta.res[j][1];
        }
        expectedValue=(double)(total_rights+1)/(double)(total_rights+total_wrongs+2);

        return expectedValue;
    }

    public double calculateReputationValue(double alpha, double beta){
        return (alpha-beta)/(alpha+beta+2);
    }

    public double discountReputation(double alpha, double beta){

        int total_rights=0, total_wrongs=0;
        for(int j =0; j< infoBeta.experts.length; j++){
            total_rights += infoBeta.res[j][0];
            total_wrongs += infoBeta.res[j][1];
        }

        double new_alpha = (2*alpha*total_rights)/((beta+2)*(total_rights+total_wrongs+2)+2*alpha);
        double new_beta = (2*alpha*total_wrongs)/((beta+2)*(total_rights+total_wrongs+2)+2*alpha);
        //System.out.println("new_alpha="+new_alpha+"----------------------------new_beta="+new_beta);

        return calculateReputationValue(new_alpha, new_beta);
    }

    public int betaTrustModel(){
        int currentAgent=0;

        double minor_probability_wrong=0;
        for(int j =0; j< infoBeta.experts.length; j++){
            int alpha= infoBeta.res[j][0], beta = infoBeta.res[j][1];
            infoBeta.reputation[j] = calculateReputationValue(alpha, beta);

            if(infoBeta.reputation[j]>=minor_probability_wrong ){
                infoBeta.reputation[j] = discountReputation(infoBeta.res[j][0], infoBeta.res[j][1]);
                minor_probability_wrong = infoBeta.reputation[j];
                System.out.println(infoBeta.experts[j].getLocalName()+"->["+infoBeta.res[j][0]+"  "+infoBeta.res[j][1]+"  --"+infoBeta.reputation[j]+"]");
                currentAgent = j;
            }else {
                System.out.println(infoBeta.experts[j].getLocalName() + "->["+ infoBeta.res[j][0] + "  " + infoBeta.res[j][1] + "  " + infoBeta.reputation[j] + "]");
            }
        }

        return currentAgent;
    }

    public void action()
    {

        ACLMessage msg = myAgent.blockingReceive();
        if (msg!=null) {
            //System.out.println( " - " + myAgent.getLocalName() + " Common.Question: " + msg.getContent());
            String[] data = msg.getContent().split(",");
            System.out.println(msg.getContent());
            String category = data[0];
            String[] solv = {data[2],data[3],data[4],data[5]};
            cat = Arrays.asList(Information.Categories).indexOf(category);
            int pos = 0;

            ACLMessage expertop = new ACLMessage(ACLMessage.REQUEST);
            expertop.setContent(msg.getContent());

            int index = betaTrustModel();
            String agentname = infoBeta.experts[index].getLocalName();

            //System.out.println("-------------------------------BETA-----------------------------"+agentname+"---index = "+index);
            expertop.addReceiver(new AID(agentname, AID.ISLOCALNAME));

            expertop.setConversationId(String.valueOf(expertop));
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
                    infoBeta.res[index][0]++;
                }else{
                    infoBeta.res[index][1]++;
                }
            }
        }

    }

    private boolean finished = false;
    public  boolean done() {  return finished;  }

}