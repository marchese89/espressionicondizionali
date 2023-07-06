package componentiEspressione;

import java.util.LinkedList;

import partiFunzionali.EC_VisitorIF;

public class TermP implements ExpCondComponent{
	
	private LinkedList<Fact> altri;
	private Fact primo;
	public TermP(Fact f){
		primo = f;
		altri = new LinkedList<Fact>();
		
	}
	
	public void addFact(Fact f){
		altri.add(f);
	}
	public int interpreta(int index){
		int i;
		if(altri.size() == 0)
			return primo.interpreta(0);
		else{
			i = primo.interpreta(0);
			for(Fact j: altri){
				
				double j1 = Double.parseDouble(new Integer(j.interpreta(0)).toString());
				
				double ris = new Double(Math.pow(Double.parseDouble
						(new Integer(i).toString()),j1));
				i = (int)ris;
				
			}
		}
		return i;
	}
	public void accept(EC_VisitorIF v){
		v.visit(this);
	}
	public Fact getPrimo(){
		return primo;
	}
	public boolean ciSonoAltri(){
		return altri.size()>0;
	}
	public LinkedList<Fact> termini(){
		if(!ciSonoAltri())
			throw new UnsupportedOperationException();
		LinkedList<Fact> l = new LinkedList<Fact>();
		for(Fact i : altri)
			l.add(i);
		return l;
	}

	@Override
	public boolean interpreta() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(ExpCondComponent c) {
		try{
		altri.add((Fact)c);
		}catch(ClassCastException e){
			e.printStackTrace();
		}
	}

	@Override
	public void remove(ExpCondComponent c) {
		Fact t1 = (Fact)c;
		for(int i = 0;i<altri.size();i++)
			if(altri.get(i).equals(t1))
			   altri.remove(i);
		
	}

	@Override
	public ExpCondComponent getChild(int index) {
	     if(index == -1)
	    	 return primo;
	     else
	    	 return altri.get(index);
	}
	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(getClass() != o.getClass())
			return false;
		TermP other = (TermP)o;
		if(!primo.equals(other.getPrimo()))
			return false;
		LinkedList<Fact> terminiOther = other.termini();
		if(altri.size()!= terminiOther.size())
			return false;
		
		for(Fact i:altri)
			if(!terminiOther.contains(i))
				return false;
		
		return true;
	}
}
