package automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Automata {
	private HashMap<Integer, Estado> aut;
	// El nombre del proximo estado que se creara
	private int cont;

	public Automata() {
		aut = new HashMap<Integer, Estado>();
		cont = 0;
	}

	public void addEstado() {
		Estado est = new Estado(cont);
		aut.put(cont, est);
		cont++;
	}

	public void addTransicion(int estado, int dest, char simb) {
		aut.get(estado).addTrans(new Transicion(dest, simb));
	}
	
	/**
	 * es1 es el estado en que quedarán los estados es1 y es2
	 * Se elimina es2
	 */
	public void unir(int es1, int es2) {
		aut.get(es1).unir(aut.get(es2).getTrans());
		aut.forEach((k,v) -> v.recolocarTransiciones(es2, es1) );
		eliminarEstado(es2);
	}
	
	public void eliminarEstado(int estado) {
		aut.remove(estado);
		aut.forEach((k,v) -> v.eliminarTransicionesA(estado) );
	}
	
	public void cambioIni(int estado, boolean ini) {
		aut.get(estado).cambioIni(ini);
	}
	
	public void cambioFin (int estado, boolean fin) {
		this.aut.get(estado).cambioFin(fin);
	}
	
	public void show() {
		aut.forEach((k, v) -> System.out.println("Key: " + k + ": Value: " + v.toString()));

	}
}
