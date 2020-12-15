package objects;

import java.util.ArrayList;

import automata.Automata;
import automata.Estado;
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
		int ini = id.nextId(), acept, iniPrev1, iniPrev2;
		ArrayList<Estado> aceptPrev1, aceptPrev2;
		Automata a1 = _e1.ThomsonAFN(id);
		Automata a2 = _e2.ThomsonAFN(id);
		
		a1.quitarTodosFin();
		a1.quitarIni();
		a2.quitarTodosFin();
		a2.quitarIni();
		
		iniPrev1 = a1.getIni().getId();
		aceptPrev1 = a1.getFin();
		iniPrev2 = a2.getIni().getId();
		aceptPrev2 = a2.getFin();
		
		acept = id.nextId();
		Automata aut = new Automata(ini, acept);
		aut.copyAll(a1);
		aut.copyAll(a2);
		
		
		aut.addTransicion(ini, iniPrev1, '&');
		aut.addTransicion(ini, iniPrev2, '&');
		for (Estado e: aceptPrev1)
			aut.addTransicion(e.getId(), acept, '&');
		for (Estado e: aceptPrev2)
			aut.addTransicion(e.getId(), acept, '&');
		return aut;
	}
	@Override
	public Automata ThomsonSimplAFN(IdEstado id) {
		// TODO Auto-generated method stub
		Estado iniPrev1, iniPrev2;
		Automata a1 = _e1.ThomsonSimplAFN(id);
		Automata a2 = _e2.ThomsonSimplAFN(id);
		
		iniPrev1 = a1.getIni();
		iniPrev2 = a2.getIni();
		a1.copyAll(a2);
		a1.copyFinals(a2);
		if (!iniPrev1.esFin() && iniPrev2.esFin()) a1.IniEsFin();
		a1.unirSinEliminar(iniPrev1.getId(), iniPrev2.getId());
		a1.eliminarEstado(iniPrev2.getId());
		
		return a1;
	}

}
