package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;

import bibliothek.Modell;

public class MessagingGateway {
	
	public static void create(PetriNet pn, Page pa, Modell model, int patternid, boolean inbound) {

		EndPoint.create(pn, pa, model, patternid, null, inbound);
	}

}
