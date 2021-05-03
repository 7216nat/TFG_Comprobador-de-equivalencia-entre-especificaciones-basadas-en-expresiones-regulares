package automata;

public class Transicion {
	private int estadoF;
	private String symbol;

	/**
	 * 
	 * @param st: estado al que va
	 * @param simb: simbolo por el que se sigue la transicion
	 */
	public Transicion(int st, String simb) {
		estadoF = st;
		symbol = simb;
	}

/**
 * 
 * @return el simbolo por el que se sigue la transicion
 */
	public String getSymb() {
		return symbol;
	}

	@Override
	public String toString() {
		return ("Viaja a " + estadoF + " por " + symbol);
	}
	/**
	 * 
	 * @return el estado final de la transicion
	 */
	public int getEstadoDest() {
		return estadoF;
	}
	/**
	 * 
	 * Comprueba si dos transiciones son iguales. Sobreescribe de la clase Object
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
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
