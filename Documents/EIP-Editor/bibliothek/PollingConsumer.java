/**
 * 
 */
package bibliothek;

/**
 * @author dudarobe
 *
 */
public class PollingConsumer extends EndPoint {

	static String bild = "Ich bin ein pollingdingenz";
	
	/**
	 * 
	 */
	public PollingConsumer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param label
	 * @param x
	 * @param y
	 */
	public PollingConsumer(String label, int x, int y) {
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
	
}
