package automata;

public class AutomataTS extends Automata {
	
	public AutomataTS() { super(); }
	public AutomataTS(int ini) { super(ini); }
	public AutomataTS(int ini, int acept) { super(ini, acept); }
	
	/* Funciones para Thompson simplificado */

	/**
	 * Une los estados con id. es1 y es2, no elimina ninguno, aunque deja es2
	 * inalcanzable
	 */
	public void unirSinEliminar(int es1, int es2) {
		aut.get(es1).unir(aut.get(es2).getTrans());
		aut.forEach((k, v) -> v.recolocarTransicionesSinBorrar(es2, es1));
	}

	/**
	 * Hace una copia de todo el automata sin las refenrencias
	 */
	public void copyAll(Automata aut) {
		this.aut.putAll(aut.aut);
	}

	/**
	 * copiar la lista de referencias a los estados finales
	 * 
	 * @param aut
	 */
	public void copyFinals(Automata aut) {
		this._acept.addAll(aut._acept);
	}

	/**
	 * pone a estado final de ini al parametro
	 */
	public void IniFinalEs(boolean es) {
		_ini.cambioFin(es);
	}

	/**
	 * Hace que el estado inicial deje de ser inicial
	 */
	public void quitarIni() {
		_ini.cambioIni(false);
		_ini.cambioFin(false);
	}

	/**
	 * pone todos los estados finales no finales
	 */
	public void quitarTodosFin() {
		for (Estado e : _acept)
			e.cambioFin(false);
	}

	/**
	 * Limpia la lista de los estados de aceptación
	 */
	public void finClear() {
		this._acept.clear();
	}
	/*
	 * End Funciones para Thomson Simplificado
	 */
}

