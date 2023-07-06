package componentiEspressione;

public class Ge extends Relop{

	@Override
	public boolean interpreta(int left,int right){
		return left >= right;
	}
	public String getString(){
		return ">=";
	}
}
