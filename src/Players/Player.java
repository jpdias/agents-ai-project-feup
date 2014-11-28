package Players;

import Players.Beta.PlayerBeta;
import Players.Travos.PlayerTravos;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 * Created by JP on 05/11/2014.
 */
public class Player extends Agent
{
    private int playernum=0;

    public Player(int playernum) {

            this.playernum=playernum;
    }
    public Player() {
    }

    protected void setup()
    {
        Object[] args = getArguments();
        if(args!= null){
            playernum=Integer.parseInt((String)args[0]);
        }

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getName());
        sd.setType("player");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch(FIPAException e) {
            e.printStackTrace();
        }

        switch(playernum){
            case 0:
                addBehaviour(new PlayerDummy(this));
                break;
            case 1:
                addBehaviour(new PlayerRandom(this));
                break;
            case 2:
                addBehaviour(new PlayerBeta(this));
                break;
            case 3:
                addBehaviour(new PlayerTravos(this));
                break;
            default:
                break;
        }
    }
}

