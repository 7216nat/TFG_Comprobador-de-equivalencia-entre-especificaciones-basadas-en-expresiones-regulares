package algoritmo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import automata.Automata;
import automata.AutomataHK;
import automata.Estado;
import automata.EstadoHK;
import automata.EstadoHKSubconj;
import automata.EstadoTh;
import automata.IdEstado;
import automata.Transicion;
import objects.BerrySethiNode;
import objects.ExpressionBase;

public class Algoritmos {
	
	static final String EQ = "Equivalentes";
	static final String ACEP_RECH = "Aceptada por lenguaje1\nRechazada por lenguaje2";
	static final String RECH_ACEP = "Rechazada por lenguaje1\nAceptada por lenguaje2";
	/**
	 * Determinacion Hopcraft-Karp
	 * 
	 * @param at1:  el automata 1 a comparar
	 * @param at2:  el automata 2 a comparar
	 * @param idst: el id del proximo estado
	 * @param simb: arraylist donde se nos dicen los simbolos que hay en la
	 *              expresion
	 * @param sinlambda: indica si hay que realizar lambdacierre
	 * @return
	 */
	public static Equivalencia detHopKarp(Automata at1, Automata at2, IdEstado idst, List<String> simb, boolean sinlambda) {
		Map<Integer, EstadoTh> aExplorar = new HashMap<>();
		Queue<SimEsEs> aComparar = new LinkedList<>();
		Estado inicial = at1.getIni();
		Estado inicial2 = at2.getIni();

		// Creo los autómatas
		Automata afd1 = new Automata();
		Automata afd2 = new Automata();

		// Creo el primer estado del autómata
		EstadoTh iniAFD1;
		EstadoTh iniAFD2;
		if (!sinlambda) {
			iniAFD1 = at1.lambdaCierre(inicial, idst.nextId());
			iniAFD2 = at2.lambdaCierre(inicial2, idst.nextId());
		}
		else {
			iniAFD1 = new EstadoTh(idst.nextId());
			iniAFD1.addEquiv(inicial.getId());
			iniAFD2 = new EstadoTh(idst.nextId());
			iniAFD2.addEquiv(inicial2.getId());
		}
		if (inicial.esFin())
			iniAFD1.cambioFin(true);
		if (inicial2.esFin())
			iniAFD2.cambioFin(true);

		// Acoplo el estado en los automatas y les doy valor inicio.
		afd1.addEstado(iniAFD1);
		afd1.cambioIni(iniAFD1);

		afd2.addEstado(iniAFD2);
		afd2.cambioIni(iniAFD2);

		if (iniAFD1.esFin() && !iniAFD2.esFin()) {
			return new Equivalencia(false, ("Cadena &\n" + ACEP_RECH));
		} else if (!iniAFD1.esFin() && iniAFD2.esFin()) {
			return new Equivalencia(false, ("Cadena &\n"
					+ "Rechazada por lenguaje1\n"
					+ "Aceptada por lenguaje2"));
		}

		aExplorar.put(iniAFD1.getId(), iniAFD1);
		aExplorar.put(iniAFD2.getId(), iniAFD2);
		aComparar.add(new SimEsEs(iniAFD1.getId(), iniAFD2.getId(), ""));

		while (!aComparar.isEmpty()) {
			SimEsEs comparo = aComparar.remove();

			Iterator<String> it = simb.iterator();
			while (it.hasNext()) {
				String s = it.next();
				// si aun no se han explorado los estados, lo hago
				if (aExplorar.containsKey(comparo.getEstado1()) && !s.equals("&"))
					determinarEstado(comparo.getEstado1(), afd1, s, idst, at1, aExplorar, sinlambda);
				if (aExplorar.containsKey(comparo.getEstado2()) && !s.equals("&"))
					determinarEstado(comparo.getEstado2(), afd2, s, idst, at2, aExplorar, sinlambda);
				// Compruebo la existencia de las transiciones y su destino
				// La transicion existe y no esta
				int dest1 = afd1.existsTransEq(comparo.getEstado1(), s);
				boolean tr1 = (dest1 > -1);
				int dest2 = afd2.existsTransEq(comparo.getEstado2(), s);
				boolean tr2 = (dest2 > -1);

				// Si hay diferencias, muestro un error
				if (tr1 && !tr2) {
					if (afd1.esFinal(dest1))
						return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"
								+ "Aceptada por lenguaje1\n"
								+ "Error en el lenguaje2"));
					else
						return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"
								+ "Rechazada por lenguaje1\n"
								+ "Error en el lenguaje2"));
				}

				else if (!tr1 && tr2) {
					if (afd2.esFinal(dest2))
						return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"
								+ "Aceptada por lenguaje2\n"
								+ "Error en el lenguaje1"));
					else
						return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"
								+ "Rechazada por lenguaje2\n"
								+ "Error en el lenguaje1"));
				} else if (tr1 && tr2 && afd1.esFinal(dest1) && !afd2.esFinal(dest2)) {
					return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"+ ACEP_RECH));
				} else if (tr1 && tr2 && !afd1.esFinal(dest1) && afd2.esFinal(dest2)) {
					return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"+ RECH_ACEP));
				}
				if (tr1 && tr2) {
					EstadoTh estado1 = (EstadoTh) afd1.getEstado(dest1);
					EstadoTh estado2 = (EstadoTh) afd2.getEstado(dest2);
					if (!estado1.sameHKDet(estado2)) {
						SimEsEs c = new SimEsEs(dest1, dest2, comparo.getSimbolos() + s);
						aComparar.add(c);
						estado1.unirIgualA(estado2);
					}
				}
			}

		}
		return new Equivalencia(true, EQ);
	}
/**
 * 
 * @param viejo: identificador del estado a expandir
 * @param afd: automata determinista
 * @param simb: simbolo con el que se explora
 * @param idst
 * @param at: afnd
 * @param aExplorar: nodos que quedan sin explorar
 * @param sinlambda: con o sin lambda-transicion
 */
	private static void determinarEstado(int viejo, Automata afd, String simb, IdEstado idst, Automata at,
			Map<Integer, EstadoTh> aExplorar, boolean sinlambda) {

		EstadoTh nuevoEstado = new EstadoTh(idst.nextId());

		// Para cada uno de los estados a comparar, saco su trozo del AFD
		EstadoTh estado = (EstadoTh) afd.getEstado(viejo);
		Iterator<Integer> eqIt = estado.getEq().iterator();
		boolean esTrans = false;
		while (eqIt.hasNext()) {
			// esEq representa el id del estado al que es equivalente y estoy explorando
			// ahora mismo.
			int esEq = eqIt.next();
			Set<Transicion> trans = at.getTransEstado(esEq);
			Iterator<Transicion> transIt = trans.iterator();
			// Recorre todas las transiciones
			while (transIt.hasNext()) {
				Transicion tAux = transIt.next();
				// Si la transicion es con el simbolo que estoy evaluando:
				if (tAux.getSymb().equals(simb)) {
					esTrans = true;
					EstadoTh aux;
					if (sinlambda) {
						aux = new EstadoTh(-1);
						aux.addEquiv(at.getEstado(tAux.getEstadoDest()).getId());
					} else
						aux = at.lambdaCierre(at.getEstado(tAux.getEstadoDest()), -1);
					aux.getEq().forEach(k -> nuevoEstado.addEquiv(k));
					if (aux.esFin() || at.getEstado(tAux.getEstadoDest()).esFin())
						nuevoEstado.cambioFin(true);
				}
			}
		}
		if (esTrans) {
			// Si el nuevo estado que he creado no estaba ya:
			Iterator<Entry<Integer, Estado>> itAut = afd.getAutomata().entrySet().iterator();
			boolean noEsta = true;
			int yaExistente = -1;
			while (noEsta && itAut.hasNext()) {
				Entry<Integer, Estado> par = itAut.next();
				EstadoTh comp = (EstadoTh) par.getValue();
				if (comp.same(nuevoEstado)) {
					noEsta = false;
					yaExistente = comp.getId();
				}
			}
	
			if (noEsta) {
				afd.addEstado(nuevoEstado);
				aExplorar.put(nuevoEstado.getId(), nuevoEstado);
				afd.addTransicion(viejo, nuevoEstado.getId(), simb);
			} else if (!simb.equals("&"))
				afd.addTransicion(viejo, yaExistente, simb);
		}
	}

	/***
	 * Metodo Berry-Sethi
	 * @param e1: er 1
	 * @param e2: er 2
	 * @param idst: contador de identificadores
	 * @param simb: conjunto de simbolos
	 * @return Mensaje de equivalencia
	 */
	public static Equivalencia equivalenciaBerrySethi(ExpressionBase e1, ExpressionBase e2, IdEstado idst, List<String> simb) {
		Map<Integer, BerrySethiNode> states = new HashMap<>();
		BerrySethiNode bsn;
		
		// creacion de nodos berrysethi ascendente
		bsn = e1.createBerrySethiNode(states, idst);
		// construir listas de follows descendente
		bsn.buildEstados(new HashSet<>());
		Automata a1 = Algoritmos.buildBerrySethiAutomata(states, bsn, idst);
		a1.show();
		states = new HashMap<>();
		bsn = e2.createBerrySethiNode(states, idst);
		bsn.buildEstados(new HashSet<>());
		Automata a2 = Algoritmos.buildBerrySethiAutomata(states, bsn, idst);
		a2.show();
		return Algoritmos.detHopKarp(a1, a2, idst, simb, true);
	}
/**
 * Construir un automata por el metodo berrysethi
 *
 * @param list: diccionario de <id, nodo>: siendo id el identificador del estado y nodo el nodo asociado
 * @param root: nodo raiz
 * @param idst: contador de identificadores
 * @return automata BS
 */
	public static Automata buildBerrySethiAutomata(Map<Integer, BerrySethiNode> list, BerrySethiNode root, IdEstado idst) {
		Automata aut = new Automata();
		Estado state;
		
		// construye todos los estados
		for (Entry<Integer, BerrySethiNode> entry : list.entrySet()) {
			int id = entry.getKey();
			BerrySethiNode bsn = entry.getValue();
			// ver si es final o no
			if (root.getLast().contains(id))
				state = new Estado(id, false, true);
			else
				state = new Estado(id);
			aut.addEstado(state);
			// a�adir las transiciones
			for (Integer i : bsn.getFollow())
				state.addTrans(new Transicion(i, list.get(i).getSim()));
		}
		
		// el estado inicial
		if (root.getEmpty())
			state = new Estado(idst.nextId(), true, true);
		else
			state = new Estado(idst.nextId(), true, false);
		aut.addEstado(state);
		for (Integer i : root.getFirst())
			state.addTrans(new Transicion(i, list.get(i).getSim()));
		return aut;
	}

	/***************************
	 * algoritmo de las derivadas
	 ***************************/
	public static Equivalencia equivalenciaDer(ExpressionBase ex1, ExpressionBase ex2, IdEstado idst, List<String> simb) {
		// Quita el simbolo & de la lista de simbolos
		quitarLambda(simb);

		AutomataHK afd1 = new AutomataHK();
		AutomataHK afd2 = new AutomataHK();

		EstadoHK s1;
		if (ex1.eqLambda()) {
			s1 = new EstadoHK(idst.nextId(), ex1, true, true);
			afd1.addEstado(s1);
		} else {
			s1 = new EstadoHK(idst.nextId(), ex1, true, false);
			afd1.addEstado(s1);
		}

		EstadoHK s2;
		if (ex2.eqLambda()) {
			s2 = new EstadoHK(idst.nextId(), ex2, true, true);
			afd2.addEstado(s2);
		} else {
			s2 = new EstadoHK(idst.nextId(), ex2, true, false);
			afd2.addEstado(s2);
		}

		Queue<SimEsEs> aComparar = new LinkedList<>();

		if (afd1.getIni().esFin() && !afd2.getIni().esFin())
			return new Equivalencia(false, ("Cadena &\n"+ ACEP_RECH));
		else if (!afd1.getIni().esFin() && afd2.getIni().esFin())
			return new Equivalencia(false, ("Cadena &\n"+ RECH_ACEP));

		aComparar.add(new SimEsEs(afd1.getIni().getId(), afd2.getIni().getId(), ""));

		// set1 y set2 llevan las expresiones que ya estan en algun estado
		Map<ExpressionBase, EstadoHK> set1 = new HashMap<>();
		Map<ExpressionBase, EstadoHK> set2 = new HashMap<>();
		set1.put(ex1, s1);
		set2.put(ex2, s2);

		while (!aComparar.isEmpty()) {
			SimEsEs nodo = aComparar.poll();
			Iterator<String> itSimb = simb.iterator();
			// Para cada simbolo existente
			while (itSimb.hasNext()) {
				String s = itSimb.next();

				EstadoHK es1 = (EstadoHK) afd1.getEstado(nodo.getEstado1());
				ExpressionBase base1 = es1.getExp().derivada(s);
				EstadoHK estadonuevo1 = new EstadoHK(idst.nextId(), base1, false, false);
				if (base1.eqLambda())
					estadonuevo1.cambioFin(true);
				if (!set1.containsKey(base1)) {
					set1.put(base1, estadonuevo1);
					afd1.addEstado(estadonuevo1);
				} else {
					estadonuevo1 = set1.get(base1);
				}
				Transicion tr = new Transicion(estadonuevo1.getId(), s);
				es1.addTrans(tr);

				EstadoHK es2 = (EstadoHK) afd2.getEstado(nodo.getEstado2());
				ExpressionBase base2 = es2.getExp().derivada(s);
				EstadoHK estadonuevo2 = new EstadoHK(idst.nextId(), base2, false, false);
				if (base2.eqLambda())
					estadonuevo2.cambioFin(true);
				if (!set2.containsKey(base2)) {
					set2.put(base2, estadonuevo2);
					afd2.addEstado(estadonuevo2);
				} else {
					estadonuevo2 = set2.get(base2);
				}
				Transicion tr2 = new Transicion(estadonuevo2.getId(), s);
				es2.addTrans(tr2);

				if (estadonuevo1.esFin() && !estadonuevo2.esFin())
					return new Equivalencia(false, ("Cadena " + nodo.getSimbolos()+s+"\n"+ ACEP_RECH));
				if (!estadonuevo1.esFin() && estadonuevo2.esFin())
					return new Equivalencia(false, ("Cadena "+nodo.getSimbolos()+s+"\n"+ RECH_ACEP));

				if (!estadonuevo1.same(estadonuevo2)) {
					SimEsEs c = new SimEsEs(estadonuevo1.getId(), estadonuevo2.getId(), nodo.getSimbolos() + s);
					aComparar.add(c);
					estadonuevo1.unirIgualA(estadonuevo2);
				}

			}
		}

		return new Equivalencia(true ,EQ);
	}

	public static Equivalencia equivalenciaDerPar(ExpressionBase ex1, ExpressionBase ex2, IdEstado idst, List<String> simb) {
		//Quitar vacios de la lista
		quitarLambda(simb);
		
		AutomataHK afd1 = new AutomataHK();
		AutomataHK afd2 = new AutomataHK();
		
		EstadoHKSubconj s1;
		Set<ExpressionBase> e1 = new HashSet<>();
		e1.add(ex1);
		if(ex1.eqLambda())
			s1 = new EstadoHKSubconj(idst.nextId(), e1, true, true);
		else
			s1 = new EstadoHKSubconj(idst.nextId(), e1, true, false);
		afd1.addEstado(s1);
		
		EstadoHKSubconj s2;
		Set<ExpressionBase> e2 = new HashSet<>();
		e2.add(ex2);
		if(ex2.eqLambda())
			s2 = new EstadoHKSubconj(idst.nextId(), e2, true, true);
		else
			s2 = new EstadoHKSubconj(idst.nextId(), e2, true, false);
		afd2.addEstado(s2);
		
		Queue<SimEsEs> aComparar = new LinkedList<>();
		
		if(afd1.getIni().esFin() && !afd2.getIni().esFin())
			return new Equivalencia(false, ("Cadena &\n"+ ACEP_RECH));
		else if (!afd1.getIni().esFin() && afd2.getIni().esFin())
			return new Equivalencia(false, ("Cadena &\n"+ RECH_ACEP));

		aComparar.add(new SimEsEs(afd1.getIni().getId(), afd2.getIni().getId(), ""));
		
		Map<Set<ExpressionBase>, EstadoHKSubconj> set1 = new HashMap<>();
		Map<Set<ExpressionBase>, EstadoHKSubconj> set2 = new HashMap<>();
		Set<ExpressionBase> aux1 = new HashSet<>();
		aux1.add(ex1);
		set1.put(aux1, s1);
		Set<ExpressionBase> aux2 = new HashSet<>();
		aux2.add(ex2);
		set2.put(aux2, s2);
		
		while(!aComparar.isEmpty()) {
			SimEsEs nodo = aComparar.poll();
			Iterator<String> itSimb = simb.iterator();
			
			while(itSimb.hasNext()){
				String s = itSimb.next();
				
				EstadoHKSubconj es1 = (EstadoHKSubconj) afd1.getEstado(nodo.getEstado1());
				Set<ExpressionBase> base1 = multiplesDerPar(es1.getExp(), s);
				EstadoHKSubconj estadoNuevo1 = new EstadoHKSubconj(idst.nextId(), base1, false, false);
				if(estadoNuevo1.eqLambda())
					estadoNuevo1.cambioFin(true);
				if(!set1.containsKey(base1)) {
					set1.put(base1,  estadoNuevo1);
					afd1.addEstado(estadoNuevo1);
				}
				else
					estadoNuevo1 = set1.get(base1);
				Transicion tr = new Transicion(estadoNuevo1.getId(), s);
				es1.addTrans(tr);
				
				EstadoHKSubconj es2 = (EstadoHKSubconj) afd2.getEstado(nodo.getEstado2());
				Set<ExpressionBase> base2 = multiplesDerPar(es2.getExp(), s);
				EstadoHKSubconj estadoNuevo2 = new EstadoHKSubconj(idst.nextId(), base2, false, false);
				if(estadoNuevo2.eqLambda())
					estadoNuevo2.cambioFin(true);
				if(!set2.containsKey(base2)) {
					set2.put(base2,  estadoNuevo2);
					afd2.addEstado(estadoNuevo2);
				}
				else
					estadoNuevo2 = set2.get(base2);
				Transicion tr2= new Transicion(estadoNuevo2.getId(), s);
				es2.addTrans(tr2);
				
				if (estadoNuevo1.esFin() && !estadoNuevo2.esFin())
					return new Equivalencia(false, ("Cadena "+nodo.getSimbolos()+s+"\n"+ ACEP_RECH));
				if (!estadoNuevo1.esFin() && estadoNuevo2.esFin())
					return new Equivalencia(false, ("Cadena "+nodo.getSimbolos()+s+"\n"+ RECH_ACEP));

				if (!estadoNuevo1.same(estadoNuevo2)) {
					SimEsEs c = new SimEsEs(estadoNuevo1.getId(), estadoNuevo2.getId(), nodo.getSimbolos() + s);
					aComparar.add(c);
					estadoNuevo1.unirIgualA(estadoNuevo2);
				}				
			}	
		}
		return new Equivalencia(true, EQ);
	}
	
	private static Set<ExpressionBase> multiplesDerPar(Set<ExpressionBase> cEx, String sim){
		Iterator<ExpressionBase> it = cEx.iterator();
		Set<ExpressionBase> ret = new HashSet<>();
		while(it.hasNext()) {
			ret.addAll(it.next().derivadaParcial(sim));
		}
		return ret;
	}
	
	private static void quitarLambda(List<String> simb) {
		Iterator<String> col = simb.iterator();
		while (col.hasNext()) {
			String comp = col.next();
			if (comp.equals("&")) {
				col.remove();
				return;
			}
		}
	}

}
