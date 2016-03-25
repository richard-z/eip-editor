/**
 * 
 */
package bibliothek;

import java.util.LinkedList;

/**
 * @author dudarobe
 *
 */
public class Normalizer extends Pattern {

	static String bild = "Ich bin ein normalisierer";
	
	private LinkedList<String> funktionen = new LinkedList<String>();
	private LinkedList<String> konditionen = new LinkedList<String>();
	
	/**
	 * 
	 */
	public Normalizer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param label
	 * @param x
	 * @param y
	 */
	public Normalizer(String label, int x, int y) {
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


	public LinkedList<String> getFunktionen() {
		return funktionen;
	}
	
	/**
	 * 
	 * @param set Name der neuen Funktion 
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean addFunktion(String fkt){
		return funktionen.add(fkt);
	}
	
	/**
	 * Entfernt eine Funktion.
	 * @param fkt ist Vom Typ Funktion, die zu entfernende Funktion
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean removeFunktion(String fkt){
		return funktionen.remove(fkt);
	}
	
	public LinkedList<String> getKonditionen() {
		return konditionen;
	}
	
	/**
	 * 
	 * @param set Name der neuen Kondition.
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean addKondition(String kond){
		return konditionen.add(kond);
	}
	
	/**
	 * Entfernt eine Kondition.
	 * @param kond ist Vom Typ String, die zu entfernende Kondition
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean removeKondition(String kond){
		return konditionen.remove(kond);
	}
	
}
