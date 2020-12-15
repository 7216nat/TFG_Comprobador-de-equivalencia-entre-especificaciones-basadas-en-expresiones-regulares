package algoritmo;


public class SimEsEs {
	private int estado1;
	private int estado2;
	private String simbolos;
	
	public SimEsEs(int es1, int es2, String sim) {
		estado1 = es1;
		estado2 = es2;
		simbolos = sim;
	}

	public int getEstado1() {
		return estado1;
	}


	public int getEstado2() {
		return estado2;
	}


	public String getSimbolos() {
		return simbolos;
	}
}
