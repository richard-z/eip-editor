/**
 * 
 */
package bibliothek;

import java.io.Serializable;

/**
 * @author dudarobe
 *
 */
public class ChannelAdapter extends MessageChannel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static String bild = "Ich bin ein ChannelAdapter";
	
	private String funktion;
	private String applicationType;
	int variante;
	
	/**
	 * 
	 */
	public ChannelAdapter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param label
	 * @param x
	 * @param y
	 */
	public ChannelAdapter(String label, int x, int y) {
		super(label, x, y);
		// TODO Auto-generated constructor stub
	}
	
	public String getFunktion() {
		return funktion;
	}

	public void setFunktion(String funktion) {
		this.funktion = funktion;
	}
	
	/**
	 * @return 1 inbound, 2 = outbound
	 */
	public int getVariante(){
		return variante;
	}
	
	/**
	 * @param 1 inbound, 2 = outbound
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

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	
}
