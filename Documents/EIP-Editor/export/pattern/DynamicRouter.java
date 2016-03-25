package export.pattern;

import java.util.LinkedList;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;

public class DynamicRouter {
	public static void create(bibliothek.Modell modell, PetriNet pn, Page pa,
			int patternid, int ausgaenge, LinkedList<String> konditionen) {

		PlaceNode eingangsplatz = Create.createPlace("pipe", pa);
		PlaceNode ruleBase = Create.createPlace("rule base", pa);
		Exporter.eingangspunkte.add(patternid + "_p_" + eingangsplatz.getId());

		String color1 = Exporter.getEntryColset(modell, patternid).poll();
		if(color1 == null) {
			color1 = "UNIT";
		}
		
		String x = Create.createInternVar("x",color1, pn);
		String r = Create.createInternVar("r", "UNIT", pn);
		// TODO: UNIT durch Typ von rule base ersetzen
		
		TransitionNode belegt = Create.createTransition("belegt", "", "", "", "P_HIGH", pa);
		TransitionNode leer = Create.createTransition("leer", "", "", "", "P_NORMAL", pa);
		PlaceNode ruleBaseEntry = Create.createPlace("", pa);
		
		try {
			Create.createArc("ptot", leer.getId(), ruleBaseEntry.getId(), "", pa);
			Create.createArc("ptot", belegt.getId(), ruleBaseEntry.getId(), "", pa);
			Create.createArc("ttop", leer.getId(), ruleBase.getId(), "", pa);
			Create.createArc("bothdir", belegt.getId(), ruleBase.getId(), "", pa);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		

		for (int i = 0; i < ausgaenge; i++) {
			String condition = "[" + konditionen.poll() + " " + x + " " + r + "]";
			TransitionNode transistion = Create.createTransition("routingTransition", condition,
					pa);
			PlaceNode ausgangsplatz = Create.createPlace("pipe", pa);
			try {
				Create.createArc("ptot", transistion.getId(),
						eingangsplatz.getId(), x, pa);
				Create.createArc("ttop", transistion.getId(),
						ausgangsplatz.getId(), x, pa);
				Create.createArc("bothdir", transistion.getId(),
						ruleBase.getId(), r, pa);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Exporter.ausgangspunkte.add(patternid + "_p_"
					+ ausgangsplatz.getId());
		}
	}
}
