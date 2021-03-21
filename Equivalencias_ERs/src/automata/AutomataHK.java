package automata;

import objects.ExpressionBase;

public class AutomataHK extends Automata {
	
	/**
	 * Encuentra y devuelve el EstadoHK que lleve expresión base ex
	 * null si no la hay
	 */
	public EstadoHK getByEx(ExpressionBase ex) {
		for (Integer k : aut.keySet()) {
			EstadoHK aux = (EstadoHK) aut.get(k);
			if(aux.getExp().equals(ex))
				return aux;
			
		}
		return null;
	}
}
