package gui.preferenceDialogs;

import gui.Pattern;
import gui.PreferenceDialogTools;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import bibliothek.MessageRouter;

@SuppressWarnings("serial")
public class PreferenceDialogMessageRouter extends JDialog {

	JFrame owner;
	private int variant = 0;
	// normal Message-Router and contentbase-router informations are similiar,
	// only PN is slightly different
	private TextField colorsetField = new TextField();
	JLabel colorsetLabel = new JLabel("type of message: ");
	private JTextPane routingConditionsField = new JTextPane();
	JLabel routingConditionsJLabel = new JLabel("routing conditions: ");

	public PreferenceDialogMessageRouter(JFrame owner,
			Pattern WorkingAreaElement) {
		super(owner);
		this.owner = owner;
		this.initialize(WorkingAreaElement);
	}

	private void initialize(Pattern WorkingAreaElement) {
		setTitle("Preferences");
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		String[] variants = { "normal", "content-base" };
		JComboBox<String> cb = new JComboBox<String>(variants);
		cb.setSelectedIndex(WorkingAreaElement.getVariant());
		cb.setSize(100, 20);
		cb.setLocation(0, 0);
		add(cb);
		setLayout(null);
		setSize(400, 400);
		setLocation(getOwner().getX() + 20, getOwner().getY() + 10);

		setVariant(WorkingAreaElement.getVariant());
		// insert Informations from selected Element to Properties Dialog
		bibliothek.MessageRouter patternDataModel = (MessageRouter) WorkingAreaElement.getPatternDataModel();
		
		getRoutingConditionsField().setText(PreferenceDialogTools.getStringFromList(patternDataModel.getKonditionen()));
		getColorsetField().setText(patternDataModel.getFilterFunktion());

		getColorsetField().setLocation(150, 40);
		getColorsetField().setSize(100,20);
		colorsetLabel.setLocation(0, 40);
		colorsetLabel.setSize(150, 20);
		
		routingConditionsJLabel.setLocation(0, 70);
		routingConditionsJLabel.setSize(150, 20);
		getRoutingConditionsField().setLocation(150, 70);
		getRoutingConditionsField().setSize(100, 200);

		add(getRoutingConditionsField());
		add(routingConditionsJLabel);
		add(getColorsetField());
		add(colorsetLabel);

		cb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				JComboBox<String> cb = (JComboBox) event.getSource();
				String Variant = (String) cb.getSelectedItem();

				System.out.println(Variant);

				if (Variant.equals("normal")) {

					setVariant(0);

				} else {
					setVariant(1);

				}
			}
		});

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

	public int getVariant() {
		return variant;
	}

	public void setVariant(int variant) {
		this.variant = variant;
	}

	public JTextPane getRoutingConditionsField() {
		return routingConditionsField;
	}

	public void setRoutingConditionsField(JTextPane routingConditionsField) {
		this.routingConditionsField = routingConditionsField;
	}

	public TextField getColorsetField() {
		return colorsetField;
	}

	public void setColorsetField(TextField colorsetField) {
		this.colorsetField = colorsetField;
	}
}
