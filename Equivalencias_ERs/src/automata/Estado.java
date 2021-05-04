package automata;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Estado {
	private int id;
	private boolean inicial;
	private boolean fin;
	private Set<Transicion> trans;

	/**
	 * Constructora si el estado no es ni inicial ni final
	 */
	public Estado(int _id) {
		id = _id;
		inicial = false;
		fin = false;
		trans = new HashSet<>();
	}
	
	/**
	 * Constructora si el estado es inicial y/o final
	 */
	public Estado(int _id, boolean ini, boolean fin) {
		id = _id;
		inicial = ini;
		this.fin = fin;
		trans = new HashSet<>();
	}
	
	/**
	 * get id del estado
	 * @return: id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * añadir transicion al set
	 * @param tr
	 */
	public void addTrans(Transicion tr) {
		trans.add(tr);
	}

	/**
	 * Elimina una transición del estado
	 */
	public void deleteTrans(Transicion tr) {
		this.trans.remove(tr);
	}
	
	/**
	 * devuelve set de transiciones
	 * @return
	 */
	public Set<Transicion> getTrans() {
		return trans;
	}

	/**
	 * Se llama con el estado al que vamos a meterle las transiciones del estado
	 * entre parentesis
	 */
	public void unir(Set<Transicion> es2) {
		this.trans.addAll(es2);
	}
	
	/**
	 * cambiar inicial a ini
	 * @param ini
	 */
	public void cambioIni(boolean ini) {
		inicial = ini;
	}
	
	/**
	 * cambiar final a fin
	 * @param fin
	 */
	public void cambioFin(boolean fin) {
		this.fin = fin;
	}
	
	/**
	 * elimina todas las transiciones que vayan a "estado"
	 * @param estado
	 */
	public void eliminarTransicionesA(int estado) {
		Iterator<Transicion> it = trans.iterator();
		while (it.hasNext()) {
			Transicion t = it.next();
			if (t.getEstadoDest() == estado) {
				it.remove();
			}
		}
	}

	/**
	 * Se hace que las transiciones que iban a es2, ahora vayan a es1
	 */
	public void recolocarTransiciones(int es2, int es1) {
		Iterator<Transicion> it = trans.iterator();
		Set<Transicion> aux = new HashSet<>();
		while (it.hasNext()) {
			Transicion t = it.next();
			if (t.getEstadoDest() == es2) {
				aux.add(new Transicion(es1, t.getSymb()));
				it.remove();
			}
		}
		trans.addAll(aux);
	}
	
	/*
	 *  Funciones para Thomson Simplificado
	 */
	
	/**
	 * getter de ini
	 * @return
	 */
	public boolean esIni() {
		return inicial;
	}
	
	/**
	 * getter de fin
	 * @return
	 */
	public boolean esFin() {
		return fin;
	}
	
	/**
	 * Se copia las transiciones que iban a es2 para es1 
	 * @param es2
	 * @param es1
	 */
	public void recolocarTransicionesSinBorrar(int es2, int es1) {
		Iterator<Transicion> it = trans.iterator();
		Set<Transicion> aux = new HashSet<>();
		while (it.hasNext()) {
			Transicion t = it.next();
			if (t.getEstadoDest() == es2) {
				aux.add(new Transicion(es1, t.getSymb()));
			}
		}
		trans.addAll(aux);
	}
	
	/*
	 * end Funciones para Thomson Simplificado
	 */
	
	/*
	  Funciones añadidas en algoritmo de seguidores
	 */
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
			 return true;
		 }
	     if (!(o instanceof Estado)) {
	            return false;
	     }
	     Estado estado = (Estado) o;
	     return id == estado.id;
	}
	
	/*
	  END Funciones añadidas en algoritmo de seguidores
	 */
	
	@Override
	public String toString() {
		String salida = "";
		trans.forEach(k -> System.out.print(k.getSymb() + " -> " + k.getEstadoDest() + "; "));
		return salida;

	}

}
