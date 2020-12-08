package automata;

public class Transicion {
	private int estadoF;
	private char symbol;
	
	public Transicion(int st, char s) {
		estadoF = st;
		symbol = s;
	}
	public String toString() {
		return ("Viaja a " + estadoF + " por " + symbol); 
	}
}
