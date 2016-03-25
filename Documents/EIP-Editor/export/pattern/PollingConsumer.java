package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;

public class PollingConsumer {
	
	public static void create(PetriNet pn, Page pa, int patternid){
		
		PlaceNode poll = Create.createPlace("poll", pa);
		PlaceNode chan = Create.createPlace("chan", "UNIT", "", pa);	// TODO: UNIT durch Colorset aus pattern ersetzen
		TransitionNode assign_recv = Create.createTransition("assign/recv", pa);
		
		// TODO neue Variable deklarieren, die dann an die Pfeile schreiben:
		// (Problem: wie Namenskonvention, damit unbegrenzte Anzahl moeglich?)
		// Loesung
		String x = Create.createInternVar("x", "UNIT", pn);
					
		try {
			Create.createArc("ptot", assign_recv.getId(), chan.getId(), x, pa);
			Create.createArc("ptot", assign_recv.getId(), poll.getId(), "", pa);

		} catch (Exception e1) {
				e1.printStackTrace();
			}
				
		Exporter.eingangspunkte.add(patternid + "_p_" + chan.getId());
		Exporter.ausgangspunkte.add(patternid + "_t_" + assign_recv.getId());
	}

}
