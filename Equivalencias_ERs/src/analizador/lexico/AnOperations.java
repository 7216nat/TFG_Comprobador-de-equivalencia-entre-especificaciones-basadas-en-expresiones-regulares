package analizador.lexico;

import analizador.sintactico.ClaseLexica;

public class AnOperations {
	  private AnalizadorLexico alex;
	  public AnOperations(AnalizadorLexico alex) {
	   this.alex = alex;   
	  }
	public UnidadLexica unidadCorAp() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.CORAP, "[");
	}
	public UnidadLexica unidadCorCie() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.CORCIE, "]");
	}
	public UnidadLexica unidadEof() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.EOF, "EOF");
	}
	public UnidadLexica unidadPAP() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.PAP, "(");
	}
	public UnidadLexica unidadPCie() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.PCIE, ")");
	}
	public UnidadLexica unidadKleen() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.ASTERISCO, "*");
	}
	public UnidadLexica unidadKleenPos() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.MAS, "+");
	}
	public UnidadLexica unidadPosibilidad() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.INTERR, "?");
	}
	public UnidadLexica unidadUnion() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.ALTER, "|");
	}
	public UnidadLexica unidadVar() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.VAR, alex.lexema().substring(1, alex.lexema().length()-1));
	}
	public UnidadLexica unidadLambda() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.LAMBDA, "&");
	}
	public UnidadLexica unidadVacio() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.VACIO, "%");
	}
	public UnidadLexica unidadSimEscape() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.ESCAPESIM, alex.lexema().substring(alex.lexema().length()-1));
	}
	public UnidadLexica unidadAux() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.AUX, "aux" );
	}
	public UnidadLexica unidadDef() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.DEF, "def" );
	}
	public UnidadLexica unidadIgual() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.IGUAL, "=" );
	}
	public UnidadLexica unidadGuion() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.GUION, "-" );
	}
	public UnidadLexica unidadComa() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.COMA, "," );
	}
	public UnidadLexica unidadSimbolo() {
		return new UnidadLexica(alex.fila(), alex.columna(),ClaseLexica.SIM, alex.lexema());
	}
}
