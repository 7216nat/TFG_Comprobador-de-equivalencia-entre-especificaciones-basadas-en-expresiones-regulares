package objects;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import automata.*;

//import java.util.regex.*; 

public class Union extends ExpressionBase {
	
	private static final String unionS = "|";
	private ExpressionBase _e1;
	private ExpressionBase _e2;
	
	/**
	 * Constructora por defecto
	 */
	public Union() {
		super(Tipo.UNION);
	}
	
	/**
	 * Construcora
	 * @param e1: expresion 1
	 * @param e2: expresion 2
	 */
	public Union(ExpressionBase e1, ExpressionBase e2) {
		super(Tipo.UNION);
		_e1 = e1;
		_e2 = e2;
	}
	
	@Override
	public String toString() {
		return  "(" + _e1.toString() + unionS +  _e2.toString() + ")";
	}

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		int ini = id.nextId(), acept, iniPrev1, iniPrev2;
		List<Estado> aceptPrev1, aceptPrev2;
		AutomataTS a1 = _e1.ThomsonAFN(id);
		AutomataTS a2 = _e2.ThomsonAFN(id);
		
		// deshacer todos los estados finales e iniciales
		a1.quitarTodosFin();
		a1.quitarIni();
		a2.quitarTodosFin();
		a2.quitarIni();
		
		// referencias a estos
		iniPrev1 = a1.getIni().getId();
		aceptPrev1 = a1.getFin();
		iniPrev2 = a2.getIni().getId();
		aceptPrev2 = a2.getFin();
		
		// nueva automata
		acept = id.nextId();
		AutomataTS aut = new AutomataTS(ini, acept);
		aut.copyAll(a1);
		aut.copyAll(a2);
		
		// consulte memoria
		aut.addTransicion(ini, iniPrev1, "&");
		aut.addTransicion(ini, iniPrev2, "&");
		for (Estado e: aceptPrev1)
			aut.addTransicion(e.getId(), acept, "&");
		for (Estado e: aceptPrev2)
			aut.addTransicion(e.getId(), acept, "&");
		return aut;
	}
	
	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		Estado iniPrev1, iniPrev2;
		AutomataTS a1 = _e1.ThomsonSimplAFN(id);
		AutomataTS a2 = _e2.ThomsonSimplAFN(id);
		
		iniPrev1 = a1.getIni();
		iniPrev2 = a2.getIni();
		
		// copio todas las transiciones de otra automata y sus estados finales
		a1.copyAll(a2);
		a1.copyFinals(a2);
		
		// en caso de que algun estado inicial sea final, el estado inicial que queda es final 
		if (iniPrev1.esFin() || iniPrev2.esFin()) a1.IniFinalEs(true);
		
		// añadir las transiciones del a2.estadoinicial a a1.estadoinicila y eliminar el primero
		a1.unirSinEliminar(iniPrev1, iniPrev2);
		a1.eliminarEstado(iniPrev2.getId());
		
		return a1;
	}
	
	@Override
	public BerrySethiNode createBerrySethiNode(Map<Integer, BerrySethiNode> map, IdEstado id) {
		Set<Integer> tmp = new HashSet<>();
		BerrySethiNode ll = _e1.createBerrySethiNode(map, id);
		BerrySethiNode rl = _e2.createBerrySethiNode(map, id);
		BerrySethiNode bs = new BerrySethiNode(ll, rl);
		
		bs.setEmpty(ll.empty || rl.empty);
		bs.setSim(unionS);
		bs.setTipo(getType());
		
		tmp.addAll(ll.first);
		tmp.addAll(rl.first);
		bs.setFirst(tmp);
		
		tmp = new HashSet<>();
		tmp.addAll(rl.last);	
		tmp.addAll(ll.last);
		bs.setLast(tmp);

		return bs;
	}
	
	public ExpressionBase getExpr1() {
		return this._e1;
	}
	
	public ExpressionBase getExpr2() {
		return this._e2;
	}
	
	@Override
	public void getSimbolosRangos(Set<String> set, List<UnionRangos> array, Set<Character> inis, Set<Character> fins) {
		_e1.getSimbolosRangos(set, array, inis, fins);
		_e2.getSimbolosRangos(set, array, inis, fins);
	}
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
			 return true;
		 }
	     if (!(o instanceof Union)) {
	            return false;
	     }
	     Union t = (Union) o;
	     return t._e1.equals(this._e1) && t._e2.equals(this._e2);
	}
	
	@Override
	public int hashCode() {
	    return _e1.hashCode() + _e2.hashCode() + this.getType().getValor();
	}
	
	
	@Override
	public boolean eqLambda() {
		return (_e1.eqLambda() || _e2.eqLambda());
	}
	@Override
	public ExpressionBase buildTreeDefinitivo() {
		_e1 = _e1.buildTreeDefinitivo();
		_e2 = _e2.buildTreeDefinitivo();
		return this;
	}
	@Override
	public String getVal() {
		return this._e1.getVal();
	}
	@Override
	public ExpressionBase derivada(String sim) {
		ExpressionBase newEx;
		ExpressionBase t1 = this._e1.derivada(sim);
		ExpressionBase t2 = this._e2.derivada(sim);

		if (t1.getType() == Tipo.VACIO)
			newEx = t2;
		else if (t2.getType() == Tipo.VACIO)
			newEx = t1;
		else {
			if (!t1.equals(t2)) {
				if (t1.menorQue(t2))
					newEx = new Union(t1, t2);
				else
					newEx = new Union(t2, t1);
			} else
				newEx = t1;
		}

		return newEx;
	}
	@Override
	public Set<ExpressionBase> derivadaParcial(String sim) {
		Set<ExpressionBase> ret = new HashSet<>();
		Set<ExpressionBase> t1 = this._e1.derivadaParcial(sim);
		Set<ExpressionBase> t2 = this._e2.derivadaParcial(sim);
		ret.addAll(t1);
		ret.addAll(t2);
		return ret;
	}

	@Override
	public ExpressionBase copy() {
		// TODO Auto-generated method stub
		return new Union(_e1.copy(), _e2.copy());
	}
	
	
}
