package automata;

public class Transicion {
	private int estadoF;
	private String symbol;

	public Transicion(int st, String simb) {
		estadoF = st;
		symbol = simb;
	}

	public int getId() {
		return estadoF;
	}

	public String getSymb() {
		return symbol;
	}

	public String toString() {
		return ("Viaja a " + estadoF + " por " + symbol);
	}

	public int getEstadoDest() {
		return estadoF;
	}
	/**
	 * 
	 * Comprueba si dos transiciones son iguales. Sobreescribe de la clase Object
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
	    if (!(o instanceof Estado)) {
	           return false;
	    }
	    Transicion tr = (Transicion) o;
		return (this.estadoF == tr.estadoF && this.symbol.equals(tr.symbol));
	}
	
	@Override
	public int hashCode() {
		return this.estadoF;
	}
}
