package partiFunzionali;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import componentiEspressione.Cond;
import componentiEspressione.Eq;
import componentiEspressione.Expr;
import componentiEspressione.Fact;
import componentiEspressione.FactB;
import componentiEspressione.Ge;
import componentiEspressione.Gt;
import componentiEspressione.Id;
import componentiEspressione.Le;
import componentiEspressione.Lt;
import componentiEspressione.MDRTerm;
import componentiEspressione.Neq;
import componentiEspressione.Num;
import componentiEspressione.PMTerm;
import componentiEspressione.Relop;
import componentiEspressione.Term;
import componentiEspressione.TermB;
import componentiEspressione.TermP;
import eccezioni.BadFileException;
import eccezioni.SintaxErrorException;

public class EC_BuilderImpl implements EC_BuilderIF{

	
	private AnalizzatoreLessicale al;
	private LinkedList<Id> variabili;
	
	public EC_BuilderImpl(AnalizzatoreLessicale al){
		this.al = al;
		variabili = new LinkedList<Id>();
	}
	public Cond build(){
		
		return buildCond();
		 
	}
	@Override
	public TermB buildTermB() {
		FactB f = buildFactB();
		TermB t = new TermB(f);
		while(al.prossimoSimbolo() == Simbolo.AND){
			t.add(buildFactB());
		}
		al.indietro();
		return t;
		
	}

	@Override
	public FactB buildFactB() {
		
		Simbolo s = al.prossimoSimbolo();
		//System.out.println("in buildFactB simbolo: " +s);
		if( s == Simbolo.NOT){
			return new FactB(null,null,null,null,buildFactB(),1);
		}else if(s == Simbolo.OPAR){
			FactB f= new FactB(null,null,null,buildCond(),null,2);
			if(al.prossimoSimbolo() != Simbolo.CPAR)
				throw new SintaxErrorException();
			return f;
		}else{
			al.indietro();
		}
		Expr left = buildExpr();
		Relop r = null;
		Simbolo relop = al.prossimoSimbolo();

		boolean eccezione = false;
		switch(relop){
		     case EQ: r = new Eq();break;
		     case NEQ: r = new Neq();break;
		     case GT: r = new Gt();break;
		     case GE: r = new Ge();break;
		     case LT: r = new Lt();break;
		     case LE: r = new Le();break;
		     default: eccezione = true;
		    		
		}
		if(eccezione){
			//System.out.println("simbolo eccezione: "+relop);
			throw new SintaxErrorException();
		    	
		}
		Expr right = buildExpr();
		
		return new FactB(left,right,r,null,null,0);
		
	}

	@Override
	public Expr buildExpr() {
		
		Simbolo s = al.prossimoSimbolo();
		
		PMTerm t = null;
		PMTerm t_generico = null;
		Expr e = null;
	    if(s == Simbolo.PLUS){
	    	t = new PMTerm("+",buildTerm());
	    }else if(s == Simbolo.MINUS){
	    	t = new PMTerm("-",buildTerm());	
	    }else if (s == Simbolo.ID || s == Simbolo.NUM || s == Simbolo.OPAR1){
	    	
	    	al.indietro();
	    	t = new PMTerm(buildTerm());
	    		
	    }else{
	    	//System.out.println(s);
	    	throw new SintaxErrorException();
	    }
	    e = new Expr(t);

	    Simbolo s1 = al.prossimoSimbolo();
	
	    while(s1 == Simbolo.MINUS || s1 == Simbolo.PLUS){
	    	if(s1 == Simbolo.PLUS){
	   	    	t_generico = new PMTerm("+",buildTerm());
	   	    	
	   	    }
	   	    if(s1 == Simbolo.MINUS){
	   	    	t_generico = new PMTerm("-",buildTerm());	
	   	    	
	   	    }
	   	    e.add(t_generico);
	   	    s1 = al.prossimoSimbolo();
	    }
	    al.indietro();
	    
	    	
		return e;
		
	}

	@Override
	public Term buildTerm() {
		
		Term t = new Term(buildTermP());
		Simbolo s = al.prossimoSimbolo();
		MDRTerm temp = null;
		while(s == Simbolo.MULT || s == Simbolo.DIV || s == Simbolo.REM){
			if(s == Simbolo.MULT){
				temp = new MDRTerm(buildTermP(),0);	
			}
			if(s == Simbolo.DIV){
				temp = new MDRTerm(buildTermP(),1);
			}
			if(s == Simbolo.REM){
				temp = new MDRTerm(buildTermP(),2);
			}
			t.add(temp);
			s = al.prossimoSimbolo();
		}
		al.indietro();
		return t;
	}

	@Override
	public TermP buildTermP() {
		
		TermP t = new TermP(buildFact());
		Simbolo s = al.prossimoSimbolo();
		while(s == Simbolo.POWER){
			
			t.addFact(buildFact());
			s = al.prossimoSimbolo();
		}
		al.indietro();
		return t;
		
	}

	@Override
	public Fact buildFact() {
		
		Fact f = null;
		Simbolo s = al.prossimoSimbolo();
		
		boolean eccezione = false;
		
		if(s == Simbolo.ID){
		   Id i = new Id(al.getID(),0);
		   variabili.add(i);
		   f = new Fact(i,0);
		}else if(s == Simbolo.NUM){
			Num n = new Num(al.getNumber());
			f = new Fact(n,1);
			
		}else if(s == Simbolo.OPAR1){
			Expr e = buildExpr();
			f = new Fact(e,2);
			if(al.prossimoSimbolo()!= Simbolo.CPAR1){
				
				eccezione = true;
				
			}
		}else{
			al.indietro();
			//System.out.println("simbolo imprevisto: "+al.prossimoSimbolo());
			throw new SintaxErrorException();
		}
		if(eccezione)
			throw new SintaxErrorException();
		
		return f;
		
	}
	@Override
	public Cond buildCond() {
		TermB primo = buildTermB();
		Cond c = new Cond(primo);
		while(al.prossimoSimbolo() == Simbolo.OR){
			c.add(buildTermB());
		}
		al.indietro();
		return c;
	}
	public void settaValoreId(String s,int val){
		for(Id i : variabili)
			if(i.getAspetto().equals(s))
				i.setValore(val);
	}
	public void SettaTuttiId(String f){
		File file = new File(f);
		HashMap<String,Integer> mappa = new HashMap<String,Integer>();
		try {
			Scanner s = new Scanner(file);
			StringTokenizer st; 
			while(s.hasNextLine()){
				String linea = s.nextLine();
				st = new StringTokenizer(linea,"= ");
				mappa.put(st.nextToken(), Integer.parseInt(st.nextToken()));
			}
		} catch (FileNotFoundException e) {
			throw new BadFileException();//se il file non c'è lanciamo questa eccezione
		}catch (Exception e){
			throw new BadFileException();//per probabili errori di sintassi nel file
		}
		Set<String> set = mappa.keySet();
		for (String i : set)
			settaValoreId(i, mappa.get(i));
	}
	
	public static void main(String[]args){
		String s = "(a<=b && c>1) || a==2 || (d == 0 and 9 !=  3 ^ 2 )";
		AnalizzatoreLessicale a = new AnalizzatoreLessicale(s);
		EC_BuilderImpl e = new EC_BuilderImpl(a);
		Cond c = e.build();
		e.SettaTuttiId("C:\\Users\\giovanni\\Desktop\\prova.txt");
		System.out.println(c.interpreta());
		EC_VisitaSimmetrica visita = new EC_VisitaSimmetrica();
		EC_VisitaPostFissa visita2 = new EC_VisitaPostFissa();
		c.accept(visita);
		System.out.println();
		c.accept(visita2);
	}

}
