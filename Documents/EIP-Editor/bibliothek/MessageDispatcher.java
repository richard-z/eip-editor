/**
 * 
 */
package bibliothek;

/**
 * @author dudarobe
 *
 */
public class MessageDispatcher extends Pattern {

	static String bild = "Ich bin ein MessageDispatcher";
	
	/**
	 * 
	 */
	public MessageDispatcher() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param label
	 * @param x
	 * @param y
	 */
	public MessageDispatcher(String label, int x, int y) {
		super(label, x, y);
		// TODO Auto-generated constructor stub
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
