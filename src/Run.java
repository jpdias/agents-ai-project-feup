import Experts.Expert;
import jade.core.Runtime;
import jade.core.Profile; 
import jade.core.ProfileImpl; 
import jade.wrapper.*;

import java.util.ArrayList;

public class Run {

    public static ArrayList<Expert> experts =  new ArrayList<Expert>();
    public static ArrayList<String> expertsName =  new ArrayList<String>();

	public static void main( String arg[] ) {
		// Get a hold on JADE runtime 
		Runtime rt = Runtime.instance(); 
		// Create a default profile    
		Profile p = new ProfileImpl();
		// Create a new non-main container, connecting to the default
		// main container (i.e. on this host, port 1099) 
		ContainerController cc = rt.createMainContainer(p);


       // Player player = new Player();
        for(int i  = 0; i< 5;i++) {
            experts.add(new Expert(i));
            expertsName.add("expert"+i);
        }
        System.out.println(expertsName.toString());
        Master master = new Master();
        PlayerDummy player = new PlayerDummy();
		try {
			//AgentController dummy = cc.createNewAgent("sfdds", "agents.AgvAgent", null);
			AgentController rma = cc.createNewAgent("rma", "jade.tools.rma.rma", null);
			AgentController f1 = cc.acceptNewAgent("main", master);
			AgentController p1 = cc.acceptNewAgent("player", player);

            rma.start();
            f1.start();
            p1.start();

            ArrayList<AgentController> allControllers = new ArrayList<AgentController>();
            for(int i =0 ; i< experts.size();i++) {
                allControllers.add(cc.acceptNewAgent("expert"+i, experts.get(i)));

                allControllers.get(i).start();
            }

			
		} catch (StaleProxyException e) {
			e.printStackTrace();
		} 
		// Fire up the a
		
	}
}
