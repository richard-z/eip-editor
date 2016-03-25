package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.Modell;
import bibliothek.Pattern;

public class MessageTranslator {

	public static void create(Modell model, Pattern pat, PetriNet pn, Page pa, int patternid, String funktion) {

		String untranslatedTyp = ((bibliothek.MessageTranslator) pat).getUntranslatedTyp();
		String translatedTyp = ((bibliothek.MessageTranslator) pat).getTranslatedTyp();
		
		if(untranslatedTyp == null || untranslatedTyp.isEmpty()) { //default colorset
			untranslatedTyp = "UNIT";
		}
		if(translatedTyp == null || translatedTyp.isEmpty()) { //default colorset
			translatedTyp = "UNIT";
		}
		
		PlaceNode eingangsPipe = Create.createPlace("eingangspipe_messageTranslator", untranslatedTyp, "", pa);
		PlaceNode ausgangspipe = Create.createPlace("ausgangspipe_messageTranslator", translatedTyp, "", pa);

		TransitionNode translator = Create.createTransition("translator_messageTranslator", pa);

		String x = Create.createInternVar("x", untranslatedTyp, pn);

		Create.setPosition(eingangsPipe, pat.getX(), pat.getY());
		Create.setPosition(ausgangspipe, pat.getX() + 200, pat.getY());
		Create.setPosition(translator, pat.getX() + 100, pat.getY());
		
		String pfeilBeschriftungFunktion = "";
		
		if(funktion != null && !funktion.isEmpty()) {
			pfeilBeschriftungFunktion = funktion + " " + x;
		}
		
		try {
			Create.createArc("ptot", translator.getId(), eingangsPipe.getId(), x, pa);
			Create.createArc("ttop", translator.getId(), ausgangspipe.getId(), pfeilBeschriftungFunktion ,pa);

		} catch (Exception e1) {
			e1.printStackTrace();

		}
		
		Exporter.eingangspunkte.add(patternid + "_p_" + eingangsPipe.getId());
		Exporter.ausgangspunkte.add(patternid + "_p_" + ausgangspipe.getId());

	}

}
