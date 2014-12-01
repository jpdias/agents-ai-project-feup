package Players;

import Players.Beta.PlayerBeta;
import Players.FIRE.PlayerFIRE;
import Players.FIRE.PlayerFIRExperience;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

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
                addBehaviour(new PlayerFIRE(this));
                break;
            case 4:
                addBehaviour(new PlayerFIRExperience(this));
                break;
            default:
                break;
        }
    }
}

