package algoritmo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

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
	/**
	 * 
	 * @param at1:  el autómata 1 a comparar
	 * @param at2:  el autómata 2 a comparar
	 * @param idst: el id del próximo estado
	 * @param simb: arraylist donde se nos dicen los símbolos que hay en la
	 *              expresión
	 * @return
	 */
	public static Equivalencia detHopKarp(Automata at1, Automata at2, IdEstado idst, ArrayList<String> simb) {
		HashMap<Integer, EstadoTh> aExplorar = new HashMap<>();
		Queue<SimEsEs> aComparar = new LinkedList<>();
		Estado inicial = at1.getIni();
		Estado inicial2 = at2.getIni();

		// Creo los autómatas
		Automata afd1 = new Automata();
		Automata afd2 = new Automata();

		// Creo el primer estado del autómata
		EstadoTh iniAFD1 = at1.lambdaCierre(inicial, idst.nextId());
		if (inicial.esFin())
			iniAFD1.cambioFin(true);
		EstadoTh iniAFD2 = at2.lambdaCierre(inicial2, idst.nextId());
		if (inicial2.esFin())
			iniAFD2.cambioFin(true);

		// Acoplo el estado en los autómatas y les doy valor inicio.
		afd1.addEstado(iniAFD1);
		afd1.cambioIni(iniAFD1);

		afd2.addEstado(iniAFD2);
		afd2.cambioIni(iniAFD2);

		if (iniAFD1.esFin() && !iniAFD2.esFin()) {
			return new Equivalencia(false, ("Cadena &\n"
					+ "Aceptada por lenguaje1\n"
					+ "Rechazada por lenguaje2"));
//			return "&, final aut1, nofinal aut2";
		} else if (!iniAFD1.esFin() && iniAFD2.esFin()) {
			return new Equivalencia(false, ("Cadena &\n"
					+ "Rechazada por lenguaje1\n"
					+ "Aceptada por lenguaje2"));
//			return "&, nofinal aut1, final aut2";
		}

		aExplorar.put(iniAFD1.getId(), iniAFD1);
		aExplorar.put(iniAFD2.getId(), iniAFD2);
		aComparar.add(new SimEsEs(iniAFD1.getId(), iniAFD2.getId(), ""));

		while (!aComparar.isEmpty()) {
			SimEsEs comparo = aComparar.remove();

			Iterator<String> it = simb.iterator();
			while (it.hasNext()) {
				String s = it.next();
				// si aún no se han explorado los estados, lo hago
				if (aExplorar.containsKey(comparo.getEstado1()) && !s.equals("&"))
					determinarEstado(comparo.getEstado1(), afd1, s, idst, at1, aExplorar);
				if (aExplorar.containsKey(comparo.getEstado2()) && !s.equals("&"))
					determinarEstado(comparo.getEstado2(), afd2, s, idst, at2, aExplorar);

				// Compruebo la existencia de las transiciones y su destino
				// La transición existe y no está
				int dest1 = afd1.existsTransEq(comparo.getEstado1(), s);
				boolean tr1 = (dest1 > -1);
				int dest2 = afd2.existsTransEq(comparo.getEstado2(), s);
				boolean tr2 = (dest2 > -1);

				// Si hay diferencias, muestro un error
				if (tr1 && !tr2) {
					if (afd1.esFinal(dest1))
						return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"
								+ "Aceptada por lenguaje1\n"
								+ "Rechazada por lenguaje2"));
//						return (comparo.getSimbolos() + s + ", final TP, error");
					else
						return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"
								+ "Rechazada por lenguaje1\n"
								+ "Aceptada por lenguaje2"));
//						return (comparo.getSimbolos() + s + ", no final TP, error");
				}

				else if (!tr1 && tr2) {
					if (afd2.esFinal(dest2))
						return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"
								+ "Aceptada por lenguaje2\n"
								+ "Error"));
//						return (comparo.getSimbolos() + s + ", final TP2, error");
					else
						return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"
								+ "Rechazada por lenguaje2\n"
								+ "Error"));
//						return (comparo.getSimbolos() + s + ", no final TP2, error");
				} else if (tr1 && tr2 && afd1.esFinal(dest1) && !afd2.esFinal(dest2)) {
					return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"
							+ "Aceptada por lenguaje1\n"
							+ "Rechazada por lenguaje2"));
//					return (comparo.getSimbolos() + s + ", final TP, no final TP2");
				} else if (tr1 && tr2 && !afd1.esFinal(dest1) && afd2.esFinal(dest2)) {
					return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"
							+ "Rechazada por lenguaje1\n"
							+ "Aceptada por lenguaje2"));
//					return (comparo.getSimbolos() + s + ", no final TP, final TP2");
				}
				if (tr1 && tr2) {
					EstadoTh estado1 = (EstadoTh) afd1.getEstado(dest1);
					EstadoTh estado2 = (EstadoTh) afd2.getEstado(dest2);
					if (!estado1.sameHKDet(estado2)) {
						SimEsEs c = new SimEsEs(dest1, dest2, comparo.getSimbolos() + s);
						aComparar.add(c);
						// No se qué pasa con la siguiente línea.
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
 */
	private static void determinarEstado(int viejo, Automata afd, String simb, IdEstado idst, Automata at,
			HashMap<Integer, EstadoTh> aExplorar) {

		EstadoTh nuevoEstado = new EstadoTh(idst.nextId());

		// Para cada uno de los estados a comparar, saco su trozo del AFD
		EstadoTh estado = (EstadoTh) afd.getEstado(viejo);
		Iterator<Integer> eqIt = estado.getEq().iterator();
		while (eqIt.hasNext()) {
			// esEq representa el id del estado al que es equivalente y estoy explorando
			// ahora mismo.
			int esEq = eqIt.next();
			HashSet<Transicion> trans = at.getTransEstado(esEq);
			Iterator<Transicion> transIt = trans.iterator();
			// Recorre todas las transiciones
			while (transIt.hasNext()) {
				Transicion tAux = transIt.next();
				// Si la transición es con el símbolo que estoy evaluando:
				if (tAux.getSymb().equals(simb)) {
					EstadoTh aux = at.lambdaCierre(at.getEstado(tAux.getEstadoDest()), -1);
					aux.getEq().forEach(k -> nuevoEstado.addEquiv(k));
					if (aux.esFin() || at.getEstado(tAux.getEstadoDest()).esFin())
						nuevoEstado.cambioFin(true);
				}
			}
		}

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

	

	/*********************************
	 * algoritmo de berrysethi
	 **********************/
	public static Automata buildBerrySethiAutomata(ArrayList<BerrySethiNode> list, BerrySethiNode root) {
		int id = 0;
		Automata aut = new Automata();
		Estado state;
		for (BerrySethiNode bsn : list) {
			if (root.getLast().contains(id))
				state = new Estado(id, false, true);
			else
				state = new Estado(id);
			aut.addEstado(state);
			for (Integer i : bsn.getFollow())
				state.addTrans(new Transicion(i, list.get(i).getSim()));
			id++;
		}
		
		if (root.getEmpty())
			state = new Estado(id, true, true);
		else
			state = new Estado(id, true, false);
		aut.addEstado(state);
		for (Integer i : root.getFirst())
			state.addTrans(new Transicion(i, list.get(i).getSim()));
		return aut;
	}

	/***************************
	 * algoritmo de las derivadas
	 ***************************/
	public static Equivalencia equivalenciaDer(ExpressionBase ex1, ExpressionBase ex2, IdEstado idst, ArrayList<String> simb) {
		// Quita el símbolo & de la lista de símbolos
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
			return new Equivalencia(false, ("Cadena &\n"
					+ "Aceptada por lenguaje1\n"
					+ "Rechazada por lenguaje2"));
		else if (!afd1.getIni().esFin() && afd2.getIni().esFin())
			return new Equivalencia(false, ("Cadena &\n"
					+ "Rechazada por lenguaje1\n"
					+ "Aceptada por lenguaje2"));

		aComparar.add(new SimEsEs(afd1.getIni().getId(), afd2.getIni().getId(), ""));

		// set1 y set2 llevan las expresiones que ya están en algún estado
		HashMap<ExpressionBase, EstadoHK> set1 = new HashMap<>();
		HashMap<ExpressionBase, EstadoHK> set2 = new HashMap<>();
		set1.put(ex1, s1);
		set2.put(ex2, s2);
		/*
		 * set1.add(ex1); set2.add(ex2);
		 */

		while (!aComparar.isEmpty()) {
			SimEsEs nodo = aComparar.poll();
			Iterator<String> itSimb = simb.iterator();
			// Para cada símbolo existente
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
					return new Equivalencia(false, ("Cadena " + nodo.getSimbolos()+s+"\n"
							+ "Aceptada por lenguaje1\n"
							+ "Rechazada por lenguaje2"));
//					return (nodo.getSimbolos() + s + ", final aut1, no final aut2");
				if (!estadonuevo1.esFin() && estadonuevo2.esFin())
					return new Equivalencia(false, ("Cadena "+nodo.getSimbolos()+s+"\n"
							+ "Rechazada por lenguaje1\n"
							+ "Aceptada por lenguaje2"));
//					return (nodo.getSimbolos() + s + ", no final aut1, final aut2");

				if (!estadonuevo1.same(estadonuevo2)) {
					SimEsEs c = new SimEsEs(estadonuevo1.getId(), estadonuevo2.getId(), nodo.getSimbolos() + s);
					aComparar.add(c);
					estadonuevo1.unirIgualA(estadonuevo2);
				}

			}
		}

		return new Equivalencia(true ,EQ);
	}

	public static Equivalencia equivalenciaDerPar(ExpressionBase ex1, ExpressionBase ex2, IdEstado idst, ArrayList<String> simb) {
		//Quitar vacíos de la lista
		quitarLambda(simb);
		
		AutomataHK afd1 = new AutomataHK();
		AutomataHK afd2 = new AutomataHK();
		
		EstadoHKSubconj s1;
		HashSet<ExpressionBase> e1 = new HashSet<>();
		e1.add(ex1);
		if(ex1.eqLambda())
			s1 = new EstadoHKSubconj(idst.nextId(), e1, true, true);
		else
			s1 = new EstadoHKSubconj(idst.nextId(), e1, true, false);
		afd1.addEstado(s1);
		
		EstadoHKSubconj s2;
		HashSet<ExpressionBase> e2 = new HashSet<>();
		e2.add(ex2);
		if(ex2.eqLambda())
			s2 = new EstadoHKSubconj(idst.nextId(), e2, true, true);
		else
			s2 = new EstadoHKSubconj(idst.nextId(), e2, true, false);
		afd2.addEstado(s2);
		
		Queue<SimEsEs> aComparar = new LinkedList<>();
		
		if(afd1.getIni().esFin() && !afd2.getIni().esFin())
			return new Equivalencia(false, ("Cadena &\n"
					+ "Aceptada por lenguaje1\n"
					+ "Rechazada por lenguaje2"));
//			return ("&, final aut1, no final aut2");
		else if (!afd1.getIni().esFin() && afd2.getIni().esFin())
			return new Equivalencia(false, ("Cadena &\n"
					+ "Rechazada por lenguaje1\n"
					+ "Aceptada por lenguaje2"));
//			return ("&, no final aut1, final aut2");

		aComparar.add(new SimEsEs(afd1.getIni().getId(), afd2.getIni().getId(), ""));
		
		HashMap<HashSet<ExpressionBase>, EstadoHKSubconj> set1 = new HashMap<>();
		HashMap<HashSet<ExpressionBase>, EstadoHKSubconj> set2 = new HashMap<>();
		HashSet<ExpressionBase> aux1 = new HashSet<>();
		aux1.add(ex1);
		set1.put(aux1, s1);
		HashSet<ExpressionBase> aux2 = new HashSet<>();
		aux2.add(ex2);
		set2.put(aux2, s2);
		
		while(!aComparar.isEmpty()) {
			SimEsEs nodo = aComparar.poll();
			Iterator<String> itSimb = simb.iterator();
			
			while(itSimb.hasNext()){
				String s = itSimb.next();
				
				EstadoHKSubconj es1 = (EstadoHKSubconj) afd1.getEstado(nodo.getEstado1());
				HashSet<ExpressionBase> base1 = multiplesDerPar(es1.getExp(), s);
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
				HashSet<ExpressionBase> base2 = multiplesDerPar(es2.getExp(), s);
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
					return new Equivalencia(false, ("Cadena "+nodo.getSimbolos()+s+"\n"
							+ "Aceptada por lenguaje1\n"
							+ "Rechazada por lenguaje2"));
//					return (nodo.getSimbolos() + s + ", final aut1, no final aut2");
				if (!estadoNuevo1.esFin() && estadoNuevo2.esFin())
					return new Equivalencia(false, ("Cadena "+nodo.getSimbolos()+s+"\n"
							+ "Rechazada por lenguaje1\n"
							+ "Aceptada por lenguaje2"));
//					return (nodo.getSimbolos() + s + ", no final aut1, final aut2");

				if (!estadoNuevo1.same(estadoNuevo2)) {
					SimEsEs c = new SimEsEs(estadoNuevo1.getId(), estadoNuevo2.getId(), nodo.getSimbolos() + s);
					aComparar.add(c);
					estadoNuevo1.unirIgualA(estadoNuevo2);
				}				
			}	
		}
		return new Equivalencia(true, EQ);
	}
	
	private static HashSet<ExpressionBase> multiplesDerPar(HashSet<ExpressionBase> cEx, String sim){
		Iterator<ExpressionBase> it = cEx.iterator();
		HashSet<ExpressionBase> ret = new HashSet<>();
		while(it.hasNext()) {
			ret.addAll(it.next().derivadaParcial(sim));
		}
		return ret;
	}
	
	private static void quitarLambda(ArrayList<String> simb) {
		Iterator<String> col = simb.iterator();
		while (col.hasNext()) {
			String comp = col.next();
			if (comp.equals("&")) {
				col.remove();
				return;
			}
		}
	}
	
	
	/***********************************
	 ******algoritmo de seguidores******
	 ***********************************/
	
	/**
	 * 
	 * @param at1:  el aut�mata 1 a comparar
	 * @param at2:  el aut�mata 2 a comparar
	 * @param idst: el id del pr�ximo estado
	 * @param simb: arraylist donde se nos dicen los s�mbolos que hay en la
	 *              expresi�n
	 * @return
	 */
	public static Equivalencia detHopKarpSinLambda(Automata at1, Automata at2, IdEstado idst, ArrayList<String> simb) {
		HashMap<Integer, EstadoTh> aExplorar = new HashMap<>();
		Queue<SimEsEs> aComparar = new LinkedList<>();
		Estado inicial = at1.getIni();
		Estado inicial2 = at2.getIni();

		// Creo los aut�matas
		Automata afd1 = new Automata();
		Automata afd2 = new Automata();

		// Creo el primer estado del aut�mata
		EstadoTh iniAFD1 = new EstadoTh(idst.nextId());
		iniAFD1.addEquiv(inicial.getId());
		if (inicial.esFin())
			iniAFD1.cambioFin(true);
		EstadoTh iniAFD2 = new EstadoTh(idst.nextId());
		iniAFD2.addEquiv(inicial2.getId());
		if (inicial2.esFin())
			iniAFD2.cambioFin(true);
		
		// Acoplo el estado en los aut�matas y les doy valor inicio.
		afd1.addEstado(iniAFD1);
		afd1.cambioIni(iniAFD1);

		afd2.addEstado(iniAFD2);
		afd2.cambioIni(iniAFD2);

		if (iniAFD1.esFin() && !iniAFD2.esFin()) {
			return new Equivalencia(false, ("Cadena &\n"
					+ "Aceptada por lenguaje1\n"
					+ "Rechazada por lenguaje2"));
//			return "&, final aut1, nofinal aut2";
		} else if (!iniAFD1.esFin() && iniAFD2.esFin()) {
			return new Equivalencia(false, ("Cadena &\n"
					+ "Rechazada por lenguaje1\n"
					+ "Aceptada por lenguaje2"));
//			return "&, nofinal aut1, final aut2";
		}

		aExplorar.put(iniAFD1.getId(), iniAFD1);
		aExplorar.put(iniAFD2.getId(), iniAFD2);
		aComparar.add(new SimEsEs(iniAFD1.getId(), iniAFD2.getId(), ""));

		while (!aComparar.isEmpty()) {
			SimEsEs comparo = aComparar.remove();

			Iterator<String> it = simb.iterator();
			while (it.hasNext()) {
				String s = it.next();
				// si a�n no se han explorado los estados, lo hago
				if (aExplorar.containsKey(comparo.getEstado1()) && !s.equals("&"))
					determinarEstadoSinLambda(comparo.getEstado1(), afd1, s, idst, at1, aExplorar);
				if (aExplorar.containsKey(comparo.getEstado2()) && !s.equals("&"))
					determinarEstadoSinLambda(comparo.getEstado2(), afd2, s, idst, at2, aExplorar);

				// Compruebo la existencia de las transiciones y su destino
				// La transici�n existe y no est�
				int dest1 = afd1.existsTransEq(comparo.getEstado1(), s);
				boolean tr1 = (dest1 > -1);
				int dest2 = afd2.existsTransEq(comparo.getEstado2(), s);
				boolean tr2 = (dest2 > -1);

				// Si hay diferencias, muestro un error
				if (tr1 && !tr2) {
					if (afd1.esFinal(dest1))
						return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"
								+ "Aceptada por lenguaje1\n"
								+ "Error"));
//						return (comparo.getSimbolos() + s + ", final TP, error");
					else
						return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"
								+ "Rechazada por lenguaje1\n"
								+ "Error"));
//						return (comparo.getSimbolos() + s + ", no final TP, error");
				}

				else if (!tr1 && tr2) {
					if (afd2.esFinal(dest2))
						return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"
								+ "Aceptada por lenguaje2\n"
								+ "Error"));
//						return (comparo.getSimbolos() + s + ", final TP2, error");
					else
						return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"
								+ "Rechazada por lenguaje2\n"
								+ "Error"));
//						return (comparo.getSimbolos() + s + ", no final TP2, error");
				} else if (tr1 && tr2 && afd1.esFinal(dest1) && !afd2.esFinal(dest2)) {
					return new Equivalencia(false, ("Cadena "+comparo.getSimbolos()+s+"\n"
							+ "Aceptada por lenguaje1\n"
							+ "Rechazada por lenguaje2"));
//					return (comparo.getSimbolos() + s + ", final TP, no final TP2");
				} else if (tr1 && tr2 && !afd1.esFinal(dest1) && afd2.esFinal(dest2)) {
					return new Equivalencia(false,("Cadena "+comparo.getSimbolos()+s+"\n"
							+ "Rechazada por lenguaje1\n"
							+ "Aceptada por lenguaje2"));
//					return (comparo.getSimbolos() + s + ", no final TP, final TP2");
				}
				if (tr1 && tr2) {
					EstadoTh estado1 = (EstadoTh) afd1.getEstado(dest1);
					EstadoTh estado2 = (EstadoTh) afd2.getEstado(dest2);
					if (!estado1.sameHKDet(estado2)) {
						SimEsEs c = new SimEsEs(dest1, dest2, comparo.getSimbolos() + s);
						aComparar.add(c);
						// No se qu� pasa con la siguiente l�nea.
						estado1.unirIgualA(estado2);
					}
				}
			}

		}

		return new Equivalencia(true ,EQ);
	}
/**
 * 
 * @param viejo: identificador del estado a expandir
 * @param afd: automata determinista
 * @param simb: simbolo con el que se explora
 * @param idst
 * @param at: afnd
 * @param aExplorar: nodos que quedan sin explorar
 */
	private static void determinarEstadoSinLambda(int viejo, Automata afd, String simb, IdEstado idst, Automata at,
			HashMap<Integer, EstadoTh> aExplorar) {

		EstadoTh nuevoEstado = new EstadoTh(idst.nextId());

		// Para cada uno de los estados a comparar, saco su trozo del AFD
		EstadoTh estado = (EstadoTh) afd.getEstado(viejo);
		Iterator<Integer> eqIt = estado.getEq().iterator();
		while (eqIt.hasNext()) {
			// esEq representa el id del estado al que es equivalente y estoy explorando
			// ahora mismo.
			int esEq = eqIt.next();
			HashSet<Transicion> trans = at.getTransEstado(esEq);
			Iterator<Transicion> transIt = trans.iterator();
			// Recorre todas las transiciones
			while (transIt.hasNext()) {
				Transicion tAux = transIt.next();
				// Si la transici�n es con el s�mbolo que estoy evaluando:
				if (tAux.getSymb().equals(simb)) {
					EstadoTh aux = new EstadoTh(-1);
					aux.addEquiv(at.getEstado(tAux.getEstadoDest()).getId());
					aux.getEq().forEach(k -> nuevoEstado.addEquiv(k));
					if (aux.esFin() || at.getEstado(tAux.getEstadoDest()).esFin())
						nuevoEstado.cambioFin(true);
				}
			}
		}

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
