package bibliothek;
import java.util.LinkedList;

public class Modell {

	private LinkedList<Pattern> patterns = new LinkedList<Pattern>();
	private int nextId;
	private LinkedList<ColorSet> colorSets = new LinkedList<ColorSet>();
	private LinkedList<Variable> var = new LinkedList<Variable>();
	private LinkedList<String> funktionen = new LinkedList<String>();
	


	public Modell() {
		nextId = 0;
		colorSets.add(new ColorSet("UNIT", "unit"));
		colorSets.add(new ColorSet("default", "unit"));
		colorSets.add(new ColorSet("INT", "int"));
		colorSets.add(new ColorSet("Key", "INT"));
		funktionen.add(new String("leer x = x;"));
	}
	
	public LinkedList<Pattern> getPatterns() {
		return patterns;
	}
	
	/**
	 * 
	 * @param name der gesuchten variable
	 * @return Variable
	 * */
	public Variable getVariable(String name)throws Exception{
		for(Variable v: var) {
			if(v.getName() == name) {
				return v;
			}
		}
		throw new Exception("Funktion nicht vorhanden!");
	}
	
	/**
	 * 
	 * @param name der gesuchten Funktion
	 * @return Funktion
	 * */
	public String getFunktion(String name)throws Exception{
		for(String fkt: funktionen) {
			if(fkt == name) {
				return fkt;
			}
		}
		throw new Exception("Funktion nicht vorhanden!");
	}
	
	/**
	 * 
	 * @param pat Hinzuzufuegendes Pattern.
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean addPattern(Pattern pat) {
		//pat.setId(nextId++);
//		if(pat instanceof MessageChannel) {
//			try {
//				((MessageChannel) pat).setColorSet(getColorSet("default"));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	
//		}
//		if(pat instanceof MessageTranslator){
//			try {
//				((MessageTranslator)pat).setFilterFunktion(getFunktion("leer x = x;"));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
			
		return patterns.add(pat);
	}
	
	/**
	 * Entfernt ein Pattern aus dem Modell, sowie alles Referenzen aus anderen Pattern.
	 * @param rempat Zu entfernendes Pattern.
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean removePattern(Pattern rempat) { //rempat = zu entfernendes Pattern
		for(Pattern modpat: patterns) {							//modpat = Pattern aus Modell-Liste
			for(Pattern objpat: modpat.getAusgaengeList()) {	//objpat = Pattern aus Aus/Eingaengeliste in Objekt
				objpat.removeAusgang(rempat);
			}
			for(Pattern objpat: modpat.getEingaengeList()) {
				objpat.removeEingang(rempat);
			}
		}
		return patterns.remove(rempat);
	}
	
	public LinkedList<ColorSet> getColorSets() {
		return colorSets;
	}
	
	/**
	 * 
	 * @param set Name des neuen ColorSets (String)
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean addColorSet(String set, String typ) {
		ColorSet cs = new ColorSet(set, typ);
		return colorSets.add(cs);
	}
	
	/**
	 * Entfernt ein Colorset und setzt alle betroffenen Pattern auf "default".
	 * @param set Referenz auf zu entfernendes ColorSet (Kein String!)
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean removeColorSet(String set) {
		for(Pattern pat: patterns) {	
			if(pat instanceof MessageChannel) {
				if(((MessageChannel) pat).getColorSet() == set) {
					try {
						((MessageChannel) pat).setColorSet("default");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return colorSets.remove(set);
	}
	
	/**
	 * 
	 * @param name des gesuchten ColorSets
	 * @return ColorSet
	 * */
	public ColorSet getColorSet(String name)throws Exception {
		for(ColorSet cs: colorSets) {
			if(cs.getName() == name) {
				return cs;
			}
		}
		throw new Exception("ColorSet nicht vorhanden!");
	}
	
	
	public LinkedList<Variable> getVariablen(){
		return var;	
	}
	
	/**
	 * 
	 * @param Name der neuen Variable
	 * @param Typ 
	 * @param zugehoeriges Pattern
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean addVariable(String name, String typ){
		Variable variable = new Variable(name, typ);
		return var.add(variable); 
	}
	
	/**
	 * Entfernt eine Variable.
	 * @param set Referenz auf zu entfernende Variable (Kein String!)
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean removeVariable(Variable variable){
		return var.remove(variable);
	}
	
	public LinkedList<String> getFunktionen(){
		return funktionen;	
	}
	
	/**
	 * 
	 * @param set Name der neuen Funktion 
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean addFunktion(String code){
		String fkt = new String(code);
		return funktionen.add(fkt);
	}
	
	/**
	 * Entfernt eine Funktion und setzt alle betroffenen Pattern auf "leer".
	 * @param set Referenz auf zu entfernende Funktion (Kein String!)
	 * @return true bei Erfolg, sonst false.
	 */
	public boolean removeFunktion(String fkt){
		for(Pattern pat: patterns) {	
			if(pat instanceof MessageTranslator) {
				if(((MessageTranslator) pat).getFilterFunktion() == fkt) {
					try {
						((MessageTranslator) pat).setFilterFunktion(getFunktion("leer x = x;"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return funktionen.remove(fkt);
	}
	
	/** 
	 * Verbindet zwei Pattern. 
	 * @param p1 ausgehendes Pattern
	 * @param p2 eingehendes Pattern
	 */
 	public boolean connect(Pattern p1, Pattern p2) {
		if(p1.addAusgang(p2)) {
			if(p2.addEingang(p1)) {
				return true;
			} else {
				p1.removeAusgang(p2);
				return false;
			}
		}
		return false;
	}
	
	/** 
	 * Trennt zwei Pattern. 
	 * @param p1 ausgehendes Pattern
	 * @param p2 eingehendes Pattern
	 */
	 public boolean disconnect(Pattern p1, Pattern p2){
		 if(p1.removeAusgang(p2)){
			 if(p2.removeEingang(p1))
				 return true;
		 }else{
			 p1.addAusgang(p2);
			 return false;
		 }
		 return false;
	 }
}
