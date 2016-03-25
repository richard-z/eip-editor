package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.*;

import export.Exporter;
import gui.simulation.Simulator;
import bibliothek.Aggregator;
import bibliothek.ChannelAdapter;
import bibliothek.ClaimCheck;
import bibliothek.Message;
import bibliothek.MessageChannel;
import bibliothek.MessageRouter;
import bibliothek.MessageTranslator;
import bibliothek.Modell;
import bibliothek.RequestReply;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static WorkingArea toolbar;
	public static JFrame guiFrame;

	public GUI() {

		guiFrame = this;
		// configure Working area
		Dimension ApplicationSize = new Dimension(1700, 1000);
		toolbar = new WorkingArea();
		toolbar.setSize(1300, 900);
		toolbar.setPreferredSize(ApplicationSize);
		toolbar.setLayout(null);
		// make scrollable

		getContentPane().add(toolbar);

		// design diagramms with Pattern, extended JLabels
		Pattern aggregator = null;
		Pattern channelAdapter = null;
		Pattern claimCheck = null;
		Pattern messageChannel = null;
		Pattern messageTranslator = null;
		Pattern messageRouter = null;
		Pattern requestReply = null;
		Pattern message = null;

		// load images for EIP
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource("icons/Aggregator.PNG"));

			aggregator = new Pattern("Aggregator", img, new Aggregator());

			img = ImageIO.read(getClass().getResource(
					"icons/ChannelAdapter-inbound.png"));
			channelAdapter = new Pattern("Channel Adapter", img,
					new ChannelAdapter());
			img = ImageIO.read(getClass().getResource("icons/ClaimCheck.png"));
			claimCheck = new Pattern("Claim Check", img, new ClaimCheck());
			img = ImageIO.read(getClass().getResource(
					"icons/MessageChannel.png"));
			messageChannel = new Pattern("Message Channel", img,
					new MessageChannel());
			img = ImageIO.read(getClass().getResource(
					"icons/MessageTranslator.png"));
			messageTranslator = new Pattern("Message Translator", img,
					new MessageTranslator());
			img = ImageIO.read(getClass()
					.getResource("icons/MessageRouter.png"));
			messageRouter = new Pattern("Message Router", img,
					new MessageRouter());

			img = ImageIO
					.read(getClass().getResource("icons/RequestReply.png"));
			requestReply = new Pattern("Request-Reply", img, new RequestReply());
			img = ImageIO.read(getClass().getResource("icons/message.png"));
			message = new Pattern("Message", img, new Message());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 'design' toolbox
		aggregator.setPatternName("aggregator");
		aggregator.setLocation(0, 0);
		channelAdapter.setPatternName("channelAdapter");
		channelAdapter.setLocation(200, 0);
		claimCheck.setPatternName("claimCheck");
		claimCheck.setLocation(0, 140);
		messageChannel.setPatternName("messageChannel");
		messageChannel.setLocation(200, 140);
		messageTranslator.setPatternName("messageTranslator");
		messageTranslator.setLocation(0, 280);
		messageRouter.setPatternName("messageRouter");
		messageRouter.setLocation(200, 280);
		requestReply.setPatternName("requestReply");
		requestReply.setLocation(0, 420);
		message.setPatternName("message");
		message.setLocation(200, 420);

		toolbar.add(aggregator);
		toolbar.add(channelAdapter);
		toolbar.add(claimCheck);
		toolbar.add(messageChannel);
		toolbar.add(messageTranslator);
		toolbar.add(messageRouter);
		toolbar.add(requestReply);
		toolbar.add(message);
		// make Objekts movable
		toolbar.rm.registerComponent(aggregator, channelAdapter, claimCheck,
				messageChannel, messageTranslator, messageRouter, requestReply,
				message);

		JMenuBar menubar = new JMenuBar();

		JMenu file = new JMenu("File");
		JMenu middleware = new JMenu("Middleware");

		JMenuItem loadDiagram = new JMenuItem("Load Middleware");
		loadDiagram.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// load serialized workingarea object

				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showOpenDialog(GUI.guiFrame) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					// load selected file

					InputStream fileStream;
					try {
						fileStream = new FileInputStream(file.getAbsolutePath());
						InputStream buffer = new BufferedInputStream(fileStream);
						ObjectInput input = new ObjectInputStream(buffer);

						GUI.guiFrame.getContentPane().remove(GUI.toolbar);
						GUI.toolbar = (WorkingArea) input.readObject();
						GUI.guiFrame.getContentPane().add(GUI.toolbar);

						input.close();
						GUI.toolbar.repaint();
						GUI.toolbar.hint.setText("Diagramm loaded!");
						// add context menue to all components on the
						// drawingArea

						for (Pattern pattern : GUI.toolbar.allElements) {
							WorkingArea.addContextMenueToPattern(pattern);
							pattern.repaint();
						}

					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});
		JMenuItem saveDiagram = new JMenuItem("Save Middleware");
		saveDiagram.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser fileChooser = new JFileChooser();

				if (fileChooser.showSaveDialog(GUI.guiFrame) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					// save to selected file

					// serialize workingArea object
					ObjectOutputStream oos = null;
					try {
						oos = new ObjectOutputStream(
								new FileOutputStream(file));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					// do the magic
					try {
						oos.writeObject(GUI.toolbar);
						oos.close();
						GUI.toolbar.hint.setText("Diagram Saved!");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		});
		JMenuItem exportDiagram = new JMenuItem("Export to CPN");
		exportDiagram.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser fileChooser = new JFileChooser();

				if (fileChooser.showSaveDialog(GUI.guiFrame) == JFileChooser.APPROVE_OPTION) {
					File exportFile = fileChooser.getSelectedFile();

					GUI.convertToPetriNet(GUI.toolbar, exportFile);
					GUI.toolbar.hint.setText("Export finished!");
				}
			}
		});
		JMenuItem simulateDiagram = new JMenuItem("Simulate");
		simulateDiagram.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				final JFileChooser chooser = new JFileChooser();
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					try {
						Simulator.initialiseSimulator(chooser
								.getSelectedFile());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		file.add(saveDiagram);
		file.add(loadDiagram);
		middleware.add(exportDiagram);
		middleware.add(simulateDiagram);
		menubar.add(file);
		menubar.add(middleware);

		this.setJMenuBar(menubar);
		menubar.setLocation(0, 0);

	}

	
	public static void createColorsets(WorkingArea drawingsurface, Modell dataModell) {
		for (int i = 0; i < drawingsurface.listModelColorsets.size(); i++) {
			StringTokenizer st = new StringTokenizer(
					drawingsurface.listModelColorsets.elementAt(i));
			String nameColorset = st.nextToken();
			String typColorset = st.nextToken();

			if (typColorset.equals("product")) {
				typColorset += st.nextToken();
			}

			dataModell.addColorSet(nameColorset, typColorset);
		}
	}
	
	public static void createVariables(WorkingArea drawingsurface, Modell dataModell) {
		for (int i = 0; i < drawingsurface.listModelVariables.size(); i++) {
			StringTokenizer st = new StringTokenizer(
					drawingsurface.listModelVariables.elementAt(i));
			dataModell.addVariable(st.nextToken(), st.nextToken());
		}
	}
	
	public static void createFunctions(WorkingArea drawingsurface, Modell dataModell) {
		for (int i = 0; i < drawingsurface.listModelFunctions.size(); i++) {
			dataModell.addFunktion(drawingsurface.listModelFunctions
					.elementAt(i));
		}
	}
	
	public static void createPatterns(WorkingArea drawingsurface, Modell dataModell) {
		for (Pattern pat : drawingsurface.allElements) {
			// parse JLabels infos to data structure

			// converted coordinates
			int convertedX = pat.getX() * 2;
			int convertedY = (drawingsurface.getHeight() - pat.getY()) * 2;

			bibliothek.Pattern patternDataModel = pat.getPatternDataModel();
			patternDataModel.setId(pat.getId());
			patternDataModel.setX(convertedX);
			patternDataModel.setY(convertedY);

			switch (pat.getPatternName()) {

			case "aggregator":

				((Aggregator) patternDataModel).setVariante(pat.getVariant());
				System.out.println("Aggregator added to Model!");
				break;

			case "channelAdapter":
				((ChannelAdapter) patternDataModel).setVariante(pat
						.getVariant());
				System.out.println("ChannelAdapter added to Model!");
				break;

			case "claimCheck":
				System.out.println("ClaimCheck added to Model!");
				break;

			case "messageChannel":
				System.out.println("MessageChannel added to Model!");
				break;

			case "messageTranslator":
				System.out.println("MessageTranslator added to Model!");
				break;

			case "messageRouter":
				((MessageRouter) patternDataModel)
						.setVariante(pat.getVariant());
				System.out.println("MessageRouter added to Model!");
				break;

			case "requestReply":
				System.out.println("RequestReply added to Model!");
				break;

			case "message":
				System.out.println("Message added to Model!");
				break;
			}
			dataModell.addPattern(patternDataModel);
		}
	}
	
	public static void connectPatterns(WorkingArea drawingsurface, Modell dataModell) {
		for (Connection connection : drawingsurface.allConnections) {
			int fromElementWithID = connection.getSource().getId();
			int ToElementWithID = connection.getTarget().getId();

			bibliothek.Pattern patternFrom = null;
			bibliothek.Pattern patternTo = null;

			// DEBUG
			System.out.println("Try to connect from " + fromElementWithID
					+ " to " + ToElementWithID + "!");

			for (bibliothek.Pattern patternTemp : dataModell.getPatterns()) {

				// DEBUG
				System.out.println(patternTemp.getId());

				if (patternTemp.getId() == fromElementWithID) {
					patternFrom = patternTemp;
					System.out.println("Connection Source found!");
					continue;
				}
				if (patternTemp.getId() == ToElementWithID) {
					patternTo = patternTemp;
					System.out.println("Connection Destination found!");
					continue;
				}
			}

			// DEBUG
			// System.exit(-1);

			dataModell.connect(patternFrom, patternTo);

		}
	}
	
	public static void convertToPetriNet(WorkingArea drawingsurface,
			File exportFile) {

		Modell dataModell = new Modell();

		createColorsets(drawingsurface, dataModell);
		createVariables(drawingsurface, dataModell);
		createFunctions(drawingsurface, dataModell);
		createPatterns(drawingsurface, dataModell);
		connectPatterns(drawingsurface, dataModell);
		

		// DEBUG
		System.out.println("Aviable Patterns:");
		System.out.println(dataModell.getPatterns());

		for (bibliothek.Pattern patternTemp : dataModell.getPatterns()) {
			System.out.println(patternTemp.getId());
		}

		System.out.println("---------------------");

		// accomplish connection list to model
		

		Exporter.process(dataModell, exportFile);

	}

	public static void main(String[] args) {
		JFrame frame = new gui.GUI();
		frame.setTitle("EIP-Editor");
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}