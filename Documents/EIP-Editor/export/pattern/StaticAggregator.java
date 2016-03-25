package export.pattern;

import java.util.LinkedList;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.Aggregator;
import bibliothek.Modell;
import bibliothek.Pattern;

public class StaticAggregator {
	public static void create(PetriNet pn, Page pa, int patternid, Pattern pat,
			Modell modell) throws Exception {

		String aggregationType = ((Aggregator) pat).getAggregationType();
		if(aggregationType == null || aggregationType.isEmpty()) {
			aggregationType = "UNIT";
		}
		
		PlaceNode ausgangPipe = Create.createPlace("ausgangspipe_staticAggregator", aggregationType, "", pa);
		TransitionNode aggr = Create.createTransition("aggr._staticAggregator", pa);

		Create.setPosition(ausgangPipe, pat.getX() + 200, pat.getY());
		Create.setPosition(aggr, pat.getX() + 100, pat.getY());

		String var = "";
		String allVars = "";
		
		LinkedList<String> allIncomingMessengesTypes =(LinkedList<String>) ((Aggregator) pat).getIncomingMessageTypes().clone();

		String Color;
		for (int i = 0; i < pat.getEingaengeList().size(); i++) {
			
			Color = allIncomingMessengesTypes.pollFirst();
			if(Color == null || Color.isEmpty()) {
				Color = "UNIT";
			}
			var = Create.createInternVar("x", Color, pn);
			PlaceNode pipe = Create.createPlace("eingangspipe_staticAggregator", Color, "", pa);
			Create.setPosition(pipe, pat.getX(), pat.getY() + 200 - 100 * i);
			Create.createArc("ptot", aggr.getId(), pipe.getId(), var, pa);

			allVars += var + ", ";
			Exporter.eingangspunkte.add("StaticAggregator_" + patternid + "_p_" + pipe.getId());
		}

		if (!allVars.equals("")) {
			allVars = allVars.substring(0, allVars.length() - 2);
		}
		
		String pfeilBeschriftungAggregateFunktion = ((bibliothek.Aggregator) pat).getFilterFunktion();
		
		if(pfeilBeschriftungAggregateFunktion != null && !pfeilBeschriftungAggregateFunktion.isEmpty()) {
			pfeilBeschriftungAggregateFunktion += " ("
					+ allVars + ")";
		}
		else {
			pfeilBeschriftungAggregateFunktion = "";
		}
		
		try {
			Create.createArc("ttop", aggr.getId(), ausgangPipe.getId(),
					 pfeilBeschriftungAggregateFunktion , pa);
		} catch (Exception e1) {
			e1.printStackTrace();

		}

		Exporter.ausgangspunkte.add(patternid + "_p_" + ausgangPipe.getId());
	}
}
