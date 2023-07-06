package componentiEspressione;

public class Neq extends Relop{

	@Override
	public boolean interpreta(int left,int right){
		return left != right;
	}
    @Override
    public String getString(){
    	return "!=";
    }
}
