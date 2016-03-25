package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.Pattern;

public class MessageFilter {	

	public static void create(PetriNet pn, Page pa, int patternid, Pattern pat) {
		
	
		PlaceNode Place1 = Create.createPlace("pipe", pa);
		PlaceNode Place2 = Create.createPlace("pipe", pa);
		
		String cond = "";
		
		if(!(((bibliothek.MessageFilter) pat).getKonditionen().isEmpty())) {
			cond = ((bibliothek.MessageFilter) pat).getKonditionen().poll();
		}
		
		
		String x = Create.createInternVar("x", "UNIT", pn);

		TransitionNode Transition1 = Create.createTransition("", "[" + cond +  x + "]", pa);
		TransitionNode Transition2 = Create.createTransition("drop", "[ not " + cond +  x + "]", pa);
		
		
		
		try {
			Create.createArc("ptot", Transition1.getId(), Place1.getId(), x,pa);
			Create.createArc("ptot", Transition2.getId(), Place1.getId(), x,pa);
			Create.createArc("ttop", Transition1.getId(), Place2.getId(), x,pa);
		} catch (Exception e1) {
			e1.printStackTrace();

		}
		
		Exporter.eingangspunkte.add(patternid + "_p_" + Place1.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + Place2.getId());

	}

}
