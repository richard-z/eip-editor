package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;

public class MessagingBridge {
	
	public static void create(PetriNet pn, Page pa, int patternid, bibliothek.MessagingBridge pat) {
		
		PlaceNode chan1 = Create.createPlace("chan", pa);
		PlaceNode chan2 = Create.createPlace("chan", pa);
		PlaceNode chan3 = Create.createPlace("chan", pa);
		PlaceNode chan4 = Create.createPlace("chan", pa);
		
		TransitionNode one_to2 = Create.createTransition("1-to-2", pa);
		TransitionNode two_to1 = Create.createTransition("2-to-1", pa);
		
		String x = Create.createInternVar("x", "UNIT", pn);
		
		String enc1=pat.getEnc1();
		String enc2=pat.getEnc2();
		String dec1=pat.getDec1();
		String dec2=pat.getDec2();
				
		try {	
				Create.createArc("ptot", one_to2.getId(), chan1.getId(), x, pa);
				Create.createArc("ttop", one_to2.getId(), chan2.getId(), enc2+dec1, pa);
				Create.createArc("ptot", two_to1.getId(), chan4.getId(), x, pa);
				Create.createArc("ttop", two_to1.getId(), chan3.getId(), enc1+dec2, pa);

		} catch (Exception e1) {
					e1.printStackTrace();
				}
		
		// Zuordnung problematisch, koennte fehlerhaft realisiert werden
		Exporter.eingangspunkte.add(patternid + "_p_" + chan1.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + chan3.getId());
		Exporter.eingangspunkte.add(patternid + "_p_" + chan4.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + chan2.getId());
	}

}
