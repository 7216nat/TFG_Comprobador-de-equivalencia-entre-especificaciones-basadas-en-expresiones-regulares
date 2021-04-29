package analizador.test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import analizador.lexico.AnalizadorLexico;
import analizador.lexico.UnidadLexica;
import analizador.sintactico.AnalizadorSintactico;
import analizador.sintactico.ClaseLexica;

public class Main {
	public static void main(String[] args) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream("input.txt"));
		AnalizadorLexico alex = new AnalizadorLexico(input);
			 
//			 Comentar el siguiente bloque para ver el resultado de tokenizar
//		UnidadLexica unidad;
//		do {
//			unidad = alex.next_token();
//			System.out.println(unidad.clase() + " " + unidad.lexema());
//		}while (unidad.clase() != ClaseLexica.EOF);
			 
			 
		try{
			 AnalizadorSintactico asint = new AnalizadorSintactico(alex);
			 asint.parse();
			 System.out.println("OK");
			 
		}catch(Exception e) {
			 e.getMessage();
		}
			 
		}
}
