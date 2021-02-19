package algoritmo;

import java.util.HashSet;

import automata.Estado;
import objects.ExpressionBase;

public class EstadoHK extends Estado {
	private HashSet<Integer> equiv;
	private ExpressionBase ex;
	
	public EstadoHK(int _id, ExpressionBase _ex) {
		super(_id);
		this.equiv = new HashSet<Integer>();
		this.ex = _ex;
		equiv.add(_id);
	}
	
	public EstadoHK(int id, ExpressionBase _ex, boolean ini, boolean fin) {
		super(id, ini, fin);
		this.equiv = new HashSet<Integer>();
		equiv.add(id);
		this.ex = _ex;
	}
	
	public boolean sameHKDet(EstadoHK es) {
		return (equiv.containsAll(es.equiv) && es.equiv.containsAll(this.equiv));
	}

	public void unirIgualA(EstadoHK es2) {
		this.equiv.addAll(es2.equiv);
		es2.equiv.addAll(this.equiv);
	}
	
	public ExpressionBase getExp() {
		return this.ex;
	}
	
	public boolean same(EstadoHK es) {
		return (equiv.containsAll(es.equiv) && es.equiv.containsAll(this.equiv));
	}

}
