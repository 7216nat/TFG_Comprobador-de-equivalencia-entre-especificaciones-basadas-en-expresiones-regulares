package automata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Estado {
	private int id;
	private boolean inicial;
	private boolean fin;
	private HashSet<Transicion> trans;
	
	/**
	 * Constructora si el estado no es ni inicial ni final
	 */
	public Estado(int _id) {
		id = _id;
		inicial = false;
		fin = false;
		trans = new HashSet<Transicion>();
	}
	
	/**
	 * Constructora si el estado es inicial y/o final
	 * */
	public Estado(int _id, boolean ini, boolean fin) {
		id = _id;
		inicial = ini;
		this.fin = fin;
		trans = new HashSet<Transicion>();
	}
	
	public void addTrans(Transicion tr) {
		trans.add(tr);
	}
	
	/**
	 * 
	 * Elimina una transición del estado
	 */
	public void deleteTrans(Transicion tr) {
		boolean encontrado = false;
		Iterator<Transicion> it = trans.iterator();
		while(!encontrado || !it.hasNext()) {
			if(it.next().compare(tr)) {
				it.remove();
				encontrado = true;
			}
		}
	}
	
	public HashSet<Transicion> getTrans(){
		return trans;
	}
	/**
	 * Se llama con el estado al que vamos a meterle las transiciones
	 * del estado entre parentesis
	 * */
	public void unir(HashSet<Transicion> es2) {
		this.trans.addAll(es2);
	}
	
	public void cambioIni(boolean ini) {
		inicial = ini;
	}
	
	public void cambioFin (boolean fin) {
		this.fin = fin;
	}
	
	public String toString() {
		String salida = "";
		trans.forEach((k) -> System.out.println(k.getId() + " por " + k.getSymb()));
		return salida;
		
	}

	public void eliminarTransicionesA(int estado) {
		Iterator it = trans.iterator();
		while(it.hasNext()) {
			Transicion t = (Transicion) it.next();
			if(t.getId() == estado) {
				it.remove();
			}
		}
	}
	/**
	 * Se hace que las transiciones que iban a es2, ahora vayan a es1
	 */
	public void recolocarTransiciones(int es2, int es1) {
		Iterator it = trans.iterator();
		HashSet<Transicion> aux = new HashSet<Transicion>();
		while(it.hasNext()) {
			Transicion t = (Transicion) it.next();
			if(t.getId() == es2) {
				aux.add(new Transicion(es1, t.getSymb()));
				it.remove();
			}
		}
		trans.addAll(aux);
	}
	
}
