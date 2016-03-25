package gui.preferenceDialogs;
import gui.Pattern;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bibliothek.ClaimCheck;

@SuppressWarnings("serial")
public class PreferencesDialogClaimCheck extends JDialog {

	JFrame owner;
	private TextField keyField = new TextField();
	JLabel keyLabel = new JLabel("function key: ");
	private TextField projectField = new TextField();
	JLabel projectLabel = new JLabel("function project: ");
	
	private TextField incomingMessageTypField = new TextField();
	JLabel incomingMessageTypLabel = new JLabel("input message type: ");
	private TextField filteredTypField = new TextField();
	JLabel filteredTypLabel = new JLabel("filtered type: ");

	public PreferencesDialogClaimCheck(JFrame owner,
			Pattern WorkingAreaElement) {
		super(owner);
		this.owner = owner;
		this.initialize(WorkingAreaElement);
	}

	private void initialize(Pattern WorkingAreaElement) {
		setTitle("Preferences");
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(null);
		setSize(400, 400);
		setLocation(getOwner().getX() + 20, getOwner().getY() + 10);
		
		
		bibliothek.ClaimCheck patternDataModel = (ClaimCheck) WorkingAreaElement.getPatternDataModel();
		// insert Informations from selected Element to Properties Dialog
		getKeyField().setText(patternDataModel.getKey());

		keyLabel.setLocation(0, 40);
		keyLabel.setSize(150, 20);
		getKeyField().setLocation(150, 40);
		getKeyField().setSize(100, 20);

		getProjectField().setText(patternDataModel.getFilterFunktion());

		projectLabel.setLocation(0, 60);
		projectLabel.setSize(150, 20);
		getProjectField().setLocation(150, 60);
		getProjectField().setSize(100, 20);
		
		getIncomingMessageTypField().setText(patternDataModel.getIncomingMessageTyp());
		
		incomingMessageTypLabel.setLocation(0, 80);
		incomingMessageTypLabel.setSize(150, 20);
		getIncomingMessageTypField().setLocation(150, 80);
		getIncomingMessageTypField().setSize(100, 20);
		
		getFilteredTypField().setText(patternDataModel.getFilteredTyp());
		
		filteredTypLabel.setLocation(0, 100);
		filteredTypLabel.setSize(150, 20);
		getFilteredTypField().setLocation(150, 100);
		getFilteredTypField().setSize(100, 20);
		
		

		add(getKeyField());
		add(keyLabel);
		add(getProjectField());
		add(projectLabel);
		add(getIncomingMessageTypField());
		add(incomingMessageTypLabel);
		add(getFilteredTypField());
		add(filteredTypLabel);
		
		JButton okButton = new JButton();
		okButton.setText("OK");
		okButton.setSize(60, 40);
		okButton.setLocation(150, 300);
		okButton.setVisible(true);
		this.add(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});

	}

	public TextField getKeyField() {
		return keyField;
	}

	public void setKeyField(TextField keyField) {
		this.keyField = keyField;
	}

	public TextField getProjectField() {
		return projectField;
	}

	public void setProjectField(TextField projectField) {
		this.projectField = projectField;
	}

	public TextField getIncomingMessageTypField() {
		return incomingMessageTypField;
	}

	public void setIncomingMessageTypField(TextField incomingMessageTypField) {
		this.incomingMessageTypField = incomingMessageTypField;
	}

	public TextField getFilteredTypField() {
		return filteredTypField;
	}

	public void setFilteredTypField(TextField filteredTypField) {
		this.filteredTypField = filteredTypField;
	}
}