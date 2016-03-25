package export.pattern;

import java.util.LinkedList;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.Modell;

public class RecipientList {
		
		public static void create(PetriNet pn, Page pa, int patternid, Modell model, bibliothek.MessageRouter pat, int ausgaenge) {

			String Type = Exporter.getEntryColset(model, patternid).poll();
			
			if(Type == null) { //default colorset
				Type = "UNIT";
			}
			
			PlaceNode input_place = Create.createPlace("pipe", Type, "", pa);
			PlaceNode addr_list = Create.createPlace("addr_list", "UNIT", "", pa);	// TODO: UNIT durch Colorset SL aus pattern ersetzen
			// oder erstellen WIR das colorset SL und das Produkt??
			
			TransitionNode interact = Create.createTransition("", pa);
			
			PlaceNode[] output_places = new PlaceNode[ausgaenge];
			PlaceNode[] middle_places = new PlaceNode[ausgaenge];
			TransitionNode[] forward = new TransitionNode[ausgaenge];
			TransitionNode[] stop = new TransitionNode[ausgaenge];
			
			Exporter.eingangspunkte.add(patternid + "_p_" + input_place.getId());
			
			String x = Create.createInternVar("x", Type, pn);
			String l = Create.createInternVar("l", "UNIT", pn);		// TODO: UNIT durch Colorset SL aus pattern ersetzen
					
			try {
				Create.createArc("ptot", interact.getId(), input_place.getId(), x, pa);
				Create.createArc("bothdir", interact.getId(), addr_list.getId(), l, pa);

			} catch (Exception e1) {
					e1.printStackTrace();
				}
			
			LinkedList<String> conds = pat.getKonditionen();
			
			for (int i=0; i<ausgaenge; i++){
			
//			String cond_1 = "[inList l 'pipe"+i+"']";
//			String cond_0 = "[not inList l 'pipe"+i+"']";
			forward[i]= Create.createTransition("", conds.poll(), pa);	// cond_1
			stop[i]= Create.createTransition("", conds.poll(), pa);		// cond_0
			middle_places[i] = Create.createPlace("", "UNIT", "", pa);	// TODO: UNIT durch colorset Product aus pattern ersetzen
			output_places[i] = Create.createPlace("pipe"+i, Type, "", pa);
			try {
				Create.createArc("ptot", forward[i].getId(), middle_places[i].getId(), "("+x+","+l+")", pa);
				Create.createArc("ptot", stop[i].getId(), middle_places[i].getId(), "("+x+","+l+")", pa);
				Create.createArc("ttop", interact.getId(), middle_places[i].getId(), "("+x+","+l+")", pa);
				Create.createArc("ttop", forward[i].getId(), output_places[i].getId(), x, pa);

			} catch (Exception e1) {
					e1.printStackTrace();
				}
			
			Exporter.ausgangspunkte.add(patternid + "_p_" + output_places[i].getId());

			}
		}

}
