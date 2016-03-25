package export.pattern;

import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;

import export.Create;
import export.Exporter;
import bibliothek.Modell;
import bibliothek.Pattern;

public class RequestReply {
	
	public static void create(Pattern pat, PetriNet pn, Page pa, int patternid, Modell model) {
		
		TransitionNode send_req = Create.createTransition("send_req_requestReply", pa);
		TransitionNode recv_req = Create.createTransition("recv_req_requestReply", pa);
		TransitionNode send_rep = Create.createTransition("send_rep_requestReply", pa);
		TransitionNode recv_rep = Create.createTransition("recv_rep_requestReply", pa);
		
		String RequestType = ((bibliothek.RequestReply) pat).getRequestType();
		String ReplyType = ((bibliothek.RequestReply) pat).getColorSet();
		
		if(ReplyType == null || ReplyType.isEmpty()) { //default colorset
			ReplyType = "UNIT";
		}
		
		if(RequestType == null || RequestType.isEmpty()) { //default colorset
			RequestType = "UNIT";
		}
		
		PlaceNode chan1 = Create.createPlace("chan1_requestReply", RequestType, "", pa);	
		PlaceNode enf_rep = Create.createPlace("enforce_reply_requestReply", "UNIT", "", pa);
		PlaceNode chan2 = Create.createPlace("chan2_requestReply", ReplyType, "", pa);
		
		String x = Create.createInternVar("x", RequestType, pn);
		String z = Create.createInternVar("z", ReplyType, pn);
		
		try {
			Create.createArc("ptot", recv_req.getId(), chan1.getId(), x, pa);
			Create.createArc("ptot", send_rep.getId(), enf_rep.getId(), "", pa);
			Create.createArc("ptot", recv_rep.getId(), chan2.getId(), z, pa);
			Create.createArc("ttop", send_req.getId(), chan1.getId(), x, pa);
			Create.createArc("ttop", recv_req.getId(), enf_rep.getId(), "", pa);
			Create.createArc("ttop", send_rep.getId(), chan2.getId(), z, pa);

		} catch (Exception e1) {
				e1.printStackTrace();
			}
		
		Create.setPosition(send_req, pat.getX() , pat.getY() + 100);
		Create.setPosition(send_rep, pat.getX() +200 , pat.getY() - 100);
		Create.setPosition(chan1, pat.getX() + 100 , pat.getY() + 100);
		Create.setPosition(chan2, pat.getX() +100 , pat.getY() - 100);
		Create.setPosition(enf_rep, pat.getX() + 200 , pat.getY());
		Create.setPosition(recv_req, pat.getX() + 200 , pat.getY() + 100);
		Create.setPosition(recv_rep, pat.getX() , pat.getY() - 100);

		
		Exporter.eingangspunkte.add("requestReply_" + patternid + "_t_" + send_req.getId());
		Exporter.eingangspunkte.add("requestReply_" + patternid + "_t_" + send_rep.getId());
		Exporter.ausgangspunkte.add("requestReply_" + patternid + "_t_" + recv_req.getId());
		Exporter.ausgangspunkte.add("requestReply_" + patternid + "_t_" + recv_rep.getId());
		
	}

}
