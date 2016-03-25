package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;

public class GuaranteedDeliveryLossy {
	
public static void create(PetriNet pn, Page pa, int patternid) {
		
		PlaceNode p2pchan = Create.createPlace("p2p\nchan", "Fifo", "", pa);

		TransitionNode push = Create.createTransition("push", pa);
		TransitionNode pop = Create.createTransition("pop", pa);

		TransitionNode lossy = Create.createTransition("lossy", pa);

		try {
			Create.createArc("ttop", push.getId(), p2pchan.getId(), "ins l x",
					pa);
			// push -> p2pchan
			Create.createArc("ptot", push.getId(), p2pchan.getId(), "l", pa);
			// push <- p2pchan
			Create.createArc("ptot", pop.getId(), p2pchan.getId(), "l", pa);
			// p2pchan -> pop
			Create.createArc("ttop", pop.getId(), p2pchan.getId(), "tl l", pa);
			// p2pchan <- pop
			Create.createArc("ptot", lossy.getId(), p2pchan.getId(), "l", pa);
			// p2pchan -> lossy
			Create.createArc("ttop", lossy.getId(), p2pchan.getId(), "tl l", pa);
			// p2pchan <- lossy

		} catch (Exception e1) {
			e1.printStackTrace();

		}
		Exporter.eingangspunkte.add(patternid + "_t_" + push.getId());
		Exporter.ausgangspunkte.add(patternid + "_t_" + pop.getId());
	}
//TODO: 'x' als eingabe und 'hd l' als ausgabe, beim verbinden realisierbar
}
