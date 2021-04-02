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
import algoritmo.Equivalencia;
import automata.Automata;
import automata.IdEstado;
import objects.BerrySethiNode;
import objects.ExpressionBase;
import objects.RangoCharacter;
import objects.Union;
import objects.UnionRangos;
import parser.ParserER;
import parser.String_ref;

public class Controller {
	
	private static String _defaultAlgoritmo = "thompson";
	private static String _defaultMode = "todos";
	
	private AlgoritmoExec _algoritmo = null;
	private ModeExecution _mode = null;
	
	private ArrayList<ExpressionBase> _elist1 = null;
	private ArrayList<ExpressionBase> _elist2 = null;
	
	private ExpressionBase _e1 = null;
	private ExpressionBase _e2 = null;
	
	private IdEstado _state = null;
	private ArrayList<String> _simList = null;
	
	public Controller() {
		setAlgoritmo(_defaultAlgoritmo);
		setMode(_defaultMode);
	}

	
	private enum AlgoritmoExec {
		THOMSON("thompson", "Algoritmo Thomson"), SEGUIDOR("seguidores", "Algoritmo seguidores"),
		BERRYSETHI("berry-sethi", "Algoritmo Berry-Sethi"), DERIVADAS("derivadas", "Algoritmo derivadas"),
		DERIVADASPARCIALES("derivadas parciales", "Algritmo derivadas parciales");

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
		TODOS("todos", "Allall Mode"), UNOAUNO("uno a uno", "UnoAUno Mode"),
		SELECTED("seleccionados", "Selected Mode");

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
		_elist1 = new ArrayList<>();
		_elist2 = new ArrayList<>();
		Set<String> simbolosSet = new HashSet<>();
		SortedSet<Character> inis = new TreeSet<>();
		SortedSet<Character> fins = new TreeSet<>();
		ArrayList<UnionRangos> rangos = new ArrayList<>();
		ExpressionBase er;
		// parse lista de expresiones 1
		for (String str: listReg1) {
			ParserER parser = new ParserER(new String_ref(str));
			er = parser.parse();
			er.getSimbolosRangos(simbolosSet, rangos, inis, fins);
			_elist1.add(er);
		}

		// parse lista de expresiones 2
		for (String str: listReg2) {
			ParserER parser = new ParserER(new String_ref(str));
			er = parser.parse();
			er.getSimbolosRangos(simbolosSet, rangos, inis, fins);
			_elist2.add(er);
		}

		// interseccion y y obtener los nuevos simbolos
		intersecUR(simbolosSet, rangos, inis, fins);

		
		_simList = new ArrayList<>();
		Iterator<String> it = simbolosSet.iterator();
		while (it.hasNext()) {
			String c = it.next();
			_simList.add(c);
		}
	}
	
	private ExpressionBase unionListaERs(ArrayList<ExpressionBase> elist) {
		Iterator<ExpressionBase> it = elist.iterator();
		ExpressionBase er = it.next();
		while (it.hasNext()) {
			er = new Union(er, it.next());
		}
		er = er.buildTreeDefinitivo();
		return er;
	}
	private Equivalencia thomsonExec() {
		return Algoritmos.detHopKarp(_e1.ThomsonSimplAFN(_state), _e2.ThomsonSimplAFN(_state), _state, _simList);
//		System.out.println(resul);
	}
	
	private Equivalencia seguidoresExec() {
//		System.out.println("PRUEBA SEGUIDORES");
		return Algoritmos.detHopKarpSinLambda(_e1.ThomsonSimplAFN(_state), _e2.ThomsonSimplAFN(_state), _state, _simList);
//		System.out.println(resul);
	}
	
	private Equivalencia derivadaExec() {
//		System.out.println("PRUEBAS DERIVADAS");
		return  Algoritmos.equivalenciaDer(_e1, _e2, _state, _simList);
//		System.out.println(resul);
	}
	
	private Equivalencia derivadasParExec() {
//		System.out.println("PRUEBAS DERIVADAS PARCIALES");
		return Algoritmos.equivalenciaDerPar(_e1, _e2, _state, _simList);
//		System.out.println(resul);
	}
	
	private Equivalencia berrySethiExec() {
//		System.out.println("PRUEBAS BERRY-SETHI");
		
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
		
		return Algoritmos.detHopKarp(a1, a2, _state, _simList);
		
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
	
	public void setMode(String m) {
		String modo = m == null ? _defaultMode: m;
		for (ModeExecution me : ModeExecution.values()) {
			if (modo.equalsIgnoreCase(me.getMode())) {
				_mode = me;
				break;
			}
		}
	}
	
	private Equivalencia compare() {
		switch(_algoritmo) {
		case THOMSON:
			return thomsonExec();
		case SEGUIDOR:
			return seguidoresExec();
		case DERIVADAS:
			return derivadaExec();
		case DERIVADASPARCIALES:
			return derivadasParExec();
		case BERRYSETHI:
			return berrySethiExec();
		default:
			return thomsonExec();
		}
	}
	
	private String emparejar() {
		String ret = "";
		Iterator<ExpressionBase> it1 = _elist1.iterator();
		while (it1.hasNext()) {
			_e1 = it1.next();
			Iterator<ExpressionBase> it2 = _elist2.iterator();
			while (it2.hasNext()) {
				_e2 = it2.next(); 
				if (compare().isEq()) {
					ret += _e1.toString() + " equivalente a " + _e2.toString() + "\n"; 
					it2.remove();
					it1.remove();
					break;
				}
			}
		}
		
		String unpair1 = "\nSin emparejar de la lista 1:\n";
		String unpair2 = "\nSin emparejar de la lista 2:\n"; 
		for (ExpressionBase er: _elist1) unpair1 += er.toString() + "\n";
		for (ExpressionBase er: _elist2) unpair2 += er.toString() + "\n";
		if (!_elist1.isEmpty()) ret += unpair1;
		if (!_elist2.isEmpty()) ret += unpair2;
		return ret;
	}
	public String run() {
		switch(_mode) {
		case TODOS:
		case SELECTED:
			_e1 = unionListaERs(_elist1);
			_e2 = unionListaERs(_elist2);
			return compare().getMsg();
		case UNOAUNO:
			return emparejar();
		default:
			break;
		}
		return "Something went wrong";
	}
	
	public String compEquiv(ArrayList<String> s1, ArrayList<String> s2,
			String algor, String mode) {
		setAlgoritmo(algor);
		setMode(mode);
		setERs(s1, s2);
		return run();
	}

}
