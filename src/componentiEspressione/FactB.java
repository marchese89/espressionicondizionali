package componentiEspressione;

import eccezioni.BadTypeException;
import partiFunzionali.EC_VisitorIF;

public class FactB implements ExpCondComponent{
	/*
	 * tipo 0: <expr> RELOP <expr>
	 * tipo 1: NOT <factb>
	 * tipo 2: OPAR <cond> CPAR
	 */
	private int tipo;
	private Expr left,right;
	private Relop r;
	private FactB notFactB;
	private Cond c;
	public FactB(Expr left,Expr right,Relop r,Cond c,FactB f,int tipo){
		this.tipo = tipo;
		if(tipo == 0){
			this.left = left;
			this.right = right;
			this.r = r;
		}
		if(tipo == 1){
			notFactB = f;
		}
		if(tipo == 2)
			this.c = c;
	}
	public boolean interpreta(){
		if(tipo ==0){
			int l1 = left.interpreta(0);
			int r1 = right.interpreta(0);
			return r.interpreta(l1,r1);
		}
		if(tipo == 1){
			return !notFactB.interpreta();
		}else{//tipo == 2
			return c.interpreta();
		}
	}
	
	public void accept(EC_VisitorIF v){
		v.visit(this);
	}
	public int getTipo(){
		return this.tipo;
	}
	public FactB getNotFactB(){
		
		if(tipo != 1)
			throw new BadTypeException();
		return notFactB;
	}
	
	public Cond getCond(){
		if(tipo != 2)
			throw new BadTypeException();
		
		return this.c;
	}
	public Relop getRelop(){
		if(tipo != 0)
			throw new BadTypeException();
		return r;
	}
	public Expr getLeftExpr(){
		if(tipo != 0)
			throw new BadTypeException();
		return this.left;
	}
	public Expr getRightExpr(){
		if(tipo != 0)
			throw new BadTypeException();
		return this.right;
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
		if(tipo == 0)
			if(index == 0)
				return (ExpCondComponent)left;
			else
				return (ExpCondComponent)right;
		if(tipo == 1)
			return notFactB;
		else //tipo == 2
			return c;
	}
	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(getClass() != o.getClass())
			return false;
		FactB other = (FactB)o;
		if(this.getTipo() != other.getTipo())
			return false;
		if(tipo == 0){
			if(!this.getRelop().equals(other.getRelop()))
			return false;
			
            return this.getLeftExpr().equals(other.getLeftExpr()) && 
            		this.getRightExpr().equals(other.getRightExpr());
		}
		if(tipo == 1)
			return this.getNotFactB().equals(other.getNotFactB());
		else//tipo == 2
			return this.getCond().equals(other.getCond());
		
		
	}

}
