package automata;

public class Transicion {
	private String estadoF;
	private char symbol;
	
	public Transicion(String st, char s) {
		estadoF = st;
		symbol = s;
	}
	public String toString() {
		return ("Viaja a " + estadoF.toString() + " por " + symbol); 
	}
}
