package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.Modell;
import bibliothek.Pattern;

public class EventDrivenConsumer {

	public static void create(PetriNet pn, Page pa, int patternid, Pattern pat, Modell model) {


		PlaceNode chan = Create.createPlace("chan", pa);
		PlaceNode ausgang = Create.createPlace("", pa);
		TransitionNode trans = Create.createTransition("recv", pa);
		
		String color1 = Exporter.getEntryColset(model, patternid).poll();
		if(color1 == null) { //default colorset
			color1 = "UNIT";
		}
		
		String x = Create.createInternVar("x", color1, pn);

		try {
			Create.createArc("ptot", trans.getId(), chan.getId(), x, pa);
			Create.createArc("ttop", trans.getId(), ausgang.getId(), ((bibliothek.EventDrivenConsumer) pat).getFunktion() + " " + x, pa);

		} catch (Exception e1) {
			e1.printStackTrace();

		}
		
		Exporter.eingangspunkte.add(patternid + "_p_" + chan.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + ausgang.getId());
	}

}
