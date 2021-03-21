package algoritmo;

import automata.AutomataTS;
import automata.IdEstado;

public interface Thomson {
	
	/**
	 * Thomson simplificado AFN
	 * @param id
	 * @return
	 */
	public AutomataTS ThomsonSimplAFN(IdEstado id);
	
	/**
	 * Thomson AFN normal con lambdatransiciones
	 * @param id
	 * @return
	 */
	public AutomataTS ThomsonAFN(IdEstado id);
}
