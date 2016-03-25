package bibliothek;

import java.io.Serializable;

/**
 * @author deppiscd
 *
 */
public class Message extends Pattern implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static String bild = "Ich bin eine Message";
	
	private String colorSet;
	private String initalMarking;
	
	public Message() {
		
	}

	/**
	 * @param label
	 * @param pos
	 */
	public Message(String label, int x, int y) {
		super(label, x, y);
	}

	
	/**
	 * @param pat Am Ausgang zu verknuepfendes Pattern
	 * @return	Bei Erfolg true, wenn Anzahl > max. Anzahl, dann false.
	 */
	public boolean addAusgang(Pattern pat) {
		return ausgaengeList.add(pat);
	}

	public String getColorSet() {
		return colorSet;
	}

	public void setColorSet(String colorSet) {
		this.colorSet = colorSet;
	}

	public String getInitalMarking() {
		return initalMarking;
	}

	public void setInitalMarking(String initalMarking) {
		this.initalMarking = initalMarking;
	}
}
