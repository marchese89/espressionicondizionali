package componentiEspressione;

import partiFunzionali.EC_VisitorIF;

public class Num implements ExpCondComponent{
	
	private int valore;
	
	public Num(int valore){
		this.valore = valore;
	}

	public int valore(){
		return valore;
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
