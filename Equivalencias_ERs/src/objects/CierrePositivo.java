package objects;

import automata.AutomataTS;
import automata.Estado;
import automata.IdEstado;

public class CierrePositivo extends ExpressionBase {

	private ExpressionBase _e1;

	public CierrePositivo(ExpressionBase e1) {
		_e1 = e1;
	}

	public CierrePositivo() {
	}

	@Override
	public String toString() {
		return "[" + _e1.toString() + "]+";
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
		
		AutomataTS a1 = _e1.ThomsonAFN(id);
		int ini = a1.getIni().getId();

		for (Estado e : a1.getFin()) {
			a1.addTransicion(e.getId(), ini, "&");
		}
		
		return a1;
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
		
		a1.IniFinalEs(false);
		return a1;
	}

}
