package componentiEspressione;

import partiFunzionali.EC_VisitorIF;

public class Relop implements ExpCondComponent{
	
	public String getString(){
		throw new UnsupportedOperationException();
	}
	
	public boolean interpreta(int left,int right){
		return false;
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
