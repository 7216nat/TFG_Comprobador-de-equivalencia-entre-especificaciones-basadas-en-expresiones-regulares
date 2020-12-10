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
	public boolean compare(Transicion tr) {
		return (this.estadoF == tr.estadoF && tr.symbol == this.symbol);
	}
	public String toString() {
		return ("Viaja a " + estadoF + " por " + symbol); 
	}
}
