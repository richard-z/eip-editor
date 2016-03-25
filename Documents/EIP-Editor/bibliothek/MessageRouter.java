package bibliothek;

import java.io.Serializable;
import java.util.LinkedList;

public class MessageRouter extends MessageTranslator implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static String bild = "Ich bin ein Router-Bild";
	
	private LinkedList<String> konditionen = new LinkedList<String>();
	
	int variante;
	
	public MessageRouter() {
		super();
	}
	public MessageRouter(String label, int x, int y) {
		super(label,  x, y);
		variante = 0;
	}
	
	/**
	 * @return default 1, content-based 2, dynamic 3, , recipient list 4
	 */
	public int getVariante(){
		return variante;
	}
	
	/**
	 * @param default 1, content-based 2, dynamic 3, recipient list 4
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
	
	public LinkedList<String> getKonditionen() {
		return konditionen;
	}
	public void setKonditionen(LinkedList<String> newKonditionen) {
		this.konditionen = newKonditionen;
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
