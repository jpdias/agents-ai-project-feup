package Run;

import Experts.Expert;
import Players.*;
import jade.core.Runtime;
import jade.core.Profile; 
import jade.core.ProfileImpl; 
import jade.wrapper.*;

import java.util.ArrayList;

public class Run {

    public static ArrayList<Expert> experts =  new ArrayList<Expert>();
    public static ArrayList<Player> players =  new ArrayList<Player>();

	public static void main( String arg[] ) {
		// Get a hold on JADE runtime 
		Runtime rt = Runtime.instance(); 
		// Create a default profile    
		Profile p = new ProfileImpl();
		// Create a new non-main container, connecting to the default
		// main container (i.e. on this host, port 1099) 
		ContainerController cc = rt.createMainContainer(p);


       // Player player = new Player();
        for(int i  = 0; i< 6;i++) {
            experts.add(new Expert(i));
        }
        //System.out.println(expertsName.toString());
        Master master = new Master();
        for(int i  = 0; i< 2;i++) {
            players.add(new Player(i));
        }


		try {
			//AgentController dummy = cc.createNewAgent("sfdds", "agents.AgvAgent", null);
			AgentController rma = cc.createNewAgent("rma", "jade.tools.rma.rma", null);
			AgentController f1 = cc.acceptNewAgent("main", master);
			//AgentController p1 = cc.acceptNewAgent("player", playerDummy);
            //AgentController p2 = cc.acceptNewAgent("playerRandom", playerRandom);

            rma.start();
            f1.start();

            ArrayList<AgentController> allControllers = new ArrayList<AgentController>();
            for(int i =0 ; i< experts.size();i++) {
                allControllers.add(cc.acceptNewAgent("expert"+i, experts.get(i)));
                allControllers.get(i).start();
            }
            int temp = allControllers.size();
            for(int i =0 ; i< players.size();i++) {
                allControllers.add(cc.acceptNewAgent("player"+i, players.get(i)));
                allControllers.get(temp + i).start();
            }
			
		} catch (StaleProxyException e) {
			e.printStackTrace();
		} 
		// Fire up the a
		
	}
}
