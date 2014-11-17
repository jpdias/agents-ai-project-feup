package Run;

import Common.Information;
import Common.Question;
import Common.Utilities;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.*;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;


public class Master extends Agent
{
    public static boolean lastQuestion= false;
    public static int numberofquestions = 0;
    public static int ncurrentquestion = 0;
    public static Hashtable<AID,Integer> results = new Hashtable<AID,Integer>();
    public static AID[] players;
    protected void setup()
    {

        System.out.println("Do you wish to start?(yes/no)");
        Scanner a = new Scanner(System.in);
        String b="no";
        try {
            b= a.nextLine();
        }catch (Exception ex){

        }

        if(!b.equals("yes")){
            System.exit(0);
        }

        System.out.println("How many questions?(>0)");
        Scanner scn = new Scanner(System.in);
        String number="0";
        try {
            number= scn.nextLine();
        }catch (Exception ex){

        }

        numberofquestions = Integer.parseInt(number);

        // First set-up answering behaviour
        addBehaviour(new Ask(this));
    }
}

class Ask extends SimpleBehaviour {

    public Ask(Agent a) {
        super(a);
        Master.players = Utilities.searchDF(a, "player");
        for(int i =0;i<Master.players.length;i++){
            Master.results.put(Master.players[i],0);
        }
    }

    private int numberofquestions = Master.numberofquestions;
    private int n = 0;
    private int right=0,wrong=0;


    public void action() {

        //Choose random category

        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(Information.Categories.length);
        String cat = Information.Categories[index];

        Question current = Information.getQuestion(cat);
        ACLMessage msg;
        msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setContent(current.makeQuestion()+","+Master.lastQuestion);
        System.out.println("Question: "+current.makeQuestion());

        for(int j = 0; j<Master.players.length;j++)
            msg.addReceiver(Master.players[j]);

        msg.setConversationId("Run.Master");
        myAgent.send(msg);
        Master.ncurrentquestion++;



        //System.out.println(response);
        //while(response)
        int temp = 0;
        while(true) {
            ACLMessage response=  myAgent.blockingReceive();
            if (response != null) {
                System.out.println("Player answer:" + response.getContent() + " -> from: " + response.getSender().getName());
                if (current.getSolution() == Integer.parseInt(response.getContent().split("|")[0])) {
                    System.out.println("---- Player is right!");
                    Master.lastQuestion = true;
                    right++;
                    int pnt = Master.results.get(response.getSender());
                    Master.results.put(response.getSender(),pnt+1);
                } else {
                    System.out.println("---- Player is wrong!");
                    Master.lastQuestion = false;
                    wrong++;
                }
               temp++;
            }
            if(temp == Master.players.length)
                break;

        }
        n++;
        if(n==numberofquestions){
            Enumeration<AID> pl = Master.results.keys();
            while(pl.hasMoreElements()) {
                AID x =  pl.nextElement();
                String str = (String)x.getLocalName();
                int pnts =  Master.results.get(x);
                System.out.println( str + " -> Total right: " + pnts +"; Total wrong: "+ (n-pnts) );
            }
        }

    }

    public boolean done() {
        return n==numberofquestions;
    }
}