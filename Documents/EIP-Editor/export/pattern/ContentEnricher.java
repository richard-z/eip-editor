package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;

public class ContentEnricher {
public static void create(PetriNet pn, Page pa, int patternid, bibliothek.Pattern pat) {
		
		PlaceNode eingangPipe = Create.createPlace("pipe", pa);
		PlaceNode ausgangPipe = Create.createPlace("pipe", pa);
		PlaceNode place = Create.createPlace("","Type1","",pa); //TODO: Typ aus pattern entnehmen
		
		
		TransitionNode trans1 = Create.createTransition("1-to-2", pa);
		TransitionNode trans2 = Create.createTransition("2-to-1", pa);
				
		try {
				Create.createArc("ptot", trans1.getId(), eingangPipe.getId(), "x", pa);
				Create.createArc("ttop", trans1.getId(), place.getId(), "x", pa);
				Create.createArc("ptot", trans2.getId(), place.getId(), "x", pa);
				Create.createArc("ttop", trans2.getId(), ausgangPipe.getId(), ((bibliothek.ContentEnricher) pat).getFilterFunktion() + " x z", pa);

		} catch (Exception e1) {
					e1.printStackTrace();
				}
		
		Exporter.eingangspunkte.add(patternid + "_p_" + eingangPipe.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + ausgangPipe.getId());
}
}
