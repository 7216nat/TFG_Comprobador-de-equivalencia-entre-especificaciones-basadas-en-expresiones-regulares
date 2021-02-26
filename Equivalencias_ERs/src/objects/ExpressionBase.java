package objects;

import java.util.ArrayList;
import java.util.Set;
import java.util.SortedSet;

import automata.*;

public abstract class ExpressionBase implements BerrySethi, Thomson {
	
	private Tipo type;
	private ExpressionBase padre;

	public ExpressionBase(ExpressionBase padre, Tipo tipo) {
		type = tipo;
		this.padre = padre;
	}

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
	
	public abstract ExpressionBase buildTreeDefinitivo();
	
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
	
	@Override
	public int hashCode() {
		return this.type.getValor();
	}
	
	public boolean eqLambda() {
		return false;
	}
	
}
