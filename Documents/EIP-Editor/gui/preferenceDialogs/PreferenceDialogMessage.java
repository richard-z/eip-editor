package gui.preferenceDialogs;
import gui.Pattern;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bibliothek.Message;

@SuppressWarnings("serial")
public class PreferenceDialogMessage extends JDialog {

	JFrame owner;
	private TextField colorsetField = new TextField();
	JLabel colorsetLabel = new JLabel("type of message: ");
	private TextField markingField = new TextField();
	JLabel markingLabel = new JLabel("data of message: ");
	
	public PreferenceDialogMessage(JFrame owner,
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
		
		bibliothek.Message patternDataModel = (Message) WorkingAreaElement.getPatternDataModel();

		// insert Informations from selected Element to Properties Dialog
		getColorsetField().setText(patternDataModel.getColorSet());
		getMarkingField().setText(patternDataModel.getInitalMarking());
		
		colorsetLabel.setLocation(0, 40);
		colorsetLabel.setSize(150, 20);
		getColorsetField().setLocation(150, 40);
		getColorsetField().setSize(100, 20);
		markingLabel.setLocation(0, 70);
		markingLabel.setSize(150, 20);
		getMarkingField().setLocation(150, 70);
		getMarkingField().setSize(100, 20);

		add(getColorsetField());
		add(colorsetLabel);
		add(getMarkingField());
		add(markingLabel);
		
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

	public TextField getColorsetField() {
		return colorsetField;
	}

	public void setColorsetField(TextField colorsetField) {
		this.colorsetField = colorsetField;
	}

	public TextField getMarkingField() {
		return markingField;
	}

	public void setMarkingField(TextField markingField) {
		this.markingField = markingField;
	}
}