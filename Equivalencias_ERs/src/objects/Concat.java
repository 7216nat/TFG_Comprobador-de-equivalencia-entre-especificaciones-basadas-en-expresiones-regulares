package objects;

import java.util.ArrayList;
// import java.util.regex.*;

import automata.*;

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
	public AutomataTS ThomsonAFN(IdEstado id) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		}
		
		// estado inicial deja de ser final, y borrar los estados finales anteriories
		a1.IniFinalEs(false);
		a1.finClear();
		
		a1.copyFinals(a2);
		a1.eliminarEstado(iniPrev.getId());
		return a1;
	}

}
