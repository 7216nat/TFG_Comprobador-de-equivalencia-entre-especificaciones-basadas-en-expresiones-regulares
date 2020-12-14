package test;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import automata.Automata;
import automata.IdEstado;
import objects.*;
import parser.ParserER;

public class main {

	//Test paso de String a expresion regular
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "hola+mundo(hola*+m)*(u+ndo)+sad*";
		str = "(a+b)*a*";
		//str = "abb";
		Set<Character> set = new HashSet<Character> ();
		ParserER parser = new ParserER(new String_ref(str), set);
		ExpressionBase er = parser.parse();
		Automata aut = er.ThomsonSimplAFN(new IdEstado());
		System.out.println(set.toString());
		System.out.println(er.toString());
		aut.show();
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
