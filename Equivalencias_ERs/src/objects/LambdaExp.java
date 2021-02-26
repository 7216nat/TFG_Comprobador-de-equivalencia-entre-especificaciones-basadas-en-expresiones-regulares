package objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import automata.*;

/********************** 
 * ******EN DESUSO ****
 * ********************
 */
public class LambdaExp extends ExpressionBase {
	
	private static final String _regex = "?";
	private static final String lambdaExp = "?";
	private ExpressionBase _e1;

	public LambdaExp() {
		super(null, Tipo.LAMBDAEXP);
	}

	public LambdaExp(ExpressionBase e1) {
		super(null, Tipo.LAMBDAEXP);
		_e1 = new Union(e1, new Lambdaa());
		_e1.setPadre(this);
	}
	
	public LambdaExp(ExpressionBase padre, ExpressionBase e1) {
		super(padre, Tipo.LAMBDAEXP);
		_e1 = new Union(e1, new Lambdaa());
		_e1.setPadre(this);
	}

	@Override
	public String toString() {
		return  _e1.toString();
	}

	@Override
	public ExpressionBase cloneMe() {
		// TODO Auto-generated method stub
		return new LambdaExp();
	}

	@Override
	public boolean match(String string) {
		// TODO Auto-generated method stub
		return false; // Pattern.matches(_regex, string);
	}

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		return _e1.ThomsonAFN(id);
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		return _e1.ThomsonSimplAFN(id);
	}
	
	@Override
	public BerrySethiNode createBerrySethiNode(IdEstado id) {
		// TODO Auto-generated method stub
		HashSet<Integer> tmp = new HashSet<Integer>();
		BerrySethiNode ll = _e1.createBerrySethiNode(id);
		BerrySethiNode bs = new BerrySethiNode(ll);
		
		bs.setEmpty(true);
		bs.setSim(lambdaExp);
		bs.setTipo(getType());
		
		tmp.addAll(ll.first);
		bs.setFirst(tmp);
		
		tmp = new HashSet<Integer>();
		tmp.addAll(ll.last);
		bs.setLast(tmp);
		
		return bs;
	}

	@Override
	public void getSimbolosRangos(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> inis, SortedSet<Character> fins) {
		// TODO Auto-generated method stub
		_e1.getSimbolosRangos(set, array, inis, fins);
	}

	@Override
	public boolean equals(Object o) {
		 if (o == this) return true;
	     if (!(o instanceof LambdaExp)) {
	            return false;
	     }
	     LambdaExp t = (LambdaExp) o;
	     return t._e1.equals(this._e1);
	}
	
	@Override
	public boolean eqLambda() {
		return true;
	}

	@Override
	public ExpressionBase buildTreeDefinitivo() {
		// TODO Auto-generated method stub
		_e1 = _e1.buildTreeDefinitivo();
		return _e1;
	}
}
