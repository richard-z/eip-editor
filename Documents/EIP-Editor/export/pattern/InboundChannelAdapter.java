package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.ChannelAdapter;
import bibliothek.Modell;
import bibliothek.Pattern;

public class InboundChannelAdapter {

	public static void create(PetriNet pn, Page pa, int patternid, Pattern pat,
			Modell model) throws Exception {

		String messageSystemType = ((ChannelAdapter) pat).getColorSet();
		if (messageSystemType == null || messageSystemType.isEmpty()) {
			messageSystemType = "UNIT";
		}
		
		String applicationType = ((ChannelAdapter) pat).getApplicationType();
		if (applicationType == null || applicationType.isEmpty()) { // default
			// colorset
			applicationType = "UNIT";
		}

		PlaceNode chan = Create.createPlace("chan_inboundChannelAdapter",
				applicationType, "", pa);
		PlaceNode app = Create.createPlace("app_inboundChannelAdapter",
				messageSystemType, "", pa);
		TransitionNode com = Create.createTransition(
				"com_inboundChannelAdapter", pa);


		String x = Create.createInternVar("x", applicationType, pn);
		String dataFunktion = ((ChannelAdapter) pat).getFunktion();
		String pfeilBeschriftungFunktion = "";

		if (dataFunktion != null && !dataFunktion.isEmpty()) {
			pfeilBeschriftungFunktion = dataFunktion + " " + x;
		} else
			pfeilBeschriftungFunktion = x;

		try {
			Create.createArc("ptot", com.getId(), chan.getId(),
					x, pa);
			Create.createArc("ttop", com.getId(), app.getId(),
					pfeilBeschriftungFunktion, pa);
		} catch (Exception e1) {
			e1.printStackTrace();

		}
		
		Create.setPosition(app, pat.getX() - 200, pat.getY());
		Create.setPosition(com, pat.getX(), pat.getY());
		Create.setPosition(chan, pat.getX() + 200, pat.getY());

		Exporter.eingangspunkte.add(patternid + "_p_" + chan.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + app.getId());
	}

}
