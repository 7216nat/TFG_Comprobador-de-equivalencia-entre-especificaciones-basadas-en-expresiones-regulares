package automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import objects.ExpressionBase;
import objects.String_ref;
import parser.ParserER;

public class Automata {
	private HashMap<Integer, ArrayList<Transicion>> aut;
	// El nombre del proximo estado que se creara
	private int cont;

	public Automata() {
		aut = new HashMap<Integer, ArrayList<Transicion>>();
		cont = 0;
	}

	public void addEstado() {
		ArrayList<Transicion> aux = new ArrayList<Transicion>();
		aut.put(cont, aux);
		cont++;
	}

	public void addTransicion(int estado, int dest, char simb) {
		if (aut.containsKey(estado) && aut.containsKey(dest)) {
			Transicion nuevaT = new Transicion(dest, simb);
			if (!aut.get(estado).contains(nuevaT))
				aut.get(estado).add(nuevaT);
		}
	}

	public void eliminarEstado(int estado) {
		aut.remove(estado);
	}

	public void show() {
		aut.forEach((k, v) -> System.out.println("Key: " + k + ": Value: " + v.toString()));

	}

}
