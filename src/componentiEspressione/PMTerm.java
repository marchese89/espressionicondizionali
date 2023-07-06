package componentiEspressione;

import partiFunzionali.EC_VisitorIF;

public class PMTerm implements ExpCondComponent{
	
	private String segno;
	private Term termine;
	private boolean conSegno = false;
	public PMTerm(String segno,Term termine){
		this.segno = segno;
		this.termine = termine;
		this.conSegno = true;
	}
	public PMTerm(Term termine){
		this.termine = termine;
	}
	public boolean conSegno(){
		return conSegno;
	}
    public boolean ePlus(){
    	return segno.charAt(0)=='+';
    }
    
    public int interpreta(int index){
    	if(conSegno()){
    	if(ePlus()){
    		return termine.interpreta(0);
    	}else{
    		return -termine.interpreta(0);
    	}
    	}else
    		return termine.interpreta(0);
    }
    public void accept(EC_VisitorIF v){
		v.visit(this);
	}
    public String getSegno(){
    	return this.segno;
    }
    public Term getTermine(){
    	return this.termine;
    }
	@Override
	public boolean interpreta() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void add(ExpCondComponent c) {
		if(termine != null)
		throw new UnsupportedOperationException();
		
	}
	@Override
	public void remove(ExpCondComponent c) {
		if(termine.equals(c))
			termine = null;
		
	}
	@Override
	public ExpCondComponent getChild(int index) {
		return termine;
	}
}
