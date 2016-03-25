package bibliothek;

public class EndPoint extends Pattern {

	static String bild = "Ich bin ein EndPoint-Bild";
	
	private boolean inbound = true;
	
	private String encode; // nur bei outbound
	
	public EndPoint() {
		
	}

	public EndPoint(String label, int x, int y) {
		super(label, x, y);
	}

	public void setInbound(boolean keks){
		inbound = keks;
	}
	
	public boolean getInbound(){
		return inbound;
	}
	
	/**
	 * @param pat Am Eingang zu verknuepfendes Pattern
	 * @return	Bei Erfolg true, wenn Anzahl > max. Anzahl, dann false.
	 */
	public boolean addEingang(Pattern pat) {
		return eingaengeList.add(pat);
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}
	
}
