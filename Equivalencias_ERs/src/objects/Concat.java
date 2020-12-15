package objects;

import java.util.ArrayList;
import java.util.regex.*;

import automata.Automata;
import automata.Estado;
import automata.IdEstado;

public class Concat extends ExpressionBase {

	// private static final String _regex = "\\w\\w[\\w\\+\\*\\(\\)]+";
	private ExpressionBase _e1;
	private ExpressionBase _e2;

	public Concat() {
	}

	public Concat(ExpressionBase e1, ExpressionBase e2) {
		_e1 = e1;
		_e2 = e2;
	}

	@Override
	public String toString() {
		return _e1.toString() + _e2.toString();
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
		Estado iniPrev;
		ArrayList<Estado> aceptPrev;
		Automata a1 = _e1.ThomsonAFN(id);
		Automata a2 = _e2.ThomsonAFN(id);

		a2.quitarIni();
		a1.quitarTodosFin();
		iniPrev = a2.getIni();
		aceptPrev = a1.getFin();
		a1.copyAll(a2);
		
		for (Estado e: aceptPrev)
			a1.addTransicion(e.getId(), iniPrev.getId(), '&');
		return a1;
	}

	@Override
	public Automata ThomsonSimplAFN(IdEstado id) {
		// TODO Auto-generated method stub
		Estado iniPrev;
		ArrayList<Estado> aceptPrev;
		Automata a1 = _e1.ThomsonSimplAFN(id);
		Automata a2 = _e2.ThomsonSimplAFN(id);

		
		iniPrev = a2.getIni();
		aceptPrev = a1.getFin();
		if (!iniPrev.esFin()) {
			a1.quitarTodosFin();
		}
		a1.copyAll(a2);
		for (Estado e: aceptPrev)
			a1.unirSinEliminar(e.getId(), iniPrev.getId());
		a1.finClear();;
		a1.copyFinals(a2);
		a1.eliminarEstado(iniPrev.getId());
		return a1;
	}

}
