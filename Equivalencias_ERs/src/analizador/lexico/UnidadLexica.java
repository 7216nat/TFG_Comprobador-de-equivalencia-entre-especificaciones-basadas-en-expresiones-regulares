package analizador.lexico;

import java_cup.runtime.Symbol;

public class UnidadLexica extends Symbol{
	private int fila;
	private int col;
	   public UnidadLexica(int fila, int col, int clase, String lexema) {
	     super(clase,lexema);
		 this.fila = fila;
		 this.col = col;
		 value = new StringLocalizado(lexema,fila,col);
	   }
	
	   
	public StringLocalizado lexema() {return (StringLocalizado)value;}
	public int fila() {return fila;}
	public int columna() {return col;}
}
