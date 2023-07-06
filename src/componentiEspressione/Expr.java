package componentiEspressione;

import java.util.LinkedList;

import partiFunzionali.EC_VisitorIF;

public class Expr implements ExpCondComponent{

	private LinkedList<PMTerm> componenti;
	
	public Expr(PMTerm t){
		componenti = new LinkedList<PMTerm>();
		componenti.add(t);
	}
	private void addPMTerm(PMTerm t){
		componenti.add(t);
	}
	
	public int interpreta(int index){
		int i = 0;
		for(PMTerm p:componenti){
			i+=p.interpreta(0);
		}
		return i;
	}
	
	public void accept(EC_VisitorIF v){
		v.visit(this);
	}
	
	public LinkedList<PMTerm> ternimi(){
		LinkedList<PMTerm> l = new LinkedList<PMTerm>();
		for(PMTerm i : componenti)
			l.add(i);
		
		return l;
	}
	@Override
	public void add(ExpCondComponent c) {
		try{
        PMTerm t = (PMTerm)c;
		addPMTerm(t);
		}catch(ClassCastException e){
			e.printStackTrace();
		}
	}
	@Override
	public void remove(ExpCondComponent c) {
		PMTerm t1 = (PMTerm)c;
		for(int i = 0;i<componenti.size();i++)
			if(componenti.get(i).equals(t1))
			   componenti.remove(i);
		
	}
	@Override
	public ExpCondComponent getChild(int index) {
		return (ExpCondComponent)componenti.get(index);
	}
	@Override
	public boolean interpreta() {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(getClass() != o.getClass())
			return false;
		Expr other = (Expr)o;
		LinkedList<PMTerm> terminiOther = other.ternimi();
		if(componenti.size()!=terminiOther.size())
			return false;
		for(PMTerm i : componenti)
			if(!terminiOther.contains(i))
				return false;
		return true;
	}
}
