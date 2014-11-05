package Common;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 * Created by JP on 05/11/2014.
 */
public class Utilities {

    public static AID [] searchDF( Agent current, String service )
    {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType( service );
        dfd.addServices(sd);

        try
        {
            DFAgentDescription[] result = DFService.search(current, dfd);
            AID[] agents = new AID[result.length];
            for (int i=0; i<result.length; i++) {
                agents[i] = result[i].getName();
            }
            return agents;

        }
        catch (FIPAException fe) { fe.printStackTrace(); }

        return null;
    }
}
