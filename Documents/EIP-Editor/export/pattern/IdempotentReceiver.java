package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;

public class IdempotentReceiver {
	
	public static void create(PetriNet pn, Page pa, int patternid) {
		
		PlaceNode chan = Create.createPlace("chan", "Type", "", pa);
		PlaceNode history = Create.createPlace("history", "Fifo", "", pa);
		PlaceNode ausgangPipe = Create.createPlace("pipe", "Type", "", pa);

		TransitionNode drop = Create.createTransition("drop","inList l x", pa);
		TransitionNode recv = Create.createTransition("recv", "not inList l x", pa);

		// TODO neue Variable deklarieren, die dann an die Pfeile schreiben:
		// (Problem: wie Namenskonvention, damit unbegrenzte Anzahl moeglich?)

		try {
			Create.createArc("ptot", recv.getId(), chan.getId(), "x",
					pa);
			// chan -> recv
			Create.createArc("ttop", recv.getId(), history.getId(), "ins l x", pa);
			// recv -> history
			Create.createArc("ptot", recv.getId(), history.getId(), "l", pa);
			// recv <- history
			Create.createArc("ptot", drop.getId(), history.getId(), "l", pa);
			// drop <- history
			Create.createArc("ttop", drop.getId(), history.getId(), "", pa);
			// drop -> history
			Create.createArc("ptot", drop.getId(), chan.getId(), "x", pa);
			// drop <- chan
			Create.createArc("ttop", recv.getId(), ausgangPipe.getId(), "x", pa);
			// recv -> ausgangsPipe
			
		} catch (Exception e1) {
			e1.printStackTrace();

		}
		Exporter.eingangspunkte.add(patternid + "_p_" + chan.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + ausgangPipe.getId());
	}

}
