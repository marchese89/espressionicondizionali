package componentiEspressione;

public interface ExpCondComponent {

	boolean interpreta();
	void add(ExpCondComponent c);
	void remove(ExpCondComponent c);
	ExpCondComponent getChild(int index);
}
