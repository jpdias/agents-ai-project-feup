package Players.FIRE;

import Common.Utilities;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

class genericPontuationExpert implements Comparable<genericPontuationExpert>{
    @Override
    public String toString() {
        return "genericPontuationExpert{" +
                "expert=" + expert.getLocalName() +
                ", points=" + points +
                '}';
    }

    public AID expert;
    public double points;
    public genericPontuationExpert(AID expert, double points) {
        this.expert = expert;
        this.points = points;
    }


    @Override
    public int compareTo(genericPontuationExpert o) {
        return Double.compare(points, o.points);
    }
}
class interationTrust{

    public AID expert;
    public String category;
    public double value;
    public interationTrust(AID expert, String category, double value) {
        this.expert = expert;
        this.category = category;
        this.value = value;
    }
}
class relationTrust{
    public AID expert;
    public String category;
    public double trust;
    public relationTrust(AID expert, String category, double trust) {
        this.expert = expert;
        this.category = category;
        this.trust = trust;
    }
}

class witnessReputation{
    public AID player;
    public String category;
    public double trust;
    public witnessReputation(AID player, String category, double trust) {
        this.player = player;
        this.category = category;
        this.trust = trust;
    }
}

class witnessTrust{
    public AID player;
    public String category;
    public double value;
    public witnessTrust(AID player, String category, double value) {
        this.player = player;
        this.category = category;
        this.value = value;
    }
}

class informationHelper{

    public  Vector<interationTrust> interationTrusts  = new Vector<interationTrust>();

    public double getExpertPontuationByCategory(AID expert, String category){
        double count = 0;
        for(int i = 0; i<interationTrusts.size();i++){
            if (interationTrusts.elementAt(i).category.equals(category)
                    && interationTrusts.elementAt(i).expert.getLocalName().equals(expert.getLocalName())) {
                count += interationTrusts.elementAt(i).value;
            }
        }
        return count;
    }

    public double getExpertRateByCategory(AID expert, int iteration){
        double count = 1;
        for(int i = 0; i<interationTrusts.size();i++){
            if(interationTrusts.elementAt(i).expert == expert){
                count += interationTrusts.elementAt(i).value;
            }
        }
        return count/iteration;
    }

}

public class PlayerFIRE extends SimpleBehaviour {
    private static double TRUSTSOURCES=0.8;
    private static AID[] experts;
    //int agentname / category
    private informationHelper information =  new informationHelper();
    private int iteration =0;

    public PlayerFIRE(Agent a) {

        super(a);
        experts= Utilities.searchDF(a, "expert");
        ContainerController cc = a.getContainerController();
        AgentController ac;
        try {
            ac = cc.createNewAgent("FIRExperience","Players.Player",new Object[] {"4"});
            ac.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }

    public void action()
    {

        ACLMessage msg = myAgent.blockingReceive();
        if (msg!=null) {
            iteration++;
            //System.out.println( " - " + myAgent.getLocalName() + " Common.Question: " + msg.getContent());
            String[] data = msg.getContent().split(",");
            String category = data[0];
            String question = data[1];
            String[] solv = {data[2],data[3],data[4],data[5]};
            int pos = 0;

            ACLMessage expertop = new ACLMessage(ACLMessage.REQUEST);
            expertop.setContent(msg.getContent());

            //Component 1 -----------------------------------------------
            Vector<genericPontuationExpert> phaseOne = new Vector<genericPontuationExpert>();

            for (AID expert : experts) {

                genericPontuationExpert temp = new genericPontuationExpert(expert, information.getExpertPontuationByCategory(expert, category));
                //System.out.println(temp.points);
                phaseOne.add(temp);
            }
            Collections.sort(phaseOne, new Comparator<genericPontuationExpert>() {
                @Override
                public int compare(genericPontuationExpert o1, genericPontuationExpert o2) {
                    return o1.expert.getLocalName().compareTo(o2.expert.getLocalName());
                }
            });
            //Component 2 -----------------------------------------------
            Vector<genericPontuationExpert> phaseTwo = new Vector<genericPontuationExpert>();

            for (AID expert : experts) {
                genericPontuationExpert temp = new genericPontuationExpert(expert, information.getExpertRateByCategory(expert, iteration));
                phaseTwo.add(temp);
            }
            Collections.sort(phaseTwo, new Comparator<genericPontuationExpert>() {
                @Override
                public int compare(genericPontuationExpert o1, genericPontuationExpert o2) {
                    return o1.expert.getLocalName().compareTo(o2.expert.getLocalName());
                }
            });
            //Component 3 -----------------------------------------------
            ACLMessage askOpinion = new ACLMessage(ACLMessage.REQUEST);
            askOpinion.setConversationId("Opinion");
            askOpinion.setContent("Opinion");
            askOpinion.addReceiver( new AID("FIRExperience", AID.ISLOCALNAME ) );
            ACLMessage opinion;
            while(true) {
                myAgent.send(askOpinion);
                opinion = myAgent.blockingReceive(100);
                if(opinion!=null && !opinion.getContent().isEmpty())
                    break;
            }
            Vector<genericPontuationExpert> phaseThree = new Vector<genericPontuationExpert>();
            String temp[] = opinion.getContent().substring(1, opinion.getContent().length() - 2).split(",");

            for (String aTemp : temp) {
                genericPontuationExpert opt = new genericPontuationExpert(new AID(aTemp.split("#")[0], AID.ISLOCALNAME), Double.parseDouble(aTemp.split("#")[1]));
                phaseThree.add(opt);
            }
           Collections.sort(phaseThree, new Comparator<genericPontuationExpert>() {
                @Override
                public int compare(genericPontuationExpert o1, genericPontuationExpert o2) {
                    return o1.expert.getLocalName().compareTo(o2.expert.getLocalName());
                }
            });
            Vector<genericPontuationExpert> finalResult = new Vector<genericPontuationExpert>();
            for(int k=0;k<phaseThree.size();k++){

                genericPontuationExpert opt;
                opt = new genericPontuationExpert(phaseThree.elementAt(k).expert,(phaseOne.elementAt(k).points*phaseTwo.elementAt(k).points)/((1+phaseThree.elementAt(k).points)*TRUSTSOURCES));
                finalResult.add(opt);
            }

            Collections.sort(finalResult, new Comparator<genericPontuationExpert>() {
                @Override
                public int compare(genericPontuationExpert o1, genericPontuationExpert o2) {
                    return Double.compare(o2.points,o1.points);
                }
            });


            AID expertAID = finalResult.firstElement().expert;//selected agent for answer

            //System.out.println(expertAID.getLocalName());
            expertop.addReceiver(expertAID);

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
                    interationTrust current =  new interationTrust(expertAID,category,1.0);
                    information.interationTrusts.add(current);

                }else{
                    interationTrust current =  new interationTrust(expertAID,category,-1.0);
                    information.interationTrusts.add(current);
                }
            }


        }

    }

    private boolean finished = false;
    public  boolean done() {  return finished;  }
}
