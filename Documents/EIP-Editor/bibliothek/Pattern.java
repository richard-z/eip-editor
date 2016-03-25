package bibliothek;
import java.util.LinkedList;


/**
 * @author deppiscd
 *
 */
public class Pattern {

	static String bild = "Hallo ich bin ein Bild";
//------------------------------------------------------------
	private String label;
	protected LinkedList<Pattern> eingaengeList = new LinkedList<Pattern>();		//wie auslesen?	//private->protected
	protected LinkedList<Pattern> ausgaengeList = new LinkedList<Pattern>();
	private int x;
	private int y;
	private int id;
//------------------------------------------------------------
	public Pattern() {
		id = -1;
		label = "";
		x = 0;
		y = 0;
	}
	/**
	 * @param String label
	 * @param	Position als x und y-Koordinate
	 */
	public Pattern(String label, int x, int y) {
		id = -1;
		this.label = label;
		this.x = x;
		this.y = y;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public LinkedList<Pattern> getEingaengeList() {
		return eingaengeList;
	}

	public LinkedList<Pattern> getAusgaengeList() {
		return ausgaengeList;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * @param pat Am Eingang zu verknuepfendes Pattern
	 * @return	Bei Erfolg true, wenn Anzahl > max. Anzahl, dann false.
	 */
	public boolean addEingang(Pattern pat) {
		return eingaengeList.add(pat);
	}
	/**
	 * @param pat zu entfernendes Pattern
	 * @return	Bei Erfolg true, sonst false
	 */
	public boolean removeEingang(Pattern pat) {
		return eingaengeList.remove(pat);
	}
	
	/**
	 * @param pat Am Ausgang zu verknuepfendes Pattern
	 * @return	Bei Erfolg true, wenn Anzahl > max. Anzahl, dann false.
	 */
	public boolean addAusgang(Pattern pat) {
		return ausgaengeList.add(pat);
	}
	
	/**
	 * @param pat zu entfernendes Pattern
	 * @return	Bei Erfolg true, sonst false
	 */
	public boolean removeAusgang(Pattern pat) {
		return ausgaengeList.remove(pat);
	}
	
	/*
	 * Nur zu Testzwecken, gibt Ausgaenge aus.
	 */
	public void zeigeAusgaenge() {
		for(Pattern pat: ausgaengeList) {
			System.out.println(pat.label);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
