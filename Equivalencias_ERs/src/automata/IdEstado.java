package automata;

/**
 * Id unico para los estados
 */
public class IdEstado {
	private int id;
	
	/**
	 * constructora por defecto
	 */
	public IdEstado () {
		id = 0;
	}
	
	/**
	 * devuelve el valor anterior y luego suma uno
	 */
	public int nextId() {
		return id++;
	}
	
	public int getId() {
		return id;
	}
}
