package bibliothek;

public class ColorSet {

	String name;
	String typ;
	
	
	public ColorSet(String name, String typ) {
		this.name = name;
		this.typ = typ;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}
}
