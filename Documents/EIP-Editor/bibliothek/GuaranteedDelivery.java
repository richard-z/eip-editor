/**
 * 
 */
package bibliothek;


/**
 * @author dudarobe
 *
 */
public class GuaranteedDelivery extends MessageChannel {

	static String bild = "Ich bin ein GuaranteedDel-Bild";
	
	int variante; // 1 =lossy,2 = abstracted lossy
	
	/**
	 * 
	 */
	public GuaranteedDelivery() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param label
	 * @param x
	 * @param y
	 */
	public GuaranteedDelivery(String label, int x, int y) {
		super(label, x, y);
		variante = 0;
	}
	
	/**
	 * @return 1 = lossy, 2 = abstracted lossy
	 */
	public int getVariante(){
		return variante;
	}
	
	/**
	 * @param 1 = lossy, 2 = abstracted lossy
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
