package algoritmo;

import automata.IdEstado;
import objects.BerrySethiNode;

public interface BerrySethi {
	/**
	 * creacion del nodo de berrysethi
	 * @param id: identificador de los estados
	 * @return: nodo inicial
	 */
	public BerrySethiNode createBerrySethiNode(IdEstado id);
}
