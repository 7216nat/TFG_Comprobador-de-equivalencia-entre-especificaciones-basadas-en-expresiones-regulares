package objects;

import automata.*;

public abstract class ExpressionBase {
	protected char _sim;

	public ExpressionBase() {}
	
	/**
	 * @return simbolo
	 */
	public char get_sim() {
		return _sim;
	}
	
	/**
	 * setter de simbolo
	 * @param sim
	 */
	public void set_sim(char sim) {
		this._sim = sim;
	}

	@Override
	public String toString() {
		return "" + _sim;
	}
	
	/**
	 * Thomson simplificado AFN
	 * @param id
	 * @return
	 */
	public abstract Automata ThomsonSimplAFN(IdEstado id);
	
	/**
	 * Thomson AFN normal con más lambda-transiciones
	 * @param id
	 * @return
	 */
	public abstract Automata ThomsonAFN(IdEstado id);

	/**
	 * devuelve una copia de la clase: funcion de la factoria
	 * @return
	 */
	public abstract ExpressionBase cloneMe();

	/**
	 * comprueba el tipo de simbolo: funcion de la factoria
	 * @param string
	 * @return
	 */
	public abstract boolean match(String string);

	/**
	 * parse de simbolo: funcion de la factoria
	 * @param string
	 * @return
	 */
	public ExpressionBase parse(String string) {
		ExpressionBase exp = null;
		if (match(string))
			exp = cloneMe();
		return exp;
	}
}
