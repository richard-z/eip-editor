package export.pattern;

import java.util.LinkedList;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;


public class Splitter {
	
	public static void create(PetriNet pn, Page pa, int patternid, bibliothek.Splitter pat){
		
		int var = pat.getVariante();
		
		switch(var){
		case 1: iterative(pn, pa, patternid, pat);break;
		case 2: Static(pn, pa, patternid, pat);break;
		default: return;
		}
		
	}
	
	private static void iterative(PetriNet pn, Page pa, int patternid, bibliothek.Splitter pat){
		
		PlaceNode in = Create.createPlace("pipe", pa);
		PlaceNode out = Create.createPlace("pipe", pa);
		PlaceNode middle = Create.createPlace("", pa);
		
		String x = Create.createInternVar("x", "UNIT", pn);
		TransitionNode forward = Create.createTransition("", pa);
		TransitionNode split = Create.createTransition("split", "["+pat.getKonditionen().poll()+"]", pa);
		TransitionNode sendLast = Create.createTransition("send Last", "["+pat.getKonditionen().poll()+"]", pa);
		
		
		
		try {
			Create.createArc("ptot", forward.getId(), in.getId(), x,pa);
			Create.createArc("ptot", split.getId(), middle.getId(), x,pa);
			Create.createArc("ptot", sendLast.getId(), middle.getId(), x,pa);
			Create.createArc("ttop", forward.getId(), middle.getId(), x, pa);
			Create.createArc("ttop", split.getId(), middle.getId(), pat.getRemainsofFunktion(), pa);
			Create.createArc("ttop", split.getId(), out.getId(), pat.getPartofFunktion(), pa);
			Create.createArc("ttop", sendLast.getId(), out.getId(), x, pa);
		} catch (Exception e1) {
			e1.printStackTrace();

		}
		
		Exporter.eingangspunkte.add(patternid + "_p_" + in.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + out.getId());
		
	}
	
	private static void Static(PetriNet pn, Page pa, int patternid, bibliothek.Splitter pat){
		
		int ausgaenge = pat.getAusgaengeList().size();
		
		PlaceNode in = Create.createPlace("data", pa);
		PlaceNode[] output_places = new PlaceNode[ausgaenge];
		
		TransitionNode split = Create.createTransition("split", pa);
		
		String x = Create.createInternVar("x", "UNIT", pn);
		
		try{
			Create.createArc("ptot", split.getId(), in.getId(), x, pa);
			
		} catch(Exception e1){
			e1.printStackTrace();
		}
		
		Exporter.eingangspunkte.add(patternid + "_p_" + in.getId());
		LinkedList<String> funcs=pat.getPart();
		
		for (int i=0; i<ausgaenge; i++){
			
			output_places[i] = Create.createPlace("data", pa);
			String part=funcs.poll();
			try{
				Create.createArc("ttop", split.getId(), output_places[i].getId(), part, pa);
			} catch(Exception e1){
				e1.printStackTrace();
			}
			
			Exporter.ausgangspunkte.add(patternid + "_p_" + output_places[i].getId());
			
		}
		
	}

}
