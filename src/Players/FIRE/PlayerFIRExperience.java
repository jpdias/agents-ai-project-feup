package Players.FIRE;

import Common.Utilities;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

class genericPontuationExpertExp implements Comparable<genericPontuationExpertExp>{
    @Override
    public String toString() {

       // NumberFormat formatter = new DecimalFormat("#0.00");

        return expert.getLocalName() + "#" + points +" ";
    }

    public AID expert;
    public double points;
    public genericPontuationExpertExp(AID expert, double points) {
        this.expert = expert;
        this.points = points;
    }

    @Override
    public int compareTo(genericPontuationExpertExp o) {
        return Double.compare(points,o.points);
    }
}
class interationTrustExp{

    public AID expert;
    public String category;
    public double value;
    public interationTrustExp(AID expert, String category, double value) {
        this.expert = expert;
        this.category = category;
        this.value = value;
    }
}

class informationHelperExp{
    public  Vector<interationTrustExp> interationTrusts  = new Vector<interationTrustExp>();

    public double getExpertRateByCategoryExp(AID expert, String category, int iteration) {
        double count = 1;
        for (int i = 0; i < interationTrusts.size(); i++) {
            if (interationTrusts.elementAt(i).category.equals(category)
                    && interationTrusts.elementAt(i).expert.getLocalName().equals(expert.getLocalName())) {
                count += interationTrusts.elementAt(i).value;
            }
        }
        return count / iteration;
    }
}

public class PlayerFIRExperience extends SimpleBehaviour {

    private static AID[] experts;
    //int agentname / category
    private informationHelperExp information =  new informationHelperExp();
    private int iteration =0 ;

    public PlayerFIRExperience(Agent a) {
        super(a);

        experts= Utilities.searchDF(a, "expert");
    }

    public void action()
    {
        ACLMessage msg;
        while(true){
            msg = myAgent.blockingReceive();
            if(!msg.getContent().equals("Opinion"))
                break;
        }
        if (msg!=null) {
            System.out.println(msg.getContent());
            iteration++;
            String[] data = msg.getContent().split(",");
            String category = data[0];
            String question = data[1];
            String[] solv = {data[2],data[3],data[4],data[5]};
            int pos = 0;

            ACLMessage expertop = new ACLMessage(ACLMessage.REQUEST);
            expertop.setContent(msg.getContent());

            //Component 1 -----------------------------------------------
            Vector<genericPontuationExpertExp> phaseTwo = new Vector<genericPontuationExpertExp>();

            for (AID expert : experts) {
                genericPontuationExpertExp temp = new genericPontuationExpertExp(expert, information.getExpertRateByCategoryExp(expert, category, iteration));
                phaseTwo.add(temp);
            }
            Collections.sort(phaseTwo, new Comparator<genericPontuationExpertExp>() {
                @Override
                public int compare(genericPontuationExpertExp o1, genericPontuationExpertExp o2) {
                    return Double.compare(o2.points,o1.points);
                }
            });

            ACLMessage optionNeed = myAgent.blockingReceive();



                ACLMessage reply = optionNeed.createReply();
                reply.setPerformative(ACLMessage.INFORM );
                reply.setContent(phaseTwo.toString());
                myAgent.send(reply);



            AID expertAID = phaseTwo.firstElement().expert; //selected agent for answer

            expertop.addReceiver(expertAID);

            expertop.setConversationId(String.valueOf(expertop));
            myAgent.send(expertop);
            ACLMessage response;
            while(true){
                response = myAgent.blockingReceive();
                if(!response.getContent().equals("Opinion"))
                    break;
            }
            //response =  myAgent.blockingReceive();
            pos = Integer.parseInt(response.getContent());

            ACLMessage resp = msg.createReply();
            resp.setPerformative( ACLMessage.INFORM );
            resp.setContent(pos+"|"+solv[pos]);
            myAgent.send(resp);

            ACLMessage lastQuestionSol =  myAgent.blockingReceive();

            if (lastQuestionSol.getContent()!= null) {
                if (lastQuestionSol.getContent().equals("true")){
                    interationTrustExp current =  new interationTrustExp(expertAID,category,1.0);
                    information.interationTrusts.add(current);
                }else{
                    interationTrustExp current =  new interationTrustExp(expertAID,category,-1.0);
                    information.interationTrusts.add(current);
                }
            }


        }

    }

    private boolean finished = false;
    public  boolean done() {  return finished;  }
}
