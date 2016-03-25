package export;

import org.cpntools.accesscpn.model.Arc;
import org.cpntools.accesscpn.model.Code;
import org.cpntools.accesscpn.model.Condition;
import org.cpntools.accesscpn.model.HLDeclaration;
import org.cpntools.accesscpn.model.HLMarking;
import org.cpntools.accesscpn.model.ModelFactory;
import org.cpntools.accesscpn.model.Name;
import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.Place;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.Priority;
import org.cpntools.accesscpn.model.Sort;
import org.cpntools.accesscpn.model.Time;
import org.cpntools.accesscpn.model.Transition;
import org.cpntools.accesscpn.model.TransitionNode;
import org.cpntools.accesscpn.model.HLAnnotation;
import org.cpntools.accesscpn.model.HLArcType;
import org.cpntools.accesscpn.model.cpntypes.CPNList;
import org.cpntools.accesscpn.model.cpntypes.CPNProduct;
import org.cpntools.accesscpn.model.cpntypes.CPNType;
import org.cpntools.accesscpn.model.cpntypes.CpntypesFactory;
import org.cpntools.accesscpn.model.declaration.DeclarationFactory;
import org.cpntools.accesscpn.model.declaration.MLDeclaration;
import org.cpntools.accesscpn.model.declaration.TypeDeclaration;
import org.cpntools.accesscpn.model.declaration.VariableDeclaration;
import org.cpntools.accesscpn.model.graphics.Coordinate;
import org.cpntools.accesscpn.model.graphics.GraphicsFactory;
import org.cpntools.accesscpn.model.graphics.NodeGraphics;

import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

/**
 * 
 */

/**
 * @author Richard
 * 
 */
public class Create {

	public static int id = 0;

	// ! factory for components of a CP net
	private static final ModelFactory factory = ModelFactory.INSTANCE;
	// ! factory for types of a CP net
	private static final CpntypesFactory typeFactory = CpntypesFactory.INSTANCE;
	// ! factory for declarations of a CP net
	private static final DeclarationFactory declFactory = DeclarationFactory.INSTANCE;

	private static final GraphicsFactory gFactory = GraphicsFactory.INSTANCE;

	// ! a hash map relating a place ID to its node
	static Map<String, PlaceNode> idToNodePlaceMap = new HashMap<String, PlaceNode>();
	// ! a hash map relating a place name to its node
	static Map<String, PlaceNode> nameToNodePlaceMap = new HashMap<String, PlaceNode>();
	// ! a hash map relating a transition ID to its node
	static Map<String, TransitionNode> idToTransitionMap = new HashMap<String, TransitionNode>();
	// ! a hash map relating a transition name to its node
	static Map<String, TransitionNode> nameToTransitionMap = new HashMap<String, TransitionNode>();


	// PetriNet erstellen

	public final static PetriNet createPetriNet(String pName) {
		final PetriNet petriNet = factory.createPetriNet();
		final Name netName = factory.createName();
		petriNet.setName(netName);
		netName.setText(pName);
		petriNet.setId(getID());

		return petriNet;
	}

	// eine neue Page in einem bestimmten PetriNet anlegen
	public final static Page createPage(String pName, PetriNet pPN) {
		final Page page = factory.createPage();
		page.setId(getID());
		final Name pageName = factory.createName();
		page.setName(pageName);
		pageName.setText(pName);
		page.setPetriNet(pPN);
		
		

		return page;
	}

	// neuen Platz auf bestimmter Seite anlegen
	public final static PlaceNode createPlace(final String pName,
			final String pSort, final String pMarking, final Page pPage) {
		final Name name = factory.createName();
		name.setText(pName + "_" + getID());
		final Sort type = factory.createSort();
		type.setText(pSort);
		final HLMarking initmark = factory.createHLMarking();
		initmark.setText(pMarking);
		if (pMarking != null) {
			initmark.setText(pMarking);
		} else {
			initmark.setText("");
		}

		final Place place = factory.createPlace();

		String id = getID();
		place.setPage(pPage);
		place.setId(id);
		place.setName(name);
		place.setSort(type);
		place.setInitialMarking(initmark);
		initmark.setParent(place);

		idToNodePlaceMap.put(id, place);
		assert (nameToNodePlaceMap.get(pName.intern()) == null);
		nameToNodePlaceMap.put(pName.intern(), place);

		return place;
	}

	public final static PlaceNode createPlace(String pName, Page pPage) {
		return createPlace(pName, "UNIT", "", pPage);
	}

	// neue Transition auf bestimmter Seite anlegen
	public final static TransitionNode createTransition(String pName,
			String pCondition, String pTime, String pCode, String pPriority,
			Page pPage) {

		final Transition transition = factory.createTransition();
		final String id = getID();
		transition.setId(id);

		final Name name = factory.createName();
		transition.setName(name);
		name.setText(pName);
		final Condition cond = factory.createCondition();
		transition.setCondition(cond);
		cond.setText(pCondition);
		cond.setParent(transition);
		final Time time = factory.createTime();
		transition.setTime(time);
		time.setText(pTime);
		final Code code = factory.createCode();
		transition.setCode(code);
		code.setText(pCode);
		final Priority priority = factory.createPriority();
		transition.setPriority(priority);
		priority.setText(pPriority);

		transition.setPage(pPage);

		idToTransitionMap.put(id, transition);
		nameToTransitionMap.put(pName.intern(), transition);

		return transition;
	}

	public final static TransitionNode createTransition(String pName,
			String pCondition, Page pPage) {
		return createTransition(pName + "_" + getID(), pCondition, "", "", "",
				pPage);
	}

	public final static TransitionNode createTransition(String pName, Page pPage) {
		return createTransition(pName, "", pPage);
	}

	public final static Arc createArc(String pOrientation, String pTransition,
			String pPlace, String pAnnotation, Page pPage) throws Exception {
		final Arc arc = factory.createArc();
		arc.setId(getID());
		final String orientation = pOrientation;
		String transIdref = pTransition;
		String placeIdref = pPlace;

		final HLAnnotation annot = factory.createHLAnnotation();
		annot.setText(pAnnotation);
		arc.setHlinscription(annot);

		if (orientation.equalsIgnoreCase("bothdir")) {
			arc.setSource(idToNodePlaceMap.get(placeIdref));
			arc.setTarget(idToTransitionMap.get(transIdref));
			arc.setKind(HLArcType.TEST);
		} else if (orientation.equalsIgnoreCase("ptot")) {
			arc.setSource(idToNodePlaceMap.get(placeIdref));
			arc.setTarget(idToTransitionMap.get(transIdref));
			arc.setKind(HLArcType.NORMAL);
		} else if (orientation.equalsIgnoreCase("ttop")) {
			arc.setSource(idToTransitionMap.get(transIdref));
			arc.setTarget(idToNodePlaceMap.get(placeIdref));
			arc.setKind(HLArcType.NORMAL);
		} else {
			throw new Exception("Unknown arc orientation");
		}
		arc.setPage(pPage);

		return arc;
	}

	public static final void createColsetList(String pName, String type,
			PetriNet pn) {

		final HLDeclaration newDecl = factory.createHLDeclaration();

		final TypeDeclaration color = declFactory.createTypeDeclaration();
		color.setTypeName(pName);
		CPNList cpnlist = typeFactory.createCPNList();
		cpnlist.setSort(type);
		color.setSort(cpnlist);
		newDecl.setId(Create.getID());
		newDecl.setParent(pn);
		newDecl.setStructure(color);
	}
	
	public static final void createColsetProduct(String pName, LinkedList<String> type,
			PetriNet pn) {

		final HLDeclaration newDecl = factory.createHLDeclaration();
		CPNProduct cpnProduct= typeFactory.createCPNProduct();
		for(String Colorset : type) {
		cpnProduct.addSort(Colorset);
		}
		final TypeDeclaration color = declFactory.createTypeDeclaration();
		color.setSort(cpnProduct);
		color.setTypeName(pName);
		
		newDecl.setId(Create.getID());
		newDecl.setParent(pn);
		newDecl.setStructure(color);
	}

	public static final void createColset(String pName, cpntypes type,
			PetriNet pn) {

		final HLDeclaration newDecl = factory.createHLDeclaration();

		final TypeDeclaration color = declFactory.createTypeDeclaration();
		color.setTypeName(pName);

		CPNType cpnunit = createType(type);
		// cpnunit.setTimed(false);
		color.setSort(cpnunit);

		newDecl.setId(Create.getID());
		newDecl.setParent(pn);
		newDecl.setStructure(color);

	}
	
	public static final HLMarking createInitialMarking(PlaceNode place, String marking) {
		
		HLMarking initialMarking = factory.createHLMarking();
		initialMarking.setText(marking);
		place.setInitialMarking(initialMarking);
		
		return null;
	}
	
	enum cpntypes {
		CPNALIAS, CPNBOOL, CPNENUM, CPNINDEX, CPNINT, CPNLIST, CPNPRODUCT, CPNREAL, CPNRECORD, CPNSTRING, CPNSUBSET, CPNUNION, CPNUNIT
	};

	public static final CPNType createType(cpntypes type) {
		CPNType cpntype = null;
		switch (type) {
		case CPNALIAS:
			cpntype = typeFactory.createCPNAlias();
			break;
		case CPNBOOL:
			cpntype = typeFactory.createCPNBool();
			break;
		case CPNENUM:
			cpntype = typeFactory.createCPNEnum();
			break;
		case CPNINDEX:
			cpntype = typeFactory.createCPNIndex();
			break;
		case CPNINT:
			cpntype = typeFactory.createCPNInt();
			break;
		case CPNLIST:
			cpntype = typeFactory.createCPNList();
			// ((CPNList)cpntype).;
			break;
		case CPNPRODUCT:
			cpntype = typeFactory.createCPNProduct();
			break;
		case CPNREAL:
			cpntype = typeFactory.createCPNReal();
			break;
		case CPNRECORD:
			cpntype = typeFactory.createCPNRecord();
			break;
		case CPNSTRING:
			cpntype = typeFactory.createCPNString();
			break;
		case CPNSUBSET:
			cpntype = typeFactory.createCPNSubset();
			break;
		case CPNUNION:
			cpntype = typeFactory.createCPNUnion();
			break;
		case CPNUNIT:
			cpntype = typeFactory.createCPNUnit();
			break;
		}

		return cpntype;
	}

	public static final void createVar(String vName,String color, PetriNet pn) {

		final HLDeclaration newDecl = factory.createHLDeclaration();
		final VariableDeclaration var = declFactory.createVariableDeclaration();

		var.setTypeName(color); // falls colorset nicht existiert ->
								// (Fehlerabfrage bereits in der GUI!)
		var.addVariable(vName); // theoretisch mit ID versehen, aber da noch
								// keine datenstruktur in Modell vorhanden
								// erstmal so belassen

		newDecl.setId(Create.getID());
		newDecl.setParent(pn);
		newDecl.setStructure(var);
	}
	
	public static final String createInternVar(String vName,String color, PetriNet pn) {

		final HLDeclaration newDecl = factory.createHLDeclaration();
		final VariableDeclaration var = declFactory.createVariableDeclaration();

		var.setTypeName(color); // falls colorset nicht existiert ->
								// (Fehlerabfrage bereits in der GUI!)
		String id=Create.getID();
		String newName = vName+"_"+id;
		var.addVariable(newName);

		newDecl.setId(id);
		newDecl.setParent(pn);
		newDecl.setStructure(var);
		
		return newName;
	}

	public static final void createFunction(String code, PetriNet pn) /*
																	 * throws
																	 * Exception
																	 */{

		// Fehlerabfrage wegen korrekt uebergebenen Listen nicht noetig:

		/*
		 * if(allFuns.contains(head)){ //Funktionen duerfen nicht doppelt
		 * deklariert werden return; }
		 * 
		 * for (int i = 0; i < params.length; i++){
		 * if(!allVars.contains(params[i])) throw new
		 * Exception("Eingangsvariablen nicht deklariert!"); //besser andere
		 * Handhabung? }
		 */

		// allFuns.add(head); //neue Funktion hizufuegen
		final HLDeclaration newDecl = factory.createHLDeclaration();

		MLDeclaration fun = declFactory.createMLDeclaration();
		fun.setCode("fun " + code);

		newDecl.setId(Create.getID());
		newDecl.setParent(pn);
		newDecl.setStructure(fun);
	}

	//fuer die groesse des Objekts kann man mit g.setdimensions(...) gesetzt werden
	
	public static void setPosition(PlaceNode place, int x, int y) {
		NodeGraphics g = gFactory.createNodeGraphics();
		Coordinate c = gFactory.createCoordinate();
		c.setX(x);
		c.setY(y);
		g.setPosition(c);
		g.setParent(place);
		place.setGraphics(g);
	}

	public static void setPosition(TransitionNode transition, int x, int y) {
		NodeGraphics g = gFactory.createNodeGraphics();
		Coordinate c = gFactory.createCoordinate();
		c.setX(x);
		c.setY(y);
		g.setPosition(c);
		g.setParent(transition);
		transition.setGraphics(g);
	}

	public static String getID() {
		return "ID" + id++;
	}

}
