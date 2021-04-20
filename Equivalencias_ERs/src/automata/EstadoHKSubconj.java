package automata;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import objects.ExpressionBase;

public class EstadoHKSubconj extends Estado{
	
	private Set<Integer> equiv;
	private Set<ExpressionBase> ex;
	
	public EstadoHKSubconj(int _id, Set<ExpressionBase> _ex) {
		super(_id);
		this.equiv = new HashSet<>();
		this.ex = _ex;
		equiv.add(_id);
	}
	
	public EstadoHKSubconj(int id, Set<ExpressionBase> _ex, boolean ini, boolean fin) {
		super(id, ini, fin);
		this.equiv = new HashSet<>();
		equiv.add(id);
		this.ex = _ex;
	}

	public void unirIgualA(EstadoHKSubconj es2) {
		this.equiv.addAll(es2.equiv);
		es2.equiv.addAll(this.equiv);
	}
	
	public Set<ExpressionBase> getExp() {
		return this.ex;
	}
	
	public boolean same(EstadoHKSubconj es) {
		return (equiv.containsAll(es.equiv) && es.equiv.containsAll(this.equiv));
	}
	
	public boolean eqLambda() {
		Iterator<ExpressionBase> it = this.ex.iterator();
		boolean fin = false;
		while(!fin && it.hasNext()) {
			ExpressionBase t = it.next();
			fin = t.eqLambda();
		}
		return fin;
	}

}
