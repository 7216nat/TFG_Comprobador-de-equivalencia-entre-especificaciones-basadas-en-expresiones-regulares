package test;

import java.util.ArrayList;
import objects.*;
import control.Controller;;

public class Main {

	// Test paso de String a expresion regular
	public static void main(String[] args) {
		
		// Estos son equivalentes
		ArrayList<String> strList = new ArrayList<>();
		ArrayList<String> strList2 = new ArrayList<>();
//		strList.add("a|(b|c)");
//		strList2.add("(a|b)|c");
//		strList.add("a|b");
//		strList2.add("b|a");		
//		strList.add("a|%");
//		strList2.add("%|a");
//		strList.add("a|a");
//		strList2.add("a");
//		strList.add("a(bc)");
//		strList2.add("(ab)c");
//		strList.add("&a");
//		strList2.add("a");
//		strList.add("&a");
//		strList2.add("a&");
//		strList.add("a(b|c)");
//		strList2.add("ab|ac");
//		strList.add("(a|b)c");
//		strList2.add("ac|bc");
//		strList.add("%");
//		strList2.add("%a");
//		strList.add("a%");
//		strList2.add("%a");
//		strList.add("&|aa*");
//		strList2.add("a*");
//		strList.add("&|a*a");
//		strList2.add("a*");
//		strList.add("(ab)*a");
//		strList2.add("a(ba)*");
//		strList.add("(a*b)*a*");
//		strList2.add("(a|b)*");
//		strList.add("a*(ba*)*");
//		strList2.add("(a|b)*");
//		strList.add("(&|a)*");
//		strList2.add("a*");
//		strList.add("aa*");
//		strList2.add("a*a");
//		strList.add("%*");
//		strList2.add("&");
//		strList.add("(a*)*");
//		strList2.add("a*");
//		strList.add("&*");
//		strList2.add("&");
//		strList.add("(a*b*)*");
//		strList2.add("(a|b)*");
//		strList.add("(ab|a)*a");
//		strList2.add("a(ba|a)*");
//		strList.add("b*");
//		strList2.add("b*|&");

		// Ejercicios FLI
//		strList.add("(0|1)*");
//		strList2.add("0*|1*");
//		strList.add("0(120)*12");
//		strList2.add("01(201)*2");
//		strList.add("(0*1*)*");
//		strList2.add("(0*1)*");
//		strList.add("(01|0)*0");
//		strList2.add("0(10|0)*");
//		strList.add("(a|b)*");
//		strList2.add("a*(ba*)*");
//		strList.add("b*a*|a*b*");
//		strList2.add("a*|b*");
//		strList.add("(cb*c|cb*b)*");
//		strList2.add("(cc)*|(cc)*(cb)(b|c)*");
//		strList.add("[a-cde-tx]*");
//		strList2.add("[a-bcd-tx]*");
		strList.add("[a-c]|[d-h]");
		strList2.add("a|b|[c-h]");
		strList.add("%|gh");
		strList2.add("%abc|gh");

		// recibe como parametros o entradas dos listas de ER y el algritmo a aplicarles+
		// todos ellos se pasan como String 
		Controller ctl = new Controller();
		ctl.setERs(strList, strList2);
		ctl.setAlgoritmo("berrysethi");
		ctl.run();

	}
	
}
