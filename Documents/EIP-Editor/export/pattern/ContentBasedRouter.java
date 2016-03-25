package export.pattern;

import java.util.LinkedList;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.Message;
import bibliothek.MessageRouter;
import bibliothek.Pattern;

public class ContentBasedRouter {
	public static void create(bibliothek.Modell modell, Pattern pat,
			PetriNet pn, Page pa, int patternid, int ausgaenge,
			LinkedList<String> konditionen) {

		String color = ((MessageRouter) pat).getFilterFunktion();

		if (color  == null || color.isEmpty()) { // default colorset
			color = "UNIT";
		}

		PlaceNode eingangsplatz = Create.createPlace("eingangspipe_contentBasedRouter", color, "", pa);
		Exporter.eingangspunkte.add(patternid + "_p_" + eingangsplatz.getId());

		Create.setPosition(eingangsplatz, pat.getX(), pat.getY());		
		
		String x = Create.createInternVar("x", color, pn);

		for (int i = 0; i < ausgaenge; i++) {

			String conditionTemp = konditionen.poll();
			String condition = "";

			if (conditionTemp == null) {
				conditionTemp = "";
				condition = "[]";
			} else {

				condition = "[ " + conditionTemp.replaceAll("INPUT", x) + " ]";
			}
			TransitionNode transistion = Create.createTransition(
					"routingTransition_contentBasedRouter", condition, pa);
			PlaceNode ausgangsplatz = Create.createPlace("pipe_contentBasedRouter", color, "", pa);
			Create.setPosition(transistion, pat.getX() + 200, pat.getY() + 200
					- 100 * i);
			Create.setPosition(ausgangsplatz, pat.getX() + 300, pat.getY()
					+ 200 - 100 * i);
			try {
				Create.createArc("ptot", transistion.getId(),
						eingangsplatz.getId(), x, pa);
				Create.createArc("ttop", transistion.getId(),
						ausgangsplatz.getId(), x, pa);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Exporter.ausgangspunkte.add("ContentBasedRouter_" + patternid + "_p_"
					+ ausgangsplatz.getId());
		}

		try {

		} catch (Exception e1) {
			e1.printStackTrace();

		}

	}
}
