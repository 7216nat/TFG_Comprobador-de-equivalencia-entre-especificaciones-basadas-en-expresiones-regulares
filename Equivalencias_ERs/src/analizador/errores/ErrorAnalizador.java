package analizador.errores;

import analizador.lexico.UnidadLexica;
import excepciones.AnalizadorErrorException;
/**
 * Envia excepciones si hay un error lexico o sintactico
 *
 */
public class ErrorAnalizador {
	
	/**
	 * Exception no controlada de error lexico
	 * @param fila: fila
	 * @param lexema: error lexema
	 */
	public void errorLexico(int fila, int columna,String lexema) {
	    throw new AnalizadorErrorException("No se ha podido cargar el archivo.\n"
	    		+ "Error léxico al leer el archivo, en fila "+fila+", columna "+columna+": Carácter inesperado: "+lexema);
	}
	/**
	 * Exception no controlada de error sintactico
	 * @param unidadLexica: instancia de unidad lexica
	 */
	public void errorSintactico(UnidadLexica unidadLexica) {
	     throw new AnalizadorErrorException("No se ha podido cargar el archivo.\n"
	     		+ "Error sintáctico al leer el archivo, en fila "+unidadLexica.fila()+", columna "+unidadLexica.columna()+": Elemento inesperado "+unidadLexica.value);
	}
}
