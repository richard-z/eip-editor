package gui.simulation;

import java.awt.Color;

import javax.swing.JLabel;

import org.cpntools.accesscpn.engine.highlevel.instance.Marking;

public class SimulationLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Marking associatedPlace;

	public SimulationLabel(Marking associatedPlace) {
		super(null,null,JLabel.CENTER);
		this.setOpaque(true);
		this.setBackground(Color.yellow);
		this.setAssociatedPlace(associatedPlace);
	}

	public Marking getAssociatedPlace() {
		return associatedPlace;
	}

	public void setAssociatedPlace(Marking associatedPlace) {
		this.associatedPlace = associatedPlace;
	}

}
