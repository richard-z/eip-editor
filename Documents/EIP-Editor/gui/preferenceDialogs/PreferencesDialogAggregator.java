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

import bibliothek.Aggregator;

public class PreferencesDialogAggregator extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	/**
	 * 
	 */
	JFrame owner;
	private int variant = 0;
	// static aggregator informations
	private TextField aggregateFuctionField = new TextField();
	JLabel aggregateFuncJLabel = new JLabel("aggregation function: ");
	private JTextPane incomingMessengesTypesField = new JTextPane();
	JLabel incomingMessengesTypesJLabel = new JLabel(
			"incoming messenges types: ");
	private TextField aggregationTypeField = new TextField();
	JLabel aggregationTypeJLabel = new JLabel("aggregation type: ");

	// dynamic aggregator informations
	private TextField fField = new TextField();
	private TextField updateField = new TextField();
	private TextField firstField = new TextField();
	private TextField aggregateField = new TextField();
	private TextField iscompleteField = new TextField();
	JLabel fLabel = new JLabel("function f: ");
	JLabel updateJLabel = new JLabel("function update: ");
	JLabel firstJLabel = new JLabel("first conditions: ");
	JLabel aggregateJLabel = new JLabel("aggregate conditions: ");
	JLabel iscompleteJLabel = new JLabel("is complete conditions: ");

	public PreferencesDialogAggregator(JFrame owner, Pattern WorkingAreaElement) {
		super(owner);
		this.owner = owner;
		this.initialize(WorkingAreaElement);
	}

	private void initialize(Pattern WorkingAreaElement) {
		setTitle("Preferences");
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		String[] variants = { "static", "dynamic" };
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
		// static aggregator informations
		bibliothek.Aggregator patternDataModel = (Aggregator) WorkingAreaElement.getPatternDataModel();
		getAggregateFuctionField().setText(patternDataModel.getFilterFunktion());
		getIncomingMessengesTypesField().setText(PreferenceDialogTools.getStringFromList(patternDataModel.getIncomingMessageTypes()));
		getAggregationTypeField().setText(patternDataModel.getAggregationType());

		aggregateFuncJLabel.setLocation(0, 60);
		aggregateFuncJLabel.setSize(150, 20);
		getAggregateFuctionField().setLocation(200, 60);
		getAggregateFuctionField().setSize(100, 20);
		incomingMessengesTypesJLabel.setLocation(0, 110);
		incomingMessengesTypesJLabel.setSize(180, 20);
		getIncomingMessengesTypesField().setLocation(200, 110);
		getIncomingMessengesTypesField().setSize(100, 150);
		getAggregationTypeField().setLocation(200, 270);
		getAggregationTypeField().setSize(100, 20);
		aggregationTypeJLabel.setLocation(0, 270);
		aggregationTypeJLabel.setSize(150, 20);

		// dynamic aggregator informations
		getfField().setText(patternDataModel.getFFunktion());
		getUpdateField().setText(patternDataModel.getUpdateFunktion());
		getFirstField().setText(PreferenceDialogTools.getStringFromList(patternDataModel.getFirst()));
		getAggregateField().setText(PreferenceDialogTools.getStringFromList(patternDataModel.getAggregate()));
		getIscompleteField().setText(PreferenceDialogTools.getStringFromList(patternDataModel.getIscomplete()));

		fLabel.setLocation(0, 40);
		fLabel.setSize(150, 20);
		getfField().setLocation(150, 40);
		getfField().setSize(100, 20);

		updateJLabel.setLocation(0, 60);
		updateJLabel.setSize(150, 20);
		getUpdateField().setLocation(150, 60);
		getUpdateField().setSize(100, 20);

		firstJLabel.setLocation(0, 80);
		firstJLabel.setSize(150, 20);
		getFirstField().setLocation(150, 80);
		getFirstField().setSize(100, 20);

		aggregateJLabel.setLocation(0, 100);
		aggregateJLabel.setSize(150, 20);
		getAggregateField().setLocation(150, 100);
		getAggregateField().setSize(100, 20);

		iscompleteJLabel.setLocation(0, 120);
		iscompleteJLabel.setSize(150, 20);
		getIscompleteField().setLocation(150, 120);
		getIscompleteField().setSize(100, 20);


		if (WorkingAreaElement.getVariant() == 0) { // static
			remove(getfField());
			remove(fLabel);
			remove(getUpdateField());
			remove(updateJLabel);
			remove(getFirstField());
			remove(firstJLabel);
			remove(getAggregateField());
			remove(aggregateJLabel);
			remove(getIscompleteField());
			remove(iscompleteJLabel);

			add(aggregateFuncJLabel);
			add(getAggregateFuctionField());
			add(getIncomingMessengesTypesField());
			add(incomingMessengesTypesJLabel);
			add(getAggregationTypeField());
			add(aggregationTypeJLabel);

		} else { // dynamic
			remove(getAggregateFuctionField());
			remove(aggregateFuncJLabel);
			remove(getIncomingMessengesTypesField());
			remove(incomingMessengesTypesJLabel);
			remove(getAggregationTypeField());
			remove(aggregationTypeJLabel);

			add(getfField());
			add(fLabel);
			add(getUpdateField());
			add(updateJLabel);
			add(getFirstField());
			add(firstJLabel);
			add(getAggregateField());
			add(aggregateJLabel);
			add(getIscompleteField());
			add(iscompleteJLabel);

		}

		cb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				JComboBox<String> cb = (JComboBox) event.getSource();
				String Variant = (String) cb.getSelectedItem();

				if (Variant.equals("static")) {

					setVariant(0);
					remove(getfField());
					remove(fLabel);
					remove(getUpdateField());
					remove(updateJLabel);
					remove(getFirstField());
					remove(firstJLabel);
					remove(getAggregateField());
					remove(aggregateJLabel);
					remove(getIscompleteField());
					remove(iscompleteJLabel);

					add(aggregateFuncJLabel);
					add(getAggregateFuctionField());
					add(getIncomingMessengesTypesField());
					add(incomingMessengesTypesJLabel);
					add(getAggregationTypeField());
					add(aggregationTypeJLabel);

				} else {
					setVariant(1);
					remove(getAggregateFuctionField());
					remove(aggregateFuncJLabel);
					remove(getIncomingMessengesTypesField());
					remove(incomingMessengesTypesJLabel);
					remove(getAggregationTypeField());
					remove(aggregationTypeJLabel);

					add(getfField());
					add(fLabel);
					add(getUpdateField());
					add(updateJLabel);
					add(getFirstField());
					add(firstJLabel);
					add(getAggregateField());
					add(aggregateJLabel);
					add(getIscompleteField());
					add(iscompleteJLabel);

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

	public TextField getAggregateFuctionField() {
		return aggregateFuctionField;
	}

	public void setAggregateFuctionField(TextField aggregateFuctionField) {
		this.aggregateFuctionField = aggregateFuctionField;
	}

	public JTextPane getIncomingMessengesTypesField() {
		return incomingMessengesTypesField;
	}

	public void setIncomingMessengesTypesField(
			JTextPane incomingMessengesTypesField) {
		this.incomingMessengesTypesField = incomingMessengesTypesField;
	}

	public TextField getAggregationTypeField() {
		return aggregationTypeField;
	}

	public void setAggregationTypeField(TextField aggregationTypeField) {
		this.aggregationTypeField = aggregationTypeField;
	}

	public TextField getfField() {
		return fField;
	}

	public void setfField(TextField fField) {
		this.fField = fField;
	}

	public TextField getUpdateField() {
		return updateField;
	}

	public void setUpdateField(TextField updateField) {
		this.updateField = updateField;
	}

	public TextField getFirstField() {
		return firstField;
	}

	public void setFirstField(TextField firstField) {
		this.firstField = firstField;
	}

	public TextField getAggregateField() {
		return aggregateField;
	}

	public void setAggregateField(TextField aggregateField) {
		this.aggregateField = aggregateField;
	}

	public TextField getIscompleteField() {
		return iscompleteField;
	}

	public void setIscompleteField(TextField iscompleteField) {
		this.iscompleteField = iscompleteField;
	}
}
