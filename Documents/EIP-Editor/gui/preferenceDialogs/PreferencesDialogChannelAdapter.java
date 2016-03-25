package gui.preferenceDialogs;
import gui.Pattern;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bibliothek.ChannelAdapter;

@SuppressWarnings("serial")
public class PreferencesDialogChannelAdapter extends JDialog {

	JFrame owner;
	private int variant = 0;
	private TextField messageSystemTypeField = new TextField();
	JLabel messageSystemTypeLabel = new JLabel("message-system type: ");
	private TextField applicationTypeField = new TextField();
	JLabel applicationTypeLabel = new JLabel("application type: ");
	// inbound ChannelAdapter informations
	private TextField dataField = new TextField();
	JLabel dataLabel = new JLabel("function data: ");
	// outbound ChannelAdapter informations
	private TextField msgField = new TextField();
	JLabel msgLabel = new JLabel("function msg: ");
	

	public PreferencesDialogChannelAdapter(JFrame owner,
			Pattern WorkingAreaElement) {
		super(owner);
		this.owner = owner;
		this.initialize(WorkingAreaElement);
	}

	private void initialize(Pattern WorkingAreaElement) {
		setTitle("Preferences");
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		String[] variants = { "inbound", "outbound" };
		JComboBox<String> cb = new JComboBox<String>(variants);
		cb.setSelectedIndex(WorkingAreaElement.getVariant());
		cb.setSize(100, 20);
		cb.setLocation(0, 0);
		add(cb);
		setLayout(null);
		setSize(400, 400);
		setLocation(getOwner().getX() + 20, getOwner().getY() + 10);

		setVariant(WorkingAreaElement.getVariant());
		bibliothek.ChannelAdapter patternDataModel = (ChannelAdapter) WorkingAreaElement.getPatternDataModel();
		// insert Informations from selected Element to Properties Dialog
		
		getDataField().setText(patternDataModel.getFunktion());

		dataLabel.setLocation(0, 40);
		dataLabel.setSize(150, 20);
		getDataField().setLocation(150, 40);
		getDataField().setSize(100, 20);

		getMsgField().setText(patternDataModel.getFunktion());

		msgLabel.setLocation(0, 40);
		msgLabel.setSize(150, 20);
		getMsgField().setLocation(150, 40);
		getMsgField().setSize(100, 20);
		
		getMessageSystemTypeField().setText(patternDataModel.getColorSet());
		
		messageSystemTypeLabel.setLocation(0, 80);
		messageSystemTypeLabel.setSize(150, 20);
		getMessageSystemTypeField().setLocation(150, 80);
		getMessageSystemTypeField().setSize(100, 20);
		
		add(getMessageSystemTypeField());
		add(messageSystemTypeLabel);
		
		getApplicationTypeField().setText(patternDataModel.getApplicationType());
		
		applicationTypeLabel.setLocation(0, 60);
		applicationTypeLabel.setSize(150, 20);
		getApplicationTypeField().setLocation(150, 60);
		getApplicationTypeField().setSize(100, 20);
		
		add(getApplicationTypeField());
		add(applicationTypeLabel);

		if (WorkingAreaElement.getVariant() == 0) { // inbound
			remove(getMsgField());
			remove(msgLabel);

			add(getDataField());
			add(dataLabel);

		} else { // outbound
			remove(getDataField());
			remove(dataLabel);

			add(getMsgField());
			add(msgLabel);

		}

		cb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				JComboBox<String> cb = (JComboBox) event.getSource();
				String Variant = (String) cb.getSelectedItem();

				System.out.println(Variant);

				if (Variant == "inbound") {

					setVariant(0);
					remove(getMsgField());
					remove(msgLabel);

					add(getDataField());
					add(dataLabel);

				} else {
					setVariant(1);
					remove(getDataField());
					remove(dataLabel);

					add(getMsgField());
					add(msgLabel);

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

	public TextField getDataField() {
		return dataField;
	}

	public void setDataField(TextField dataField) {
		this.dataField = dataField;
	}

	public TextField getMsgField() {
		return msgField;
	}

	public void setMsgField(TextField msgField) {
		this.msgField = msgField;
	}

	public TextField getMessageSystemTypeField() {
		return messageSystemTypeField;
	}

	public void setMessageSystemTypeField(TextField messageSystemTypeField) {
		this.messageSystemTypeField = messageSystemTypeField;
	}

	public TextField getApplicationTypeField() {
		return applicationTypeField;
	}

	public void setApplicationTypeField(TextField applicationTypeField) {
		this.applicationTypeField = applicationTypeField;
	}
}