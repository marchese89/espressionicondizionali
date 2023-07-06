package componentiEspressione;

import eccezioni.BadTypeException;
import partiFunzionali.EC_VisitorIF;

public class Fact implements ExpCondComponent{
	
	/*
	 * tipo 0: ID
	 * tipo 1: NUM
	 * tipo 2: Expr 
	 */
     private Id id;
     private Num num;
     private Expr expr;
     private int tipo;
     
     public Fact(Object o,int tipo){
    	 
    	 this.tipo = tipo;
    	 if(tipo ==0){
    		 id = (Id)o;
    	 }
    	 if(tipo == 1){
    		 num = (Num)o;
    	 }
    	 if(tipo == 2){
    		 expr = (Expr)o;
    	 }
     }
     
     public int interpreta(int index){
    	 if(tipo == 0)
    		 return id.valore();
    	 if(tipo == 1)
    		 return num.valore();
    	 else
    		 return expr.interpreta(0);
     }
     
     public void accept(EC_VisitorIF v){
 		v.visit(this);
 	}
    public int getTipo(){
    	return this.tipo;
    }
    public Id getId(){
    	if(tipo != 0)
    		throw new BadTypeException();
    	return this.id;
    }
    public Num getNum(){
    	if(tipo != 1)
    		throw new BadTypeException();
    	return this.num;
    }
    public Expr getExpr(){
    	if(tipo != 2)
    		throw new BadTypeException();
    	return this.expr;
    }

	@Override
	public void add(ExpCondComponent c) {
		try{
		if(tipo == 0){
			Id id = (Id)c;
			this.id = id;
		}
		if (tipo == 1){
			Num n = (Num)c;
			this.num = n;
		}
		if(tipo == 2){
			Expr e = (Expr)c;
			this.expr = e;
		}
		}catch(ClassCastException e){
			e.printStackTrace();
		}
	}

	@Override
	public void remove(ExpCondComponent c) {
	
		try{
		if(tipo == 0){
			Id id = (Id)c;
			if(this.id.equals(id))
			this.id = null;
		}
		if (tipo == 1){
			Num n = (Num)c;
			if(this.num.equals(n))
			this.num = null;
		}
		if(tipo == 2){
			Expr e = (Expr)c;
			if(this.expr.equals(e))
			this.expr = null;
		}
		}catch(ClassCastException e){
			
		}
	}

	@Override
	public ExpCondComponent getChild(int index) {
		if(tipo == 0){
		   return (ExpCondComponent)id;
		}
		else if (tipo == 1){
	       return (ExpCondComponent)num;
		}
		else{//tipo == 2
			return expr;
		}
	}

	@Override
	public boolean interpreta() {
		throw new UnsupportedOperationException();
	}
}
