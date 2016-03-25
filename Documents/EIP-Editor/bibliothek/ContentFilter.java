package bibliothek;

public class ContentFilter extends MessageTranslator {

	static String bild = "Ich bin ein ContentFilter-Bild";
	
	public ContentFilter() {
	}

	public ContentFilter(String label, int x, int y) {
		super(label,  x, y);
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

}
