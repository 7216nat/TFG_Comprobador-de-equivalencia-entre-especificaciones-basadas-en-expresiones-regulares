package automata;

import java.util.ArrayList;
import java.util.HashMap;

import objects.ExpressionBase;
import objects.String_ref;
import parser.ParserER;

public class Automata {
	private HashMap<String, ArrayList<Transicion>> aut;
	private int tam;
	
	public Automata() {
		aut = new HashMap<String, ArrayList<Transicion>>();
		tam = 0;
	}
	
	public void addEstado(String estado) {
		if(!aut.containsKey(estado)) {
			ArrayList<Transicion> aux = new ArrayList<Transicion>();
			aut.put(estado, aux);
			tam++;
		}
	}
	
	public void addTransicion(String estado, String dest, char simb) {
		if(aut.containsKey(estado) && aut.containsKey(dest)) {
			Transicion nuevaT = new Transicion(dest, simb);
			if(!aut.get(estado).contains(nuevaT))
				aut.get(estado).add(nuevaT);
		}
	}
	
	public void eliminarEstado(String estado) {
		aut.remove(estado);
	}
	
	public void show() {
		aut.forEach((k,v) -> System.out.println("Key: " + k + ": Value: " + v.toString()));
		
	}

}
