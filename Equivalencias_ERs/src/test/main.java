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
		System.out.println(!false);
		IdEstado state = new IdEstado();
		
		String str = "(cb*c+cb*b)*";
		String str2 = "(cc)*+(cc)*(cb)(b+c)*";
		
		Set<Character> set = new HashSet<Character> ();
		ParserER parser = new ParserER(new String_ref(str), set);
		ExpressionBase er = parser.parse();
		//Automata aut = (Automata)er.ThomsonAFN(state);
		Automata aut = (Automata)er.ThomsonSimplAFN(state);
		System.out.println(set.toString());
		System.out.println(er.toString());
		aut.show();
		
		Set<Character> set2 = new HashSet<Character> ();
		ParserER parser2 = new ParserER(new String_ref(str2), set2);
		ExpressionBase er2 = parser2.parse();
		//Automata aut2 = (Automata)er2.ThomsonAFN(state);
		Automata aut2 = (Automata)er2.ThomsonSimplAFN(state);
		System.out.println(set2.toString());
		System.out.println(er2.toString());
		aut2.show();
		
		ArrayList<Character> simb = new ArrayList<Character>();
		Iterator<Character> it = set.iterator();
		while(it.hasNext()) {
			char c = (char) it.next();
			simb.add(c);
		}
		
		String resul = Algoritmos.detHopKarp(aut, aut2, state, simb);
		System.out.println(resul);
		//*/
		/*
		String str = "hola+mundo(hola*+m)*(u+ndo)+sad*";
		//str = "((a+b)a)*";
		str = "abb";
		Set<Character> set = new HashSet<Character> ();
		ParserER parser = new ParserER(new String_ref(str), set);
		ExpressionBase er = parser.parse();
		Automata aut = er.ThomsonSimplAFN(new IdEstado());
		System.out.println(set.toString());
		System.out.println(er.toString());
		aut.show();
		*/
		}

}
