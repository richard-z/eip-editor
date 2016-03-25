package gui.simulation;

import javax.swing.JButton;

import org.cpntools.accesscpn.engine.highlevel.instance.Binding;
import org.cpntools.accesscpn.engine.highlevel.instance.Instance;
import org.cpntools.accesscpn.model.Transition;

public class SimulationButton extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Instance<? extends Transition> associatedTransition;
	private Binding associatedBinding;
	
	public SimulationButton(Instance<? extends Transition> associatedTransition) {
		super();
		this.setAssociatedTransition(associatedTransition);
	}

	public Instance<? extends Transition> getAssociatedTransition() {
		return associatedTransition;
	}

	public void setAssociatedTransition(Instance<? extends Transition> associatedTransition) {
		this.associatedTransition = associatedTransition;
	}

	public Binding getAssociatedBinding() {
		return associatedBinding;
	}

	public void setAssociatedBinding(Binding associatedBinding) {
		this.associatedBinding = associatedBinding;
	}

}
