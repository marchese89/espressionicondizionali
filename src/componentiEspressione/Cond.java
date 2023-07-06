package componentiEspressione;

import java.util.LinkedList;

import partiFunzionali.EC_VisitorIF;

public class Cond implements ExpCondComponent{

	private LinkedList<TermB> componenti;
	
	public Cond(TermB t){
		componenti = new LinkedList<TermB>();
		componenti.add(t);
	}
	private void addTermB(TermB t){
		componenti.add(t);
	}
	
	public boolean interpreta(){
		for( TermB i : componenti)
			if(i.interpreta())
				return true;
		return false;
	}
	public void accept(EC_VisitorIF v){
		v.visit(this);
	}
	
	public LinkedList<TermB> termini(){
		LinkedList<TermB> l = new LinkedList<TermB>();
		for(TermB i:componenti)
			l.add(i);
		
		return l;
	}
	@Override
	public void add(ExpCondComponent c) {
		try{
		TermB t = (TermB)c;
		addTermB(t);
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
		return componenti.get(index);
	}
	
}
