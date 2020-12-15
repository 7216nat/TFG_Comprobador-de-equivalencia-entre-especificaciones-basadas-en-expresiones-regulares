package test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import algoritmo.Algoritmos;
import automata.Automata;
import automata.Estado;
import automata.IdEstado;
import objects.*;
import parser.ParserER;

public class main {

	//Test paso de String a expresion regular
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		IdEstado estado = new IdEstado();
		System.out.println(estado.getId());
		System.out.println(estado.getId());
		System.out.println(estado.getId());
		int prueba = -9;
		prueba = estado.nextId();
		System.out.println("prueba" + prueba);
		prueba = estado.nextId();
		System.out.println("prueba" + prueba);
		/*String str = "hola+mundo(hola*+m)*(u+ndo)+sad*";
		str = "((a+b)a)*";
		//str = "abb";
		Set<Character> set = new HashSet<Character> ();
		ParserER parser = new ParserER(new String_ref(str), set);
		ExpressionBase er = parser.parse();
		Automata aut = er.ThomsonSimplAFN(new IdEstado());
		System.out.println(set.toString());
		System.out.println(er.toString());
		aut.show();
		
		
		//Pruebas Hopcroft-Karp+determinacion
		Automata aut1 = new Automata();
		aut1.addEstado(new Estado(0, true, true));
		Automata aut2 = new Automata();
		aut2.addEstado(new Estado(1, true, false));
		aut2.addEstado(new Estado(2, false, true));
		aut2.addTransicion(1, 2, '&');
		ArrayList<Character> sim = new ArrayList();
		sim.add('&');
		String salida = Algoritmos.detHopKarp(aut1, aut2, new IdEstado(), sim);
		System.out.println(salida);


		//Prueba2: Pasada
				 aut1 = new Automata();
				aut1.addEstado(new Estado(0, true, true));
				 aut2 = new Automata();
				aut2.addEstado(new Estado(1, true, false));
				aut2.addEstado(new Estado(2, false, false));
				aut2.addTransicion(1, 2, '&');
				aut2.addEstado(new Estado(3, false, true));
				aut2.addTransicion(2, 3, 'a');
				sim = new ArrayList();
				sim.add('&');
				sim.add('a');
				IdEstado state = new IdEstado();
				state.nextId();
				state.nextId();
				state.nextId();
				salida = Algoritmos.detHopKarp(aut1, aut2, state, sim);
				System.out.println(salida);
		//Prueba3: Pasada
				 aut1 = new Automata();
				aut1.addEstado(new Estado(0, true, true));
				aut1.addEstado(new Estado(1, false, false));
				aut1.addTransicion(0, 1, 'a');
				
				 aut2 = new Automata();
				aut2.addEstado(new Estado(2, true, false));
				aut2.addEstado(new Estado(3, false, true));
				aut2.addTransicion(2,3, '&');
				aut2.addEstado(new Estado(4));
				aut2.addTransicion(3, 4, 'a');
				 sim = new ArrayList();
				sim.add('&');
				sim.add('a');
				 state = new IdEstado();
				state.nextId();
				state.nextId();
				state.nextId();
				state.nextId();
				state.nextId();
				 salida = Algoritmos.detHopKarp(aut1, aut2, state, sim);
				System.out.println(salida);


		//Prueba4: Pasada
				 aut1 = new Automata();
				aut1.addEstado(new Estado(0, true, false));
				aut1.addEstado(new Estado(1, false, true));
				aut1.addTransicion(0, 1, 'a');
				
				 aut2 = new Automata();
				aut2.addEstado(new Estado(2, true, false));
				aut2.addEstado(new Estado(3, false, true));
				aut2.addTransicion(2,3, '&');
				aut2.addEstado(new Estado(4));
				aut2.addTransicion(3, 4, 'a');
				 sim = new ArrayList();
				sim.add('&');
				sim.add('a');
				 state = new IdEstado();
				state.nextId();
				state.nextId();
				state.nextId();
				state.nextId();
				state.nextId();
				 salida = Algoritmos.detHopKarp(aut1, aut2, state, sim);
				System.out.println(salida);

		//Prueba5:Pasada
		 aut1 = new Automata();
				aut1.addEstado(new Estado(0, true, false));
				aut1.addEstado(new Estado(1, false, true));
				aut1.addEstado(new Estado(2, false, false));
				aut1.addTransicion(0, 1, '&');
				aut1.addTransicion(1, 2, 'a');
				
				 aut2 = new Automata();
				aut2.addEstado(new Estado(3, true, false));
				aut2.addEstado(new Estado(4, false, true));
				aut2.addEstado(new Estado(5, false, true));
				aut2.addTransicion(3,4, '&');
				aut2.addTransicion(4, 5, 'a');
				 sim = new ArrayList();
				sim.add('&');
				sim.add('a');
				 state = new IdEstado();
				state.nextId();
				state.nextId();
				state.nextId();
				state.nextId();
				state.nextId();
				state.nextId();
				 salida = Algoritmos.detHopKarp(aut1, aut2, state, sim);
				System.out.println(salida);

Prueba6:
		aut1 = new Automata();
		aut1.addEstado(new Estado(0, true, false));
		aut1.addEstado(new Estado(1, false, false));
		aut1.addEstado(new Estado(2, false, false));
		aut1.addEstado(new Estado(3, false, true));
		aut1.addEstado(new Estado(4, false, false));
		aut1.addTransicion(0, 1, 'a');
		aut1.addTransicion(1, 2, 'b');
		aut1.addTransicion(1, 4, 'a');
		aut1.addTransicion(4, 4, 'a');
		aut1.addTransicion(4, 2, 'b');
		aut1.addTransicion(0, 2, 'b');
		aut1.addTransicion(2,3, 'a');
		aut1.addTransicion(3, 0, 'b');
		aut1.addTransicion(3,3, 'a');
		aut1.addTransicion(0,0, 'a');
		aut2 = new Automata();
		aut2.addEstado(new Estado(5, true, false));
		aut2.addEstado(new Estado(6, false, false));
		aut2.addEstado(new Estado(7, false, false));
		aut2.addEstado(new Estado(8, false, true));
		aut2.addTransicion(5, 6, 'a');
		aut2.addTransicion(6, 6, 'a');
		aut2.addTransicion(6, 7, 'b');
		aut2.addTransicion(5, 7, 'b');
		aut2.addTransicion(7, 8, 'a');
		aut2.addTransicion(8, 8, 'a');
		aut2.addTransicion(8, 5, 'b');
		sim = new ArrayList();
		//sim.add('&');
		sim.add('a');
		sim.add('b');
		state = new IdEstado();
		state.nextId();
		state.nextId();
		state.nextId();
		state.nextId();
		state.nextId();
		state.nextId();
		state.nextId();
		state.nextId();
		state.nextId();
		salida = Algoritmos.detHopKarp(aut1, aut2, state, sim);
		System.out.println(salida);
		*/
	}
	
	
	//Test creación automatas
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		Automata aut = new Automata();
		aut.addEstado();
		aut.addEstado();
		aut.addEstado();
		aut.addEstado();
		
		aut.addTransicion(0, 1, 'a');
		aut.addTransicion(1, 2, 'a');
		aut.addTransicion(2, 0, 'b');
		aut.addTransicion(1, 2, 'b');
		aut.addTransicion(0, 3, 'a');
		aut.addTransicion(2, 3, 'a');
		
		aut.show();
		System.out.println("\n");
		
		aut.eliminarEstado(1);
		aut.show();
		System.out.println("\n");
		
		aut.unir(2, 3);
		aut.show();
	}*/

}
