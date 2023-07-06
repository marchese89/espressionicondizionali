package componentiEspressione;

public class Lt extends Relop{

	@Override
	public boolean interpreta(int left,int right){
		return left < right;
	}
	public String getString(){
		return "<";
	}
}
