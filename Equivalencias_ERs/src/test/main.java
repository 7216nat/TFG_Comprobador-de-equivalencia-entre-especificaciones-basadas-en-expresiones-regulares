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
		aut.addEstado("es1");
		aut.addEstado("es2");
		aut.addEstado("es3");
		
		aut.addTransicion("es1", "es2", 'a');
		aut.addTransicion("es2", "es3", 'a');
		aut.addTransicion("es3", "es1", 'b');
		aut.addTransicion("es2", "es3", 'b');
		
		aut.show();
		System.out.println("\n\n");
		aut.eliminarEstado("es2");
		aut.show();
	}

}
