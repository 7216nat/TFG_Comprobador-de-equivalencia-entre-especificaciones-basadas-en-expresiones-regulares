package objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import automata.AutomataTS;
import automata.Estado;
import automata.IdEstado;

public class KleenPos extends ExpressionBase {
	
	private static final String _regex = "+";
	private static final String KleenPos = "+";
	private ExpressionBase _e1;

	public KleenPos() {
		super(KleenPos, null, Tipo.KLEENPOS);
	}

	public KleenPos(ExpressionBase e1) {
		super(KleenPos, null, Tipo.KLEENPOS);
		_e1 = e1;
		if(_e1 instanceof Kleen) {
			_e1 = ((Kleen) _e1).getExpr();
		}
		else if(_e1 instanceof KleenPos) {
			_e1 = ((Kleen) _e1).getExpr();
		}
		e1.setPadre(this);
	}
	
	public KleenPos(ExpressionBase padre, ExpressionBase e1) {
		super(KleenPos, padre, Tipo.KLEENPOS);
		_e1 = e1;
		e1.setPadre(this);
	}

	@Override
	public String toString() {
		return "[" + _e1.toString() + "]" + _sim;
	}

	@Override
	public ExpressionBase cloneMe() {
		// TODO Auto-generated method stub
		return new KleenPos();
	}

	@Override
	public boolean match(String string) {
		// TODO Auto-generated method stub
		return false; // Pattern.matches(_regex, string);
	}

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		
		AutomataTS a1 = _e1.ThomsonAFN(id);
		int ini = a1.getIni().getId();

		for (Estado e : a1.getFin()) {
			a1.addTransicion(e.getId(), ini, "&");
		}
		
		return a1;
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		// TODO Auto-generated method stub
		AutomataTS a1 = _e1.ThomsonSimplAFN(id);
		Estado ini = a1.getIni();
		
		// Añado todas las transiciones del estado inicial a los estados finales
		for (Estado e : a1.getFin()) {
			a1.unirSinEliminar(e, ini);
		}
		
		a1.IniFinalEs(false);
		return a1;
	}
	
	@Override
	public BerrySethiNode createBerrySethiNode(IdEstado id) {
		// TODO Auto-generated method stub
		HashSet<Integer> tmp = new HashSet<Integer>();
		BerrySethiNode ll = _e1.createBerrySethiNode(id);
		BerrySethiNode bs = new BerrySethiNode(ll);
		
		bs.setEmpty(false);
		bs.setSim(_sim);
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
	     if (!(o instanceof KleenPos)) {
	            return false;
	     }
	     KleenPos t = (KleenPos) o;
	     return t._e1.equals(this._e1);
	}

}
