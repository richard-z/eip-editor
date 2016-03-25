package export.pattern;

import java.util.LinkedList;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.Modell;

public class Normalizer {
	
	public static void create(PetriNet pn, Page pa, int patternid, Modell model, bibliothek.Normalizer pat) {

		int ausgaenge = pat.getAusgaengeList().size();
		
		PlaceNode input_place = Create.createPlace("", pa);
		
		PlaceNode[] output_places = new PlaceNode[ausgaenge];
		PlaceNode[] middle_places = new PlaceNode[ausgaenge];
		TransitionNode[] forward = new TransitionNode[ausgaenge];
		TransitionNode[] translate = new TransitionNode[ausgaenge];
		
		Exporter.eingangspunkte.add(patternid + "_p_" + input_place.getId());
		
		String x = Create.createInternVar("x", "UNIT", pn);
		String unique = Exporter.getExitColset(model, patternid).poll();	// NormalisierungsColorset
		
		if(unique == null) {
			unique = "UNIT";
		}
		
		LinkedList<String> conds=pat.getKonditionen();
		LinkedList<String> functions = pat.getFunktionen();
		for (int i=0; i<ausgaenge; i++){
		String cond=conds.poll();
		forward[i]= Create.createTransition("", cond, pa);
		translate[i]= Create.createTransition("translate", pa);
		String special = Exporter.getEntryColset(model, patternid).poll();	// Colorset i
		middle_places[i] = Create.createPlace("", special, "", pa);
		output_places[i] = Create.createPlace("", unique, "", pa);
		String xi = Create.createInternVar("x", special, pn);
		
		try {
			Create.createArc("ptot", forward[i].getId(), input_place.getId(), x, pa);
			Create.createArc("ttop", forward[i].getId(), middle_places[i].getId(), xi, pa);
			Create.createArc("ptot", translate[i].getId(), middle_places[i].getId(), xi, pa);
			Create.createArc("ttop", translate[i].getId(), output_places[i].getId(), functions.poll(), pa);

		} catch (Exception e1) {
				e1.printStackTrace();
			}
		
		Exporter.ausgangspunkte.add(patternid + "_p_" + output_places[i].getId());

		}
	}

}
