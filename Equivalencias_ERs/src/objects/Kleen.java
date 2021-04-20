package objects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import automata.*;

//import java.util.regex.*;  
public class Kleen extends ExpressionBase {

	// private static final String _regex =
	// "\\w\\*[\\w\\+\\*\\(\\)]+|\\)\\*[\\w\\+\\*\\(\\)]*";
	// private static final String _regex = "\\w\\*.*";
	private static final String _regex = "*";
	private static final String KLEENS = "*";
	private ExpressionBase _e1;

	public Kleen() {
		super(Tipo.KLEEN);
	}

	public Kleen(ExpressionBase e1) {
		super(Tipo.KLEEN);
		_e1 = e1;		
		if(_e1 instanceof Kleen) {
			_e1 = ((Kleen)e1).getExpr();
		}
	}
	
	public Kleen(ExpressionBase padre, ExpressionBase e1) {
		super(Tipo.KLEEN);
		_e1 = e1;
		if(_e1 instanceof Kleen) {
			_e1 = ((Kleen)e1).getExpr();
		}
	}

	@Override
	public String toString() {
		return "[" + _e1.toString() + "]" + KLEENS;
	}

	@Override
	public ExpressionBase cloneMe() {
		return new Kleen();
	}

	@Override
	public boolean match(String string) {
		return false; // Pattern.matches(_regex, string);
	}

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		int ini = id.nextId();
		int acept;
		Estado iniPrev;
		List<Estado> aceptPrev;
		AutomataTS a1 = _e1.ThomsonAFN(id);
		
		// deshago todos los estados iniciales y finales 
		a1.quitarTodosFin();
		a1.quitarIni();
		iniPrev = a1.getIni();
		aceptPrev = a1.getFin();
		acept = id.nextId();
		
		// Creo nueva automata
		AutomataTS aut = new AutomataTS(ini, acept);
		aut.copyAll(a1);
		
		
		// nuevas transiciones, consulte memoria
		aut.addTransicion(ini, acept, "&");
		aut.addTransicion(ini, iniPrev.getId(), "&");
		for (Estado e : aceptPrev) {
			aut.addTransicion(e.getId(), acept, "&");
			aut.addTransicion(e.getId(), iniPrev.getId(), "&");
		}
		return aut;
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		AutomataTS a1 = _e1.ThomsonSimplAFN(id);
		Estado ini = a1.getIni();
		
		// A�ado todas las transiciones del estado inicial a los estados finales
		for (Estado e : a1.getFin()) {
			a1.unirSinEliminar(e, ini);
		}
		
		// Convierto estado inicial a estado final
		a1.IniFinalEs(true);
		return a1;
	}
	
	@Override
	public BerrySethiNode createBerrySethiNode(IdEstado id) {
		Set<Integer> tmp = new HashSet<>();
		BerrySethiNode ll = _e1.createBerrySethiNode(id);
		BerrySethiNode bs = new BerrySethiNode(ll);
		
		bs.setEmpty(true);
		bs.setSim(KLEENS);
		bs.setTipo(getType());
		
		tmp.addAll(ll.first);
		bs.setFirst(tmp);
		
		tmp = new HashSet<>();
		tmp.addAll(ll.last);
		bs.setLast(tmp);
		
		return bs;
	}
	
	@Override
	public void getSimbolosRangos(Set<String> set, List<UnionRangos> array, Set<Character> inis, Set<Character> fins) {
		_e1.getSimbolosRangos(set, array, inis, fins);
	}

	public ExpressionBase getExpr() {
		return this._e1;
	}
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
			 return true;
		 }
	     if (!(o instanceof Kleen)) {
	            return false;
	     }
	     Kleen t = (Kleen) o;
	     return t._e1.equals(this._e1);
	}
	
	@Override
	public boolean eqLambda() {
		return true;
	}

	@Override
	public ExpressionBase buildTreeDefinitivo() {
		_e1 = _e1.buildTreeDefinitivo();
		return this;
	}

	@Override
	public String getVal() {
		return this._e1.getVal();
	}

	@Override
	public ExpressionBase derivada(String sim) {
		ExpressionBase newEx = null;
		ExpressionBase t1 = this._e1.derivada(sim);
		if (t1 instanceof Vacio)
			newEx = new Vacio();
		else if (t1 instanceof Lambdaa)
			newEx = this;
		else if (this._e1 instanceof Lambdaa)
			newEx = t1;
		else
			newEx = new Concat(t1, this);

		return newEx;
	}

	@Override
	public Set<ExpressionBase> derivadaParcial(String sim) {
		Set<ExpressionBase> ret = new HashSet<>();
		Set<ExpressionBase> t1 = this._e1.derivadaParcial(sim);
		ret.addAll(concatAll(t1, this));
		return ret;
	}
}
