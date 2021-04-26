package algoritmo;

import java.util.Map;

import automata.IdEstado;
import objects.BerrySethiNode;

public interface BerrySethi {
	/**
	 * creacion del nodo de berrysethi
	 * @param map: mapa de estados validos
	 * @param id: identificador de los estados
	 * @return: nodo inicial
	 */
	public BerrySethiNode createBerrySethiNode(Map<Integer, BerrySethiNode> map, IdEstado id);
}
