package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import algoritmo.Algoritmos;
import automata.Automata;
import automata.Estado;
import automata.IdEstado;
import objects.*;
import parser.ParserER;
import parser.String_ref;

public class main {

	// Test paso de String a expresion regular
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/// *
		/*****************************************
		 *** LOS RANGOS SON DEL TIPO [a-bk-lxksa]***
		 ******************************************/
		// Estos son equivalentes
		ArrayList<String> strList = new ArrayList<String>();
		ArrayList<String> strList2 = new ArrayList<String>();
		strList.add("a|(b|c)");
		strList2.add("(a|b)|c");
		strList.add("a|b");
		strList2.add("b|a");
		strList.add("a|%");
		strList2.add("%|a");
		strList.add("a|a");
		strList2.add("a");
		strList.add("a(bc)");
		strList2.add("(ab)c");
		strList.add("&a");
		strList2.add("a");
		strList.add("&a");
		strList2.add("a&");
		strList.add("a(b|c)");
		strList2.add("ab|ac");
		strList.add("(a|b)c");
		strList2.add("ac|bc");
		strList.add("%");
		strList2.add("%a");
		strList.add("a%");
		strList2.add("%a");
		strList.add("&|aa*");
		strList2.add("a*");
		strList.add("&|a*a");
		strList2.add("a*");
		strList.add("(ab)*a");
		strList2.add("a(ba)*");
		strList.add("(a*b)*a*");
		strList2.add("(a|b)*");
		strList.add("a*(ba*)*");
		strList2.add("(a|b)*");
		strList.add("(&|a)*");
		strList2.add("a*");
		strList.add("aa*");
		strList2.add("a*a");
		strList.add("%*");
		strList2.add("&");
		strList.add("(a*)*");
		strList2.add("a*");
		strList.add("&*");
		strList2.add("&");
		strList.add("(a*b*)*");
		strList2.add("(a|b)*");
		strList.add("(ab|a)*a");
		strList2.add("a(ba|a)*");
		strList.add("b*");
		strList2.add("b*|&");

		// Ejercicios FLI
		strList.add("(0|1)*");
		strList2.add("0*|1*");
		strList.add("0(120)*12");
		strList2.add("01(201)*2");
		strList.add("(0*1*)*");
		strList2.add("(0*1)*");
		strList.add("(01|0)*0");
		strList2.add("0(10|0)*");
		strList.add("(a|b)*");
		strList2.add("a*(ba*)*");
		strList.add("b*a*|a*b*");
		strList2.add("a*|b*");
		strList.add("(cb*c|cb*b)*");
		strList2.add("(cc)*|(cc)*(cb)(b|c)*");

		// String str = "(cb*c|cb*b)*";
		// String str2 = "(cc)*|(cc)*(cb)(b|c)*";
		// String str = "[a-cde-tx]*";
		// String str2 = "[a-bcd-tx]*";
		// String str = "[a-c]|[d-h]";
		// String str2 = "a|b|[c-h]";
		// String str = "%|gh";
		// String str2 = "%abc|gh";


		Iterator<String> deb = strList.iterator();
		Iterator<String> deb2 = strList2.iterator();
		while (deb.hasNext()) {
			String str = deb.next();
			String str2 = deb2.next();
			System.out.println(str);
			System.out.println(str2);

			// variables globales en dos expresiones
			IdEstado state = new IdEstado();
			Set<String> simbolosSet = new HashSet<String>();
			SortedSet<Character> inis = new TreeSet<Character>();
			SortedSet<Character> fins = new TreeSet<Character>();
			// SortedSet<Character> ssRango = new TreeSet<Character>();

			// parse expresion 1
			ArrayList<UnionRangos> rangos1 = new ArrayList<UnionRangos>();
			ParserER parser = new ParserER(new String_ref(str));
			ExpressionBase er = parser.parse();
			er.getSimbolosRangos(simbolosSet, rangos1, inis, fins);

			// parse expresion 2
			ArrayList<UnionRangos> rangos2 = new ArrayList<UnionRangos>();
			ParserER parser2 = new ParserER(new String_ref(str2));
			ExpressionBase er2 = parser2.parse();
			er2.getSimbolosRangos(simbolosSet, rangos2, inis, fins);

			// interseccion y y obtener los nuevos simbolos
			intersecUR(simbolosSet, rangos1, inis, fins);
			intersecUR(simbolosSet, rangos2, inis, fins);

			// quitar unionRango del arbol
			er = er.buildTreeDefinitivo();
			er2 = er2.buildTreeDefinitivo();

			System.out.println("PRUEBA THOMPSON");
			// Automata aut = (Automata)er.ThomsonAFN(state);
			Automata aut = (Automata) er.ThomsonSimplAFN(state);
//		System.out.println(er.toString());
			// aut.show();
			// Automata aut2 = (Automata)er2.ThomsonAFN(state);
			Automata aut2 = (Automata) er2.ThomsonSimplAFN(state);
//		System.out.println(er2.toString());
			// aut2.show();

			ArrayList<String> simb = new ArrayList<String>();
			Iterator<String> it = simbolosSet.iterator();
			while (it.hasNext()) {
				String c = it.next();
				simb.add(c);
			}

//		System.out.println(simb.toString());
			String resul = Algoritmos.detHopKarp(aut, aut2, state, simb);
			System.out.println(resul);
			// */
			
			System.out.println("PRUEBA THOMPSON");
			Automata aut5 = (Automata) er.ThomsonSimplAFN(state);
			Automata aut6 = (Automata) er2.ThomsonSimplAFN(state);

			String resul2 = Algoritmos.detHopKarpSinLambda(aut5, aut6, state, simb);
			System.out.println(resul2);

			System.out.println("PRUEBAS DERIVADAS");
			String d = Algoritmos.equivalenciaDer(er, er2, state, simb);
			System.out.println(d);

			System.out.println("PRUEBAS DERIVADAS PARCIALES");
			String dp = Algoritmos.equivalenciaDerPar(er, er2, state, simb);
			System.out.println(dp);

			System.out.println("PRUEBAS BERRY-SETHI");
			state = new IdEstado();
			BerrySethiNode bsn = er.createBerrySethiNode(state);
			ArrayList<BerrySethiNode> states = new ArrayList<BerrySethiNode>();
			bsn.buildEstados(states, new HashSet<Integer>());
			Automata aut3 = Algoritmos.buildBerrySethiAutomata(states, bsn);
			// aut3.show();

			state = new IdEstado();
			bsn = er2.createBerrySethiNode(state);
			states = new ArrayList<BerrySethiNode>();
			bsn.buildEstados(states, new HashSet<Integer>());
			Automata aut4 = Algoritmos.buildBerrySethiAutomata(states, bsn);
			// aut4.show();

			// System.out.println(simb.toString());
			String resulBS = Algoritmos.detHopKarp(aut3, aut4, state, simb);
			System.out.println(resulBS);

			System.out.println("---------------------------------------------------------");
		}

		// Comprobar lambda-cierre:
		/*
		 * Automata at1 = new Automata(); at1.addEstado(new Estado(state.nextId(), true,
		 * false));//0 at1.addEstado(new Estado(state.nextId(), false, true));//1
		 * at1.addEstado(new Estado(state.nextId(), false, false));//2 at1.addEstado(new
		 * Estado(state.nextId(), false, false));//3 at1.addEstado(new
		 * Estado(state.nextId(), false, false));//4 at1.addEstado(new
		 * Estado(state.nextId(), false, false));//5 at1.addEstado(new
		 * Estado(state.nextId(), false, true));//6 at1.addEstado(new
		 * Estado(state.nextId(), false, false));//7 at1.addEstado(new
		 * Estado(state.nextId(), false, false));//8 at1.addEstado(new
		 * Estado(state.nextId(), false, false));//9
		 * 
		 * at1.addTransicion(0, 1, "&"); at1.addTransicion(1, 2, "a");
		 * at1.addTransicion(2, 4, "&"); at1.addTransicion(4, 9, "a");
		 * at1.addTransicion(2, 3, "b"); at1.addTransicion(3, 5, "a");
		 * at1.addTransicion(5, 6, "&"); at1.addTransicion(5, 7, "&");
		 * at1.addTransicion(5, 8, "&");
		 * 
		 * at1.show();
		 * 
		 * at1.lambdaCierreCompleto(); System.out.println("\n\n"); at1.show();
		 */

		/***************************************
		 * **********PRUEBA BERRYSETHI********** *************************************
		 ************************************/
		// String str = "(cb*c|cb*b)*";
		// String str2 = "(cc)*|(cc)*(cb)(b|c)*";
//		String str = "[a-cde-tx]*";
//		String str2 = "[a-bcd-tx]*";
//		String str = "[a-c]|[d-h]";
//		String str2 = "a|b|[c-h]";
//		String str = "%|gh";
//		String str2 = "%abc|gh";
//		String str = "(a?|d)+b";
//		String str2 = "(a|&|b)(a|&|d)*b";
//	
//		// variables globales en dos expresiones
//		IdEstado state = new IdEstado();
//		state.nextId();
//		Set<String> simbolosSet = new HashSet<String> ();
//		SortedSet<Character> inis = new TreeSet<Character>();
//		SortedSet<Character> fins = new TreeSet<Character>();
//		// SortedSet<Character> ssRango = new TreeSet<Character>();
//		
//		// parse expresion 1
//		ArrayList<UnionRangos> rangos1 = new ArrayList<UnionRangos>();
//		ParserER parser = new ParserER(new String_ref(str));
//		ExpressionBase er = parser.parse();
//		er.getSimbolosRangos(simbolosSet, rangos1, inis, fins);
//		
//		// parse expresion 2
//		ArrayList<UnionRangos> rangos2 = new ArrayList<UnionRangos>();
//		ParserER parser2 = new ParserER(new String_ref(str2));
//		ExpressionBase er2 = parser2.parse();
//		er2.getSimbolosRangos(simbolosSet, rangos2, inis, fins);
//		
//		// interseccion y y obtener los nuevos simbolos
//		intersecUR(simbolosSet, rangos1, inis, fins);
//		intersecUR(simbolosSet, rangos2, inis, fins);
//		
//		er = er.buildTreeDefinitivo();
//		er2 = er2.buildTreeDefinitivo();
//		System.out.println(er.toString());
//		System.out.println(er2.toString());
//		
//		BerrySethiNode bsn = er.createBerrySethiNode(state);
//		ArrayList<BerrySethiNode> states = new ArrayList<BerrySethiNode>();
//		bsn.buildEstados(states, new HashSet<Integer>());
//		Automata aut = Algoritmos.buildBerrySethiAutomata(states, bsn);
//		aut.show();
//		
//		state = new IdEstado();
//		state.nextId();
//		bsn = er2.createBerrySethiNode(state);
//		states = new ArrayList<BerrySethiNode>();
//		bsn.buildEstados(states, new HashSet<Integer>());
//		Automata aut2 = Algoritmos.buildBerrySethiAutomata(states, bsn);
//		aut2.show();
//		
//		ArrayList<String> simb = new ArrayList<String>();
//		Iterator<String> it = simbolosSet.iterator();
//		while(it.hasNext()) {
//			String c = it.next();
//			simb.add(c);
//		}
//		
//		System.out.println(simb.toString());
//		String resul = Algoritmos.detHopKarp(aut, aut2, state, simb);
//		System.out.println(resul);
	}

	public static void intersecUR(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> inis,
			SortedSet<Character> fins) {

		for (int i = 0; i < array.size(); i++) {
			array.get(i).intersec(set, inis, fins);
		}
	}

}
