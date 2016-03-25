package bibliothek;

import java.io.Serializable;

public class MessageChannel extends Pattern implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static String bild = "Ich bin ein MessageChannel-Bild";
	
	String colorSet;
	
	public MessageChannel() {
		super();
	}
	
	public MessageChannel(String label, int x, int y) {
		super(label, x, y);

	}

	public String getColorSet() {
		return colorSet;
	}

	public void setColorSet(String colorSet) {
		this.colorSet = colorSet;
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
