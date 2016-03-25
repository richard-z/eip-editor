package export.pattern;

import java.util.LinkedList;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.Message;
import bibliothek.Modell;


public class MessageRouter {

	public static void create(Modell model, PetriNet pn, Page pa, int patternid, bibliothek.MessageRouter pat) {

		int ausgaenge = pat.getAusgaengeList().size();
		
		switch(pat.getVariante()){
		case 1: ;
		case 2: ContentBasedRouter.create(model, pat, pn, pa, patternid, ausgaenge, pat.getKonditionen());return;
		case 3: DynamicRouter.create(model, pn, pa, patternid, ausgaenge,pat.getKonditionen());return;
		case 4: RecipientList.create(pn, pa, patternid, model, pat, ausgaenge);return;
		default:;
		}
		
		String messageType = pat.getFilterFunktion();
		if(messageType == null || messageType.isEmpty()) {
			messageType = "UNIT";
		}
		PlaceNode input_place = Create.createPlace("eingangspipe_messageRouter", messageType,"" , pa);
		
		Create.setPosition(input_place, pat.getX(), pat.getY());
		
		
		PlaceNode[] output_places = new PlaceNode[ausgaenge];
		TransitionNode[] transis = new TransitionNode[ausgaenge];
		
		Exporter.eingangspunkte.add(patternid + "_p_" + input_place.getId());
		
		
		String x = Create.createInternVar("x", messageType, pn);
		
		LinkedList<String> conds = pat.getKonditionen();
		
		
		for (int i=0; i<ausgaenge; i++){
			
			String conditionTemp = conds.poll();
			if(conditionTemp == null) {
				conditionTemp = "";
			}
			
			conditionTemp = conditionTemp.replace("x", x);
			
			String cond = "["+conditionTemp+"]";
			transis[i]= Create.createTransition("routingTransition_messageRouter", cond, pa);
			output_places[i] = Create.createPlace("ausgangspipe_messageRouter", messageType, "", pa);
			
			Create.setPosition(transis[i], pat.getX() + 200, pat.getY() + 200 - 100*i);
			Create.setPosition(output_places[i], pat.getX() + 300, pat.getY()+ 200 - 100*i);
			
			try {
				Create.createArc("ptot", transis[i].getId(), input_place.getId(), x, pa);
				Create.createArc("ttop", transis[i].getId(), output_places[i].getId(), x, pa);
	
			} catch (Exception e1) {
					e1.printStackTrace();
				}
			
			Exporter.ausgangspunkte.add("MessageRouter_" + patternid + "_p_" + output_places[i].getId());

		}
	}

}
