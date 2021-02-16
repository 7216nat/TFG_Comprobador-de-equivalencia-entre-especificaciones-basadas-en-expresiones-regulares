package test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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

	//Test paso de String a expresion regular
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		///*
		/*****************************************
		***LOS RANGOS SON DEL TIPO [a-bk-lxksa]*** 
		******************************************/
		
		//String str = "(cb*c|cb*b)*";
		//String str2 = "(cc)*|(cc)*(cb)(b|c)*";
		//String str = "[a-cde-tx]*";
		//String str2 = "[a-bcd-tx]*";
		//String str = "[a-c]|[d-h]";
		//String str2 = "a|b|[c-h]";
		//String str = "%|gh";
		//String str2 = "%abc|gh";
		String str = "(a?|d)+";
		String str2 = "(a|&|d)(a|&|d)*";
	
		// variables globales en dos expresiones
		IdEstado state = new IdEstado();
		Set<String> simbolosSet = new HashSet<String> ();
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
		
		//Automata aut = (Automata)er.ThomsonAFN(state);
		Automata aut = (Automata)er.ThomsonSimplAFN(state);
		System.out.println(er.toString());
		aut.show();
		//Automata aut2 = (Automata)er2.ThomsonAFN(state);
		Automata aut2 = (Automata)er2.ThomsonSimplAFN(state);
		System.out.println(er2.toString());
		aut2.show();
		
		ArrayList<String> simb = new ArrayList<String>();
		Iterator<String> it = simbolosSet.iterator();
		while(it.hasNext()) {
			String c = it.next();
			simb.add(c);
		}
		
		System.out.println(simb.toString());
		String resul = Algoritmos.detHopKarp(aut, aut2, state, simb);
		System.out.println(resul);
		//*/
		
		/*
		String str = "hola+mundo(hola*+m)*(u+ndo)+sad*";
		//str = "((a+b)a)*";
		str = "ab?b+[a-dxgc-h][c-lk-m][l-xks]";
		Set<String> set = new HashSet<String> ();
		ArrayList<UnionRangos> array = new ArrayList<UnionRangos>();
		SortedSet<Character> ss = new TreeSet<Character>();
		ParserER parser = new ParserER(new String_ref(str), set, array, ss);
		ExpressionBase er = parser.parse();
		
		intersecUR(set, array, ss);
		
		Automata aut = er.ThomsonSimplAFN(new IdEstado());
		System.out.println(set.toString());
		System.out.println(er.toString());
		aut.show();
		*/
		
		/*
		RangoCharacter e1 = new RangoCharacter('a', 'h'), e2 = new RangoCharacter('a', 'h');
		System.out.println(e1.equals(e2));
		*/
		
		
		//Comprobar lambda-cierre:
		/*Automata at1 = new Automata();
		at1.addEstado(new Estado(state.nextId(), true, false));//0
		at1.addEstado(new Estado(state.nextId(), false, true));//1
		at1.addEstado(new Estado(state.nextId(), false, false));//2
		at1.addEstado(new Estado(state.nextId(), false, false));//3
		at1.addEstado(new Estado(state.nextId(), false, false));//4
		at1.addEstado(new Estado(state.nextId(), false, false));//5
		at1.addEstado(new Estado(state.nextId(), false, true));//6
		at1.addEstado(new Estado(state.nextId(), false, false));//7
		at1.addEstado(new Estado(state.nextId(), false, false));//8
		at1.addEstado(new Estado(state.nextId(), false, false));//9
		
		at1.addTransicion(0, 1, "&");
		at1.addTransicion(1, 2, "a");
		at1.addTransicion(2, 4, "&");
		at1.addTransicion(4, 9, "a");
		at1.addTransicion(2, 3, "b");
		at1.addTransicion(3, 5, "a");
		at1.addTransicion(5, 6, "&");
		at1.addTransicion(5, 7, "&");
		at1.addTransicion(5, 8, "&");
		
		at1.show();
		
		at1.lambdaCierreCompleto();
		System.out.println("\n\n");
		at1.show();
	*/	
	}
	
	public static void intersecUR(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> inis, SortedSet<Character> fins){

		for (int i = 0; i < array.size(); i++) {
			array.get(i).intersec(set, inis, fins);
		}
	}

}
