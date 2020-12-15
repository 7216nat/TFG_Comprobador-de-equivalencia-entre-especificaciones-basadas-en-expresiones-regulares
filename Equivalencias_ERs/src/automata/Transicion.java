package automata;

public class Transicion {
	private int estadoF;
	private char symbol;

	public Transicion(int st, char simb) {
		estadoF = st;
		symbol = simb;
	}

	public int getId() {
		return estadoF;
	}

	public char getSymb() {
		return symbol;
	}
	
	/**
	 * compruba si dos transiciones son iguales
	 * @param tr
	 * @return
	 */
	public boolean compare(Transicion tr) {
		return (this.estadoF == tr.estadoF && tr.symbol == this.symbol);
	}

	public String toString() {
		return ("Viaja a " + estadoF + " por " + symbol);
	}

	public int getEstadoDest() {
		return estadoF;
	}
}
