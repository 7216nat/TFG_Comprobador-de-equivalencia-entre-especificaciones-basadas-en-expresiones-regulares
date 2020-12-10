package automata;

import java.util.HashMap;

public class Automata {
	private HashMap<Integer, Estado> aut;
	// El nombre del proximo estado que se creara
	private int _ini;
	private int _acept;
	// problemente necesitemos ArrayList ac

	public Automata() {
		aut = new HashMap<Integer, Estado>();
	}

	public Automata(int ini) {
		_ini = ini;
		aut = new HashMap<Integer, Estado>();
		addEstado(new Estado(ini, true, false));
	}

	public Automata(int ini, int acept) {
		_ini = ini;
		_acept = acept;
		aut = new HashMap<Integer, Estado>();
		addEstado(new Estado(ini, true, false));
		addEstado(new Estado(acept, false, true));
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
		aut.get(_ini).cambioIni(ini);
	}

	public void cambioFin(boolean fin) {
		this.aut.get(_acept).cambioFin(fin);
	}

	public int getIni() {
		return _ini;
	}

	public int getFin() {
		return _acept;
	}

	public void show() {
		// aut.forEach((k, v) -> + ));
		for (Integer k : aut.keySet()) {
			Estado aux = aut.get(k);
			if (k == _ini)
				System.out.print("-Estado Inicial- ");
			if (k == _acept)
				System.out.print("-Estado Final- ");
			System.out.print("Key: " + k + ": Transitions: ");
			System.out.println(aux.toString());
		}

	}
}
