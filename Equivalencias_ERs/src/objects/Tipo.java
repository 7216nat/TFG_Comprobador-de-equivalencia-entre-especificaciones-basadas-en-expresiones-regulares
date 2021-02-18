package objects;

public enum Tipo {
	KLEENPOS(1),
	CONCAT(2),
	KLEEN(3),
	LAMBDA(4),
	LAMBDAEXP(5),
	RANGO(6),
	SIMB(7),
	UNION(8),
	UNIONRANGOS(9),
	VACIO(10); 
	Tipo(int val) {
		this.valor = val;
	}

	private final int valor;
	
	public int getValor() {
		return this.valor;
	}
}
