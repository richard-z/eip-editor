package gui;

import gui.preferenceDialogs.PreferenceDialogMessage;
import gui.preferenceDialogs.PreferenceDialogMessageRouter;
import gui.preferenceDialogs.PreferenceDialogMessageTranslator;
import gui.preferenceDialogs.PreferenceDialogRequestReply;
import gui.preferenceDialogs.PreferencesDialogAggregator;
import gui.preferenceDialogs.PreferencesDialogChannelAdapter;
import gui.preferenceDialogs.PreferencesDialogClaimCheck;
import gui.preferenceDialogs.PreferencesDialogMessageChannel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import bibliothek.Aggregator;
import bibliothek.ChannelAdapter;

public class WorkingArea extends JPanel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LinkedList<Connection> allConnections = new LinkedList<Connection>();
	public LinkedList<Pattern> allElements = new LinkedList<Pattern>();
	public DefaultListModel<String> listModelVariables = new DefaultListModel<String>();
	public DefaultListModel<String> listModelFunctions = new DefaultListModel<String>();
	public DefaultListModel<String> listModelColorsets = new DefaultListModel<String>();

	final JList<String> listColorsets;
	JButton btnFunctions3 = new JButton("Remove");
	JTextField tfFunctions3 = new JTextField();
	JLabel declareColorsetJLabel = new JLabel("declare colorsets");
	final JList<String> listVariabels;
	JButton btnFunctions2 = new JButton("Remove");
	JTextField tf2 = new JTextField();
	JLabel declareVarsJLabel = new JLabel("declare variabels");
	final JList<String> listFunctions;
	JButton btnFunctions = new JButton("Remove");
	JTextField tfFunctions = new JTextField();
	JLabel declarefuncJLabel = new JLabel("declare functions");

	public JTextPane hint;
	int id = 0;
	ComponentMover rm;
	private final int ARR_SIZE = 9;

	/**
	 * 
	 */

	public WorkingArea() {
		super();
		rm = new ComponentMover();

		// configure GUI, add Buttons, declarationlists and Toolbox
		this.hint = new JTextPane();
		this.hint.setLocation(0, 950);
		this.hint.setSize(200, 50);
		this.hint.setText("Enjoi Editing!");
		this.add(this.hint);
		
		//TODOs parse .xml with default Colorsets, Vars and Functions

		listColorsets = new JList<String>(this.listModelColorsets);

		declareColorsetJLabel.setLocation(1600, 0);
		declareColorsetJLabel.setSize(100, 20);
		tfFunctions3.setLocation(1600, 20);
		tfFunctions3.setSize(100, 30);
		listColorsets.setLocation(1600, 50);
		listColorsets.setSize(100, 230);
		btnFunctions3.setLocation(1600, 280);
		btnFunctions3.setSize(100, 20);

		listVariabels = new JList<String>(this.listModelVariables);

		declareVarsJLabel.setLocation(1600, 300);
		declareVarsJLabel.setSize(100, 20);
		tf2.setLocation(1600, 320);
		tf2.setSize(100, 30);
		listVariabels.setLocation(1600, 350);
		listVariabels.setSize(100, 230);
		btnFunctions2.setLocation(1600, 580);
		btnFunctions2.setSize(100, 20);

		listFunctions = new JList<String>(this.listModelFunctions);

		declarefuncJLabel.setLocation(1600, 600);
		declarefuncJLabel.setSize(100, 20);
		tfFunctions.setLocation(1600, 620);
		tfFunctions.setSize(100, 30);
		listFunctions.setLocation(1600, 650);
		listFunctions.setSize(100, 230);
		btnFunctions.setLocation(1600, 880);
		btnFunctions.setSize(100, 20);

		this.add(tfFunctions);
		this.add(btnFunctions);
		this.add((listFunctions));
		this.add(declarefuncJLabel);
		this.add(tf2);
		this.add(btnFunctions2);
		this.add((listVariabels));
		this.add(declareVarsJLabel);
		this.add(btnFunctions3);
		this.add(tfFunctions3);
		this.add(declareColorsetJLabel);
		this.add(listColorsets);

		addListenerToButtons(this);

	}
	
	static private JComponent createHeader(String header) {
		JLabel label = new JLabel(header);
		label.setFont(label.getFont().deriveFont(Font.ITALIC));
		label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		return label;
	}

	static void addContextMenueToPattern(Pattern pattern) {
		JPopupMenu popmen = new JPopupMenu();
		popmen.add(createHeader("perform action"));
		popmen.addSeparator();
		popmen.add(new AbstractAction("Connect Output (Right Side) to...") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				ComponentMover.connectionSource = (Pattern) ComponentMover.currentSelectedComponent;
				ComponentMover.connectionSource.setName(e.getActionCommand());
				ComponentMover.makeConnection = true;
				GUI.toolbar.hint.setText("Now select the target.");
				;
			}
		});
		popmen.add(new AbstractAction("Connect Input (Left Side) to...") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				ComponentMover.connectionSource = (Pattern) ComponentMover.currentSelectedComponent;
				ComponentMover.connectionSource.setName(e.getActionCommand());
				ComponentMover.makeConnection = true;
				GUI.toolbar.hint.setText("Now select the target.");
				;
			}
		});

		if (pattern.getName().equals("claimCheck")) {
			popmen.add(new AbstractAction("Connect store to...") {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					ComponentMover.connectionSource = (Pattern) ComponentMover.currentSelectedComponent;
					ComponentMover.connectionSource.setName(e
							.getActionCommand());
					ComponentMover.makeConnection = true;
					GUI.toolbar.hint.setText("Now select the target.");
					;
				}
			});

		}

		popmen.add(new AbstractAction("Remove Arcs") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Pattern selectedElement = (Pattern) ComponentMover.currentSelectedComponent;
				for (Connection connection : GUI.toolbar.allConnections) {
					if (connection.getSource().equals(selectedElement)
							| connection.getTarget().equals(selectedElement)) {
						GUI.toolbar.allConnections.remove(connection);
					}
				}
				GUI.toolbar.repaint();
			}
		});
		popmen.add(new AbstractAction("Set Properties") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// create Settings menu for each pattern

				Pattern WorkingAreaElement = (Pattern) ComponentMover.currentSelectedComponent;

				switch (WorkingAreaElement.getPatternName()) {

				case "aggregator":

					bibliothek.Aggregator aggregatorDataModel = (Aggregator) WorkingAreaElement
							.getPatternDataModel();

					PreferencesDialogAggregator prefAggregator = new PreferencesDialogAggregator(
							GUI.guiFrame, WorkingAreaElement);
					prefAggregator.setVisible(true);

					WorkingAreaElement.setVariant(prefAggregator.getVariant());

					// static aggregator informations

					if (prefAggregator.getVariant() == 0) {

						aggregatorDataModel
								.setFilterFunktion(prefAggregator.getAggregateFuctionField()
										.getText());

						aggregatorDataModel.setIncomingMessageTypes(PreferenceDialogTools
								.getListFromString(prefAggregator.getIncomingMessengesTypesField()
										.getText()));
						aggregatorDataModel
								.setAggregationType(prefAggregator.getAggregationTypeField()
										.getText());
					} else {

						// dynamic aggregator informations
						aggregatorDataModel.setFFunktion(prefAggregator.getfField()
								.getText());
						aggregatorDataModel
								.setUpdateFunktion(prefAggregator.getUpdateField()
										.getText());
						aggregatorDataModel.setFirst(PreferenceDialogTools
								.getListFromString(prefAggregator.getFirstField()
										.getText()));
						aggregatorDataModel.setAggregate(PreferenceDialogTools
								.getListFromString(prefAggregator.getAggregateField()
										.getText()));
						aggregatorDataModel.setIsComplete(PreferenceDialogTools
								.getListFromString(prefAggregator.getIscompleteField()
										.getText()));
					}

					break;

				case "channelAdapter":

					bibliothek.ChannelAdapter channelAdapterDataModel = (ChannelAdapter) WorkingAreaElement
							.getPatternDataModel();
					PreferencesDialogChannelAdapter prefChannelAdapter = new PreferencesDialogChannelAdapter(
							GUI.guiFrame,
							(Pattern) ComponentMover.currentSelectedComponent);
					prefChannelAdapter.setVisible(true);

					WorkingAreaElement.setVariant(prefChannelAdapter.getVariant());

					if (WorkingAreaElement.getVariant() == 0) {
						// inbound channelAdapter informations
						channelAdapterDataModel
								.setFunktion(prefChannelAdapter.getDataField()
										.getText());
					} else {
						// outbound channelAdapter informations
						channelAdapterDataModel
								.setFunktion(prefChannelAdapter.getMsgField()
										.getText());
					}

					channelAdapterDataModel
							.setColorSet(prefChannelAdapter.getMessageSystemTypeField()
									.getText());
					channelAdapterDataModel
					.setApplicationType(prefChannelAdapter.getApplicationTypeField()
							.getText());

					if (WorkingAreaElement.getVariant() == 0) {
						Image img = null;
						try {
							img = ImageIO.read(getClass().getResource(
									"icons/ChannelAdapter-inbound.png"));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						WorkingAreaElement.setIcon(new ImageIcon(img));
					} else {
						Image img = null;
						try {
							img = ImageIO.read(getClass().getResource(
									"icons/ChannelAdapter-outbound.png"));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						WorkingAreaElement.setIcon(new ImageIcon(img));
					}
					break;

				case "claimCheck":
					bibliothek.ClaimCheck claimCheckDataModel = (bibliothek.ClaimCheck) WorkingAreaElement
							.getPatternDataModel();
					PreferencesDialogClaimCheck prefClaimCheck = new PreferencesDialogClaimCheck(
							GUI.guiFrame,
							(Pattern) ComponentMover.currentSelectedComponent);
					prefClaimCheck.setVisible(true);

					claimCheckDataModel.setKey(prefClaimCheck.getKeyField()
							.getText());
					claimCheckDataModel
							.setFilterFunktion(prefClaimCheck.getProjectField()
									.getText());
					claimCheckDataModel
							.setIncomingMessageTyp(prefClaimCheck.getIncomingMessageTypField()
									.getText());
					claimCheckDataModel
							.setFilteredTyp(prefClaimCheck.getFilteredTypField()
									.getText());
					break;

				case "messageChannel":
					bibliothek.MessageChannel messageChannelDataModel = (bibliothek.MessageChannel) WorkingAreaElement
							.getPatternDataModel();
					PreferencesDialogMessageChannel prefMessageChannel = new PreferencesDialogMessageChannel(
							GUI.guiFrame,
							(Pattern) ComponentMover.currentSelectedComponent);
					prefMessageChannel.setVisible(true);

					messageChannelDataModel
							.setColorSet(prefMessageChannel.getColorsetField()
									.getText());
					WorkingAreaElement.setToolTipText(messageChannelDataModel
							.getColorSet());
					break;

				case "messageTranslator":
					bibliothek.MessageTranslator messageTranslatorDataModel = (bibliothek.MessageTranslator) WorkingAreaElement
							.getPatternDataModel();
					PreferenceDialogMessageTranslator prefmessageTranslator = new PreferenceDialogMessageTranslator(
							GUI.guiFrame,
							(Pattern) ComponentMover.currentSelectedComponent);
					prefmessageTranslator.setVisible(true);

					messageTranslatorDataModel
							.setUntranslatedTyp(prefmessageTranslator.getUntranslatedTypField()
									.getText());
					messageTranslatorDataModel
							.setTranslatedTyp(prefmessageTranslator.getTranslatedTypField()
									.getText());
					messageTranslatorDataModel
							.setFilterFunktion(prefmessageTranslator.getTranslateFunctionField()
									.getText());
					break;

				case "messageRouter":
					bibliothek.MessageRouter messageRouterDataModel = (bibliothek.MessageRouter) WorkingAreaElement
							.getPatternDataModel();
					PreferenceDialogMessageRouter prefmessageRouter = new PreferenceDialogMessageRouter(
							GUI.guiFrame,
							(Pattern) ComponentMover.currentSelectedComponent);
					prefmessageRouter.setVisible(true);
					WorkingAreaElement.setVariant(prefmessageRouter.getVariant());
					messageRouterDataModel.setKonditionen(PreferenceDialogTools
							.getListFromString(prefmessageRouter.getRoutingConditionsField()
									.getText()));
					messageRouterDataModel
							.setFilterFunktion(prefmessageRouter.getColorsetField()
									.getText());
					break;

				case "requestReply":
					bibliothek.RequestReply requestReplyDataModel = (bibliothek.RequestReply) WorkingAreaElement
							.getPatternDataModel();
					PreferenceDialogRequestReply prefRequestReply = new PreferenceDialogRequestReply(
							GUI.guiFrame,
							(Pattern) ComponentMover.currentSelectedComponent);
					prefRequestReply.setVisible(true);
					requestReplyDataModel
							.setRequestType(prefRequestReply.getRequestMessageField()
									.getText());
					requestReplyDataModel
							.setColorSet(prefRequestReply.getReplyMessageField()
									.getText());
					break;

				case "message":
					bibliothek.Message messageDataModel = (bibliothek.Message) WorkingAreaElement
							.getPatternDataModel();
					PreferenceDialogMessage prefMessage = new PreferenceDialogMessage(
							GUI.guiFrame,
							(Pattern) ComponentMover.currentSelectedComponent);
					prefMessage.setVisible(true);

					messageDataModel.setColorSet(prefMessage.getColorsetField()
							.getText());
					WorkingAreaElement.setToolTipText(messageDataModel
							.getColorSet());
					messageDataModel.setInitalMarking(prefMessage.getMarkingField()
							.getText());

					break;

				}
			}
		});

		pattern.addMouseListener(new PopupMenuMouseListener(popmen));
	}



	void addListenerToButtons(WorkingArea workingArea) {

		// workingArea.saveSimReportButton.addActionListener(new
		// ActionListener() {
		// public void actionPerformed(ActionEvent event) {
		// final JFileChooser chooser = new JFileChooser();
		// if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
		// try {
		// s.saveSimulationReport(chooser.getSelectedFile());
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// });

		workingArea.btnFunctions3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = GUI.toolbar.listColorsets.getSelectedIndex();
				if (index == -1)
					return;
				GUI.toolbar.listModelColorsets.remove(index);
			}
		});

		workingArea.tfFunctions3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = ((JTextField) e.getSource()).getText();
				GUI.toolbar.listModelColorsets.addElement(text);
				((JTextField) e.getSource()).setText("");
			}
		});

		workingArea.btnFunctions2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = GUI.toolbar.listVariabels.getSelectedIndex();
				if (index == -1)
					return;
				GUI.toolbar.listModelVariables.remove(index);
			}
		});

		workingArea.tf2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = ((JTextField) e.getSource()).getText();
				GUI.toolbar.listModelVariables.addElement(text);
				((JTextField) e.getSource()).setText("");
			}
		});

		workingArea.btnFunctions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = GUI.toolbar.listFunctions.getSelectedIndex();
				if (index == -1)
					return;
				GUI.toolbar.listModelFunctions.remove(index);
			}
		});

		workingArea.tfFunctions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = ((JTextField) e.getSource()).getText();
				GUI.toolbar.listModelFunctions.addElement(text);
				((JTextField) e.getSource()).setText("");
			}
		});

	}

	void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
		Graphics2D g = (Graphics2D) g1.create();

		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);

		// Draw horizontal arrow starting in (0, 0)
		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] { len, len - ARR_SIZE, len - ARR_SIZE, len },
				new int[] { 0, -ARR_SIZE, ARR_SIZE, 0 }, 4);
	}

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		for (Connection currentConnection : allConnections) {

			JLabel source = currentConnection.getSource();
			JLabel target = currentConnection.getTarget();

			if (currentConnection.getConnectionSideSource() == Connection.Directions.INPUT) {
				if (currentConnection.getConnectionSideTarget() == Connection.Directions.INPUT) {
					drawArrow(g2d, source.getX(),
							source.getY() + (source.getHeight() / 2),
							target.getX(), target.getY()
									+ (target.getHeight() / 2));
				}
				if (currentConnection.getConnectionSideTarget() == Connection.Directions.OUTPUT) {
					drawArrow(g2d, source.getX(),
							source.getY() + (source.getHeight() / 2),
							target.getX() + target.getWidth(), target.getY()
									+ (target.getHeight() / 2));
				}
			}

			if (currentConnection.getConnectionSideSource() == Connection.Directions.OUTPUT) {
				if (currentConnection.getConnectionSideTarget() == Connection.Directions.INPUT) {
					drawArrow(g2d, source.getX() + source.getWidth(),
							source.getY() + (source.getHeight() / 2),
							target.getX(), target.getY()
									+ (target.getHeight() / 2));
				}
				if (currentConnection.getConnectionSideTarget() == Connection.Directions.OUTPUT) {
					drawArrow(g2d, source.getX() + source.getWidth(),
							source.getY() + (source.getHeight() / 2),
							target.getX() + target.getWidth(), target.getY()
									+ (target.getHeight() / 2));
				}
			}

		}

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		doDrawing(g);
	}

	public int getID() {
		return id++;
	}

}
