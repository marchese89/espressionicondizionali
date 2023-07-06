package partiFunzionali;

import componentiEspressione.Cond;
import componentiEspressione.Expr;
import componentiEspressione.Fact;
import componentiEspressione.FactB;
import componentiEspressione.Id;
import componentiEspressione.MDRTerm;
import componentiEspressione.Num;
import componentiEspressione.PMTerm;
import componentiEspressione.Relop;
import componentiEspressione.Term;
import componentiEspressione.TermB;
import componentiEspressione.TermP;

public interface EC_VisitorIF {
	
	public void visit(Cond c);
	public void visit(TermB t);
	public void visit(FactB f);
	public void visit(Expr e);
	public void visit(PMTerm t);
	public void visit(Term t);
	public void visit(Relop r);
	public void visit(TermP t);
	public void visit(MDRTerm t);
	public void visit(Fact f);
	public void visit(Id i);
	public void visit(Num n);
}
