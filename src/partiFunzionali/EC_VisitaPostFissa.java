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

public class EC_VisitaPostFissa implements EC_VisitorIF {
	
	private JTextArea j;
	
	public EC_VisitaPostFissa(JTextArea j){
		this.j = j;
	}
	public EC_VisitaPostFissa(){}
	@Override
	public void visit(Cond c) {
		LinkedList<TermB> l = c.termini();
		int contaOR = l.size()-1;
		
		for(TermB i:l)
		    i.accept(this);
		for(int i = contaOR;i>0;i--){
			//System.out.print(" OR");
			j.append(" OR");
		}
		//System.out.print(" ");
        j.append(" ");
	}

	@Override
	public void visit(TermB t) {
		LinkedList<FactB> l = t.termini();
        int contaAND = l.size()-1;
        
        for(FactB i:l)
        	i.accept(this);
        for(int i = contaAND;i>0;i--){
        	//System.out.print(" AND");
        	j.append(" AND");
        }
        //System.out.print(" ");
        j.append(" ");
	}

	@Override
	public void visit(FactB f) {
		int tipo = f.getTipo();
		if(tipo == 0){
			f.getLeftExpr().accept(this);
			f.getRightExpr().accept(this);
			f.getRelop().accept(this);
		}else if(tipo == 1){
			f.getNotFactB().accept(this);
			//System.out.print(" NOT");
			j.append(" NOT");
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
		t.getTermine().accept(this);
		//System.out.print(segno);
		j.append(segno);
		}else{
			t.getTermine().accept(this);
		}
		
		

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
		int contaPow = 0;
		if(t.ciSonoAltri()){
			LinkedList<Fact> l = t.termini();
			for(Fact i : l){
				contaPow++;
				i.accept(this);
			}
			for(int i= contaPow;i>0;i--){
				//System.out.print("^");
				j.append("^");
			}
		}

	}

	@Override
	public void visit(MDRTerm t) {
		int tipo = t.getTipo();
		if(tipo == 0){
			t.getTermine().accept(this);
			//System.out.print("*");
			j.append("*");
		}else if(tipo == 1){			
			t.getTermine().accept(this);
			//System.out.print("/");
			j.append("/");
		}else{
			t.getTermine().accept(this);
			//System.out.print("%");
			j.append("%");
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
