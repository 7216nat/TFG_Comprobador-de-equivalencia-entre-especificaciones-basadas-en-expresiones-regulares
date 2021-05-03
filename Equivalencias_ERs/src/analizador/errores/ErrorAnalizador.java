package analizador.errores;

import analizador.lexico.UnidadLexica;
import excepciones.AnalizadorErrorException;
/**
 * Envia excepciones si hay un error lexico o sintactico
 *
 */
public class ErrorAnalizador {
	public void errorLexico(int fila, String lexema) {
	    throw new AnalizadorErrorException("No se ha podido cargar el archivo.\n"
	    		+ "Error léxico al leer el archivo, en fila "+fila+": Carácter inesperado: "+lexema);
	}
	public void errorSintactico(UnidadLexica unidadLexica) {
	     throw new AnalizadorErrorException("No se ha podido cargar el archivo.\n"
	     		+ "Error sintáctico al leer el archivo, en fila "+unidadLexica.fila()+", columna "+unidadLexica.columna()+": Elemento inesperado "+unidadLexica.value);
	}
}
