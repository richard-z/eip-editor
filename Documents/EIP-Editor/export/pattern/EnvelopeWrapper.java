package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.Modell;
import bibliothek.Pattern;

public class EnvelopeWrapper {

	public static void create(PetriNet pn, Page pa, int patternid, Pattern pat, Modell model) {

		String entryColorset = Exporter.getEntryColset(model, patternid).poll();
		if(entryColorset == null) { //default colorset
			entryColorset = "UNIT";
		}
		
		String exitColorset = Exporter.getExitColset(model, patternid).poll();
		if(exitColorset == null) { //default colorset
			exitColorset = "UNIT";
		}
		
		PlaceNode eingangPipe = Create.createPlace("pipe", entryColorset, "", pa);
		PlaceNode ausgangPipe = Create.createPlace("pipe", exitColorset, "", pa);
		
		TransitionNode trans = Create.createTransition("", pa);

		try {
			Create.createArc("ptot", trans.getId(), eingangPipe.getId(), "x", pa);
			Create.createArc("ttop", trans.getId(), ausgangPipe.getId(), ((bibliothek.EnvelopeWrapper) pat).getFilterFunktion() + " x", pa);

		} catch (Exception e1) {
			e1.printStackTrace();

		}
		
		Exporter.eingangspunkte.add(patternid + "_p_" + eingangPipe.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + ausgangPipe.getId());
	}

}
