package objects;

import automata.*;

public abstract class ExpressionBase {
	
	protected String _sim;
	private Tipo type;
	private ExpressionBase padre;

	public ExpressionBase(Tipo tipo) {
		type = tipo;
	}
	
	/**
	 * @return simbolo
	 */
	public String get_sim() {
		return _sim;
	}
	
	/**
	 * setter de simbolo
	 * @param sim
	 */
	public void set_sim(String sim) {
		this._sim = sim;
	}

	@Override
	public String toString() {
		return _sim;
	}
	
	/**
	 * Thomson simplificado AFN
	 * @param id
	 * @return
	 */
	public abstract AutomataTS ThomsonSimplAFN(IdEstado id);
	
	/**
	 * Thomson AFN normal con más lambda-transiciones
	 * @param id
	 * @return
	 */
	public abstract AutomataTS ThomsonAFN(IdEstado id);

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
	
	public Tipo getType() {
		return this.type;
	}
	
	public void setPadre(ExpressionBase ex) {
		this.padre = ex;
	}
	public ExpressionBase getPadre() {
		return this.padre;
	}
	
	public boolean eqLambda() {
		return false;
	}
	
	public void insertarVacio() {	}
	public void cambiarHijo(ExpressionBase sust, ExpressionBase nueva) {}
	
	//@Override
	//public abstract boolean equals(Object o);
	@Override
	public int hashCode() {
		return this.type.getValor();
	}
	
}
