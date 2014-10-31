package Experts;

import jade.core.Agent;


/**
 * Created by JP on 29/10/2014.
 */
public class Expert extends Agent
{
    private int agentnumber = 0;

    public Expert(int num) {
        agentnumber = num;
    }

    protected void setup()
    {
        switch (agentnumber){
            case 0:{
                addBehaviour(new RandomBehavior(this));
                break;
            }
            case 1:{
                addBehaviour(new PlacesBehavior(this));
                break;
            }
            case 2:{
                addBehaviour(new SportsBehavior(this));
                break;
            }
            case 3:{
                addBehaviour(new ScienceBehavior(this));
                break;
            }
            case 4:{
                addBehaviour(new HistoryBehavior(this));
                break;
            }
            case 5:{
                addBehaviour(new ArtsBehavior(this));
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