package gui.preferenceDialogs;
import gui.Pattern;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bibliothek.MessageChannel;

@SuppressWarnings("serial")
public class PreferencesDialogMessageChannel extends JDialog {

	JFrame owner;
	private TextField colorsetField = new TextField();
	JLabel colorsetLabel = new JLabel("colorset: ");

	public PreferencesDialogMessageChannel(JFrame owner,
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
		
		bibliothek.MessageChannel patternDataModel = (MessageChannel) WorkingAreaElement.getPatternDataModel();

		// insert Informations from selected Element to Properties Dialog
		getColorsetField().setText(patternDataModel.getColorSet());

		colorsetLabel.setLocation(0, 40);
		colorsetLabel.setSize(150, 20);
		getColorsetField().setLocation(150, 40);
		getColorsetField().setSize(100, 20);


		add(getColorsetField());
		add(colorsetLabel);
		
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
}