/**
 * 
 */
package bibliothek;

/**
 * @author dudarobe
 *
 */
public class PointToPointChannel extends MessageChannel {

	static String bild = "Ich bin ein PointToPoint-Bild";
	
	private int kapazitaet = -1;//nur relevant bei abstracted 3
	
	int variante;//fifo 1, out of order 2, abstracted 3
	
	private String ins;
	private String tl;
	
	/**
	 * 
	 */
	public PointToPointChannel() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param label
	 * @param x
	 * @param y
	 */
	public PointToPointChannel(String label, int x, int y) {
		super(label, x, y);
		variante = 0;
	}
	
	/**
	 * nur relevant bei abstracted 3
	 */
	public int getKapazitaet() {
		return kapazitaet;
	}

	/**
	 * nur relevant bei abstracted 3
	 */
	public void setKapazitaet(int kapazitaet) {
		this.kapazitaet = kapazitaet;
	}
	
	/**
	 * @return fifo 1, out of order 2, abstracted 3
	 */
	public int getVariante(){
		return variante;
	}
	
	/**
	 * @param fifo 1, out of order 2, abstracted 3
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
	
	public String getIns() {
		return ins;
	}

	public void setIns(String ins) {
		this.ins = ins;
	}
	
	public String getTL() {
		return tl;
	}

	public void setTl(String tl) {
		this.tl = tl;
	}
}
