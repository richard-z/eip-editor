/**
 * 
 */
package bibliothek;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author dudarobe
 *
 */
public class Aggregator extends MessageTranslator implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static String bild = "Ich bin ein aligator =D";
	
	private LinkedList<String> incomingMessageTypes = new LinkedList<String>();
	private String aggregationType;
	private String f_function;
	private String update_function;
	private LinkedList<String> first = new LinkedList<String>();
	private LinkedList<String> aggregate = new LinkedList<String>();
	private LinkedList<String> iscomplete = new LinkedList<String>();
	
	int variante; //dynamic 1, static 2
	
	/**
	 * 
	 */
	public Aggregator() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param label
	 * @param x
	 * @param y
	 */
	public Aggregator(String label, int x, int y) {
		super(label, x, y);
		variante= 0;
	}
	
	public String getFFunktion() {
		return f_function;
	}

	public void setFFunktion(String f) {
		this.f_function = f;
	}
	
	public String getUpdateFunktion() {
		return update_function;
	}

	public void setUpdateFunktion(String update) {
		this.update_function = update;
	}
	
	/**
	 * @return dynamic 1, static 2
	 */
	public int getVariante(){
		return variante;
	}
	
	/**
	 * @param dynamic 1, static 2
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
	
	public LinkedList<String> getFirst() {
		return first;
	}
	
	public void setFirst(LinkedList<String> newFirst) {
		this.first = newFirst;
	}
	
	/**
	 * 
	 * @param set Name der neuen Kondition first.
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean addFirst(String kond){
		return first.add(kond);
	}
	
	/**
	 * Entfernt eine Kondition first.
	 * @param kond ist Vom Typ String, die zu entfernende Kondition
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean removeFirst(String kond){
		return first.remove(kond);
	}
	
	public LinkedList<String> getAggregate() {
		return aggregate;
	}
	
	public void setAggregate(LinkedList<String> newAggregate) {
		this.aggregate = newAggregate;
	}
	
	/**
	 * 
	 * @param set Name der neuen Kondition aggregate.
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean addAggregate(String kond){
		return aggregate.add(kond);
	}
	
	/**
	 * Entfernt eine Kondition aggregate.
	 * @param kond ist Vom Typ String, die zu entfernende Kondition
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean removeAggregate(String kond){
		return aggregate.remove(kond);
	}

	public LinkedList<String> getIscomplete() {
		return iscomplete;
	}

	public void setIsComplete(LinkedList<String> newIsComplete) {
		this.iscomplete = newIsComplete;
	}
	
	/**
	 * 
	 * @param set Name der neuen Kondition iscomplete.
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean addIscomplete(String kond){
		return iscomplete.add(kond);
	}
	
	/**
	 * Entfernt eine Kondition iscomplete.
	 * @param kond ist Vom Typ String, die zu entfernende Kondition
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean removeIscomplete(String kond){
		return iscomplete.remove(kond);
	}

	public LinkedList<String> getIncomingMessageTypes() {
		return incomingMessageTypes;
	}

	public void setIncomingMessageTypes(LinkedList<String> incomingMessageTypes) {
		this.incomingMessageTypes = incomingMessageTypes;
	}
	
	public boolean addIncomingMessengeTypes(String colorset) {
		return incomingMessageTypes.add(colorset);
	}
	
	public boolean removeIncomingMessengeTypes(String colorset) {
		return incomingMessageTypes.remove(colorset);
	}

	public String getAggregationType() {
		return aggregationType;
	}

	public void setAggregationType(String aggregationType) {
		this.aggregationType = aggregationType;
	}
	
}
