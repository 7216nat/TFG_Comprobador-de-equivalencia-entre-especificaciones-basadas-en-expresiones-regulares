package control;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import algoritmo.Algoritmos;
import automata.Automata;
import automata.IdEstado;
import objects.BerrySethiNode;
import objects.ExpressionBase;
import objects.Union;
import objects.UnionRangos;
import parser.ParserER;
import parser.String_ref;

public class Controller {
	
	private static String _defaultAlgoritmo = "thomson";
	
	private AlgoritmoExec _algoritmo = null;
	private ModeExecution _mode = null;
	
	private ExpressionBase _e1 = null;
	private ExpressionBase _e2 = null;
	
	private IdEstado _state = null;
	private ArrayList<String> _simList = null;
	
	public Controller() {	
		setAlgoritmo(_defaultAlgoritmo);
	}
	
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
	
	private void parser(ArrayList<String> listReg1, ArrayList<String> listReg2) {
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
	
	private void thomsonExec() {
		String resul = Algoritmos.detHopKarp(_e1.ThomsonSimplAFN(_state), _e2.ThomsonSimplAFN(_state), _state, _simList);
		System.out.println(resul);
	}
	
	private void seguidoresExec() {
		System.out.println("PRUEBA SEGUIDORES");
		String resul = Algoritmos.detHopKarpSinLambda(_e1.ThomsonSimplAFN(_state), _e2.ThomsonSimplAFN(_state), _state, _simList);
		System.out.println(resul);
	}
	
	private void derivadaExec() {
		System.out.println("PRUEBAS DERIVADAS");
		String resul = Algoritmos.equivalenciaDer(_e1, _e2, _state, _simList);
		System.out.println(resul);
	}
	
	private void derivadasParExec() {
		System.out.println("PRUEBAS DERIVADAS PARCIALES");
		String resul = Algoritmos.equivalenciaDerPar(_e1, _e2, _state, _simList);
		System.out.println(resul);
	}
	
	private void berrySethiExec() {
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
		
		String resul = Algoritmos.detHopKarp(a1, a2, _state, _simList);
		System.out.println(resul);
	}
	
	private void intersecUR(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> inis,
			SortedSet<Character> fins) {

		for (int i = 0; i < array.size(); i++) {
			array.get(i).intersec(set, inis, fins);
		}
	}
	
	/*
	 *  
	 **/
	public void setERs(ArrayList<String> listER1, ArrayList<String> listER2) {
		parser(listER1, listER2);
	}
	
	public void setAlgoritmo(String algoritmoUsuario) {
		String algoritmo = algoritmoUsuario == null ? _defaultAlgoritmo: algoritmoUsuario;
		for (AlgoritmoExec ae : AlgoritmoExec.values()) {
			if (algoritmo.equalsIgnoreCase(ae.getAlgoritmo())) {
				_algoritmo = ae;
				break;
			}
		}
	}
	
	public void run() {
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

}
