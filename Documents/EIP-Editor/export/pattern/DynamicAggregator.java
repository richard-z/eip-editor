package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.Aggregator;
import bibliothek.Modell;
import bibliothek.Pattern;

public class DynamicAggregator {
	public static void create(PetriNet pn, Page pa, int patternid, Pattern pat,
			Modell modell) throws Exception {

		String incomingMessageType = ((Aggregator) pat).getIncomingMessageTypes().pollFirst();
		if (incomingMessageType == null || incomingMessageType.isEmpty()) { // default colorset
			incomingMessageType = "UNIT";
		}

		String aggregationType = ((Aggregator) pat).getAggregationType();
		if (aggregationType == null || aggregationType.isEmpty()) { // default colorset
			aggregationType = "UNIT";
		}
		
		PlaceNode eingangPipe = Create.createPlace("Eingangspipe_DynamicAggregator", incomingMessageType, "", pa);
		PlaceNode ausgangPipe = Create.createPlace("Ausgangspipe_DynamicAggregator", aggregationType, "", pa);
		PlaceNode data = Create.createPlace("data_DynamicAggregator", aggregationType, "", pa);

		
		String x = Create.createInternVar("x", incomingMessageType, pn);
		String y = Create.createInternVar("y", aggregationType, pn);

		String aggrKondition = ((bibliothek.Aggregator) pat).getAggregate().pollFirst();
		String firstKondition = ((bibliothek.Aggregator) pat).getFirst().pollFirst(); 
		String isCompleteKondition = ((bibliothek.Aggregator) pat).getIscomplete().pollFirst(); 
		
		if(aggrKondition != null && !aggrKondition.isEmpty()) {
			aggrKondition = aggrKondition.replace("x", x);
		}
		
		if(firstKondition != null && !firstKondition.isEmpty()) {
			firstKondition = firstKondition.replace("x", x);
		}
		
		if(isCompleteKondition != null && !isCompleteKondition.isEmpty()) {
			isCompleteKondition = isCompleteKondition.replace("y", y);
		}
		
		TransitionNode aggr = Create
				.createTransition("aggr._DynamicAggregator", "["
						+ aggrKondition
						+ "]", pa);
		TransitionNode first = Create.createTransition("first_DynamicAggregator", "["
				+ firstKondition + "]", pa);
		TransitionNode test = Create.createTransition("test_DynamicAggregator", "["
				+isCompleteKondition  + "]",
				pa);

		String FunktionF = ((bibliothek.Aggregator) pat).getFFunktion();
		String FunktionUpdate = ((bibliothek.Aggregator) pat)
				.getUpdateFunktion();
		
		String FunktionFPfeilbeschriftung = "";
		String FunktionUpdatePfeilbeschriftung = "";

		if (FunktionF != null && !FunktionF.isEmpty()) {
			FunktionFPfeilbeschriftung = FunktionF + " " + x;
		}
		if (FunktionUpdate != null && !FunktionUpdate.isEmpty()) {
			FunktionUpdatePfeilbeschriftung = FunktionUpdate + " (" + y + ", " + x + ")";
		}

		try {
			Create.createArc("ptot", first.getId(), eingangPipe.getId(), x, pa);
			Create.createArc("ptot", aggr.getId(), eingangPipe.getId(), x, pa);
			Create.createArc("ttop", first.getId(), data.getId(),
					FunktionFPfeilbeschriftung, pa);
			Create.createArc("ptot", aggr.getId(), data.getId(), y, pa);
			Create.createArc("ttop", aggr.getId(), data.getId(),
					FunktionUpdatePfeilbeschriftung, pa);
			Create.createArc("ptot", test.getId(), data.getId(), y, pa);
			Create.createArc("ttop", test.getId(), ausgangPipe.getId(), y, pa);
		} catch (Exception e1) {
			e1.printStackTrace();

		}

		Create.setPosition(eingangPipe, pat.getX(), pat.getY());
		Create.setPosition(first, pat.getX() + 100, pat.getY());
		Create.setPosition(data, pat.getX() + 200, pat.getY());
		Create.setPosition(test, pat.getX() + 300, pat.getY());
		Create.setPosition(ausgangPipe, pat.getX() + 400, pat.getY());
		Create.setPosition(aggr, pat.getX() + 100, pat.getY() - 100);

		Exporter.eingangspunkte.add(patternid + "_p_" + eingangPipe.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + ausgangPipe.getId());
	}
}
