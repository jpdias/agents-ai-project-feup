import Common.Information;
import Common.Question;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.util.Random;
import java.util.Scanner;


public class Master extends Agent
{
    public static boolean lastQuestion= false;

    protected void setup()
    {
        System.out.println("Do you wish to start?(yes/no)");
        Scanner a = new Scanner(System.in);
        String b="no";
        try {
            b= a.nextLine();
        }catch (Exception ex){}

        if(!b.equals("yes")){
           System.exit(0);
        }

        // First set-up answering behaviour
        addBehaviour(new Ask(this));
    }
}

class Ask extends SimpleBehaviour {

    public Ask(Agent a) {
        super(a);
    }

    private int numberofquestions = 500;
    private int n = 0;
    private int right=0,wrong=0;


    public void action() {

        //Choose random category

        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(Information.Categories.length);
        String cat = Information.Categories[index];

        Question current = Information.getQuestion(cat);
        ACLMessage msg;
        msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent(current.makeQuestion()+","+Master.lastQuestion);
        System.out.println("Question: "+current.makeQuestion());
        msg.addReceiver(new AID( "player", AID.ISLOCALNAME) );
        myAgent.send(msg);

        ACLMessage response=  myAgent.blockingReceive();
        if (response!=null){
            System.out.println( "Player answer:" +  response.getContent() + " -> from: " +  response.getSender().getName() );
            if(current.getSolution()==Integer.parseInt(response.getContent().split("|")[0])){
                System.out.println( "---- Player is right!");
                Master.lastQuestion = true;
                right++;
            }
            else{
                System.out.println( "---- Player is wrong!");
                Master.lastQuestion = false;
                wrong++;
            }
            n++;
        }
        if(n==numberofquestions){
            System.out.println( "\nTotal right: " + right +"; Total wrong: "+ wrong);
        }

    }

    public boolean done() {
        return n==numberofquestions;
    }
}