package automata;

public class Transicion {
	private int estadoF;
	private char symbol;
	
	public Transicion(int st, char s) {
		estadoF = st;
		symbol = s;
	}
	
	public int getId() {
		return estadoF;
	}
	public char getSymb() {
		return symbol;
	}
	public boolean compare(Transicion tr) {
		return (this.estadoF == tr.estadoF && tr.symbol == this.symbol);
	}
	public String toString() {
		return ("Viaja a " + estadoF + " por " + symbol); 
	}
}
