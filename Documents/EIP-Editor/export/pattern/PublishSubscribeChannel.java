package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.Modell;

public class PublishSubscribeChannel {
	
	public static void create(PetriNet pn, Page pa, int patternid, Modell model, bibliothek.PublishSubscribeChannel pat){
		
		String entryExitColset=Exporter.getEntryColset(model, patternid).poll();
		
		if(entryExitColset == null) { //default colorset
			entryExitColset = "UNIT";
		}
		
		PlaceNode pub = Create.createPlace("pub", entryExitColset, "", pa);
		TransitionNode forward = Create.createTransition("", pa);
		
		String x = Create.createInternVar("x", entryExitColset, pn);
		try {
			Create.createArc("ptot", forward.getId(), pub.getId(), x, pa);
		} catch (Exception e1) {
				e1.printStackTrace();
			}
		
		Exporter.eingangspunkte.add(patternid + "_p_" + pub.getId());
		
		int ausgaenge = pat.getAusgaengeList().size();
		PlaceNode[] output_places = new PlaceNode[ausgaenge];
		
		for (int i=0; i<ausgaenge; i++){
			
			output_places[i] = Create.createPlace("sub"+i, entryExitColset, "", pa);
			try {
				Create.createArc("ttop", forward.getId(), output_places[i].getId(), x, pa);

			} catch (Exception e1) {
					e1.printStackTrace();
				}
			
			Exporter.ausgangspunkte.add(patternid + "_p_" + output_places[i].getId());

			}
		
	}

}
