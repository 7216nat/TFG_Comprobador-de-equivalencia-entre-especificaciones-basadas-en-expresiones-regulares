package objects;

import automata.Automata;
import automata.IdEstado;

//import java.util.regex.*; 

public class Union extends ExpressionBase {
	
	//private static final String _regex = "\\w+[+][\\w\\+\\*\\(\\)]+";
	
	private ExpressionBase _e1;
	private ExpressionBase _e2;
	
	public Union() {}
	public Union(ExpressionBase e1, ExpressionBase e2) {
		_e1 = e1;
		_e2 = e2;
	}
	
	@Override
	public String toString() {
		return  "( " + _e1.toString() + "+" +  _e2.toString() + " )";
	}

	@Override
	public ExpressionBase cloneMe() {
		// TODO Auto-generated method stub
		return new Union();
	}

	@Override
	public boolean match(String string) {
		// TODO Auto-generated method stub
		return false; //Pattern.matches(_regex, string);
	}
	@Override
	public Automata ThomsonAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId(), acept, iniPrev1, aceptPrev1, iniPrev2, aceptPrev2;
		Automata a1 = _e1.ThomsonAFN(id);
		Automata a2 = _e2.ThomsonAFN(id);
		
		a1.cambioFin(false);
		a1.cambioIni(false);
		a2.cambioFin(false);
		a2.cambioIni(false);
		
		iniPrev1 = a1.getIni();
		aceptPrev1 = a1.getFin();
		iniPrev2 = a2.getIni();
		aceptPrev2 = a2.getFin();
		
		acept = id.nextId();
		Automata aut = new Automata(ini, acept);
		aut.copyAll(a1);
		aut.copyAll(a2);
		
		
		aut.addTransicion(ini, iniPrev1, "&");
		aut.addTransicion(ini, iniPrev2, "&");
		aut.addTransicion(aceptPrev1, acept, "&");
		aut.addTransicion(aceptPrev2, acept, "&");
		return aut;
	}

}
