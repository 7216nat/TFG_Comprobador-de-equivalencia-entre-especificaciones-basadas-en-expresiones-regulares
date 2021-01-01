package test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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
		///*
		IdEstado state = new IdEstado();
		
		String str = "(cb*c|cb*b)*";
		String str2 = "(cc)*|(cc)*(cb)(b|c)*";
		str = "c[a-l]";
		str2 = "a[b-c]";
		
		Set<Character> set = new HashSet<Character> ();
		ArrayList<UnionRangos> array = new ArrayList<UnionRangos>();
		ParserER parser = new ParserER(new String_ref(str), set, array);
		ExpressionBase er = parser.parse();
		
		intersecUR(array);
		
		//Automata aut = (Automata)er.ThomsonAFN(state);
		Automata aut = (Automata)er.ThomsonSimplAFN(state);
		System.out.println(set.toString());
		System.out.println(er.toString());
		aut.show();
		
		Set<Character> set2 = new HashSet<Character> ();
		ArrayList<UnionRangos> array2 = new ArrayList<UnionRangos>();
		ParserER parser2 = new ParserER(new String_ref(str2), set2, array2);
		ExpressionBase er2 = parser2.parse();
		intersecUR(array2);
		//Automata aut2 = (Automata)er2.ThomsonAFN(state);
		Automata aut2 = (Automata)er2.ThomsonSimplAFN(state);
		System.out.println(set2.toString());
		System.out.println(er2.toString());
		aut2.show();
		
		ArrayList<String> simb = new ArrayList<String>();
		Iterator<Character> it = set.iterator();
		while(it.hasNext()) {
			String c = "" + (char) it.next();
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
		Set<Character> set = new HashSet<Character> ();
		ArrayList<UnionRangos> array = new ArrayList<UnionRangos>();
		ParserER parser = new ParserER(new String_ref(str), set, array);
		ExpressionBase er = parser.parse();
		
		if (array.size() ==  1) {
			array.get(0).unirRangos();
		}
		else if (array.size() > 1) {
			UnionRangos tmp1, tmp2;
			for (int i = 0; i < array.size(); i++) {
				tmp1 = array.get(i);
				for (int j = i+1; j < array.size(); j++) {
					tmp2 = array.get(j);
					tmp1.intersec(tmp2);
					tmp2.intersec(tmp1);
				}
				tmp1.unirRangos();
			}
		}
		
		Automata aut = er.ThomsonSimplAFN(new IdEstado());
		System.out.println(set.toString());
		System.out.println(er.toString());
		aut.show();
		*/
	}
	
	public static void intersecUR(ArrayList<UnionRangos> array){
		if (array.size() ==  1) {
			array.get(0).unirRangos();
		}
		else if (array.size() > 1) {
			UnionRangos tmp1, tmp2;
			for (int i = 0; i < array.size(); i++) {
				tmp1 = array.get(i);
				for (int j = i+1; j < array.size(); j++) {
					tmp2 = array.get(j);
					tmp1.intersec(tmp2);
					tmp2.intersec(tmp1);
				}
				tmp1.unirRangos();
			}
		}
	}

}
