package objects;

public enum Tipo {
	CONCAT(1),
	KLEEN(2),
	LAMBDA(3),
	RANGO(4),
	SIMB(5),
	UNION(6),
	UNIONRANGOS(7),
	VACIO(8); 
	Tipo(int val) {
		this.valor = val;
	}

	private final int valor;
	
	public int getValor() {
		return this.valor;
	}
}
