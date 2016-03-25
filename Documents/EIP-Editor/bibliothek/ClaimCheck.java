/**
 * 
 */
package bibliothek;

import java.io.Serializable;

/**
 * @author dudarobe
 *
 */
public class ClaimCheck extends MessageTranslator implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static String bild = "Ich bin ein ClaimCheck";
	
	private String key; // eine funktion wird vom translator vererbt
	private String incomingMessageTyp;
	private String filteredTyp;
	
	/**
	 * 
	 */
	public ClaimCheck() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param label
	 * @param x
	 * @param y
	 */
	public ClaimCheck(String label, int x, int y) {
		super(label, x, y);
		// TODO Auto-generated constructor stub
	}

	public String getKey() {
		return key;
	}

	public void setKey(String filterFunktion) {
		this.key = filterFunktion;
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

	public String getIncomingMessageTyp() {
		return incomingMessageTyp;
	}

	public void setIncomingMessageTyp(String incomingMessageTyp) {
		this.incomingMessageTyp = incomingMessageTyp;
	}

	public String getFilteredTyp() {
		return filteredTyp;
	}

	public void setFilteredTyp(String claimTyp) {
		this.filteredTyp = claimTyp;
	}

	
}
