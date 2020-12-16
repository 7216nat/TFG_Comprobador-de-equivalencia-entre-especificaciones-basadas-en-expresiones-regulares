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
		
		// deshago estado inicial de a2 y finales de a1
		a2.quitarIni();
		a1.quitarTodosFin();
		iniPrev = a2.getIni();
		aceptPrev = a1.getFin();
		a1.copyAll(a2);
		
		// añadir una lambda-transicion de a1.estadofinal a a2.estadoinicial 
		for (Estado e: aceptPrev)
			a1.addTransicion(e.getId(), iniPrev.getId(), '&');
		return a1;
	}

	@Override
	public Automata ThomsonSimplAFN(IdEstado id) {
		// TODO Auto-generated method stub
		Estado iniPrev;
		ArrayList<Estado> aceptPrev1, aceptPrev2;
		Automata a1 = _e1.ThomsonSimplAFN(id);
		Automata a2 = _e2.ThomsonSimplAFN(id);

		iniPrev = a2.getIni();
		aceptPrev1 = a1.getFin();
		aceptPrev2 = a2.getFin();
		a1.copyAll(a2);
		
		// añadir a los estados finales de a1 todos las transiciones de a2.estadoinicial
		for (Estado e: aceptPrev1)
			a1.unirSinEliminar(e.getId(), iniPrev.getId());
		
		// si a2.estado inicial no es final, se elimina la lista de estado finales
		if (iniPrev.esFin()) {
			/*for (Estado e: aceptPrev2)
				a1.addTransicion(a1.getIni().getId(), e.getId(), '&');*/
		}
		// en caso contrario
		else {
			a1.quitarTodosFin();
			a1.finClear();
		}
		
		a1.copyFinals(a2);
		a1.eliminarEstado(iniPrev.getId());
		return a1;
	}

}
