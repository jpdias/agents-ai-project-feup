package Experts;

import jade.core.Agent;


/**
 * Created by JP on 29/10/2014.
 */
public class Expert extends Agent
{
    private static int agentnumber = 0;

    public Expert(int num) {
        agentnumber = num;
    }

    protected void setup()
    {
        switch (agentnumber){
            case 0:{
                addBehaviour(new RandomAgent(this));
                break;
            }
            case 1:{
                addBehaviour(new PlacesAgent(this));
                break;
            }
            case 2:{
                addBehaviour(new SportsAgent(this));
                break;
            }
            case 3:{
                addBehaviour(new ScienceAgent(this));
                break;
            }
            case 4:{
                addBehaviour(new HistoryAgent(this));
                break;
            }
            case 5:{
                addBehaviour(new ArtsAgent(this));
                break;
            }
            default:{
                break;
            }
        }


    }
}

//End class B1