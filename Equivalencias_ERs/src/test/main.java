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

public class main {

	//Test paso de String a expresion regular
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		///*
		IdEstado state = new IdEstado();
		
		//String str = "(cb*c|cb*b)*";
		//String str2 = "(cc)*|(cc)*(cb)(b|c)*";
		String str = "[a-b]";
		String str2 = "[a-c]";
		
		/*Set<String> simblosSet = new HashSet<String> ();
		ArrayList<UnionRangos> array = new ArrayList<UnionRangos>();
		SortedSet<Character> ss = new TreeSet<Character>();
		ParserER parser = new ParserER(new String_ref(str), simblosSet, array, ss);
		ExpressionBase er = parser.parse();
		
		Set<String> simblosSet2 = new HashSet<String> ();
		ArrayList<UnionRangos> array2 = new ArrayList<UnionRangos>();
		ParserER parser2 = new ParserER(new String_ref(str2), simblosSet2, array2, ss);
		ExpressionBase er2 = parser2.parse();
		
		intersecUR(simblosSet, array, ss);
		intersecUR(simblosSet2, array2, ss);
		
		//Automata aut = (Automata)er.ThomsonAFN(state);
		Automata aut = (Automata)er.ThomsonSimplAFN(state);
		System.out.println(er.toString());
		aut.show();
		//Automata aut2 = (Automata)er2.ThomsonAFN(state);
		Automata aut2 = (Automata)er2.ThomsonSimplAFN(state);
		System.out.println(er2.toString());
		aut2.show();
		
		ArrayList<String> simb = new ArrayList<String>();
		simblosSet.addAll(simblosSet2);
		Iterator<String> it = simblosSet.iterator();
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
		Automata at1 = new Automata();
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
		
	}
	
	public static void intersecUR(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> ss){
		
		for (int i = 0; i < array.size(); i++) {
			array.get(i).intersec(set, ss);
		}
	}

}
