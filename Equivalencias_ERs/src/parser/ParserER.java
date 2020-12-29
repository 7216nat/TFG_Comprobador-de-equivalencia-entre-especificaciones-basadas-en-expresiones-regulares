package parser;

import java.util.Set;
import java.util.Stack;

import factories.FactoryER;
import objects.*;

/**
 *  Clase para parsear de string a un árbol de ERs
 */
public class ParserER {

	private String_ref exreg; // string a analizar
	private Stack<ExpressionBase> pila; // pila para controlar parentesis, suma y cierre kleen
	private Set<Character> set;
	private int parentesis; // controlar si los parentesis están bien cerrados

	/**
	 *  constructor
	 * @param exreg
	 * @param set
	 */
	public ParserER(String_ref exreg, Set<Character> set) {
		this.exreg = exreg;
		this.pila = new Stack<ExpressionBase>();
		this.parentesis = 0;
		this.set = set;
	}

	/**
	 *  si quedan más simbolos por procesar
	 * @return
	 */
	private boolean terminado() {
		return exreg.get_len() > 0;
	}

	/**
	 *  el primer simbolo
	 * @return
	 */
	private char prim() {
		if (this.exreg.get_len() > 0)
			return this.exreg.ini();
		else
			return '\0';
	}

	/**
	 *  consume un simbolo y devuelve el consumido
	 * @return
	 */
	private char next() {
		char c = prim();
		this.exreg.sub(1);
		return c;
	}
	
	private void comprobarSintasisError() {
		// error al tener 2 * seguidos o no hay simbolo
		if (prim() == '*' || prim() == '+' || prim() == '?' || pila.empty()) {
			System.out.println("ER inválido");
			System.exit(0);
		}	
	}

	/**
	 * dado un puntero, concatenar todas las instancias de ExpressionBase
	 * posteriores la CONCATENACIÓN está implicta en esta funcion
	 */
	private void concatenarAll(int ptrPila) {
		while (this.pila.size() > ptrPila) {
			ExpressionBase er1 = this.pila.pop();
			ExpressionBase er2 = this.pila.pop();
			ExpressionBase concat = new Concat(er2, er1);
			this.pila.push(concat);
		}
	}

	/**
	 *  Llamada inicial para el parse
	 * @return
	 */
	public ExpressionBase parse() {

		parseDeVerdad(1);

		concatenarAll(1);

		if (this.parentesis != 0) {
			System.out.println("ER inválido");
			System.exit(0);
		}

		return this.pila.pop();
	}

	/**
	 * La idea de union y parentesis es que cada vez que ocurran, al terminar formen
	 * solo una ER
	 */
	private void parseDeVerdad(int ptrPila) {

		int ptrTemp; // puntero a la pila auxiliar
		while (terminado()) {
			if (prim() == '(') { // apertura de parentesis
				next();
				this.parentesis++;

				ptrTemp = pila.size() + 1; // se guarda una copia del puntero de la pila

				parseDeVerdad(pila.size() + 1); // se procesa lo que hay entre parentesis

				concatenarAll(ptrTemp); // concatenar al terminar de procesar entre parentesis
			} else if (prim() == ')') {
				next();
				this.parentesis--;
				return; // return a leer cierre parentesis
			} else if (prim() == '*') { // kleen
				next();

				comprobarSintasisError();
				
				// cierre del kleen a la cima de pila
				ExpressionBase kleen = this.pila.pop();
				kleen = new Kleen(kleen);
				this.pila.push(kleen);
			} else if (prim() == '+') { // cierre positivo
				next();

				comprobarSintasisError();

				// cierre del kleen a la cima de pila
				ExpressionBase cPositivo = this.pila.pop();
				cPositivo = new CierrePositivo(cPositivo);
				this.pila.push(cPositivo);
			} else if (prim() == '?') { // cierre positivo
				next();

				comprobarSintasisError();

				// cierre del kleen a la cima de pila
				ExpressionBase lamb = this.pila.pop();
				lamb = new LambdaExp(lamb);
				this.pila.push(lamb);
			} else if (prim() == '|') { // Union
				next();

				comprobarSintasisError();

				// concatenar todos los simbolos posteriores al puntero
				concatenarAll(ptrPila);

				ptrTemp = pila.size() + 1; // se guarda una copia del puntero de la pila
				parseDeVerdad(pila.size() + 1); // se procesa el segundo operando de la union

				concatenarAll(ptrTemp); // concatenar

				// union las dos ERs primeras de la pila
				ExpressionBase er1 = this.pila.pop();
				ExpressionBase er2 = this.pila.pop();
				ExpressionBase union = new Union(er2, er1);
				this.pila.push(union);
				return; // union es considerado tambien con si fuera entre parentesis
			} else if (prim() == '%') {
				next();
				if (pila.size() == 0 && terminado()) {
					pila.push(new Vacio());
					return;
				}
				System.out.println("error conjunto vacio");
				System.exit(0);
			} else if (prim() == '[') {
				next();
				String str = "";
				while(prim() != ']')
					str = str + ("" + next());
				next();
				ExpressionBase rSimbolo = new RangoSimbolos(str);
				this.pila.push(rSimbolo);
			} else {

				/**
				 * comprobar si es simbolo o lambda No está restringido el vacio()
				 */
				ExpressionBase simbolo = FactoryER.parseER("" + prim());
				if (simbolo == null) {
					System.out.println("Simbolo inválido");
					System.exit(0);
				}
				set.add(prim());
				simbolo.set_sim(""+ next());
				this.pila.push(simbolo);
			}
		}
	}
}
