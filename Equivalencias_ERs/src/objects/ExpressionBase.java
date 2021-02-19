package objects;

import java.util.ArrayList;
import java.util.Set;
import java.util.SortedSet;

import automata.*;

public abstract class ExpressionBase {
	
	protected String _sim;
	private Tipo type;
	private ExpressionBase padre;

	public ExpressionBase(String sim, ExpressionBase padre, Tipo tipo) {
		type = tipo;
		this.padre = padre;
		_sim = sim;
	}
	
	public String get_sim() {return _sim;}
	
	public void set_sim(String sim) { _sim = sim; }
	
	@Override
	public String toString() { return _sim; }
	
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
	
	public abstract void getSimbolosRangos(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> inis, SortedSet<Character> fins);
	
	public Tipo getType() {
		return this.type;
	}
	
	public void setPadre(ExpressionBase ex) {
		this.padre = ex;
	}
	
	public ExpressionBase getPadre() {
		return this.padre;
	}
	
	public boolean produceVacio() {
		return true;
	}
	
}
