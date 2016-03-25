package gui.preferenceDialogs;

import gui.Pattern;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bibliothek.RequestReply;

@SuppressWarnings("serial")
public class PreferenceDialogRequestReply extends JDialog {

	JFrame owner;
	int variant = 0;
	private TextField requestMessageField = new TextField();
	JLabel requestMessageLabel = new JLabel("type of request message(-->): ");
	private TextField replyMessageField = new TextField();
	JLabel replyMessageJLabel = new JLabel("type of reply message(<--): ");

	public PreferenceDialogRequestReply(JFrame owner, Pattern WorkingAreaElement) {
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

		bibliothek.RequestReply patternDataModel = (RequestReply) WorkingAreaElement.getPatternDataModel();
		
		getRequestMessageField().setText(patternDataModel.getRequestType());
		getReplyMessageField().setText(patternDataModel.getColorSet());

		getRequestMessageField().setLocation(200, 40);
		getRequestMessageField().setSize(100, 20);
		requestMessageLabel.setLocation(0, 40);
		requestMessageLabel.setSize(200, 20);

		replyMessageJLabel.setLocation(0, 70);
		replyMessageJLabel.setSize(200, 20);
		getReplyMessageField().setLocation(200, 70);
		getReplyMessageField().setSize(100, 20);

		add(getReplyMessageField());
		add(replyMessageJLabel);
		add(getRequestMessageField());
		add(requestMessageLabel);

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

	public TextField getRequestMessageField() {
		return requestMessageField;
	}

	public void setRequestMessageField(TextField requestMessageField) {
		this.requestMessageField = requestMessageField;
	}

	public TextField getReplyMessageField() {
		return replyMessageField;
	}

	public void setReplyMessageField(TextField replyMessageField) {
		this.replyMessageField = replyMessageField;
	}
}
