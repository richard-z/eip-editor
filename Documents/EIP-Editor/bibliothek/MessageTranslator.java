package bibliothek;

import java.io.Serializable;

public class MessageTranslator extends Pattern implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static String bild = "Ich bin ein Translator-Bild";
	
	private String funktion;
	private String untranslatedTyp;
	private String translatedTyp;
	
	public MessageTranslator() {
		super();
	}

	public MessageTranslator(String label, int x, int y) {
		super(label, x, y);
		
	}

	public String getFilterFunktion() {
		return funktion;
	}

	public void setFilterFunktion(String filterFunktion) {
		this.funktion = filterFunktion;
	}

	/**
	 * @param pat Am Eingang zu verknuepfendes Pattern
	 * @return	Bei Erfolg true, wenn Anzahl > max. Anzahl, dann false.
	 */
	public boolean addEingang(Pattern pat) {
		return eingaengeList.add(pat);
	}

	/**
	 * @param pat Am Ausgang zu verknuepfendes Pattern
	 * @return	Bei Erfolg true, wenn Anzahl > max. Anzahl, dann false.
	 */
	public boolean addAusgang(Pattern pat) {
		return ausgaengeList.add(pat);
	}

	public String getUntranslatedTyp() {
		return untranslatedTyp;
	}

	public void setUntranslatedTyp(String untranslatedTyp) {
		this.untranslatedTyp = untranslatedTyp;
	}

	public String getTranslatedTyp() {
		return translatedTyp;
	}

	public void setTranslatedTyp(String translatedTyp) {
		this.translatedTyp = translatedTyp;
	}
}
