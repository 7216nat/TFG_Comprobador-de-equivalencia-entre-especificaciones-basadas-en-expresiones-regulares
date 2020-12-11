package automata;

import java.util.HashMap;

public class Automata {
	private HashMap<Integer, Estado> aut;
	// referencias al estado inicial y final
	private Estado _ini;
	private Estado _acept;
	// problemente necesitemos ArrayList para los estados de aceptacion
	
	/**
	 * Constructora por defecto
	 */
	public Automata() {
		aut = new HashMap<Integer, Estado>();
	}
	
	/*
	 * Constructora para el conjunto vacio
	 */
	public Automata(int ini) {
		_ini = new Estado(ini, true, false);
		aut = new HashMap<Integer, Estado>();
		addEstado(_ini);
	}
	
	/**
	 * Constructora para automata cualquiera 
	 */
	public Automata(int ini, int acept) {
		_ini = new Estado(ini, true, false);
		_acept = new Estado(acept, false, true);
		aut = new HashMap<Integer, Estado>();
		addEstado(_ini);
		addEstado(_acept);
	}

	public void addEstado(Estado estado) {
		aut.put(estado.getId(), estado);
	}

	public void addTransicion(int estado, int dest, String simb) {
		aut.get(estado).addTrans(new Transicion(dest, simb));
	}

	public HashMap<Integer, Estado> getAutomata() {
		return this.aut;
	}
	
	/**
	 * copia del otro automata
	 */
	public void copyAll(Automata aut) {
		this.aut.putAll(aut.getAutomata());
	}

	/**
	 * es1 es el estado en que quedarán los estados es1 y es2 Se elimina es2
	 */
	public void unir(int es1, int es2) {
		aut.get(es1).unir(aut.get(es2).getTrans());
		aut.forEach((k, v) -> v.recolocarTransiciones(es2, es1));
		eliminarEstado(es2);
	}

	public void eliminarEstado(int estado) {
		aut.remove(estado);
		aut.forEach((k, v) -> v.eliminarTransicionesA(estado));
	}

	public void cambioIni(boolean ini) {
		_ini.cambioIni(ini);
	}

	public void cambioFin(boolean fin) {
		_acept.cambioFin(fin);
	}

	public int getIni() {
		return _ini.getId();
	}

	public int getFin() {
		return _acept.getId();
	}

	public void show() {
		// aut.forEach((k, v) -> + ));
		for (Integer k : aut.keySet()) {
			Estado aux = aut.get(k);
			if (aux.esIni())
				System.out.print("-Estado Inicial- ");
			if (aux.esFin())
				System.out.print("-Estado Final- ");
			System.out.print("Key: " + k + ": Transitions: ");
			System.out.println(aux.toString());
		}

	}
}
