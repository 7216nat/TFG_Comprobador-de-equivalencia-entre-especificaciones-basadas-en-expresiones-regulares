package automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Automata {
	protected Map<Integer, Estado> aut;
	// referencias al estado inicial y finales
	protected Estado _ini;
	protected List<Estado> _acept;

	/**
	 * Constructora por defecto
	 */
	public Automata() {
		aut = new HashMap<>();
		_ini = null;
		_acept = new ArrayList<>();
	}

	/**
	 * Constructora para el conjunto vacio
	 */
	public Automata(int ini) {
		_ini = new Estado(ini, true, false);
		_acept = new ArrayList<>();
		aut = new HashMap<>();
		addEstado(_ini);
	}

	/**
	 * Constructora para automata cualquiera
	 */
	public Automata(int ini, int acept) {
		_acept = new ArrayList<>();
		aut = new HashMap<>();
		if (ini != acept) {
			_ini = new Estado(ini, true, false);
			Estado tmp = new Estado(acept, false, true);
			addEstado(_ini);
			addEstado(tmp);
		} else {
			_ini = new Estado(ini, true, true);
			addEstado(_ini);
			_acept.clear();
		}
	}

	/**
	 * Añade estado, compruba si es inicial o final para añadir nuevas referencias
	 */
	public void addEstado(Estado estado) {
		aut.put(estado.getId(), estado);
		if (estado.esIni()) {
			cambioIni(estado);
		}
		if (estado.esFin()) {
			this._acept.add(estado);
		}
	}

	/**
	 * Añade transicion del estado con id estado al estado con id dest, a través del
	 * símbolo simb
	 */
	public void addTransicion(int estado, int dest, String simb) {
		aut.get(estado).addTrans(new Transicion(dest, simb));
	}

	/**
	 * Devuelve el mapa de estados del autómata
	 */
	public Map<Integer, Estado> getAutomata() {
		return this.aut;
	}

	/**
	 * es1 es el estado en que quedarán los estados es1 y es2 Se elimina es2
	 */
	public void unirEliminar(int es1, int es2) {
		aut.get(es1).unir(aut.get(es2).getTrans());
		aut.forEach((k, v) -> v.recolocarTransiciones(es2, es1));
		eliminarEstado(es2);
	}

	/**
	 * Se elimina el estado con id estado, dependiendo de que sea inicial o final se
	 * eliminan tambien sus referencias
	 */
	public void eliminarEstado(int estado) {
		if (estado == this._ini.getId())
			this._ini = null;
		if (aut.get(estado).esFin()) {
			this._acept.removeIf(k -> k.getId() == estado);
		}
		aut.remove(estado);
		aut.forEach((k, v) -> v.eliminarTransicionesA(estado));
	}

	/**
	 * Devuelve true si el estado con id: id es final, false si no
	 */
	public boolean esFinal(int id) {
		return (aut.get(id).esFin());
	}

	/**
	 * Devuelve true si la transición que sale de estado con simbolo s existe.
	 * Devuelve false si no.
	 */
	public boolean existsTrans(int estado, String s) {
		Transicion cmp = new Transicion(estado, s);
		return this.aut.get(estado).getTrans().contains(cmp);
	}

	/**
	 * Devuelve el id del destino de la transición que sale de estado con simbolo s
	 */
	public int getDestTrans(int estado, String s) {
		Set<Transicion> tr = this.aut.get(estado).getTrans();
		Iterator<Transicion> trIt = tr.iterator();
		boolean encontrado = false;
		int destino = -1;
		while (!encontrado && trIt.hasNext()) {
			Transicion aux = trIt.next();
			if (aux.getSymb().equals(s)) {
				destino = aux.getEstadoDest();
				encontrado = true;
			}
		}
		return destino;
	}

	/**
	 * Devuelve el estado identificado por id
	 */
	public Estado getEstado(int id) {
		return this.aut.get(id);

	}

	/**
	 * devuelve el conjunto de transiciones de estado id
	 * @param id: identificador
	 * @return: set de transiciones
	 */
	public Set<Transicion> getTransEstado(int id) {
		return aut.get(id).getTrans();
	}

	// -----------------Funciones comunes sacadas de Thomson------------------

	/**
	 * Cambia a inicial el estado con el id que le pasemos por parámetro
	 */
	public void cambioIni(Estado id) {
		if (this._ini != null)
			this._ini.cambioIni(false);
		this._ini = id;
		this._ini.cambioIni(true);
	}

	/**
	 * Cambia si estado con identificador "es" es final o no
	 */
	public void cambioFin(Estado es, boolean fin) {
		es.cambioFin(fin);
		if (fin) {
			if (!this._acept.contains(es)) {
				_acept.add(es);
			}
		}
		if (!fin) {
			if (this._acept.contains(es)) {
				_acept.remove(es);
			}
		}
	}

	/**
	 * devuelve estado inicial
	 * @return Int identificador
	 */
	public Estado getIni() {
		return _ini;
	}

	/**
	 * devuelve lista de estados finales
	 * @return List<Estado>
	 */
	public List<Estado> getFin() {
		return _acept;
	}

	// ---------------END Funciones comunes sacadas de Thomson --------------------

//	/* ---------------Funciones para Thompson simplificado------------------------- */
//	
//	/**
//	 * Une los estados con id. es1 y es2, no elimina ninguno, aunque deja es2
//	 * inalcanzable
//	 */
//	
//	public void unirSinEliminar(int es1, int es2) {
//		aut.get(es1).unir(aut.get(es2).getTrans());
//		aut.forEach((k, v) -> v.recolocarTransicionesSinBorrar(es2, es1));
//	}
//
//	/**
//	 * Hace una copia de todo el automata sin las refenrencias
//	 */
//	public void copyAll(Automata aut) {
//		this.aut.putAll(aut.aut);
//	}
//
//	/**
//	 * copiar la lista de referencias a los estados finales
//	 * 
//	 * @param aut
//	 */
//	public void copyFinals(Automata aut) {
//		this._acept.addAll(aut._acept);
//	}
//
//	/**
//	 * pone a estado final de ini al parametro
//	 */
//	public void IniFinalEs(boolean es) {
//		_ini.cambioFin(es);
//	}
//
//	/**
//	 * Hace que el estado inicial deje de ser inicial
//	 */
//	public void quitarIni() {
//		_ini.cambioIni(false);
//		_ini.cambioFin(false);
//	}
//
//	/**
//	 * pone todos los estados finales no finales
//	 */
//	public void quitarTodosFin() {
//		for (Estado e : _acept)
//			e.cambioFin(false);
//	}
//
//	/**
//	 * Limpia la lista de los estados de aceptación
//	 */
//	public void finClear() {
//		this._acept.clear();
//	}
//
//	/*
//	 * ----------------------End Funciones para Thomson Simplificado--------------------
//	 */
	
	/**
	 * mostrar todos los estados con sus transiciones
	 */
	public void show() {
		// aut.forEach((k, v) -> + ));
		for (Integer k : aut.keySet()) {
			Estado aux = aut.get(k);
			if (aux.esIni())
				System.out.print("-Estado Inicial- ");
			if (aux.esFin())
				System.out.print("-Estado Final- ");
			System.out.print("Key: " + k + ": Transitions: ");
			System.out.println(aux.toString());
		}

	}

	/* Funciones Hopcroft-karp */
	/**
	 * Solo se puede usar si el automata tiene estados EstadoTh. Dado un estado y un
	 * carácter, devuelve -1 si no hay una transición que sale del estado con
	 * caracter s, si existe, devuelve el id del estado
	 */
	public int existsTransEq(int estado, String s) {
		int exist = -1;
		EstadoTh es = (EstadoTh) this.aut.get(estado);
		Iterator<Transicion> it = es.getTrans().iterator();
		while (exist == -1 && it.hasNext()) {
			Transicion tr = it.next();
			if (tr.getSymb().equals(s))
				exist = tr.getEstadoDest();
		}
		return exist;
	}
	
	/**
	 * lambda cierre de un estado concreto
	 * @param es: estado a aplicar
	 * @param idst: id del nuevo estado que entrara en AFD
	 * @return: estado nuevo
	 */
	public EstadoTh lambdaCierre(Estado es, int idst) {
		// Creo el estado que entrará en el AFD
		EstadoTh nuevo = new EstadoTh(idst);
		// Le añado a sí mismo
		nuevo.addEquiv(es.getId());

		Queue<Estado> estados = new LinkedList<>();
		estados.add(es);
		Estado estado;

		while (!estados.isEmpty()) {
			estado = estados.poll();
			// Recorro las transiciones del viejo estado buscando vacías
			// Cuando encuentro transición vacía, añado el objetivo a la clase de
			// equivalencia
			// También le añado al nuevo sus transiciones para no tener que volver a él
			Iterator<Transicion> it = estado.getTrans().iterator();
			while (it.hasNext()) {
				Transicion aux = it.next();
				if (aux.getSymb().equals("&")) {
					// Solo lo añado como estado para explorar más lambdatransiciones si no lo he
					// hecho ya.
					if (!nuevo.getEq().contains(aux.getEstadoDest()))
						estados.add(aut.get(aux.getEstadoDest()));
					nuevo.addEquiv(aux.getEstadoDest());
					if (aut.get(aux.getEstadoDest()).esFin())
						nuevo.cambioFin(true);
				}
			}
		}
		return nuevo;
	}

	/**
	 * Hace el lambdaCierre de todo el autómata en una sola función
	 */
	public void lambdaCierreCompleto() {
		Set<Integer> tragados = new HashSet<>();
		Set<Transicion> transiciones;
		Queue<Estado> estados = new LinkedList<>();
		Transicion tr;
		// Recorro todos los estados
		for (Map.Entry<Integer, Estado> dato : aut.entrySet()) {
			// Si el estado se lo ha atragado otro, ni lo miro
			if (!tragados.contains(dato.getKey())) {
				estados.add(dato.getValue());
				Estado ini = dato.getValue();
				Set<Integer> visitados = new HashSet<>();
				while (!estados.isEmpty()) {
					// Recorro las transiciones buscando vacías
					Estado est = estados.poll();
					transiciones = new HashSet<>();
					transiciones.addAll(est.getTrans());
					Iterator<Transicion> it = transiciones.iterator();
					while (it.hasNext()) {
						tr = it.next();
						// Si es transicion vacia
						if (tr.getSymb().equals("&")) {
							Estado es2 = aut.get(tr.getEstadoDest());
							ini.unir(es2.getTrans());
							if(es2.esFin())
								ini.cambioFin(true);
							if (!visitados.contains(es2.getId())) {
								estados.add(es2);
								visitados.add(es2.getId());
							}
							tragados.add(es2.getId());
						}
					}
				}
			}

		}
		
		Iterator<Integer> it2 = tragados.iterator();
		int it2d;
		while(it2.hasNext()) {
			it2d = it2.next();
			eliminarEstado(it2d);
		}
	}
}
