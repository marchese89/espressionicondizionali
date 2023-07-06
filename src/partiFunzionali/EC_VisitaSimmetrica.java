package partiFunzionali;

import java.util.LinkedList;

import javax.swing.JTextArea;

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

public class EC_VisitaSimmetrica implements EC_VisitorIF {
	
	private JTextArea j;
	
	public EC_VisitaSimmetrica(JTextArea j){
		this.j = j;
	}
	public EC_VisitaSimmetrica(){}
	

	@Override
	public void visit(Cond c) {
		LinkedList<TermB> l = c.termini();
		int contaOR = l.size()-1;
	
		for(TermB i:l){
			if(contaOR > 0){
		    i.accept(this);
			//System.out.print(" OR ");
			j.append(" OR ");
			contaOR--;
			}else{
				i.accept(this);
			}
		}
		contaOR = 0;
	}

	@Override
	public void visit(TermB t) {
		
        LinkedList<FactB> l = t.termini();
        int contaAND = l.size()-1;
        for(FactB i : l){
        	if(contaAND>0){
            i.accept(this);
        	//System.out.print(" AND ");
        	j.append(" AND ");
        	contaAND--;
        	}else{
        		i.accept(this);
        	}
        }
        contaAND = 0;
	}

	@Override
	public void visit(FactB f) {
		
		int tipo = f.getTipo();
		if(tipo == 0){
			f.getLeftExpr().accept(this);
			f.getRelop().accept(this);
			f.getRightExpr().accept(this);
		}else if(tipo == 1){
			//System.out.print(" NOT ");
			j.append(" NOT ");
			f.getNotFactB().accept(this);
		}else{//tipo == 2
			f.getCond().accept(this);
		}

	}

	@Override
	public void visit(Expr e) {
		
		LinkedList<PMTerm> l = e.ternimi();
		for(PMTerm i : l)
			i.accept(this);
     
	}

	@Override
	public void visit(PMTerm t) {
		if(t.conSegno()){
		String segno = t.getSegno();
		//System.out.print(segno);
		j.append(segno);
		t.getTermine().accept(this);
		}else
			t.getTermine().accept(this);
	}

	@Override
	public void visit(Term t) {
		
		t.getPrimo().accept(this);
		if(t.ciSonoAltri()){
			LinkedList<MDRTerm> l = t.termini();
			for(MDRTerm i : l)
				i.accept(this);
		}

	}

	@Override
	public void visit(Relop r) {
		//System.out.print(r.getString());
        j.append(r.getString());
	}

	@Override
	public void visit(TermP t) {
		
		t.getPrimo().accept(this);
		if(t.ciSonoAltri()){
			LinkedList<Fact> l = t.termini();
			for(Fact i : l){
				//System.out.print("^");
				j.append("^");
				i.accept(this);
			}
		}

	}

	@Override
	public void visit(MDRTerm t) {
		int tipo = t.getTipo();
		if(tipo == 0){
			//System.out.print("*");
			j.append("*");
			t.getTermine().accept(this);
		}else if(tipo == 1){
			//System.out.print("/");
			j.append("/");
			t.getTermine().accept(this);;
		}else{
			//System.out.print("%");
			j.append("%");
			t.getTermine().accept(this);
		}

	}

	@Override
	public void visit(Fact f) {
		int tipo = f.getTipo();
		if(tipo == 0){
			f.getId().accept(this);
		}else if(tipo == 1){
			f.getNum().accept(this);
		}else{
			f.getExpr().accept(this);
		}

	}

	@Override
	public void visit(Id i) {
		//System.out.print(i.getAspetto());
        j.append(i.getAspetto());
	}

	@Override
	public void visit(Num n) {
		//System.out.print(n.valore());
        j.append(new Integer(n.valore()).toString());
	}

}
