package objects;

import java.util.ArrayList;
import java.util.HashSet;
// import java.util.regex.*;
import java.util.Set;
import java.util.SortedSet;

import automata.*;

public class Concat extends ExpressionBase {

	// private static final String _regex = "\\w\\w[\\w\\+\\*\\(\\)]+";
	private static final String _regex = "";
	private static final String Conc = "";
	private ExpressionBase _e1;
	private ExpressionBase _e2;

	public Concat() {
		super(null, Tipo.CONCAT);
	}

	public Concat(ExpressionBase e1, ExpressionBase e2) {
		super(null, Tipo.CONCAT);
		_e1 = e1;
		_e2 = e2;
		e1.setPadre(this);
		e2.setPadre(this);
	}
	
	public Concat(ExpressionBase padre, ExpressionBase e1, ExpressionBase e2) {
		super(padre, Tipo.CONCAT);
		_e1 = e1;
		_e2 = e2;
		e1.setPadre(this);
		e2.setPadre(this);
	}

	@Override
	public String toString() {
		return _e1.toString() + _e2.toString();
	}

	@Override
	public ExpressionBase cloneMe() {
		return new Concat();
	}

	@Override
	public boolean match(String string) {
		return false; // Pattern.matches(_regex, string);
	}

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		Estado iniPrev;
		ArrayList<Estado> aceptPrev;
		AutomataTS a1 = _e1.ThomsonAFN(id);
		AutomataTS a2 = _e2.ThomsonAFN(id);
		
		// deshago estado inicial de a2 y finales de a1
		a2.quitarIni();
		a1.quitarTodosFin();
		iniPrev = a2.getIni();
		aceptPrev = a1.getFin();
		a1.copyAll(a2);
		for (Estado e: aceptPrev)
			a1.addTransicion(e.getId(), iniPrev.getId(), "&");
		a1.finClear();	
		a1.copyFinals(a2);
		// añadir una lambda-transicion de a1.estadofinal a a2.estadoinicial 
		return a1;
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		Estado iniPrev;
		ArrayList<Estado> aceptPrev1;
		AutomataTS a1 = _e1.ThomsonSimplAFN(id);
		AutomataTS a2 = _e2.ThomsonSimplAFN(id);

		iniPrev = a2.getIni();
		aceptPrev1 = a1.getFin();
		a1.copyAll(a2);
		
		// añadir a los estados finales de a1 todos las transiciones de a2.estadoinicial
		for (Estado e: aceptPrev1)
			a1.unirSinEliminar(e, iniPrev);
		
		// si a2.estado inicial no es final, se elimina la lista de estado finales
		if (!iniPrev.esFin()) {
			a1.quitarTodosFin();
			a1.finClear();
		}
		
		a1.copyFinals(a2);
		a1.eliminarEstado(iniPrev.getId());
		return a1;
	}
	
	@Override
	public BerrySethiNode createBerrySethiNode(IdEstado id) {
		//  Auto-generated method stub
		HashSet<Integer> tmp = new HashSet<>();
		BerrySethiNode ll = _e1.createBerrySethiNode(id);
		BerrySethiNode rl = _e2.createBerrySethiNode(id);
		BerrySethiNode bs = new BerrySethiNode(ll, rl);
		
		bs.setEmpty(ll.empty && rl.empty);
		bs.setSim(Conc);
		bs.setTipo(getType());
		
		tmp.addAll(ll.first);
		if (ll.empty)
			tmp.addAll(rl.first);
		bs.setFirst(tmp);
		
		tmp = new HashSet<>();
		tmp.addAll(rl.last);
		if(rl.empty)
			tmp.addAll(ll.last);
		bs.setLast(tmp);

		return bs;
	}
	
	@Override
	public void getSimbolosRangos(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> inis, SortedSet<Character> fins) {
		_e1.getSimbolosRangos(set, array, inis, fins);
		_e2.getSimbolosRangos(set, array, inis, fins);
	}
	
	public ExpressionBase getExpr1() {
		return this._e1;
	}
	public ExpressionBase getExpr2() {
		return this._e2;
	}
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
			 return true;
		 }
	     if (!(o instanceof Concat)) {
	            return false;
	     }
	     Concat t = (Concat) o;
	     return t._e1.equals(this._e1) && t._e2.equals(this._e2);
	}
	@Override
	public boolean eqLambda() {
		return (_e1.eqLambda() && _e2.eqLambda());
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
		ExpressionBase t2 = this._e2;
		if (t1 instanceof Vacio || t2 instanceof Vacio) {
			newEx = new Vacio();
		} else if (t1 instanceof Lambdaa)
			newEx = t2;
		else if (t2 instanceof Lambdaa)
			newEx = t1;
		else
			newEx = new Concat(t1, t2);

		if (this._e1.eqLambda()) {
			t2 = this._e2.derivada(sim);
			if (!(t2 instanceof Vacio) && !(newEx instanceof Vacio)) {
				if (!newEx.equals(t2)) {
					if (newEx.menorQue(t2)) {
						newEx = new Union(newEx, t2);
					} 
					else
						newEx = new Union(t2, newEx);
				}
			} else if (t1 instanceof Vacio && !(t2 instanceof Vacio)) {
				newEx = t2;
			}

		}
		return newEx;
	}

	@Override
	public HashSet<ExpressionBase> derivadaParcial(String sim) {
		HashSet<ExpressionBase> ret = new HashSet<>();
		HashSet<ExpressionBase> t1 = this._e1.derivadaParcial(sim);
		ExpressionBase t2 = this._e2;
		if(t2 instanceof Lambdaa)
			ret.addAll(t1);
		else if(t2 instanceof Vacio)
			ret.add(new Vacio());
		else {
			HashSet<ExpressionBase> aux = concatAll(t1, t2);
			ret.addAll(aux);
		}
		
		//Si la primera parte puede dar el vacío
		if(this._e1.eqLambda()) {
			HashSet<ExpressionBase> anexo;
			anexo = this._e2.derivadaParcial(sim);
			ret.addAll(anexo);
		}
			
		return ret;
	}


}
