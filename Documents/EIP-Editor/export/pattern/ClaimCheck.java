package export.pattern;

import java.util.LinkedList;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.Modell;
import bibliothek.Pattern;

public class ClaimCheck {
	public static void create(Modell modell, PetriNet pn, Page pa,
			int patternid, Pattern pat) {

		String incomingMessagetypeColorset = ((bibliothek.ClaimCheck) pat).getIncomingMessageTyp();
		if (incomingMessagetypeColorset == null) { // default colorset
			incomingMessagetypeColorset = "UNIT";
		}
		
		String filteredTypeColorset = ((bibliothek.ClaimCheck) pat).getFilteredTyp();
		if (filteredTypeColorset == null) { // default colorset
			filteredTypeColorset = "UNIT";
		}

		LinkedList<String> storeColorset = new LinkedList<String>();
		storeColorset.add("Key");
		storeColorset.add(incomingMessagetypeColorset);
		String nameOfColorsetStore = "claimcheckStore" + Create.getID();
		Create.createColsetProduct(nameOfColorsetStore, storeColorset, pn);
		
		LinkedList<String> claimColorset = new LinkedList<String>();
		claimColorset.add("Key");
		claimColorset.add(filteredTypeColorset);
		String nameOfColorsetClaim = "claimcheckClaim" + Create.getID();
		Create.createColsetProduct(nameOfColorsetClaim, claimColorset, pn);
		
		PlaceNode eingangPipe = Create
				.createPlace("eingangspipe_claimCheck", incomingMessagetypeColorset, "", pa);
		PlaceNode ausgangPipe = Create.createPlace("ausgangspipe_claimCheck", nameOfColorsetClaim, "", pa); 
		
		PlaceNode store = Create.createPlace("store_claimCheck", nameOfColorsetStore, "", pa); 

		TransitionNode trans = Create.createTransition("trans_claimCheck", pa);

		String pfeilBeschriftungKey = "";
		String pfeilBeschriftungKeyProject = "";

		String x = Create.createInternVar("x", "UNIT", pn);

		if (((bibliothek.ClaimCheck) pat).getKey() != null && ((bibliothek.ClaimCheck) pat).getKey() != "") {
			pfeilBeschriftungKey = "(" + ((bibliothek.ClaimCheck) pat).getKey() + " "
					+ x + "," + x + ")";
			if (((bibliothek.ClaimCheck) pat).getFilterFunktion() != null && ((bibliothek.ClaimCheck) pat).getFilterFunktion() != "") {
				pfeilBeschriftungKeyProject = "("
						+ ((bibliothek.ClaimCheck) pat).getKey() + " " + x
						+ ","
						+ ((bibliothek.ClaimCheck) pat).getFilterFunktion()
						+ " " + x + ")";
			}
		}

		try {
			Create.createArc("ptot", trans.getId(), eingangPipe.getId(), x,
					pa);
			Create.createArc("ttop", trans.getId(), store.getId(),
					pfeilBeschriftungKey, pa);
			Create.createArc("ttop", trans.getId(), ausgangPipe.getId(),
					pfeilBeschriftungKeyProject, pa);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Create.setPosition(eingangPipe, pat.getX(), pat.getY());
		Create.setPosition(trans, pat.getX() + 100, pat.getY());
		Create.setPosition(store, pat.getX() + 100, pat.getY() - 100);
		Create.setPosition(ausgangPipe, pat.getX() + 200, pat.getY());

		Exporter.eingangspunkte.add(patternid + "_p_" + eingangPipe.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + ausgangPipe.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + store.getId());
	}
}
