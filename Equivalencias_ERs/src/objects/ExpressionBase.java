package objects;

import automata.*;

public abstract class ExpressionBase {
	protected char _sim;

	public ExpressionBase() {
	}

	public char get_sim() {
		return _sim;
	}

	public void set_sim(char sim) {
		this._sim = sim;
	}

	@Override
	public String toString() {
		return "" + _sim;
	}
	
	public abstract Automata ThomsonSimplAFN(IdEstado id);
	
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
