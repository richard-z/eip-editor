package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;

import export.Create;
import export.Exporter;
import bibliothek.Pattern;

public class Message {

	public static void create(Pattern pat, PetriNet pn, Page pa, int patternid) {
		
		String initialMarking = ((bibliothek.Message) pat).getInitalMarking();
		String color =  ((bibliothek.Message) pat).getColorSet();
		
		if(color == null || color.isEmpty()) {
			color = "UNIT";
		}
		
		if(initialMarking == null || initialMarking.isEmpty() && color == "UNIT") {
			initialMarking = "1`()";
		}
		PlaceNode message = Create.createPlace("message_message", color , "", pa);
		
		Create.setPosition(message, pat.getX(), pat.getY());

		Create.createInitialMarking(message, initialMarking); //for simulation
		
		Exporter.eingangspunkte.add(patternid + "_p_" + message.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + message.getId());	
	}
}
