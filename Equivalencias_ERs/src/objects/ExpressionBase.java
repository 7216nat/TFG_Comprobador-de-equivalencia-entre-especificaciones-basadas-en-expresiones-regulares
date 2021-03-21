package objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

import algoritmo.BerrySethi;
import algoritmo.Derivada;
import algoritmo.DerivadaParcial;
import algoritmo.Thomson;

public abstract class ExpressionBase implements BerrySethi, Thomson, Derivada, DerivadaParcial {
	
	private Tipo type;
	private ExpressionBase padre;

	protected ExpressionBase(ExpressionBase padre, Tipo tipo) {
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
	
	public abstract String getVal();
	
	public boolean menorQue(ExpressionBase e) {
		//Si this es símbolo o rango
		if(this instanceof Lenguaje) {
			if(!(e instanceof Lenguaje))
				return false;
			else
				return (this.getVal().charAt(0) < e.getVal().charAt(0));  
		}
		//Si this es Union
		else if(this instanceof Union) {
			if(e instanceof Lenguaje) {
				return false;
			}
			else if (e instanceof Union){
				return (((Union)this).getVal().charAt(0) < ((Union)e).getVal().charAt(0));
			}
			else {//e instanceOf Concat or Kleen
				return true;
				
			}
		}
		else if (this instanceof Concat) {
			if((e instanceof Lenguaje) || (e instanceof Union))
				return false;
			else if(e instanceof Concat) {
				return (((Concat)this).getVal().charAt(0) < ((Concat)e).getVal().charAt(0));
			}
			else { //e instanceof Kleen
				return true;
			}
			
		}
		//this instanceof Kleen
		else {
			if(e instanceof Kleen) {
				return (((Kleen)this).getVal().charAt(0) < ((Kleen)e).getVal().charAt(0));
			}
			else { //e instanceof Kleen
				return true;
			}
		}
	}
	
	protected HashSet<ExpressionBase> concatAll(HashSet<ExpressionBase> e, ExpressionBase e2) {
		Iterator<ExpressionBase> it = e.iterator();
		HashSet<ExpressionBase> ret = new HashSet<>();
		while(it.hasNext()) {
			ExpressionBase aux = it.next();
			if(!(aux instanceof Lambdaa) && !(aux instanceof Vacio))
				ret.add(new Concat(aux, e2));
			else if (aux instanceof Lambdaa)
				ret.add(e2);
			else
				ret.add(new Vacio());
		}
		return ret;
	}
}
