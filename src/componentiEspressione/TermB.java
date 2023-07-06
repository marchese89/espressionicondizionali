package componentiEspressione;

import java.util.LinkedList;

import partiFunzionali.EC_VisitorIF;

public class TermB implements ExpCondComponent{

	private LinkedList<FactB> componenti;
	
	public TermB(FactB t){
		componenti = new LinkedList<FactB>();
		componenti.add(t);
	}
	private void addFactB(FactB t){
		componenti.add(t);
	}
	
	public boolean interpreta(){
		for( FactB i : componenti)
			if(!i.interpreta())
				return false;
		return true;
	}
	public void accept(EC_VisitorIF v){
		v.visit(this);
	}
	public LinkedList<FactB> termini(){
		LinkedList<FactB> l = new LinkedList<FactB>();
		for(FactB i:componenti)
			l.add(i);
		
		return l;
	}
	@Override
	public void add(ExpCondComponent c) {
		try{
		FactB t = (FactB)c;
		addFactB(t);
		}catch(ClassCastException e){
			e.printStackTrace();
		}
		
	}
	@Override
	public void remove(ExpCondComponent c) {
		if(componenti.size()==1)
			return;
		for(int i = 0 ;i < componenti.size();i++)
			if(componenti.get(i).equals(c))
				componenti.remove(i);
		
	}
	@Override
	public ExpCondComponent getChild(int index) {
		return (ExpCondComponent)componenti.get(index);
	}
	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(getClass() != o.getClass())
			return false;
		TermB other = (TermB)o;
		LinkedList<FactB> terminiOther = other.termini();
		if(componenti.size() != terminiOther.size())
			return false;
		for(FactB i : componenti)
			if(!terminiOther.contains(i))
				return false;
		
		return true;
	}

}
