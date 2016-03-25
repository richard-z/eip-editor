package bibliothek;

import java.util.LinkedList;

public class MessageFilter extends MessageRouter {

	static String bild = "Ich bin ein Message-Filter-Bild";
	
	private LinkedList<String> konditionen2 = new LinkedList<String>();
	
	public MessageFilter() {
		super();
	}

	public MessageFilter(String label, int x, int y) {
		super(label, x, y);
	}

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
	
	public LinkedList<String> getKonditionen2() {
		return konditionen2;
	}
	
	/**
	 * 
	 * @param set Name der neuen Kondition.
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean addKondition2(String kond2){
		return konditionen2.add(kond2);
	}
	
	/**
	 * Entfernt eine Kondition.
	 * @param kond ist Vom Typ String, die zu entfernende Kondition
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean removeKondition2(String kond2){
		return konditionen2.remove(kond2);
	}
	
}
