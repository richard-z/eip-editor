package gui.preferenceDialogs;

import gui.Pattern;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bibliothek.MessageTranslator;

@SuppressWarnings("serial")
public class PreferenceDialogMessageTranslator extends JDialog {

	JFrame owner;
	private TextField untranslatedTypField = new TextField();
	JLabel untranslatedTypLabel = new JLabel("untranslated typ: ");
	private TextField translatedTypField = new TextField();
	JLabel translatedTypLabel = new JLabel("translated typ: ");
	private TextField translateFunctionField = new TextField();
	JLabel translateFunctionLabel = new JLabel("translation function: ");

	public PreferenceDialogMessageTranslator(JFrame owner,
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

		// insert Informations from selected Element to Properties Dialog
		bibliothek.MessageTranslator patternDataModel = (MessageTranslator) WorkingAreaElement.getPatternDataModel();
		getUntranslatedTypField().setText(patternDataModel.getUntranslatedTyp());

		untranslatedTypLabel.setLocation(0, 40);
		untranslatedTypLabel.setSize(150, 20);
		getUntranslatedTypField().setLocation(150, 40);
		getUntranslatedTypField().setSize(100, 20);

		getTranslatedTypField().setText(patternDataModel.getTranslatedTyp());

		translatedTypLabel.setLocation(0, 60);
		translatedTypLabel.setSize(150, 20);
		getTranslatedTypField().setLocation(150, 60);
		getTranslatedTypField().setSize(100, 20);

		getTranslateFunctionField().setText(patternDataModel.getFilterFunktion());

		translateFunctionLabel.setLocation(0, 80);
		translateFunctionLabel.setSize(150, 20);
		getTranslateFunctionField().setLocation(150, 80);
		getTranslateFunctionField().setSize(100, 20);

		add(getUntranslatedTypField());
		add(untranslatedTypLabel);
		add(getTranslatedTypField());
		add(translatedTypLabel);
		add(getTranslateFunctionField());
		add(translateFunctionLabel);
		
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

	public TextField getUntranslatedTypField() {
		return untranslatedTypField;
	}

	public void setUntranslatedTypField(TextField untranslatedTypField) {
		this.untranslatedTypField = untranslatedTypField;
	}

	public TextField getTranslatedTypField() {
		return translatedTypField;
	}

	public void setTranslatedTypField(TextField translatedTypField) {
		this.translatedTypField = translatedTypField;
	}

	public TextField getTranslateFunctionField() {
		return translateFunctionField;
	}

	public void setTranslateFunctionField(TextField translateFunctionField) {
		this.translateFunctionField = translateFunctionField;
	}
}