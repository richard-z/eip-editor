package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;

public class MessageDispatcher {
	
	public static void create(PetriNet pn, Page pa, int patternid, int trans){
		
		PlaceNode input_place = Create.createPlace("pipe", pa);
		
		PlaceNode[] output_places = new PlaceNode[trans];
		TransitionNode[] transis = new TransitionNode[trans];
		
		Exporter.eingangspunkte.add(patternid + "_p_" + input_place.getId());
		
		String x = Create.createInternVar("x", "UNIT", pn);
		
		// TODO: Conditions (aus Pattern) beim Erzeugen der Transitionen direkt mit dranschreiben	(waren im pattern noch nicht vorhanden)
		// (dafuer muss das Pattern uebergeben werden)
		for (int i=0; i<trans; i++){
			String cond = "cond"+i+" "+x;
			transis[i]= Create.createTransition("dispatch", cond, pa);
			output_places[i] = Create.createPlace("pipe", pa);
			try {
				Create.createArc("ptot", transis[i].getId(), input_place.getId(), x, pa);
				Create.createArc("ttop", transis[i].getId(), output_places[i].getId(), x, pa);

		} catch (Exception e1) {
					e1.printStackTrace();
				}
			
			Exporter.ausgangspunkte.add(patternid + "_p_" + output_places[i].getId());
		}
	}

}
