package automata;

import java.util.HashSet;
import java.util.Set;

public class EstadoTh extends Estado{
	private Set<Integer> equiv;
	private Set<Integer> igualA;
	
	public EstadoTh(int id) {
		super(id);
		this.equiv = new HashSet<>();
		this.igualA = new HashSet<>();
		igualA.add(id);
	}
	
	public void addEquiv(int id) {
		equiv.add(id);
	}
	/**
	 * Iguala las equiv de es2 y this
	 */
	public void unirEquiv(EstadoTh es2) {
		this.equiv.addAll(es2.equiv);
		es2.equiv.addAll(this.equiv);
	}
	
	public Set<Integer> getEq(){
		return this.equiv;
	}
	
	/**
	 * Devuelve true si es y this tienen el mismo conjunto de equiv
	 */
	public boolean same(EstadoTh es) {
		return (equiv.containsAll(es.equiv) && es.equiv.containsAll(this.equiv));
	}
	
	/**
	 * Devuelve true si es y this tienen el mismo conjunto de igualA
	 */
	public boolean sameHKDet(EstadoTh es) {
		return (igualA.containsAll(es.igualA) && es.igualA.containsAll(this.igualA));
	}

	/**
	 * Introduce todos los indices de estados del conjunto igualA de es2 en this y viceversa
	 */
	public void unirIgualA(EstadoTh es2) {
		this.igualA.addAll(es2.igualA);
		es2.igualA.addAll(this.igualA);
	}
	
	
	
}
