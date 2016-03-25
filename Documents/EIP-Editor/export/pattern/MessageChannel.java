package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;

import export.Create;
import export.Exporter;
import bibliothek.Pattern;

public class MessageChannel {

	public static void create(Pattern pat, PetriNet pn, Page pa, int patternid, String color) {

		PlaceNode forward = Create.createPlace("forward_messageChannel", color, "", pa);

		Create.setPosition(forward, pat.getX(), pat.getY());
		Exporter.eingangspunkte.add(patternid + "_p_" + forward.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + forward.getId());
		

	}

}
