package automata;

import java.util.ArrayList;
import java.util.HashMap;

public class Automata {
	private HashMap<Integer, Estado> aut;
	// referencias al estado inicial y final
	private Estado _ini;
	private ArrayList<Estado> _acept;
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
		_acept = new ArrayList<Estado> ();
		aut = new HashMap<Integer, Estado>();
		if (ini != acept) {
			_ini = new Estado(ini, true, false);
			Estado tmp = new Estado(acept, false, true);
			_acept.add(tmp);
			addEstado(_ini);
			addEstado(tmp);
		}
		else {
			_ini = new Estado(ini, true, true);
			_acept.add(_ini);
			addEstado(_ini);
		}
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
	
	
	/**
	 * Funciones para Thomson Simplificado
	 * 
	 * copia del otro automata
	 */
	public void unirSinEliminar(int es1, int es2) {
		aut.get(es1).unir(aut.get(es2).getTrans());
		aut.forEach((k, v) -> v.recolocarTransicionesSinBorrar(es2, es1));
		//eliminarEstado(es2);
	}
	
	public void copyAll(Automata aut) {
		this.aut.putAll(aut.getAutomata());
	}
	
	public void copyFinals(Automata aut) {
		this._acept.addAll(aut.getFin());
	}
	
	public void IniEsFin() {
		_ini.cambioFin(true);
		_acept.add(_ini);
	}
	
	public void cambioIni(boolean ini) {
		_ini.cambioIni(ini);
	}

	public void cambioFin(boolean fin) {
		for (Estado e: _acept) e.cambioFin(fin);
	}

	public Estado getIni() {
		return _ini;
	}

	public ArrayList<Estado> getFin() {
		return _acept;
	}
	
	public void finClear() {
		this._acept.clear();
	}
	/*
	 * End Funciones para Thomson Simplificado
	 */
	
	
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
