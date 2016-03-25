/**
 * 
 */
package bibliothek;

/**
 * @author dudarobe
 *
 */
public class ServiceActivator extends EndPoint {

	static String bild = "Ich bin ein activatordingenz";
	
	int variante;
	
	/**
	 * 
	 */
	public ServiceActivator() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param label
	 * @param x
	 * @param y
	 */
	public ServiceActivator(String label, int x, int y) {
		super(label, x, y);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return default EventDrivenConsumer 1, PollingConsumer 2 
	 */
	public int getVariante(){
		return variante;
	}
	
	/**
	 * @param default EventDrivenConsumer 1, PollingConsumer 2 
	 */
	public void setVariante(int var){
		variante = var;
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
