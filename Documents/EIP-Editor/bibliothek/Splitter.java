/**
 * 
 */
package bibliothek;

import java.util.LinkedList;

/**
 * @author dudarobe
 *
 */
public class Splitter extends MessageRouter {
	
	static String bild = "Ich bin ein splitter";
	
	private String partof;
	private String remainsof;
	int variante; //iterative 1, static 2
	
	private LinkedList<String> part = new LinkedList<String>(); //nur bei static
	
	/**
	 * 
	 */
	public Splitter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param label
	 * @param x
	 * @param y
	 */
	public Splitter(String label, int x, int y) {
		super(label, x, y);
		variante = 0;
	}
	
	public String getPartofFunktion() {
		return partof;
	}

	public void setPartofFunktion(String partof) {
		this.partof = partof;
	}
	
	public String getRemainsofFunktion() {
		return remainsof;
	}

	public void setRemainsofFunktion(String remainsof) {
		this.remainsof = remainsof;
	}
	
	/**
	 * @return iterative 1, static 2
	 */
	public int getVariante(){
		return variante;
	}
	
	/**
	 * @param iterative 1, static 2
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
	/*
	 * nur bei variante  static relevant
	 */
	public LinkedList<String> getPart() {
		return part;
	}
	
	/**
	 * 
	 * @param set Name der neuen Funktion.
	 * @return true bei Erfolg, sonst false.
	 * nur bei variante  static relevant
	 */
	public boolean addPart(String par){
		return part.add(par);
	}
	
	/**
	 * Entfernt eine Funktion.
	 * @param par ist Vom Typ Funktion, die zu entfernende Funktion
	 * @return true bei Erfolg, sonst false.
	 * nur bei variante  static relevant
	 */
	public boolean removePart(String par){
		return part.remove(par);
	}
	
}
