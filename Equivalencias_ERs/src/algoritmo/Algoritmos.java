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
	 * @param at1:  el aut�mata 1 a comparar
	 * @param at2:  el aut�mata 2 a comparar
	 * @param idst: el id del pr�ximo estado
	 * @param simb: arraylist donde se nos dicen los s�mbolos que hay en la
	 *              expresi�n
	 * @return
	 */
	public static String detHopKarp(Automata at1, Automata at2, IdEstado idst, ArrayList<String> simb) {
		HashMap<Integer, EstadoTh> aExplorar = new HashMap<Integer, EstadoTh>();
		Queue<SimEsEs> aComparar = new LinkedList<SimEsEs>();
		Estado inicial = at1.getIni();
		Estado inicial2 = at2.getIni();

		// Creo los aut�matas
		Automata afd1 = new Automata();
		Automata afd2 = new Automata();

		// Creo el primer estado del aut�mata
		EstadoTh iniAFD1 = at1.lambdaCierre(inicial, idst.getId());
		if (inicial.esFin())
			iniAFD1.cambioFin(true);
		idst.nextId();
		EstadoTh iniAFD2 = at2.lambdaCierre(inicial2, idst.getId());
		if (inicial2.esFin())
			iniAFD2.cambioFin(true);
		idst.nextId();

		// Acoplo el estado en los aut�matas y les doy valor inicio.
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
				// si a�n no se han explorado los estados, lo hago
				if (aExplorar.containsKey(comparo.getEstado1()) && !s.equals("&"))
					determinarEstado(comparo.getEstado1(), afd1, s, idst, at1, aExplorar);
				if (aExplorar.containsKey(comparo.getEstado2()) && !s.equals("&"))
					determinarEstado(comparo.getEstado2(), afd2, s, idst, at2, aExplorar);

				// Compruebo la existencia de las transiciones y su destino
				// La transici�n existe y no est�
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
						//No se qu� pasa con la siguiente l�nea.
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
					// Si la transici�n es con el s�mbolo que estoy evaluando:
					if (tAux.getSymb().equals(simb)) {
						EstadoTh aux = at.lambdaCierre(at.getEstado(tAux.getEstadoDest()), -1);
						aux.getEq().forEach((k) -> nuevoEstado.addEquiv(k));
						if (aux.esFin() || at.getEstado(tAux.getEstadoDest()).esFin() )
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
			} else if(!noEsta && !simb.equals("&"))
				afd.addTransicion(viejo, yaExistente, simb);
		}

	
	
	/********************************* algoritmo de seguidores **********************/
	
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
	
	/***************************algoritmo de las derivadas***************************/
	public static String derivadasHK(ExpressionBase ex1, ExpressionBase ex2, IdEstado idst, ArrayList<String> simb) {
		return "Equivalentes";
	}
	
	private ExpressionBase derivada(ExpressionBase ex, String sim) {
		Tipo tipo = (Tipo) ex.getType();
		//Casos base
		if(tipo == Tipo.VACIO) {
			return new Vacio();
		}
		else if (tipo == Tipo.LAMBDA)
			return new Lambdaa();
		else if (tipo == Tipo.SIMB && ex.get_sim() == sim)
			return new Lambdaa();
		else if (tipo == Tipo.SIMB && ex.get_sim() != sim)
			return new Vacio();
		//Casos recursivos
		else if(tipo == Tipo.CONCAT) {
			Concat exAux = (Concat) ex;
			ExpressionBase newEx = new Concat(
					derivada((Concat) exAux.getExpr1(), sim),
					exAux.getExpr2());
			if(newEx.produceVacio())
				newEx = new Union(newEx,
						derivada(exAux.getExpr2(), sim));
			return newEx;
		}
		
		else if(tipo == Tipo.UNION) {
			Union exAux = (Union) ex;
			ExpressionBase newEx = new Union(
					derivada(exAux.getExpr1(), sim),
					derivada(exAux.getExpr2(), sim));
			return newEx;
		}
		
		else if(tipo == Tipo.KLEEN) {
			Kleen exAux = (Kleen) ex;
			ExpressionBase newEx = new Concat(
					derivada(exAux.getExpr(), sim),
					ex); 
			return newEx;
		}
		
		return null;
		
	}
	
}
