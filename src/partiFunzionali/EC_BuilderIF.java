package partiFunzionali;

import componentiEspressione.Cond;
import componentiEspressione.Expr;
import componentiEspressione.Fact;
import componentiEspressione.FactB;
import componentiEspressione.Term;
import componentiEspressione.TermB;
import componentiEspressione.TermP;

public interface EC_BuilderIF {
 
	TermB buildTermB();
	FactB buildFactB();
	Expr buildExpr();
	Term buildTerm();
	TermP buildTermP();
	Fact buildFact();
	Cond buildCond();
	
}
