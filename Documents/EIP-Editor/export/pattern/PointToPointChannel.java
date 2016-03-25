package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.Modell;

public class PointToPointChannel {
	
	public static void create(PetriNet pn, Page pa, int patternid, Modell model, bibliothek.PointToPointChannel pat) {
		
		switch(pat.getVariante()){
		case 1: fifo(pn, pa, patternid, pat.getKapazitaet(), model, pat);return;
		case 2: outOfOrder(pn, pa, patternid, pat.getKapazitaet(), model);return;
		case 3: abstracted(pn, pa, patternid, pat.getKapazitaet());return;
		default:return;
		}
	}
	
	private static void fifo(PetriNet pn, Page pa, int patternid, int capacity, Modell model, bibliothek.PointToPointChannel pat){
		
		String Fifo=Exporter.getEntryColset(model, patternid).poll();
		
		if(Fifo == null) { //default colorset
			Fifo = "UNIT";
		}
		
		PlaceNode p2pchan = Create.createPlace("p2p\nchan", Fifo, "", pa);

		String cond="";
		if (capacity>-1) cond="[Laenge(list)<"+capacity+"]";	// oder die Condition aus pattern holen? (muesste noch angelegt werden)
		Create.createTransition("push", cond, pa);
		TransitionNode push = Create.createTransition("push", pa);
		TransitionNode pop = Create.createTransition("pop", pa);

		String x = Create.createInternVar("x", Fifo, pn);

		try {
			String ins=pat.getIns();
			Create.createArc("ttop", push.getId(), p2pchan.getId(), ins,pa);
			// push -> p2pchan
			Create.createArc("ptot", push.getId(), p2pchan.getId(), x, pa);
			// push <- p2pchan
			Create.createArc("ptot", pop.getId(), p2pchan.getId(), x, pa);
			// p2pchan -> pop
			String tl=pat.getTL();
			Create.createArc("ttop", pop.getId(), p2pchan.getId(), tl, pa);
			// p2pchan <- pop

		} catch (Exception e1) {
			e1.printStackTrace();

		}
		Exporter.eingangspunkte.add(patternid + "_t_" + push.getId());
		Exporter.ausgangspunkte.add(patternid + "_t_" + pop.getId());
		
	}
	
	private static void outOfOrder(PetriNet pn, Page pa, int patternid, int capacity, Modell model){
		
		String Type = Exporter.getEntryColset(model, patternid).poll();
		
		if(Type == null) { //default colorset
			Type = "UNIT";
		}
		
		PlaceNode p2pchan = Create.createPlace("p2p\nchan", Type, "", pa);

		if (capacity>-1) {
		String cap =""+capacity;
		PlaceNode capacityP = Create.createPlace("capacity", "UNIT", cap, pa);
		TransitionNode in = Create.createTransition("", pa);
		TransitionNode out = Create.createTransition("", pa);
		
		try {
			Create.createArc("ttop", in.getId(), p2pchan.getId(), "", pa);
			Create.createArc("ptot", in.getId(), capacityP.getId(), "", pa);
			Create.createArc("ptot", out.getId(), p2pchan.getId(), "", pa);
			Create.createArc("ttop", out.getId(), capacityP.getId(), "", pa);
		} catch (Exception e1) {
			e1.printStackTrace();

		}
		
		Exporter.eingangspunkte.add(patternid + "_t_" + in.getId());
		Exporter.ausgangspunkte.add(patternid + "_t_" + out.getId());
		}
		
		else {
			Exporter.eingangspunkte.add(patternid + "_p_" + p2pchan.getId());
			Exporter.ausgangspunkte.add(patternid + "_p_" + p2pchan.getId());
		}
	}

	private static void abstracted(PetriNet pn, Page pa, int patternid, int capacity){
		
		PlaceNode p2pchan = Create.createPlace("p2p\nchan", "UNIT", "", pa);
		if (capacity>-1) {
		String cap =""+capacity;
		PlaceNode capacityP = Create.createPlace("capacity", "UNIT", cap, pa);
		TransitionNode in = Create.createTransition("", pa);
		TransitionNode out = Create.createTransition("", pa);
		
		try {
			Create.createArc("ttop", in.getId(), p2pchan.getId(), "", pa);
			Create.createArc("ptot", in.getId(), capacityP.getId(), "", pa);
			Create.createArc("ptot", out.getId(), p2pchan.getId(), "", pa);
			Create.createArc("ttop", out.getId(), capacityP.getId(), "", pa);
		} catch (Exception e1) {
			e1.printStackTrace();

		}
		
		Exporter.eingangspunkte.add(patternid + "_t_" + in.getId());
		Exporter.ausgangspunkte.add(patternid + "_t_" + out.getId());
		}
		
		else {
			Exporter.eingangspunkte.add(patternid + "_p_" + p2pchan.getId());
			Exporter.ausgangspunkte.add(patternid + "_p_" + p2pchan.getId());
		}
	
	}

}
