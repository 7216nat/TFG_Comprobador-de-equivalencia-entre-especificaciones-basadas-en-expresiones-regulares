package objects;

import java.util.ArrayList;

import automata.Automata;
import automata.Estado;
import automata.IdEstado;

//import java.util.regex.*;  
public class Kleen extends ExpressionBase {

	// private static final String _regex =
	// "\\w\\*[\\w\\+\\*\\(\\)]+|\\)\\*[\\w\\+\\*\\(\\)]*";
	// private static final String _regex = "\\w\\*.*";
	private ExpressionBase _e1;

	public Kleen(ExpressionBase e1) {
		_e1 = e1;
	}

	public Kleen() {
	}

	@Override
	public String toString() {
		return "[" + _e1.toString() + "]*";
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
	public Automata ThomsonAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId(), acept;
		Estado iniPrev;
		ArrayList<Estado> aceptPrev;
		Automata a1 = _e1.ThomsonAFN(id);
		
		// deshago todos los estados iniciales y finales 
		a1.quitarTodosFin();
		a1.quitarIni();
		iniPrev = a1.getIni();
		aceptPrev = a1.getFin();
		acept = id.nextId();
		
		// Creo nueva automata
		Automata aut = new Automata(ini, acept);
		aut.copyAll(a1);
		
		
		// nuevas transiciones, consulte memoria
		aut.addTransicion(ini, acept, '&');
		aut.addTransicion(ini, iniPrev.getId(), '&');
		for (Estado e : aceptPrev) {
			aut.addTransicion(e.getId(), acept, '&');
			aut.addTransicion(e.getId(), iniPrev.getId(), '&');
		}
		return aut;
	}

	@Override
	public Automata ThomsonSimplAFN(IdEstado id) {
		// TODO Auto-generated method stub
		Automata a1 = _e1.ThomsonSimplAFN(id);
		
		// Añado todas las transiciones del estado inicial a los estados finales
		for (Estado e : a1.getFin()) {
			a1.unirSinEliminar(e.getId(), a1.getIni().getId());
		}
		
		// Convierto estado inicial a estado final
		a1.IniEsFin();
		return a1;
	}

}
