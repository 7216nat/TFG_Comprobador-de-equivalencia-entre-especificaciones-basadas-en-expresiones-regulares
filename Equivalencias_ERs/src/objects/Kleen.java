package objects;

import java.util.ArrayList;
import java.util.Set;
import java.util.SortedSet;

import automata.*;

//import java.util.regex.*;  
public class Kleen extends ExpressionBase {

	// private static final String _regex =
	// "\\w\\*[\\w\\+\\*\\(\\)]+|\\)\\*[\\w\\+\\*\\(\\)]*";
	// private static final String _regex = "\\w\\*.*";
	private static final String _regex = "*";
	private static final String Kleen = "*";
	private ExpressionBase _e1;

	public Kleen() {
		super(Kleen, null, Tipo.KLEEN);
	}

	public Kleen(ExpressionBase e1) {
		super(Kleen, null, Tipo.KLEEN);
		_e1 = e1;
		e1.setPadre(this);
	}
	
	public Kleen(ExpressionBase padre, ExpressionBase e1) {
		super(Kleen, padre, Tipo.KLEEN);
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
		return new Kleen();
	}

	@Override
	public boolean match(String string) {
		// TODO Auto-generated method stub
		return false; // Pattern.matches(_regex, string);
	}

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId(), acept;
		Estado iniPrev;
		ArrayList<Estado> aceptPrev;
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
		// TODO Auto-generated method stub
		AutomataTS a1 = _e1.ThomsonSimplAFN(id);
		Estado ini = a1.getIni();
		
		// Añado todas las transiciones del estado inicial a los estados finales
		for (Estado e : a1.getFin()) {
			a1.unirSinEliminar(e, ini);
		}
		
		// añado una lambda-transicion de estado inicial a los estados finales
		for (Estado e : a1.getFin()) {
			a1.addTransicion(ini.getId(), e.getId(), "&");
		}
		// Convierto estado inicial a estado final
		a1.IniFinalEs(true);
		return a1;
	}
	
	@Override
	public void getSimbolosRangos(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> inis, SortedSet<Character> fins) {
		// TODO Auto-generated method stub
		_e1.getSimbolosRangos(set, array, inis, fins);
	}

	public ExpressionBase getExpr() {
		return this._e1;
	}
	
	@Override
	public boolean eqLambda() {
		return true;
	}
	
	@Override
	public void insertarVacio() {
		this.getPadre().cambiarHijo(this,  new Lambdaa());
	}

	@Override
	public boolean equals(Object o) {
		 if (o == this) return true;
	     if (!(o instanceof Kleen)) {
	            return false;
	     }
	     Kleen t = (Kleen) o;
	     return t._e1.equals(this._e1);
	}
	
}
