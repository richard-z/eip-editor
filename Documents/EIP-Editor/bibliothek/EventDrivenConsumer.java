/**
 * 
 */
package bibliothek;

/**
 * @author dudarobe
 *
 */
public class EventDrivenConsumer extends EndPoint {

	static String bild = "Ich bin ein eventdingenz";
	
	private String funktion;
	
	/**
	 * 
	 */
	public EventDrivenConsumer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param label
	 * @param x
	 * @param y
	 */
	public EventDrivenConsumer(String label, int x, int y) {
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
	
	public String getFunktion() {
		return funktion;
	}

	public void setFunktion(String funktion) {
		this.funktion = funktion;
	}
	
}
