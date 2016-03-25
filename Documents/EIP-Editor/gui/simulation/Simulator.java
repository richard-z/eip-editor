package gui.simulation;

import gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;

import org.cpntools.accesscpn.engine.highlevel.HighLevelSimulator;
import org.cpntools.accesscpn.engine.highlevel.InstancePrinter;
import org.cpntools.accesscpn.engine.highlevel.checker.Checker;
import org.cpntools.accesscpn.engine.highlevel.checker.ErrorInitializingSMLInterface;
import org.cpntools.accesscpn.engine.highlevel.instance.Binding;
import org.cpntools.accesscpn.engine.highlevel.instance.Instance;
import org.cpntools.accesscpn.engine.highlevel.instance.Marking;
import org.cpntools.accesscpn.model.ModelPrinter;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.Transition;
import org.cpntools.accesscpn.model.importer.DOMParser;

public class Simulator {
	private static PrintWriter logfileout;

	private static LinkedList<Instance<? extends Transition>> translatedTransitions = new LinkedList<Instance<? extends Transition>>();
	private static LinkedList<String> translatedBindings = new LinkedList<String>();
	private static HighLevelSimulator s;
	private static LinkedList<SimulationLabel> allSimLabel = new LinkedList<SimulationLabel>();
	private static LinkedList<SimulationButton> allSimButtons = new LinkedList<SimulationButton>();

	/**
	 * cpn-tools datei mithilfe von access cpn parsen und Simulator
	 * intialisieren
	 * 
	 * @param selectedFile
	 * @throws Exception
	 */
	public static void initialiseSimulator(final File selectedFile)
			throws Exception {
		final PetriNet petriNet = DOMParser.parse(selectedFile.toURI().toURL());
		System.out.println(ModelPrinter.printModel(petriNet));
		System.out
				.println("=======================================================");
		System.out.println(InstancePrinter.printModel(petriNet));
		System.out
				.println("=======================================================");
		System.out.println(InstancePrinter.printMonitors(petriNet));
		s = HighLevelSimulator.getHighLevelSimulator();

		s.setSimulationReportOptions(true, true, "");
		final Checker checker = new Checker(petriNet, selectedFile, s);
		try {
			checker.checkEntireModel(selectedFile.getParent(),
					selectedFile.getParent());
		} catch (final ErrorInitializingSMLInterface _) {
			// Ignore
		}
		System.out.println("Done");
		System.out.println("\n\n Starting Simulation Process \n");

		GregorianCalendar now = new GregorianCalendar();
		DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM);

		logfileout = new PrintWriter(selectedFile.getAbsolutePath() + "log"
				+ "(" + df.format(now.getTime()).replace(":", "-") + ")"
				+ ".txt");

		System.out.println(selectedFile.getAbsolutePath());

		translateTransitionsToButtons();
		createLabelsForPlaces();
		updateLabels();

	}

	/**
	 * entfernt alle Simulationsschaltflächen
	 */
	private static void removeAllSimulationButtons() {
		for (SimulationButton simButton : allSimButtons) {
			GUI.toolbar.remove(simButton);

		}
		Simulator.translatedBindings.clear();
		Simulator.translatedTransitions.clear();
		GUI.toolbar.repaint();
	}

	/**
	 * entfernt den variablen namen aus dem binding String
	 * 
	 * @param binding
	 * @return
	 */
	private static String getValueFromBinding(Binding binding) {
		String bindingValue = "";
		if (binding.toString().contains("=")) {
			String[] allValues = binding.toString().split(",");
			for (String Value : allValues) {
				Value = Value.split("=")[1];
				bindingValue += Value + " ";
			}
		}
		return bindingValue.substring(0, bindingValue.length() - 2);
	}

	/**
	 * bestimmt alle aktivierten Transitionen und zeigt
	 * Simulationsschaltflaechen entsprechend dem Simulationskonzept an
	 */
	private static void translateTransitionsToButtons() {

		Iterator<Instance<? extends Transition>> it = null;
		try {
			it = s.isEnabled(s.getAllTransitionInstances()).iterator();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// DEBUG
		try {
			System.out.println(s.isEnabled(s.getAllTransitionInstances()));
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		// alle aktivierten Transitionen zu Buttons etc übersetzen
		while (it.hasNext()) {
			Instance<? extends Transition> enabledTrans = it.next();
			String enabledTransName = enabledTrans.getNode().getName()
					.toString();

			if (translatedTransitions.contains(enabledTrans)) {
				continue;
			}

			if (enabledTransName.contains("Verbindungstransition")) {

				int position = 0;

				for (Binding binding : s.getBindings(enabledTrans)) {

					if (translatedBindings.contains(binding.toString())) {
						continue;
					}

					SimulationButton transmitButton = new SimulationButton(
							enabledTrans);
					Simulator.allSimButtons.add(transmitButton);
					transmitButton.setAssociatedBinding(binding);
					transmitButton.setSize(150, 30);
					transmitButton.setLocation((int) enabledTrans.getNode()
							.getNodeGraphics().getPosition().getX() / 2 - 50,
							GUI.toolbar.getHeight()
									+ 100
									+ position
									- (int) enabledTrans.getNode()
											.getNodeGraphics().getPosition()
											.getY() / 2);
					position += 30;

					transmitButton.setText("transmit "
							+ getValueFromBinding(binding));
					translatedBindings.add(binding.toString());

					GUI.toolbar.add(transmitButton);
					GUI.toolbar.repaint();
					transmitButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent event) {
							try {
								s.execute(((SimulationButton) event.getSource())
										.getAssociatedBinding());
								removeAllSimulationButtons();
							} catch (Exception e) {
								e.printStackTrace();
							}
							logfileout.println("Button "
									+ ((SimulationButton) event.getSource())
											.getText() + " clicked");
							logfileout.flush();

							updateLabels();
							translateTransitionsToButtons();
							GUI.toolbar.repaint();
						}
					});
				}
			}

			if (enabledTransName.contains("messageTranslator")) {

				int position = 0;

				for (Binding binding : s.getBindings(enabledTrans)) {

					if (translatedBindings.contains(binding.toString())) {
						continue;
					}

					SimulationButton translatorButton = new SimulationButton(
							enabledTrans);
					Simulator.allSimButtons.add(translatorButton);
					translatorButton.setSize(150, 30);
					translatorButton.setAssociatedBinding(binding);
					translatorButton.setLocation((int) enabledTrans.getNode()
							.getNodeGraphics().getPosition().getX() / 2 - 50,
							GUI.toolbar.getHeight()
									+ 100
									+ position
									- (int) enabledTrans.getNode()
											.getNodeGraphics().getPosition()
											.getY() / 2);

					position += 30;

					translatorButton.setText("translate"
							+ getValueFromBinding(binding));
					translatedBindings.add(binding.toString());

					GUI.toolbar.add(translatorButton);
					GUI.toolbar.repaint();
					translatorButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent event) {
							try {
								s.execute(((SimulationButton) event.getSource())
										.getAssociatedBinding());
								removeAllSimulationButtons();

							} catch (Exception e) {
								e.printStackTrace();
							}

							logfileout.println("Button "
									+ ((SimulationButton) event.getSource())
											.getText() + " clicked");
							logfileout.flush();
							updateLabels();
							translateTransitionsToButtons();
							GUI.toolbar.repaint();

						}
					});
				}
			}

			if (enabledTransName.contains("com_outboundChannelAdapter")) {

				int position = 0;

				for (Binding binding : s.getBindings(enabledTrans)) {

					if (translatedBindings.contains(binding.toString())) {
						continue;
					}

					SimulationButton receiveButton = new SimulationButton(
							enabledTrans);
					Simulator.allSimButtons.add(receiveButton);
					receiveButton.setSize(210, 30);
					receiveButton.setLocation((int) enabledTrans.getNode()
							.getNodeGraphics().getPosition().getX() / 2 - 60,
							GUI.toolbar.getHeight()
									+ 120
									+ position
									- (int) enabledTrans.getNode()
											.getNodeGraphics().getPosition()
											.getY() / 2);

					position += 30;

					receiveButton.setText("publish message "
							+ getValueFromBinding(binding));
					receiveButton.setAssociatedBinding(binding);
					translatedBindings.add(binding.toString());

					GUI.toolbar.add(receiveButton);
					GUI.toolbar.repaint();
					receiveButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent event) {
							try {
								s.execute(((SimulationButton) event.getSource())
										.getAssociatedBinding());
								removeAllSimulationButtons();

							} catch (Exception e) {
								e.printStackTrace();
							}

							logfileout.println("Button "
									+ ((SimulationButton) event.getSource())
											.getText() + " clicked");
							logfileout.flush();
							translateTransitionsToButtons();
							updateLabels();
							GUI.toolbar.repaint();

						}
					});
				}
			}

			if (enabledTransName.contains("com_inboundChannelAdapter")) {

				int position = 0;

				for (Binding binding : s.getBindings(enabledTrans)) {

					if (translatedBindings.contains(binding.toString())) {
						continue;
					}

					SimulationButton sendToSystemButton = new SimulationButton(
							enabledTrans);
					Simulator.allSimButtons.add(sendToSystemButton);
					sendToSystemButton.setSize(150, 30);
					sendToSystemButton.setLocation((int) enabledTrans.getNode()
							.getNodeGraphics().getPosition().getX() / 2 - 50,
							GUI.toolbar.getHeight()
									+ 100
									+ position
									- (int) enabledTrans.getNode()
											.getNodeGraphics().getPosition()
											.getY() / 2);

					position += 30;
					sendToSystemButton.setText("receive message "
							+ getValueFromBinding(binding));
					sendToSystemButton.setAssociatedBinding(binding);
					translatedBindings.add(binding.toString());
					GUI.toolbar.add(sendToSystemButton);
					GUI.toolbar.repaint();
					sendToSystemButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent event) {
							try {
								s.execute(((SimulationButton) event.getSource())
										.getAssociatedBinding());
								removeAllSimulationButtons();
							} catch (Exception e) {
								e.printStackTrace();
							}

							logfileout.println("Button "
									+ ((SimulationButton) event.getSource())
											.getText() + " clicked");
							logfileout.flush();
							translateTransitionsToButtons();
							updateLabels();
							GUI.toolbar.repaint();

						}
					});
				}
			}

			if (enabledTransName.contains("aggr._staticAggregator")) {

				int position = 0;

				for (Binding binding : s.getBindings(enabledTrans)) {

					if (translatedBindings.contains(binding.toString())) {
						continue;
					}

					SimulationButton staticAggregateButton = new SimulationButton(
							enabledTrans);
					Simulator.allSimButtons.add(staticAggregateButton);
					staticAggregateButton.setSize(170, 30);
					staticAggregateButton.setLocation(
							(int) enabledTrans.getNode().getNodeGraphics()
									.getPosition().getX() / 2 - 50,
							GUI.toolbar.getHeight()
									+ 120
									+ position
									- (int) enabledTrans.getNode()
											.getNodeGraphics().getPosition()
											.getY() / 2);

					staticAggregateButton.setText("collect messages "
							+ getValueFromBinding(binding));
					staticAggregateButton.setAssociatedBinding(binding);
					translatedBindings.add(binding.toString());
					position += 30;

					GUI.toolbar.add(staticAggregateButton);
					GUI.toolbar.repaint();
					staticAggregateButton
							.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent event) {
									try {
										s.execute(((SimulationButton) event
												.getSource())
												.getAssociatedBinding());
										removeAllSimulationButtons();

									} catch (Exception e) {
										e.printStackTrace();
									}

									logfileout.println("Button "
											+ ((SimulationButton) event
													.getSource()).getText()
											+ " clicked");
									logfileout.flush();
									translateTransitionsToButtons();
									updateLabels();
									GUI.toolbar.repaint();

								}
							});
				}
			}

			if (enabledTransName.contains("first_DynamicAggregator")) {

				int position = 0;

				for (Binding binding : s.getBindings(enabledTrans)) {

					if (translatedBindings.contains(binding.toString())) {
						continue;
					}

					SimulationButton dynamicAggregateButton = new SimulationButton(
							enabledTrans);
					Simulator.allSimButtons.add(dynamicAggregateButton);
					dynamicAggregateButton.setSize(170, 30);
					dynamicAggregateButton.setLocation(
							(int) enabledTrans.getNode().getNodeGraphics()
									.getPosition().getX() / 2 - 50,
							GUI.toolbar.getHeight()
									+ 120
									+ position
									- (int) enabledTrans.getNode()
											.getNodeGraphics().getPosition()
											.getY() / 2);

					dynamicAggregateButton.setText("collect messages "
							+ getValueFromBinding(binding));
					dynamicAggregateButton.setAssociatedBinding(binding);
					translatedBindings.add(binding.toString());
					position += 30;

					GUI.toolbar.add(dynamicAggregateButton);
					GUI.toolbar.repaint();
					dynamicAggregateButton
							.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent event) {
									try {
										s.execute(((SimulationButton) event
												.getSource())
												.getAssociatedBinding());
										removeAllSimulationButtons();

										Iterator<Instance<? extends Transition>> it = null;
										try {
											it = s.isEnabled(
													s.isEnabled(s
															.getAllTransitionInstances()))
													.iterator();
										} catch (Exception e1) {
											e1.printStackTrace();
										}

										while (it.hasNext()) {
											Instance<? extends Transition> enabledTrans = it
													.next();
											if (enabledTrans
													.getNode()
													.getName()
													.toString()
													.contains(
															"aggr._DynamicAggregator")) {
												s.execute(enabledTrans);
												it = s.isEnabled(
														s.isEnabled(s
																.getAllTransitionInstances()))
														.iterator();
												continue;
											}
											if (enabledTrans
													.getNode()
													.getName()
													.toString()
													.contains(
															"test_DynamicAggregator")) {
												s.execute(enabledTrans);
												break;
											}

										}

									} catch (Exception e) {
										e.printStackTrace();
									}

									logfileout.println("Button "
											+ ((SimulationButton) event
													.getSource()).getText()
											+ " clicked");
									logfileout.flush();
									translateTransitionsToButtons();
									updateLabels();
									GUI.toolbar.repaint();

								}
							});
				}
			}

			if (enabledTransName.contains("trans_claimCheck")) {

				int position = 0;

				for (Binding binding : s.getBindings(enabledTrans)) {

					if (translatedBindings.contains(binding.toString())) {
						continue;
					}

					SimulationButton claimcheckButton = new SimulationButton(
							enabledTrans);
					Simulator.allSimButtons.add(claimcheckButton);
					claimcheckButton.setSize(170, 30);
					claimcheckButton.setLocation((int) enabledTrans.getNode()
							.getNodeGraphics().getPosition().getX() / 2 - 50,
							GUI.toolbar.getHeight()
									+ 120
									+ position
									- (int) enabledTrans.getNode()
											.getNodeGraphics().getPosition()
											.getY() / 2);

					claimcheckButton.setText("store & claim check "
							+ getValueFromBinding(binding));
					claimcheckButton.setAssociatedBinding(binding);
					translatedBindings.add(binding.toString());

					GUI.toolbar.add(claimcheckButton);
					GUI.toolbar.repaint();
					claimcheckButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent event) {
							try {
								s.execute(((SimulationButton) event.getSource())
										.getAssociatedBinding());
								removeAllSimulationButtons();

							} catch (Exception e) {
								e.printStackTrace();
							}

							logfileout.println("Button "
									+ ((SimulationButton) event.getSource())
											.getText() + " clicked");
							logfileout.flush();
							translateTransitionsToButtons();
							updateLabels();
							GUI.toolbar.repaint();

						}
					});
				}
			}

			if (enabledTransName
					.contains("routingTransition_contentBasedRouter")
					| enabledTransName
							.contains("routingTransition_messageRouter")) {

				int position = 0;

				for (Binding binding : s.getBindings(enabledTrans)) {

					if (translatedBindings.contains(binding.toString())) {
						continue;
					}

					SimulationButton receiveButton = new SimulationButton(
							enabledTrans);
					Simulator.allSimButtons.add(receiveButton);
					receiveButton.setSize(170, 30);
					receiveButton.setLocation((int) enabledTrans.getNode()
							.getNodeGraphics().getPosition().getX() / 2 - 50,
							GUI.toolbar.getHeight()
									+ 120
									+ position
									- (int) enabledTrans.getNode()
											.getNodeGraphics().getPosition()
											.getY() / 2);

					receiveButton.setText("route "
							+ getValueFromBinding(binding));
					receiveButton.setAssociatedBinding(binding);
					translatedBindings.add(binding.toString());
					position += 30;

					GUI.toolbar.add(receiveButton);
					GUI.toolbar.repaint();

					receiveButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent event) {
							try {
								s.execute(((SimulationButton) event.getSource())
										.getAssociatedBinding());
								removeAllSimulationButtons();

							} catch (Exception e) {
								e.printStackTrace();
							}

							logfileout.println("Button "
									+ ((SimulationButton) event.getSource())
											.getText() + " clicked");
							logfileout.flush();
							translateTransitionsToButtons();
							updateLabels();
							GUI.toolbar.repaint();

						}
					});
				}
			}

			if (enabledTransName.contains("send_req_requestReply")) {

				int position = 0;

				for (Binding binding : s.getBindings(enabledTrans)) {

					if (translatedBindings.contains(binding.toString())) {
						continue;
					}

					SimulationButton receiveButton = new SimulationButton(
							enabledTrans);
					Simulator.allSimButtons.add(receiveButton);
					receiveButton.setSize(170, 30);
					receiveButton.setLocation((int) enabledTrans.getNode()
							.getNodeGraphics().getPosition().getX() / 2 - 50,
							GUI.toolbar.getHeight()
									+ 150
									+ position
									- (int) enabledTrans.getNode()
											.getNodeGraphics().getPosition()
											.getY() / 2);

					receiveButton.setText("send a request");
					receiveButton.setAssociatedBinding(binding);
					translatedBindings.add(binding.toString());
					position += 30;

					GUI.toolbar.add(receiveButton);
					GUI.toolbar.repaint();

					receiveButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent event) {
							try {
								s.execute(((SimulationButton) event.getSource())
										.getAssociatedBinding());
								removeAllSimulationButtons();

								Iterator<Instance<? extends Transition>> it = null;
								try {
									it = s.isEnabled(
											s.isEnabled(s
													.getAllTransitionInstances()))
											.iterator();
								} catch (Exception e1) {
									e1.printStackTrace();
								}

								while (it.hasNext()) {
									Instance<? extends Transition> enabledTrans = it
											.next();

									if (enabledTrans.getNode().getName()
											.toString()
											.contains("recv_req_requestReply")) {
										s.execute(enabledTrans);
										break;
									}
								}

							} catch (Exception e) {
								e.printStackTrace();
							}

							logfileout.println("Button "
									+ ((SimulationButton) event.getSource())
											.getText() + " clicked");
							logfileout.flush();
							translateTransitionsToButtons();
							updateLabels();
							GUI.toolbar.repaint();

						}
					});
				}
			}

			if (enabledTransName.contains("send_rep_requestReply")) {

				int position = 0;

				for (Binding binding : s.getBindings(enabledTrans)) {

					if (translatedBindings.contains(binding.toString())) {
						continue;
					}
					SimulationButton receiveButton = new SimulationButton(
							enabledTrans);
					Simulator.allSimButtons.add(receiveButton);
					receiveButton.setSize(170, 30);
					receiveButton.setLocation((int) enabledTrans.getNode()
							.getNodeGraphics().getPosition().getX() / 2 - 50,
							GUI.toolbar.getHeight()
									+ 60
									+ position
									- (int) enabledTrans.getNode()
											.getNodeGraphics().getPosition()
											.getY() / 2);

					receiveButton.setText("respond with reply");
					receiveButton.setAssociatedBinding(binding);
					translatedBindings.add(binding.toString());
					position += 30;

					GUI.toolbar.add(receiveButton);
					GUI.toolbar.repaint();

					receiveButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent event) {
							try {
								s.execute(((SimulationButton) event.getSource())
										.getAssociatedTransition());
								removeAllSimulationButtons();

								Iterator<Instance<? extends Transition>> it = null;
								try {
									it = s.isEnabled(
											s.isEnabled(s
													.getAllTransitionInstances()))
											.iterator();
								} catch (Exception e1) {
									e1.printStackTrace();
								}

								while (it.hasNext()) {
									Instance<? extends Transition> enabledTrans = it
											.next();

									if (enabledTrans.getNode().getName()
											.toString()
											.contains("recv_rep_requestReply")) {
										s.execute(enabledTrans);
										break;
									}
								}

							} catch (Exception e) {
								e.printStackTrace();
							}

							logfileout.println("Button "
									+ ((SimulationButton) event.getSource())
											.getText() + " clicked");
							logfileout.flush();
							translateTransitionsToButtons();
							updateLabels();
							GUI.toolbar.repaint();

						}
					});
				}
			}

			Simulator.translatedTransitions.add(enabledTrans);
		}
	}

	/**
	 * ermittelt die Belegung aller Plaetze und zeigt Simulationsausschriften
	 * gemaess dem Simulationskonzept an
	 */
	private static void createLabelsForPlaces() {

		Iterator<Marking> itPlaces = null;
		try {
			itPlaces = s.getMarking().getAllMarkings().iterator();
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (itPlaces.hasNext()) {

			Marking markingOfAPlace = itPlaces.next();
			String placeName = markingOfAPlace.getPlaceInstance().getNode()
					.getName().toString();

			if (placeName.contains("message_message")) {

				SimulationLabel simulationLabel = new SimulationLabel(
						markingOfAPlace);
				simulationLabel.setSize(200, 30);
				simulationLabel.setLocation((int) markingOfAPlace
						.getPlaceInstance().getNode().getNodeGraphics()
						.getPosition().getX() / 2, GUI.toolbar.getHeight()
						- 35
						- (int) markingOfAPlace.getPlaceInstance().getNode()
								.getNodeGraphics().getPosition().getY() / 2);
				simulationLabel.setText("messages: "
						+ presentMarking(markingOfAPlace.getMarking()));

				GUI.toolbar.add(simulationLabel);
				GUI.toolbar.repaint();
				Simulator.allSimLabel.add(simulationLabel);
			}
			if (placeName.contains("store_claimCheck")) {
				
				SimulationLabel simulationLabel = new SimulationLabel(
						markingOfAPlace);
				simulationLabel.setSize(200, 30);
				simulationLabel.setLocation((int) markingOfAPlace
						.getPlaceInstance().getNode().getNodeGraphics()
						.getPosition().getX() / 2, GUI.toolbar.getHeight()
						- 35
						- (int) markingOfAPlace.getPlaceInstance().getNode()
						.getNodeGraphics().getPosition().getY() / 2);
				simulationLabel.setText("stored message data: "
						+ presentMarking(markingOfAPlace.getMarking()));
				
				GUI.toolbar.add(simulationLabel);
				GUI.toolbar.repaint();
				Simulator.allSimLabel.add(simulationLabel);
			}

			if (placeName.contains("forward_messageChannel")) {

				SimulationLabel simulationLabel = new SimulationLabel(
						markingOfAPlace);
				simulationLabel.setSize(200, 60);
				simulationLabel.setLocation((int) markingOfAPlace
						.getPlaceInstance().getNode().getNodeGraphics()
						.getPosition().getX() / 2 - 50, GUI.toolbar.getHeight()
						- 50
						- (int) markingOfAPlace.getPlaceInstance().getNode()
								.getNodeGraphics().getPosition().getY() / 2);
				simulationLabel.setText("messenges on the channel: "
						+ markingOfAPlace.getTokenCount());

				GUI.toolbar.add(simulationLabel);
				GUI.toolbar.repaint();
				Simulator.allSimLabel.add(simulationLabel);
			}

			if (placeName.toString().contains("chan_inboundChannelAdapter")) {

				SimulationLabel simulationLabel = new SimulationLabel(
						markingOfAPlace);
				simulationLabel.setSize(200, 30);
				simulationLabel.setLocation((int) markingOfAPlace
						.getPlaceInstance().getNode().getNodeGraphics()
						.getPosition().getX() / 2 + 40, GUI.toolbar.getHeight()
						- 25
						- (int) markingOfAPlace.getPlaceInstance().getNode()
								.getNodeGraphics().getPosition().getY() / 2);
				simulationLabel.setText("messages for application: "
						+ markingOfAPlace.getTokenCount());

				GUI.toolbar.add(simulationLabel);
				GUI.toolbar.repaint();
				Simulator.allSimLabel.add(simulationLabel);
			}

			if (placeName.toString().contains("chan_outboundChannelAdapter")) {

				SimulationLabel simulationLabel = new SimulationLabel(
						markingOfAPlace);
				simulationLabel.setSize(200, 30);
				simulationLabel.setLocation((int) markingOfAPlace
						.getPlaceInstance().getNode().getNodeGraphics()
						.getPosition().getX() / 2 - 60, GUI.toolbar.getHeight()
						- 30
						- (int) markingOfAPlace.getPlaceInstance().getNode()
								.getNodeGraphics().getPosition().getY() / 2);
				simulationLabel.setText("messages from application: "
						+ markingOfAPlace.getTokenCount());

				GUI.toolbar.add(simulationLabel);
				GUI.toolbar.repaint();
				Simulator.allSimLabel.add(simulationLabel);
			}

			if (placeName.toString().contains("enforce_reply_requestReply")) {

				SimulationLabel simulationLabel = new SimulationLabel(
						markingOfAPlace);
				simulationLabel.setSize(200, 30);
				simulationLabel.setLocation((int) markingOfAPlace
						.getPlaceInstance().getNode().getNodeGraphics()
						.getPosition().getX() / 2 - 50, GUI.toolbar.getHeight()
						- 50
						- (int) markingOfAPlace.getPlaceInstance().getNode()
								.getNodeGraphics().getPosition().getY() / 2);

				GUI.toolbar.add(simulationLabel);
				GUI.toolbar.repaint();
				Simulator.allSimLabel.add(simulationLabel);
			}

		}

	}

	/**
	 * uebertraegt neue Belegungswerte in die Simulationsausschriften
	 */
	private static void updateLabels() {

		Iterator<Marking> itPlaces = null;
		try {
			itPlaces = s.getMarking().getAllMarkings().iterator();
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (itPlaces.hasNext()) {

			Marking markingOfAPlace = itPlaces.next();

			for (SimulationLabel simulationLabel : allSimLabel) {

				String placeName = simulationLabel.getAssociatedPlace()
						.getPlaceInstance().getNode().getName().toString();

				if (placeName.equals(markingOfAPlace.getPlaceInstance()
						.getNode().getName().toString())) {
					simulationLabel.setAssociatedPlace(markingOfAPlace);

					if (placeName.contains("enforce_reply_requestReply")) {
						if (simulationLabel.getAssociatedPlace()
								.getTokenCount() != 0) {
							simulationLabel.setText("waiting for a reply");
							simulationLabel.setVisible(true);
							continue;
						} else {
							simulationLabel.setText("");
							simulationLabel.setVisible(false);
							continue;
						}
					}
					if (placeName.contains("message_message")) {
						simulationLabel.setText("<html>messenges:<br>"
								+ presentMarking((simulationLabel
										.getAssociatedPlace().getMarking()))+ "</html>");
						continue;
					}
					if (placeName.contains("store_claimCheck")) {
						simulationLabel.setText("<html>stored message data: <br>"
								+ presentMarking((simulationLabel
										.getAssociatedPlace().getMarking()))+ "</html>");
						continue;
					}

					simulationLabel.setText(simulationLabel.getText()
							.split(":")[0]
							+ ": "
							+ simulationLabel.getAssociatedPlace()
									.getTokenCount());
				}

			}
		}
		GUI.toolbar.repaint();

	}

	/**
	 * wandelt Markierung vom CPN-Tool in einfach lesbarere Form um
	 * 
	 * @param marking
	 * @return
	 */
	private static String presentMarking(String marking) {

		return marking.replace("++", ",");
	}
}
