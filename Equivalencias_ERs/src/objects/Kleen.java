package objects;

import automata.Automata;
import automata.IdEstado;

//import java.util.regex.*;  
public class Kleen extends ExpressionBase {
	
	//private static final String _regex = "\\w\\*[\\w\\+\\*\\(\\)]+|\\)\\*[\\w\\+\\*\\(\\)]*";
	//private static final String _regex = "\\w\\*.*";
	private ExpressionBase _e1;
	public Kleen(ExpressionBase e1) {
		_e1 = e1;
	}
	
	public Kleen() {}
	
	@Override
	public String toString() {
		return  "["+_e1.toString() + "]*";
	}

	@Override
	public ExpressionBase cloneMe() {
		// TODO Auto-generated method stub
		return new Kleen();
	}

	@Override
	public boolean match(String string) {
		// TODO Auto-generated method stub
		return false; //Pattern.matches(_regex, string);
	}

	@Override
	public Automata ThomsonAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId(), acept, iniPrev, aceptPrev;
		Automata a1 = _e1.ThomsonAFN(id);
		
		a1.cambioFin(false);
		a1.cambioIni(false);
		iniPrev = a1.getIni();
		aceptPrev = a1.getFin();
		acept = id.nextId();
		Automata aut = new Automata(ini, acept);
		aut.copyAll(a1);
		
		
		aut.addTransicion(ini, acept, "&");
		aut.addTransicion(ini, iniPrev, "&");
		aut.addTransicion(aceptPrev, acept, "&");
		aut.addTransicion(aceptPrev, iniPrev, "&");
		return aut;
	}

}
