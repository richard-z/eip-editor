package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;

public class CompetingConsumer {
	
public static void create(PetriNet pn, Page pa, int numConsumer, int patternid) throws Exception {
		
		PlaceNode eingang = Create.createPlace("", pa);
		PlaceNode place = Create.createPlace("", pa);

		
		TransitionNode trans = Create.createTransition("1-to-2", pa);
		
		for(int i =0; i< numConsumer; i++){
			TransitionNode consumer = Create.createTransition("cons" + i, pa);
			Create.createArc("ptot", consumer.getId(), place.getId(), "x", pa);
			Exporter.ausgangspunkte.add(patternid + "_t_" + consumer.getId());
		}
				
		try {
				Create.createArc("ptot", trans.getId(), eingang.getId(), "x", pa);
				Create.createArc("ttop", trans.getId(), place.getId(), "x", pa);
		} catch (Exception e1) {
					e1.printStackTrace();
				}
		
		Exporter.eingangspunkte.add(patternid + "_p_" + eingang.getId());
		
}
}
