package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;

public class SelectiveConsumer {
	
	public static void create(PetriNet pn, Page pa, int patternid, bibliothek.MessageRouter pat){
		
		PlaceNode in = Create.createPlace("chan", pa);
		PlaceNode out = Create.createPlace("", pa);
		
		String x = Create.createInternVar("x", "UNIT", pn);
		
		String cond = pat.getKonditionen().poll();
		TransitionNode select = Create.createTransition("", cond, pa);
		
		try {
			Create.createArc("ptot", select.getId(), in.getId(), x, pa);
			Create.createArc("ttop", select.getId(), out.getId(), x, pa);

		} catch (Exception e1) {
				e1.printStackTrace();
			}					
		
		Exporter.eingangspunkte.add(patternid + "_p_" + in.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + out.getId());
	}

}
