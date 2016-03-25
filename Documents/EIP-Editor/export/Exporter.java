package export;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.cpntools.accesscpn.model.exporter.DOMGenerator;
import org.cpntools.accesscpn.model.impl.ArcImpl;
import org.cpntools.accesscpn.model.Arc;
import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;
import org.w3c.dom.Document;

import export.Create.cpntypes;
import bibliothek.ColorSet;
//import bibliothek.Filter; existiert nicht mehr
import bibliothek.MessageChannel;
import bibliothek.Modell;
import bibliothek.Pattern;
import bibliothek.Variable;

/**
 * 
 */

/**
 * @author Dominique
 * 
 */

public class Exporter {

	static int patternId = 0;
	public static LinkedList<String> eingangspunkte = new LinkedList<String>();
	public static LinkedList<String> ausgangspunkte = new LinkedList<String>();

	// Struktur vom Export, wenn noetige Datenstrukturen bestehen

	/**
	 * durchsucht den uebergeben String nach der id des Platzes/Transition und
	 * gibt diese zurueck
	 * 
	 * @param id
	 * @return
	 */
	public static String extractElementId(String id) {
		int idStart;

		if (id.indexOf("_p_") == -1) { // handelt es sich um eine Transition,
										// ist der String _p_ nicht enthalten un
										// die methode indexof gibt -1 zurück
			idStart = id.indexOf("_t_") + 3; // liefert den index von _t_ im
												// String, da _p_ auch
												// uebersprungen werden soll
												// '+3'
		} else
			idStart = id.indexOf("_p_") + 3;

		return id.substring(idStart);
	}

	/**
	 * durchsucht die Liste der Ausgangspunkte nach einer Patternid, gibt den String zurueck der id des Platzes/Transisiton enthaelt
	 * @param pat1
	 * @return String id
	 * @throws Exception 
	 */
	public static String searchPatternAusgang(Pattern pat) throws Exception { 
		for (String patterncode : ausgangspunkte) {
			if (patterncode.startsWith(pat.getId() + "_") || patterncode.contains("_" + pat.getId() + "_")) {
				if(patterncode.contains("MessageRouter") || patterncode.contains("ContentBasedRouter") || patterncode.contains("requestReply")) {
					//nur ein Pattern pro Ausgang verbinden
					ausgangspunkte.remove(patterncode);
				}
				return patterncode;
			}
		}
		throw new Exception("Pattern not found");
	}

	/**
	 * 	 durchsucht die Liste der Eingangspunkte nach einer Patternid, gibt den String zurueck der id des Platzes/Transisiton enthaelt
	 * @param patternid
	 * @return String id
	 * @throws Exception 
	 */
	public static String searchPatternEingang(Pattern pat) throws Exception { 
		for (String patterncode : eingangspunkte) {
			if (patterncode.startsWith(pat.getId() + "_")  || patterncode.contains("_" + pat.getId() + "_")) {
				if(patterncode.contains("StaticAggregator") || patterncode.contains("requestReply")) {
					//nur ein Pattern pro Eingang verbinden
					eingangspunkte.remove(patterncode);
				}
				return patterncode;
			}
		}
		throw new Exception("Pattern not found");
	}

	public static void connectPattern(PetriNet PN, Page pa, Pattern pat1, Pattern pat2) throws Exception {

		String ausgangPattern1 = searchPatternAusgang(pat1);
		String eingangPattern2 = searchPatternEingang(pat2);

		try {
			if (ausgangPattern1.contains("_p_")) {
				if (eingangPattern2.contains("_p_")) { // Fall 1: platz mit
														// platz verbinden

					TransitionNode connect = Create.createTransition(
							"Verbindungstransition", pa); // Transition zwischen
															// die zwei plätze
															// einfügen
					Create.setPosition(connect,
							(pat1.getX() + 100 + pat2.getX()) / 2, // add
																	// patternsize
							(pat1.getY() + pat2.getY()) / 2);
					
					String uebergabeVariable = Create.createInternVar("x", (Create.idToNodePlaceMap.get(extractElementId(ausgangPattern1))).getSort().asString(), PN);
					
					Create.createArc("ptot", connect.getId(),
							extractElementId(ausgangPattern1), uebergabeVariable, pa);
					Create.createArc("ttop", connect.getId(),
							extractElementId(eingangPattern2), uebergabeVariable, pa);

				} else { // Fall 2: platz mit Transition verbinden

					String uebergabeVariable = Create.createInternVar("x", (Create.idToNodePlaceMap.get(extractElementId(ausgangPattern1))).getSort().asString(), PN);
					
					Create.createArc("ptot", extractElementId(eingangPattern2),
							extractElementId(ausgangPattern1), uebergabeVariable, pa);

				}
			}

			if (ausgangPattern1.contains("_t_")) {
				if (eingangPattern2.contains("_p_")) { // Fall 3: Transition mit
														// Platz verbinden
					
					String uebergabeVariable = Create.createInternVar("x", (Create.idToNodePlaceMap.get(extractElementId(eingangPattern2))).getSort().asString(), PN);
					
					Create.createArc("ttop", extractElementId(ausgangPattern1),
							extractElementId(eingangPattern2), uebergabeVariable, pa);

				} else { // Fall 4: Transition mit Transition verbinden
					PlaceNode connect = Create.createPlace("Verbindungsplatz",
							pa); // Platz zwischen die zwei transitionen
									// einfügen
					Create.setPosition(connect,
							(pat1.getX() + 100 + pat2.getX()) / 2, // add
																	// patternsize
							(pat1.getY() + pat2.getY()) / 2);
					
					
					
					Create.createArc("ttop", extractElementId(ausgangPattern1),
							connect.getId(), "", pa);
					Create.createArc("ptot", extractElementId(eingangPattern2),
							connect.getId(), "", pa);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static LinkedList<String> getEntryColset(Modell model, int patternid) {

		// LinkedList<String> EntryColset = new LinkedList<String>();
		// LinkedList<Pattern> patterns = (LinkedList<Pattern>) model
		// .getPatterns().clone();
		//
		// SearchAllPatterns: while (!patterns.isEmpty()) {
		// Pattern pat = patterns.pop();
		// if (!(pat instanceof bibliothek.Message)) {
		// continue;
		// }
		// // zwischen 2 Pattern ist immer ein MessageChannel
		//
		// LinkedList<Pattern> ausgangsPatterns = (LinkedList<Pattern>) pat
		// .getAusgaengeList().clone();
		//
		// while (!ausgangsPatterns.isEmpty()) {
		// // prueft ob Messagechannel mit Pattern nr. patternid verbunden
		// // ist
		// Pattern ausgangsPat = ausgangsPatterns.pop();
		// if (ausgangsPat.getId() == patternid) {
		// EntryColset.push(((bibliothek.Message) pat).getColorSet());
		// break SearchAllPatterns;
		// }
		// }
		// }
		return new LinkedList<String>();
	}

	public static LinkedList<String> getExitColset(Modell model, int patternid) {

		// LinkedList<String> ExitColset = new LinkedList<String>();
		// LinkedList<Pattern> patterns = (LinkedList<Pattern>) model
		// .getPatterns().clone();
		//
		// SearchAllPatterns: while (!patterns.isEmpty()) {
		// Pattern pat = patterns.pop();
		// if (!(pat instanceof MessageChannel))
		// continue;
		// // zwischen 2 Pattern ist immer ein MessageChannel
		//
		// LinkedList<Pattern> eingangsPatterns = (LinkedList<Pattern>) pat
		// .getEingaengeList().clone();
		//
		// while (!eingangsPatterns.isEmpty()) {
		// // prueft ob Pattern nr. patternid mit Messagechannel verbunden
		// // ist
		// Pattern eingangsPat = eingangsPatterns.pop();
		// if (eingangsPat.getId() == patternid) {
		// ExitColset.push(((MessageChannel) pat).getColorSet());
		// ;
		// break SearchAllPatterns;
		// }
		// }
		// }
		return new LinkedList<String>();
	}

	// step 1: alle im Modell deklarierten colorsets im CPN anlegen

	public static void createColsets(PetriNet PN, Modell model) {

		for (ColorSet color : model.getColorSets()) {

			if (color.getTyp().contains("product")) {

				// erase the "product" part
				String Types = color.getTyp().substring(7,
						color.getTyp().length());

				StringTokenizer st = new StringTokenizer(Types, "*");

				LinkedList<String> productTypes = new LinkedList<String>();

				while (st.hasMoreTokens()) {
					productTypes.add(st.nextToken());
				}

				Create.createColsetProduct(color.getName(), productTypes, PN);
				continue;
			}

			Create.createColset(color.getName(),
					StringToCpntype(color.getTyp()), PN);
		}
	}

	public static cpntypes StringToCpntype(String sType) {

		cpntypes cpntype = null;

		switch (sType.toLowerCase()) {

		case "alias":
			cpntype = cpntypes.CPNALIAS;
			break;
		case "bool":
			cpntype = cpntypes.CPNBOOL;
			break;
		case "enum":
			cpntype = cpntypes.CPNENUM;
			break;
		case "index":
			cpntype = cpntypes.CPNINDEX;
			break;
		case "int":
			cpntype = cpntypes.CPNINT;
			break;
		case "list":
			cpntype = cpntypes.CPNLIST;
			break;
		case "product":
			cpntype = cpntypes.CPNPRODUCT;
			break;
		case "real":
			cpntype = cpntypes.CPNREAL;
			break;
		case "record":
			cpntype = cpntypes.CPNRECORD;
			break;
		case "string":
			cpntype = cpntypes.CPNSTRING;
			break;
		case "subset":
			cpntype = cpntypes.CPNSUBSET;
			break;
		case "union":
			cpntype = cpntypes.CPNUNION;
			break;
		case "unit":
			cpntype = cpntypes.CPNUNIT;
			break;
		default:
			System.out.println("Typ wird von CPN-Tools nicht unterstützt!");
		}
		return cpntype;
	}

	/*
	 * step 2: alle im Modell deklarierten variablen im CPN anlegen public
	 */

	public static void createVars(PetriNet pn, Modell model) {
		for (Variable var : model.getVariablen())
			Create.createVar(var.getName(), var.getTyp(), pn);
	}

	/*
	 * step 3: alle im Modell deklarierten Funktionen im CPN anlegen public
	 */

	public static void createFuncs(PetriNet pn, Modell model) {
		for (String fun : model.getFunktionen())
			Create.createFunction(fun, pn);
	}

	public static void process(Modell model, File exportFile) {

		LinkedList<Pattern> patterns = (LinkedList<Pattern>) model
				.getPatterns().clone();

		PetriNet PN = Create.createPetriNet("PNneu");
		Page page = Create.createPage("seite", PN);

		createColsets(PN, model); // step 1
		// step 2, createvars
		createVars(PN, model);
		// step 3, createfuncs
		createFuncs(PN, model);

		do { // step 4, pattern erstellen
			if (patterns.isEmpty()) {
				break;
			}
			Pattern pat = patterns.pop();
			int id = pat.getId();

			if (pat instanceof bibliothek.ClaimCheck) {
				export.pattern.ClaimCheck.create(model, PN, page, id, pat);
				continue;
			}
			if (pat instanceof bibliothek.CompetingConsumers) {
				try {
					export.pattern.CompetingConsumer.create(PN, page, pat
							.getAusgaengeList().size(), id);
					continue;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (pat instanceof bibliothek.ContentEnricher) {
				export.pattern.ContentEnricher.create(PN, page, id, pat);
				continue;
			}
			if (pat instanceof bibliothek.ContentFilter) {
				export.pattern.ContentFilter.create(PN, page, id, model, pat);
				continue;
			}
			if (pat instanceof bibliothek.Aggregator) {
				if ((((bibliothek.Aggregator) pat).getVariante() == 0)) {
					try {
						export.pattern.StaticAggregator
								.create(PN, page, id, pat, model);
						continue;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (((bibliothek.Aggregator) pat).getVariante() == 1) {
					try {
						export.pattern.DynamicAggregator.create(PN, page, id, pat,
								model);
						continue;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if (pat instanceof bibliothek.EnvelopeWrapper) {
				export.pattern.EnvelopeWrapper.create(PN, page, id, pat, model);
				continue;
			}
			if (pat instanceof bibliothek.EventDrivenConsumer) {
				export.pattern.EventDrivenConsumer.create(PN, page, id, pat, model);
				continue;
			}
			if (pat instanceof bibliothek.GuaranteedDelivery) {
				if (((bibliothek.GuaranteedDelivery) pat).getVariante() == 0) {
					export.pattern.GuaranteedDeliveryLossy.create(PN, page, id);
					continue;
				} else if (((bibliothek.GuaranteedDelivery) pat).getVariante() == 1) {
					export.pattern.GuaranteedDeliveryAbstract.create(PN, page, id);
					continue;
				}
			}
			if (pat instanceof bibliothek.IdempotentReceiver) {
				export.pattern.IdempotentReceiver.create(PN, page, id);
				continue;
			}
			if (pat instanceof bibliothek.ChannelAdapter) {
				if (((bibliothek.ChannelAdapter) pat).getVariante() == 0)
					try {
						export.pattern.InboundChannelAdapter.create(PN, page, id, pat,
								model);
						continue;
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				else if (((bibliothek.ChannelAdapter) pat).getVariante() == 1)
					try {
						export.pattern.OutboundChannelAdaptder.create(PN, page, id,
								pat, model);
						continue;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			if (pat instanceof bibliothek.Message) {
				export.pattern.Message.create(pat, PN, page, id);
				continue;
			}
			if (pat instanceof bibliothek.MessageDispatcher) {
				export.pattern.MessageDispatcher.create(PN, page, id, pat
						.getAusgaengeList().size());
				continue;
			}
			if (pat instanceof bibliothek.MessagingBridge) {
				export.pattern.MessagingBridge.create(PN, page, id,
						(bibliothek.MessagingBridge) pat);
				continue;
			}
			if (pat instanceof bibliothek.MessagingGateway) {
				export.pattern.MessagingGateway.create(PN, page, model, id,
						((bibliothek.MessagingGateway) pat).getInbound());
				continue;
			}
			if (pat instanceof bibliothek.Normalizer) {
				export.pattern.Normalizer.create(PN, page, id, model,
						(bibliothek.Normalizer) pat);
				continue;
			}
			if (pat instanceof bibliothek.PointToPointChannel) {
				export.pattern.PointToPointChannel.create(PN, page, id, model,
						(bibliothek.PointToPointChannel) pat);
				continue;
			}
			if (pat instanceof bibliothek.PollingConsumer) {
				export.pattern.PollingConsumer.create(PN, page, id);
				continue;
			}
			if (pat instanceof bibliothek.PublishSubscribeChannel) {
				export.pattern.PublishSubscribeChannel.create(PN, page, id, model,
						(bibliothek.PublishSubscribeChannel) pat);
				continue;
			}
			if (pat instanceof bibliothek.RequestReply) {
				export.pattern.RequestReply.create(pat, PN, page, id, model);
				continue;
			}

			if (pat instanceof bibliothek.MessageRouter) {
				export.pattern.MessageRouter.create(model, PN, page, id,
						(bibliothek.MessageRouter) pat);
				continue;
			}
			if (pat instanceof bibliothek.MessageFilter) {
				export.pattern.MessageFilter.create(PN, page, id, pat);
				continue;
			}
			if (pat instanceof bibliothek.EndPoint) {
				export.pattern.EndPoint.create(PN, page, model, id,
						(bibliothek.EndPoint) pat,
						((bibliothek.EndPoint) pat).getInbound());
				continue;
			}
			if (pat instanceof bibliothek.MessageTranslator) {
				export.pattern.MessageTranslator.create(model, pat, PN, page, id,
						((bibliothek.MessageTranslator) pat)
								.getFilterFunktion());
				continue;
			}

			if (pat instanceof bibliothek.MessageChannel) {
				export.pattern.MessageChannel.create(pat, PN, page, id,
						((bibliothek.MessageChannel) pat).getColorSet());
				continue;
			}

		} while (!patterns.isEmpty());

		try {
			DOMGenerator.export(PN, "testOhneVerbidung.cpn"); // in Datei
																// schreiben
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Erstellen ohne Verbindungen erfolgreich");

		// step 5, pattern verbinden
		//DEBUG
		System.out.println("Verbindung vom Model -> cpn uebertragen \r\n\r\n");
		
		patterns = (LinkedList<Pattern>) model.getPatterns().clone();

		for (int i = 0; i < patterns.size(); i++) {
			Pattern pat = patterns.get(i);
			
			for (Pattern ausgangspattern : pat.getAusgaengeList()) { // alle
																		// ausgehenden
																		// verbindungen
																		// des
																		// pattern
																		// uebertragen
				
				try {
					connectPattern(PN, page, pat,
							ausgangspattern);
				} catch (Exception e) {
					System.out.println("Pattern not found!");
					e.printStackTrace();
				}
			}

		}
		
		//DEBUG
		System.out.println("Verbindungen erfolgreich ins Modell uebertragen! Modell:");
		System.out.println(PN.getPage().get(0).getObject());
		
		
		try {
			
			DOMGenerator.export(PN, exportFile.getAbsolutePath()); // in Datei
																	// schreiben
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Verbinden erfolgreich");

	}
}
