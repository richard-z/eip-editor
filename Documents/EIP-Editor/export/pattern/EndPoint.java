package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.Modell;

public class EndPoint {	// TODO Unterscheidung inbound/outbound

	public static void create(PetriNet pn, Page pa, Modell model, int patternid, bibliothek.EndPoint pat, boolean inbound) {

		if (inbound) inbound(pn, pa, model, patternid);
		else outbound(pn, pa, model, patternid,pat);
	}
	
	private static void inbound(PetriNet pn, Page pa, Modell model, int patternid){
		
		
		
		String MessageType = Exporter.getEntryColset(model, patternid).poll();	
		if(MessageType == null) {//default colorset
			MessageType = "UNIT";
		}
		
		PlaceNode chan = Create.createPlace("chan", MessageType, "", pa);
		TransitionNode assign_recv = Create.createTransition("assign/recv", pa);
				
		String x = Create.createInternVar("x", MessageType, pn);

		try {
			Create.createArc("ptot", assign_recv.getId(), chan.getId(), x, pa);
			// chan -> assign/recv

		} catch (Exception e1) {
			e1.printStackTrace();

		}
				
		/*Exporter.eingangspunkte.add(patternid + "_t_" + assign_recv.getId());
		Exporter.ausgangspunkte.add(patternid + "_t_" + assign_recv.getId());*/
				
		// besser:
		Exporter.eingangspunkte.add(patternid + "_p_" + chan.getId());
	}
	
	private static void outbound(PetriNet pn, Page pa, Modell model, int patternid, bibliothek.EndPoint pat){
		
		String MessageType = Exporter.getExitColset(model, patternid).poll();
		
		if(MessageType == null) {
			MessageType = "UNIT";
		}
		
		PlaceNode chan = Create.createPlace("chan", MessageType, "", pa);
		TransitionNode assign_send = Create.createTransition("assign/send", pa);

		try {	// TODO: Funktion encode durch funktion aus pattern ersetzen
			String encode=pat.getEncode();
			Create.createArc("ttop", assign_send.getId(), chan.getId(), encode, pa);
			
		} catch (Exception e1) {
			e1.printStackTrace();

		}
		
		/*Exporter.eingangspunkte.add(patternid + "_t_" + assign_send.getId());
		Exporter.ausgangspunkte.add(patternid + "_t_" + assign_send.getId());*/
		
		// besser:
		Exporter.ausgangspunkte.add(patternid + "_p_" + chan.getId());
	}

}
