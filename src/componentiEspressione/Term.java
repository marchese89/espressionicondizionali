package componentiEspressione;

import java.util.LinkedList;

import partiFunzionali.EC_VisitorIF;

public class Term implements ExpCondComponent{
	
	private TermP primo;
	private LinkedList<MDRTerm> altri;
	
	public Term(TermP t){
		primo = t;
		altri = new LinkedList<MDRTerm>();
	}
    private void addMDRTerm(MDRTerm t){
    	altri.add(t);
    }
    public int interpreta(int index){
    	int i = primo.interpreta(0);
    	for(MDRTerm t : altri){
    		i = t.interpreta(i);
    	}
    	return i;
    }
    public void accept(EC_VisitorIF v){
		v.visit(this);
	}
    public TermP getPrimo(){
    	return this.primo;
    }
    public boolean ciSonoAltri(){
    	return altri.size()>0;
    }
    public LinkedList<MDRTerm> termini(){
    	
    	if(!ciSonoAltri())
    		throw new IllegalStateException();
    	
    	LinkedList<MDRTerm> l = new LinkedList<MDRTerm>();
    	for(MDRTerm i : altri)
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
			MDRTerm t = (MDRTerm)c;
			addMDRTerm(t);
		}catch(ClassCastException e){
			e.printStackTrace();
		}
		
	}
	@Override
	public void remove(ExpCondComponent c) {
		MDRTerm t1 = (MDRTerm)c;
		for(int i = 0;i<altri.size();i++)
			if(altri.get(i).equals(t1))
			   altri.remove(i);
		
	}
	@Override
	public ExpCondComponent getChild(int index) {
		if(index == -1)
			return (ExpCondComponent)primo;
		
		return (ExpCondComponent)altri.get(index);
	}
	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(getClass() != o.getClass())
			return false;
		Term other = (Term)o;
		if(!primo.equals(other.getPrimo()))
			return false;
		LinkedList<MDRTerm> terminiOther = other.termini();
		if(altri.size()!= terminiOther.size())
			return false;
		for(MDRTerm i : altri)
			if(!terminiOther.contains(i))
				return false;
		
		return true;

	}
}
