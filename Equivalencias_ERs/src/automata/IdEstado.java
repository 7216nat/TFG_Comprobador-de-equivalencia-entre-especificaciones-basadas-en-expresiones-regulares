package automata;

public class IdEstado {
	private int id;
	public IdEstado () {
		id = 0;
	}
	
	public int nextId() {
		return id++;
	}
	
	public int getId() {
		return id;
	}
}
