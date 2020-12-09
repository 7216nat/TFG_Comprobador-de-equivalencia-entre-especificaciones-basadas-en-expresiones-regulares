package automata;

import java.util.ArrayList;
import java.util.HashMap;

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
	public void unir(int es1, int es2) {
		aut.get(es1).unir(aut.get(es2).getTrans());
	}
	
	public void eliminarEstado(int estado) {
		aut.remove(estado);
	}

	public void show() {
		aut.forEach((k, v) -> System.out.println("Key: " + k + ": Value: " + v.toString()));

	}
}
