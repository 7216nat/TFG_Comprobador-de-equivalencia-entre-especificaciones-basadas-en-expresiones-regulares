package algoritmo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import automata.Automata;
import automata.Estado;
import automata.IdEstado;
import automata.Transicion;

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
	public static String detHopKarp(Automata at1, Automata at2, IdEstado idst, ArrayList<Character> simb) {
		HashMap<Integer, EstadoTh> aExplorar = new HashMap<Integer, EstadoTh>();
		Queue<SimEsEs> aComparar = new LinkedList();
		Estado inicial = at1.getIni();
		Estado inicial2 = at2.getIni();

		// Creo los autómatas
		Automata afd1 = new Automata();
		Automata afd2 = new Automata();

		// Creo el primer estado del autómata
		EstadoTh iniAFD1 = at1.lambdaCierre(inicial, idst.getId(), at1);
		if (inicial.esFin())
			iniAFD1.cambioFin(true);
		idst.nextId();
		EstadoTh iniAFD2 = at2.lambdaCierre(inicial2, idst.getId(), at2);
		if (inicial2.esFin())
			iniAFD2.cambioFin(true);
		idst.nextId();

		// Acoplo el estado en los autómatas y les doy valor inicio.
		afd1.addEstado(iniAFD1);
		afd1.cambioIni(iniAFD1.getId());

		afd2.addEstado(iniAFD2);
		afd2.cambioIni(iniAFD2.getId());

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

			Iterator<Character> it = simb.iterator();
			while (it.hasNext()) {
				char s = it.next();
				// si aún no se han explorado los estados, lo hago
				if (aExplorar.containsKey(comparo.getEstado1()) && s != '&')
					determinarEstado(comparo.getEstado1(), afd1, s, idst, at1, aExplorar);
				if (aExplorar.containsKey(comparo.getEstado2()) && s != '&')
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
						//No se qué pasa con la siguiente línea.
						estado1.unirIgualA(estado2);
					}
				}
			}

		}

		return "Equivalentes";
	}

	private static void determinarEstado(int viejo, Automata afd, char simb, IdEstado idst, Automata at,
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
					if (tAux.getSymb() == simb) {
						EstadoTh aux = at.lambdaCierre(at.getEstado(tAux.getEstadoDest()), -1, at);
						aux.getEq().forEach((k) -> nuevoEstado.addEquiv(k));
						if (at.getEstado(tAux.getEstadoDest()).esFin())
							nuevoEstado.cambioFin(true);
					}
				}
			}

			// Si el nuevo estado que he creado no estaba ya:
			Iterator itAut = afd.getAutomata().entrySet().iterator();
			boolean noEsta = true;
			int yaExistente = -1;
			while (noEsta && itAut.hasNext()) {
				Map.Entry par = (Entry) itAut.next();
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
			} else if(!noEsta && simb != '&')
				afd.addTransicion(viejo, yaExistente, simb);
		}
	

}
