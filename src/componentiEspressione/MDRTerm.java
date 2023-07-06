package componentiEspressione;

import partiFunzionali.EC_VisitorIF;

public class MDRTerm implements ExpCondComponent{
	/*
	 * tipo 0: MUL (*)
	 * tipo 1:DIV  (/)
	 * tipo 2: REM (%)
	 */
	private int tipo;
	private TermP termine;
	
	public MDRTerm(TermP t,int tipo){
		this.tipo = tipo;
		this.termine = t;
	}
	public int interpreta(int n){
		if(tipo == 0){
			return n*termine.interpreta(0);
		}
		if(tipo == 1){
			return n/termine.interpreta(0);
		}else{
			return n%termine.interpreta(0);
		}
	}
    
	public void accept(EC_VisitorIF v){
		v.visit(this);
	}
	public int getTipo(){
		return this.tipo;
	}
	public TermP getTermine(){
		return this.termine;
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
		return termine;
	}
	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(getClass() != o.getClass())
			return false;
		MDRTerm other = (MDRTerm)o;
		if(tipo != other.getTipo())
			return false;
		if(!termine.equals(other.getTermine()))
			return false;
		
		return true;
	}
}
