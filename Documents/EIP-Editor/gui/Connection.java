package gui;
import java.io.Serializable;

public class Connection implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Directions {INPUT,OUTPUT};
	
	private Pattern source;
	private Pattern target;
	private Directions connectionSideSource;
	private Directions connectionSideTarget;
	
	public Connection(Pattern source, Pattern target, Directions connectionSideSource, Directions connectionSideTarget) {
		this.setSource(source);
		this.setTarget(target);
		this.setConnectionSideSource(connectionSideSource);
		this.setConnectionSideTarget(connectionSideTarget);
	}

	public Pattern getTarget() {
		return target;
	}

	public void setTarget(Pattern target) {
		this.target = target;
	}

	public Pattern getSource() {
		return source;
	}

	public void setSource(Pattern source) {
		this.source = source;
	}

	public Directions getConnectionSideSource() {
		return connectionSideSource;
	}

	public void setConnectionSideSource(Directions connectionSideSource) {
		this.connectionSideSource = connectionSideSource;
	}

	public Directions getConnectionSideTarget() {
		return connectionSideTarget;
	}

	public void setConnectionSideTarget(Directions connectionSideTarget) {
		this.connectionSideTarget = connectionSideTarget;
	}
	
	

}
