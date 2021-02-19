package algoritmo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import automata.Automata;
import automata.AutomataHK;
import automata.Estado;
import automata.IdEstado;
import automata.Transicion;
import objects.Concat;
import objects.ExpressionBase;
import objects.Kleen;
import objects.Lambdaa;
import objects.Simbolo;
import objects.Tipo;
import objects.Union;
import objects.Vacio;

public class Algoritmos {
	/**
	 * 
	 * @param at1:  el autómata 1 a comparar
	 * @param at2:  el autómata 2 a comparar
	 * @param idst: el id del próximo estado
	 * @param simb: arraylist donde se nos dicen los símbolos que hay en la
	 *              expresión
	 * @return
	 */
	public static String detHopKarp(Automata at1, Automata at2, IdEstado idst, ArrayList<String> simb) {
		HashMap<Integer, EstadoTh> aExplorar = new HashMap<Integer, EstadoTh>();
		Queue<SimEsEs> aComparar = new LinkedList<SimEsEs>();
		Estado inicial = at1.getIni();
		Estado inicial2 = at2.getIni();

		// Creo los autómatas
		Automata afd1 = new Automata();
		Automata afd2 = new Automata();

		// Creo el primer estado del autómata
		EstadoTh iniAFD1 = at1.lambdaCierre(inicial, idst.getId());
		if (inicial.esFin())
			iniAFD1.cambioFin(true);
		idst.nextId();
		EstadoTh iniAFD2 = at2.lambdaCierre(inicial2, idst.getId());
		if (inicial2.esFin())
			iniAFD2.cambioFin(true);
		idst.nextId();

		// Acoplo el estado en los autómatas y les doy valor inicio.
		afd1.addEstado(iniAFD1);
		afd1.cambioIni(iniAFD1);

		afd2.addEstado(iniAFD2);
		afd2.cambioIni(iniAFD2);

		if (iniAFD1.esFin() && !iniAFD2.esFin()) {
			return "&, final aut1, nofinal aut2";
		} else if (!iniAFD1.esFin() && iniAFD2.esFin()) {
			return "&, nofinal aut1, final aut2";
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
						return (comparo.getSimbolos() + s + ", final TP, error");
					else
						return (comparo.getSimbolos() + s + ", no final TP, error");
				}

				else if (!tr1 && tr2) {
					if (afd2.esFinal(dest2))
						return (comparo.getSimbolos() + s + ", final TP2, error");
					else
						return (comparo.getSimbolos() + s + ", no final TP2, error");
				} else if (tr1 && tr2 && afd1.esFinal(dest1) && !afd2.esFinal(dest2)) {
					return (comparo.getSimbolos() + s + ", final TP, no final TP2");
				} else if (tr1 && tr2 && !afd1.esFinal(dest1) && afd2.esFinal(dest2)) {
					return (comparo.getSimbolos() + s + ", no final TP, final TP2");
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

		return "Equivalentes";
	}

	private static void determinarEstado(int viejo, Automata afd, String simb, IdEstado idst, Automata at,
			HashMap<Integer, EstadoTh> aExplorar) {

		EstadoTh nuevoEstado = new EstadoTh(idst.getId());
		idst.nextId();

		// Para el cada uno de los estados a comparar, saco su trozo del AFD
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
					aux.getEq().forEach((k) -> nuevoEstado.addEquiv(k));
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
			Entry<Integer, Estado> par = (Entry<Integer, Estado>) itAut.next();
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
		} else if (!noEsta && !simb.equals("&"))
			afd.addTransicion(viejo, yaExistente, simb);
	}

	/*********************************
	 * algoritmo de seguidores
	 **********************/

//	public static void lambdaCierre(Automata aut) {
//		Set<Estado> afd = new HashSet<Estado>();
//		Stack<Estado> aExplorar = new Stack<Estado>();
//		afd.add(aut.getIni());
//		aExplorar.add(aut.getIni());
//		
//		Estado tmp;
//		while(!aExplorar.empty()) {
//			tmp = aExplorar.pop();
//			
//			
//		}
//		
//	}

	/***************************
	 * algoritmo de las derivadas
	 ***************************/
	public static String derivadasHK(ExpressionBase ex1, ExpressionBase ex2, IdEstado idst, ArrayList<String> simb) {
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

		Queue<SimEsEs> aComparar = new LinkedList<SimEsEs>();

		if (afd1.getIni().esFin() && !afd2.getIni().esFin())
			return ("&, final aut1, no final aut2");
		else if (!afd1.getIni().esFin() && afd2.getIni().esFin())
			return ("&, no final aut1, final aut2");

		aComparar.add(new SimEsEs(afd1.getIni().getId(), afd2.getIni().getId(), ""));

		// set1 y set2 llevan las expresiones que ya están en algún estado
		HashMap<ExpressionBase, EstadoHK> set1 = new HashMap<ExpressionBase, EstadoHK>();
		HashMap<ExpressionBase, EstadoHK> set2 = new HashMap<ExpressionBase, EstadoHK>();
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
				ExpressionBase base1 = derivada(es1.getExp(), s);
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
				estadonuevo1.addTrans(tr);

				EstadoHK es2 = (EstadoHK) afd2.getEstado(nodo.getEstado2());
				ExpressionBase base2 = derivada(es2.getExp(), s);
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
				estadonuevo2.addTrans(tr2);

				if (estadonuevo1.esFin() && !estadonuevo2.esFin())
					return (nodo.getSimbolos() + s + ", final aut1, no final aut2");
				if (!estadonuevo1.esFin() && estadonuevo2.esFin())
					return (nodo.getSimbolos() + s + ", no final aut1, final aut2");

				if (!estadonuevo1.same(estadonuevo2)) {
					SimEsEs c = new SimEsEs(estadonuevo1.getId(), estadonuevo2.getId(), nodo.getSimbolos() + s);
					aComparar.add(c);
					estadonuevo1.unirIgualA(estadonuevo2);
				}

			}
		}

		return "Equivalentes";
	}

	private static ExpressionBase derivada(ExpressionBase ex, String sim) {
		Tipo tipo = (Tipo) ex.getType();
		// Casos base
		if (tipo == Tipo.VACIO) {
			return new Vacio();
		} else if (tipo == Tipo.LAMBDA)
			return new Lambdaa();
		else if ((tipo == Tipo.SIMB || tipo == Tipo.UNIONRANGOS || tipo == Tipo.RANGO)) {
			if(ex.get_sim().equals(sim))
				return new Lambdaa();
			else
				return new Vacio();
			
		}
		// Casos recursivos
		else if (tipo == Tipo.CONCAT) {
			Concat exAux = (Concat) ex;
			ExpressionBase newEx;
			ExpressionBase t1 = derivada(exAux.getExpr1(), sim);
			ExpressionBase t2 = exAux.getExpr2();
			if(t1 instanceof Vacio || t2 instanceof Vacio) {
				newEx = new Vacio();
			}
			else if(t1 instanceof Lambdaa)
				newEx = t2;
			else if(t2 instanceof Lambdaa)
				newEx = t1;
			else
				newEx = new Concat(t1,t2);
			/*
			try {
				newEx = new Concat(t1, exAux.getExpr2());
			} catch (VacioException e) {
				newEx = new Vacio();
			} catch (LambdaException e) {
				if (t1.getType() == Tipo.LAMBDA)
					newEx = exAux.getExpr2();
				else
					newEx = t1;
			}*/
			if (t1.eqLambda()) {
				t2 = derivada(exAux.getExpr2(), sim);
				if(!(t2 instanceof Vacio) && !(newEx instanceof Vacio))
					newEx = new Union(newEx, t2);
				/*try {
					newEx = new Union(newEx, derivada(exAux.getExpr2(), sim));
				} catch (VacioException e) {
					if (newEx.getType() == Tipo.VACIO)
						newEx = new Vacio();
				}*/
			}
			return newEx;
		}

		else if (tipo == Tipo.UNION) {
			Union exAux = (Union) ex;
			ExpressionBase newEx;
			ExpressionBase t1 = derivada(exAux.getExpr1(), sim);
			ExpressionBase t2 = derivada(exAux.getExpr2(), sim);
			
			if(t1 instanceof Vacio)
				newEx = t2;
			else if(t2 instanceof Vacio)
				newEx=t1;
			else
				newEx = new Union(t1,t2);
			/*try {
				newEx = new Union(derivada(exAux.getExpr1(), sim), derivada(exAux.getExpr2(), sim));
			} catch (VacioException e) {
				if (derivada(exAux.getExpr1(), sim).getType() == Tipo.VACIO)
					newEx = derivada(exAux.getExpr2(), sim);
				else
					newEx = derivada(exAux.getExpr1(), sim);

			}*/
			return newEx;
		}

		else if (tipo == Tipo.KLEEN) {
			Kleen exAux = (Kleen) ex;
			ExpressionBase newEx = null;
			ExpressionBase t1 = derivada(exAux.getExpr(), sim);
			if(t1 instanceof Vacio || ex instanceof Vacio)
				newEx = new Lambdaa();
			else if(t1 instanceof Lambdaa)
				newEx = ex;
			else if(ex instanceof Lambdaa)
				newEx = t1;
			else
				newEx = new Concat(t1,ex);
			
			/*try {
				newEx = new Concat(t1, ex);
			} catch (VacioException e) {
				newEx = new Lambdaa();
			} catch (LambdaException e) {
				if (t1.getType() == Tipo.LAMBDA)
					newEx = ex;
				else
					newEx = t1;
			}*/
			return newEx;

		}

		return null;
	}

}
