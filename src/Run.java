import jade.core.Runtime;
import jade.core.Profile; 
import jade.core.ProfileImpl; 
import jade.wrapper.*; 

public class Run {
	public static void main( String arg[] ) {
		// Get a hold on JADE runtime 
		Runtime rt = Runtime.instance(); 
		// Create a default profile    
		Profile p = new ProfileImpl();     
		// Create a new non-main container, connecting to the default 
		// main container (i.e. on this host, port 1099) 
		ContainerController cc = rt.createMainContainer(p); 

        Master master = new Master();
        Player player = new Player();
        Expert1 expert1 = new Expert1();

		try {
			//AgentController dummy = cc.createNewAgent("sfdds", "agents.AgvAgent", null);
			AgentController rma = cc.createNewAgent("rma", "jade.tools.rma.rma", null);

			AgentController f1 = cc.acceptNewAgent("main", master);

			AgentController m1 = cc.acceptNewAgent("player", player);
			AgentController m2 = cc.acceptNewAgent("expert1", expert1);

			//start agents, pops up ui
			rma.start();
            f1.start();
            f1.suspend();
            m1.start();
            m2.start();




			
		} catch (StaleProxyException e) {
			e.printStackTrace();
		} 
		// Fire up the a
		
	}
}
