package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;

public class GuaranteedDeliveryAbstract {
	
public static void create(PetriNet pn, Page pa, int patternid) {
		
		PlaceNode p2pchan = Create.createPlace("p2p\nchan", "Fifo", "", pa);
		PlaceNode k = Create.createPlace("k", pa);
		
		TransitionNode push = Create.createTransition("push", pa);
		TransitionNode pop = Create.createTransition("pop", pa);

		TransitionNode lossy = Create.createTransition("lossy", pa);

		try {
			Create.createArc("ttop", push.getId(), p2pchan.getId(), "",
					pa);
			// push -> p2pchan
			Create.createArc("ptot", push.getId(), k.getId(), "", pa);
			// push <- k
			Create.createArc("ptot", pop.getId(), p2pchan.getId(), "", pa);
			// p2pchan -> pop
			Create.createArc("ttop", pop.getId(), k.getId(), "", pa);
			// k <- pop
			Create.createArc("ptot", lossy.getId(), p2pchan.getId(), "", pa);
			// p2pchan -> lossy
			Create.createArc("ttop", lossy.getId(), k.getId(), "", pa);
			// k <- lossy

		} catch (Exception e1) {
			e1.printStackTrace();

		}
		Exporter.eingangspunkte.add(patternid + "_t_" + push.getId());
		Exporter.ausgangspunkte.add(patternid + "_t_" + pop.getId());
	}

}
