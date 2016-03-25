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

public class OutboundChannelAdaptder {

	public static void create(PetriNet pn, Page pa, int patternid, Pattern pat,
			Modell model) throws Exception {

		String messageSystemType = ((ChannelAdapter) pat).getColorSet();
		if (messageSystemType == null || messageSystemType.isEmpty()) { // default
																		// colorset
			messageSystemType = "UNIT";
		}
		String applicationType = ((ChannelAdapter) pat).getApplicationType();
		if (applicationType == null || applicationType.isEmpty()) { // default
			// colorset
			applicationType = "UNIT";
		}
		PlaceNode chan = Create.createPlace("chan_outboundChannelAdapter",
				messageSystemType, "", pa);
		PlaceNode app = Create.createPlace("app_outboundChannelAdapter",
				applicationType, "", pa);

		TransitionNode com = Create.createTransition(
				"com_outboundChannelAdapter", pa);


		String x = Create.createInternVar("x", applicationType, pn);
		String msgFunktion = ((ChannelAdapter) pat).getFunktion();
		String pfeilBeschriftungFunktion = "";

		if (msgFunktion != null && !msgFunktion.isEmpty()) {
			pfeilBeschriftungFunktion = msgFunktion + " " + x;
		} else
			pfeilBeschriftungFunktion = x;

		try {
			Create.createArc("ptot", com.getId(), app.getId(),
					x, pa);
			Create.createArc("ttop", com.getId(), chan.getId(),
					pfeilBeschriftungFunktion, pa);
		} catch (Exception e1) {
			e1.printStackTrace();

		}
		
		Create.setPosition(app, pat.getX() - 100, pat.getY());
		Create.setPosition(com, pat.getX(), pat.getY());
		Create.setPosition(chan, pat.getX() + 100, pat.getY());

		Exporter.eingangspunkte.add(patternid + "_p_" + app.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + chan.getId());
	}

}