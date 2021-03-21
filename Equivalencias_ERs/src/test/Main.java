package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Deque;
import java.util.LinkedList;

import algoritmo.Algoritmos;
import automata.Automata;
import automata.IdEstado;
import objects.*;
import parser.ParserER;
import parser.String_ref;

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
		parser(strList, strList2);
		
		String algoritmoUsuario = null;
		String algoritmo = algoritmoUsuario == null ? _defaultAlgoritmo: algoritmoUsuario;
		for (AlgoritmoExec ae : AlgoritmoExec.values()) {
			if (algoritmo.equalsIgnoreCase(ae.getAlgoritmo())) {
				_algoritmo = ae;
				break;
			}
		}
		
		switch(_algoritmo) {
		case THOMSON:
			thomsonExec();
			break;
		case SEGUIDOR:
			seguidoresExec();
			break;
		case DERIVADAS:
			derivadaExec();
			break;
		case DERIVADASPARCIALES:
			derivadasParExec();
			break;
		case BERRYSETHI:
			berrySethiExec();
			break;
		default:
			thomsonExec();
			break;
		}
		

	}
	
	private static String _defaultAlgoritmo = "thomson";
	
	private static AlgoritmoExec _algoritmo = null;
	private static ModeExecution _mode = null;
	
	private static ExpressionBase _e1 = null;
	private static ExpressionBase _e2 = null;
	
	private static IdEstado _state = null;
	private static ArrayList<String> _simList = null;
	
	private enum AlgoritmoExec {
		THOMSON("thomson", "Algoritmo Thomson"), SEGUIDOR("seguidor", "Algoritmo seguidores"),
		BERRYSETHI("berrysethi", "Algoritmo Berry-Sethi"), DERIVADAS("derivadas", "Algoritmo derivadas"),
		DERIVADASPARCIALES("derivadaspar", "Algritmo derivadas parciales");

		private String runAlgoritmo;
		private String descMode;

		private AlgoritmoExec(String algoritmo, String algoritmoDesc) {
			runAlgoritmo = algoritmo;
			descMode = algoritmoDesc;
		}

		private String getAlgoritmo() {
			return runAlgoritmo;
		}

		private String getAlgoritmoDesc() {
			return descMode;
		}
	}
	
	private enum ModeExecution {
		ALLALL("allall", "Allall Mode"), PARCIAL("parcial", "Parcial Mode"),
		SELECTED("selected", "Selected Mode");

		private String runMode;
		private String descMode;

		private ModeExecution(String mode, String modeDesc) {
			runMode = mode;
			descMode = modeDesc;
		}

		private String getMode() {
			return runMode;
		}

		private String getModeDesc() {
			return descMode;
		}
	}
	
	private static void parser(ArrayList<String> listReg1, ArrayList<String> listReg2) {
		// variables globales en dos expresiones
		_state = new IdEstado();
		Set<String> simbolosSet = new HashSet<>();
		SortedSet<Character> inis = new TreeSet<>();
		SortedSet<Character> fins = new TreeSet<>();
		ArrayList<UnionRangos> rangos = new ArrayList<>();
		ExpressionBase er;
		Deque<ExpressionBase> ers;
		// parse expresion 1
		ExpressionBase er1;
		ers = new LinkedList<>();
		for (String str: listReg1) {
			ParserER parser = new ParserER(new String_ref(str));
			er = parser.parse();
			er.getSimbolosRangos(simbolosSet, rangos, inis, fins);
			ers.add(er);
		}
		er1 = ers.pop();
		while(!ers.isEmpty()) {
			er1 = new Union(ers.pop(), er1);
		}

		// parse expresion 2
		ExpressionBase er2;
		ers = new LinkedList<>();
		for (String str: listReg2) {
			ParserER parser = new ParserER(new String_ref(str));
			er = parser.parse();
			er.getSimbolosRangos(simbolosSet, rangos, inis, fins);
			ers.add(er);
		}
		er2 = ers.pop();
		while(!ers.isEmpty()) {
			er2 = new Union(ers.pop(), er2);
		}
		// interseccion y y obtener los nuevos simbolos
		intersecUR(simbolosSet, rangos, inis, fins);

		// quitar unionRango del arbol
		_e1 = er1.buildTreeDefinitivo();
		_e2 = er2.buildTreeDefinitivo();
		
		_simList = new ArrayList<>();
		Iterator<String> it = simbolosSet.iterator();
		while (it.hasNext()) {
			String c = it.next();
			_simList.add(c);
		}
	}
	
	private static void thomsonExec() {
		String resul = Algoritmos.detHopKarp(_e1.ThomsonSimplAFN(_state), _e2.ThomsonSimplAFN(_state), _state, _simList);
		System.out.println(resul);
	}
	
	private static void seguidoresExec() {
		System.out.println("PRUEBA SEGUIDORES");
		String resul = Algoritmos.detHopKarpSinLambda(_e1.ThomsonSimplAFN(_state), _e2.ThomsonSimplAFN(_state), _state, _simList);
		System.out.println(resul);
	}
	
	private static void derivadaExec() {
		System.out.println("PRUEBAS DERIVADAS");
		String resul = Algoritmos.equivalenciaDer(_e1, _e2, _state, _simList);
		System.out.println(resul);
	}
	
	private static void derivadasParExec() {
		System.out.println("PRUEBAS DERIVADAS PARCIALES");
		String resul = Algoritmos.equivalenciaDerPar(_e1, _e2, _state, _simList);
		System.out.println(resul);
	}
	
	private static void berrySethiExec() {
		System.out.println("PRUEBAS BERRY-SETHI");
		
		_state = new IdEstado();
		ArrayList<BerrySethiNode> states = new ArrayList<>();
		BerrySethiNode bsn;
		
		bsn = _e1.createBerrySethiNode(_state);
		bsn.buildEstados(states, new HashSet<>());
		Automata a1 = Algoritmos.buildBerrySethiAutomata(states, bsn);
		
		_state = new IdEstado();
		bsn = _e2.createBerrySethiNode(_state);
		states = new ArrayList<>();
		bsn.buildEstados(states, new HashSet<>());
		Automata a2 = Algoritmos.buildBerrySethiAutomata(states, bsn);
		// aut4.show();

		// System.out.println(simb.toString());
		String resul = Algoritmos.detHopKarp(a1, a2, _state, _simList);
		System.out.println(resul);
	}
	
	private static void intersecUR(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> inis,
			SortedSet<Character> fins) {

		for (int i = 0; i < array.size(); i++) {
			array.get(i).intersec(set, inis, fins);
		}
	}
	

}
