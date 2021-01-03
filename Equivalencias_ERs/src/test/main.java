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
		/*
		IdEstado state = new IdEstado();
		
		String str = "(cb*c|cb*b)*";
		String str2 = "(cc)*|(cc)*(cb)(b|c)*";
		
		Set<String> simblosSet = new HashSet<String> ();
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
		Iterator<String> it = simblosSet.iterator();
		while(it.hasNext()) {
			String c = it.next();
			simb.add(c);
		}
		
		System.out.println(simb.toString());
		String resul = Algoritmos.detHopKarp(aut, aut2, state, simb);
		System.out.println(resul);
		*/
		
		
		///*
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
		//*/
		
		/*
		RangoCharacter e1 = new RangoCharacter('a', 'h'), e2 = new RangoCharacter('a', 'h');
		System.out.println(e1.equals(e2));
		*/
	}
	
	public static void intersecUR(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> ss){
		
		for (int i = 0; i < array.size(); i++) {
			array.get(i).intersec(set, ss);
		}
	}

}
