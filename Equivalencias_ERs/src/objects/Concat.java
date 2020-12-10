package objects;
import java.util.regex.*;

import automata.Automata;
import automata.IdEstado;  
public class Concat extends ExpressionBase {
	
	// private static final String _regex = "\\w\\w[\\w\\+\\*\\(\\)]+";
	private ExpressionBase _e1;
	private ExpressionBase _e2;
	
	public Concat() {}
	public Concat(ExpressionBase e1, ExpressionBase e2) {
		_e1 = e1;
		_e2 = e2;
	}
	
	@Override
	public String toString() {
		return  _e1.toString() + _e2.toString() ;
	}

	@Override
	public ExpressionBase cloneMe() {
		// TODO Auto-generated method stub
		return new Concat();
	}

	@Override
	public boolean match(String string) {
		// TODO Auto-generated method stub
		return false; // Pattern.matches(_regex, string);
	}
	@Override
	public Automata ThomsonAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int iniPrev, aceptPrev;
		Automata a1 = _e1.ThomsonAFN(id);
		Automata a2 = _e2.ThomsonAFN(id);
		
		a2.cambioIni(false);
		a1.cambioFin(false);
		iniPrev = a2.getIni();
		aceptPrev = a1.getFin();
		Automata aut = new Automata(a1.getIni(), a2.getFin());
		aut.copyAll(a1);
		aut.copyAll(a2);
		
		aut.addTransicion(aceptPrev, iniPrev, "&");
		return aut;
	}

}
