package Run;

import Experts.Expert;
import Interface.*;
import Players.*;
import jade.core.Agent;
import jade.core.Runtime;
import jade.core.Profile; 
import jade.core.ProfileImpl; 
import jade.wrapper.*;

import java.util.ArrayList;

public class Run {

    //public static ArrayList<Expert> experts =  new ArrayList<Expert>();
    //public static ArrayList<Player> players =  new ArrayList<Player>();
    public static boolean init=false;
    public static ContainerController cc;

	public static void main( String arg[] ) {
        initRMA();
        MenuManager im = new MenuManager();
	}

    public static void initRMA(){
        // Get a hold on JADE runtime
        Runtime rt = Runtime.instance();
        // Create a default profile
        Profile p = new ProfileImpl();
        // Create a new non-main container, connecting to the default
        // main container (i.e. on this host, port 1099)
        cc = rt.createMainContainer(p);

        AgentController rma = null;
        try {
            rma = cc.createNewAgent("rma", "jade.tools.rma.rma", null);
            rma.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }


    public static void initAgents(MasterMenu im, int numQuests){

        Master master = new Master(numQuests);
        try {
            AgentController f1 = cc.acceptNewAgent("MASTER", master);

            ArrayList<AgentController> allControllers = new ArrayList<AgentController>();

            //adding experts
            allControllers.add(cc.acceptNewAgent("SportsE", new Expert(1)));
            allControllers.add(cc.acceptNewAgent("ScienceE", new Expert(2)));
            allControllers.add(cc.acceptNewAgent("HistoryE", new Expert(3)));
            allControllers.add(cc.acceptNewAgent("ArtsE", new Expert(4)));
            allControllers.add(cc.acceptNewAgent("PlacesE", new Expert(5)));
            allControllers.add(cc.acceptNewAgent("RandomE", new Expert(0)));
            //allControllers.add(cc.acceptNewAgent("AllE", new Expert(6)));

            //adding players
            allControllers.add(cc.acceptNewAgent("FIRE", new Player(3)));
            allControllers.add(cc.acceptNewAgent("DUMMY", new Player(0)));
            allControllers.add(cc.acceptNewAgent("RANDOM", new Player(1)));
            allControllers.add(cc.acceptNewAgent("BETA", new Player(2)));

            for(int i =0 ; i< allControllers.size();i++) {
                if(im.getSelectedCheckBox(i)){
                    allControllers.get(i).start();
                }
            }

            boolean state=true;
            /*while(!state){
                state=true;
                for(int j=0;j<allControllers.size();j++){
                    if(allControllers.get(j).getState().getCode()!=Agent.AP_ACTIVE){
                        System.out.println("HELLO["+j+"]"+allControllers.get(j).getState().getCode()+"/ code="+Agent.AP_ACTIVE/*AgentState.cAGENT_STATE_ACTIVE);
                        state=false;
                    }else{
                        System.out.println("HELLO["+j+"]"+allControllers.get(j).getState().getCode()+"/ code="+Agent.AP_ACTIVE);
                    }
                }
            }*/
            Thread.sleep(1000);
            f1.start();


        } catch (StaleProxyException e) {
            e.printStackTrace();
        } catch (ControllerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
