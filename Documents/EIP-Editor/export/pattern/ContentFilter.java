package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.Modell;
import bibliothek.Pattern;

public class ContentFilter {

	public static void create(PetriNet pn, Page pa, int patternid, Modell model, Pattern pat) {
		
		String entryColorset = Exporter.getEntryColset(model, patternid).poll();
		if(entryColorset == null) { //default colorset
			entryColorset = "UNIT";
		}
		
		String exitColorset = Exporter.getExitColset(model, patternid).poll();
		if(exitColorset == null) { //default colorset
			exitColorset = "UNIT";
		}
		
		PlaceNode Place1 = Create.createPlace("pipe", entryColorset, "", pa); // Platz
																		// 1
		PlaceNode Place2 = Create.createPlace("pipe", exitColorset, "", pa); // Platz
																				// 2

		TransitionNode Transition1 = Create.createTransition("", pa); // Transition
																		// 1
		String color2 = Exporter.getEntryColset(model, patternid).poll();
		if(color2 == null) { //default colorset
			color2 = "UNIT";
		}
		String x = Create.createInternVar("x", color2, pn);
		
		try {
			Create.createArc("ptot", Transition1.getId(), Place1.getId(), x,
					pa); // Pfeil 1
			Create.createArc("ttop", Transition1.getId(), Place2.getId(),
					((bibliothek.ContentFilter) pat).getFilterFunktion() +  " " + x, pa); // Pfeil 2
		} catch (Exception e1) {
			e1.printStackTrace();

		}
		
		Exporter.eingangspunkte.add(patternid + "_p_" + Place1.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + Place2.getId());

	}

}
