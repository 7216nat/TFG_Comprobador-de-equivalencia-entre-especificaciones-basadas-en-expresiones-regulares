package algoritmo;

import java.util.HashSet;

import automata.Estado;

public class EstadoDetHK extends Estado{
	//equiv para la parte de determinación, igualA para la de HK
	private HashSet<Integer> equiv;
	private HashSet<Integer> igualA;
	public EstadoDetHK(int id) {
		super(id);
		this.equiv = new HashSet<Integer>();
		this.igualA = new HashSet<Integer>();
		igualA.add(id);
	}
	
	public void addEquiv(int id) {
		equiv.add(id);
	}
	/**
	 * Iguala las equiv de es2 y this
	 */
	public void unirEquiv(EstadoDetHK es2) {
		this.equiv.addAll(es2.equiv);
		es2.equiv.addAll(this.equiv);
	}
	
	public HashSet<Integer> getEq(){
		return this.equiv;
	}
	
	
	public boolean same(EstadoDetHK es) {
		return (equiv.containsAll(es.equiv) && es.equiv.containsAll(this.equiv));
	}
	public boolean sameHKDet(EstadoDetHK es) {
		return (igualA.containsAll(es.igualA) && es.igualA.containsAll(this.igualA));
	}

	public void unirIgualA(EstadoDetHK es2) {
		this.igualA.addAll(es2.igualA);
		es2.igualA.addAll(this.igualA);
	}
	
	
	
}
