package analizador.errores;

import analizador.lexico.UnidadLexica;

public class ErrorAnalizador {
	public void errorLexico(int fila, String lexema) {//throws AnalizadorErrorException{
	     //throw new AnalizadorErrorException("ERROR LEXICO fila "+fila+": Caracter inexperado: "+lexema);
		System.out.println("ERROR LEXICO fila "+fila+": Caracter inexperado: "+lexema);
	}
	public void errorSintactico(UnidadLexica unidadLexica) {//throws AnalizadorErrorException{
	     //throw new AnalizadorErrorException("ERROR SINTACTICO fila "+unidadLexica.fila()+", columna "+unidadLexica.columna()+" : Elemento inexperado "+unidadLexica.value);
	     System.out.println("ERROR SINTACTICO fila "+unidadLexica.fila()+", columna "+unidadLexica.columna()+" : Elemento inexperado "+unidadLexica.value);
	}
}
