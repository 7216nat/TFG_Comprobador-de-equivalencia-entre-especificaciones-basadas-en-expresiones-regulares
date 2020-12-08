package test;
import automata.Automata;
import objects.*;
import parser.ParserER;

public class main {

	//Test paso de String a expresion regular
/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "hola+mundo(hola*+m)*(u+ndo)+sad*";
		ParserER parser = new ParserER(new String_ref(str));
		ExpressionBase er = parser.parse();
		System.out.println(er.toString());
	}
	*/
	
	//Test creación automatas
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Automata aut = new Automata();
		aut.addEstado();
		aut.addEstado();
		aut.addEstado();
		
		aut.addTransicion(1, 2, 'a');
		aut.addTransicion(2, 3, 'a');
		aut.addTransicion(3, 1, 'b');
		aut.addTransicion(2, 3, 'b');
		
		aut.show();
		System.out.println("\n\n");
		aut.eliminarEstado(2);
		aut.show();
	}

}
