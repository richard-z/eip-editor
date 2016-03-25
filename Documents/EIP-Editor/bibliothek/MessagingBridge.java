/**
 * 
 */
package bibliothek;

/**
 * @author dudarobe
 *
 */
public class MessagingBridge extends MessageChannel {
	
	static String bild = "Ich bin ein MessagingBridge";
	
	private String enc1;
	private String enc2;
	private String dec1;
	private String dec2;

	/**
	 * 
	 */
	public MessagingBridge() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param label
	 * @param x
	 * @param y
	 */
	public MessagingBridge(String label, int x, int y) {
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

	public String getEnc1() {
		return enc1;
	}

	public void setEnc1(String enc1) {
		this.enc1 = enc1;
	}

	public String getEnc2() {
		return enc2;
	}

	public void setEnc2(String enc2) {
		this.enc2 = enc2;
	}

	public String getDec1() {
		return dec1;
	}

	public void setDec1(String dec1) {
		this.dec1 = dec1;
	}

	public String getDec2() {
		return dec2;
	}

	public void setDec2(String dec2) {
		this.dec2 = dec2;
	}
}
