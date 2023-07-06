package componentiEspressione;

import partiFunzionali.EC_VisitorIF;

public class Id implements ExpCondComponent{
	
	private String aspetto;
    private int valore = 0;
    
    public Id(String aspetto,int valore){
    	this.aspetto = aspetto;
    	this.valore = valore;
    }
    public int valore(){
    	return valore;
    }
    public String getAspetto(){
    	return aspetto;
    }
    public void setValore(int val){
    	valore = val;
    }
    public void accept(EC_VisitorIF v){
		v.visit(this);
	}
	@Override
	public boolean interpreta() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void add(ExpCondComponent c) {
		throw new UnsupportedOperationException();
		
	}
	@Override
	public void remove(ExpCondComponent c) {
		throw new UnsupportedOperationException();
		
	}
	@Override
	public ExpCondComponent getChild(int index) {
		throw new UnsupportedOperationException();
	}
}
