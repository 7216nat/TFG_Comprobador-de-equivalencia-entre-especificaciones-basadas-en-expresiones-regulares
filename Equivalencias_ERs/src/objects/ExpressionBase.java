package objects;

import automata.*;

public abstract class ExpressionBase {
	protected String _sim;

	public ExpressionBase() {
	}

	public String get_sim() {
		return _sim;
	}

	public void set_sim(String sim) {
		this._sim = sim;
	}

	@Override
	public String toString() {
		return _sim;
	}

	public abstract Automata ThomsonAFN(IdEstado id);

	// devuelve una copia de la clase: funcion de la factoria
	public abstract ExpressionBase cloneMe();

	// comprueba el tipo de simbolo: funcion de la factoria
	public abstract boolean match(String string);

	// parse de simbolo: funcion de la factoria
	public ExpressionBase parse(String string) {
		ExpressionBase exp = null;
		if (match(string))
			exp = cloneMe();
		return exp;
	}
}
