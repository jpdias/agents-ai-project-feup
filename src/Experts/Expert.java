package Experts;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;


/**
 * Created by JP on 29/10/2014.
 */
public class Expert extends Agent
{
    private int agentnumber = 0;

    public Expert(int num) {
        agentnumber = num;
    }

    public Expert() {
    }

    protected void setup()
    {
        Object[] args = getArguments();
        if(args!= null){
            agentnumber=Integer.parseInt((String)args[0]);
        }

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getName());
        sd.setType("expert");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch(FIPAException e) {
            e.printStackTrace();
        }

        switch (agentnumber){

            case 0:{
                addBehaviour(new RandomBehavior(this));
                break;
            }
            case 1:{
                addBehaviour(new SportsBehavior(this));
                break;
            }
            case 2:{
                addBehaviour(new ScienceBehavior(this));
                break;
            }
            case 3:{
                addBehaviour(new HistoryBehavior(this));
                break;
            }
            case 4:{
                addBehaviour(new ArtsBehavior(this));
                break;
            }
            case 5:{
                addBehaviour(new PlacesBehavior(this));
                break;
            }
            case 6:{
                addBehaviour(new AllBehavior(this));
                break;
            }
            default:{
                break;
            }
        }


    }
}

//End class B1