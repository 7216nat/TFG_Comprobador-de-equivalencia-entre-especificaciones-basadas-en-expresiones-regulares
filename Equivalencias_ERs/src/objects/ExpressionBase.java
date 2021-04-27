package objects;

import java.util.List;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import algoritmo.BerrySethi;
import algoritmo.Derivada;
import algoritmo.DerivadaParcial;
import algoritmo.Thomson;

public abstract class ExpressionBase implements BerrySethi, Thomson, Derivada, DerivadaParcial {
	
	private Tipo type;

	protected ExpressionBase(Tipo tipo) {
		type = tipo;
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
	
	/**
	 * conseguir de la expresion siguiendo el orden de los parametros: 
	 * 	1. set de simbolos
	 *  2. lista de posiciones de URangos
	 *  3. iniciales de los rangos
	 *  4. finales de los rangos 
	 * @param set: set de simbolos
	 * @param array: lista de rangos
	 * @param inis: inciales de rangos
	 * @param fins: finales de rangos
	 */
	public abstract void getSimbolosRangos(Set<String> set, List<UnionRangos> array, Set<Character> inis, Set<Character> fins);
	
	/**
	 * deshacer los unionRangos
	 * @return ExpressionBase final
	 */
	public abstract ExpressionBase buildTreeDefinitivo();
	
	public Tipo getType() {
		return this.type;
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
	
	protected Set<ExpressionBase> concatAll(Set<ExpressionBase> e, ExpressionBase e2) {
		Iterator<ExpressionBase> it = e.iterator();
		Set<ExpressionBase> ret = new HashSet<>();
		while(it.hasNext()) {
			ExpressionBase aux = it.next();
			if(aux.getType() != Tipo.LAMBDA && aux.getType() != Tipo.VACIO)
				ret.add(new Concat(aux, e2));
			else if (aux.getType() == Tipo.LAMBDA)
				ret.add(e2);
			else
				ret.add(new Vacio());
		}
		return ret;
	}
}
