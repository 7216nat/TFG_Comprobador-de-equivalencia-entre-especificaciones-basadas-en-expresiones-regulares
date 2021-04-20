package algoritmo;

import automata.AutomataTS;
import automata.IdEstado;

public interface Thomson {
	
	/**
	 * Thomson simplificado AFN
	 * @param id: identificador de los estados 
	 * @return AutomataTS
	 */
	public AutomataTS ThomsonSimplAFN(IdEstado id);
	
	/**
	 * Thomson AFN normal con lambdatransiciones
	 * @param id: identificador de los estados 
	 * @return AutomataTS
	 */
	public AutomataTS ThomsonAFN(IdEstado id);
}
