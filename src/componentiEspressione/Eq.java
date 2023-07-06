package componentiEspressione;

public class Eq extends Relop{
	
	@Override
	public boolean interpreta(int left, int right){
		return left == right;
	}
	@Override
	public String getString(){
		return "==";
	}

}
