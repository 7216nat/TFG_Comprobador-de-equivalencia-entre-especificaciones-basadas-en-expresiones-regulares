package control;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import algoritmo.Algoritmos;
import algoritmo.Equivalencia;
import automata.IdEstado;
import objects.ExpressionBase;
import objects.Union;
import objects.UnionRangos;

public class Controller {
	
	private static String _defaultAlgoritmo = "thompson";
	private static String _defaultMode = "todos";
	
	private AlgoritmoExec _algoritmo = null;
	private ModeExecution _mode = null;
	
	private List<ExpressionBase> _elist1 = null;
	private List<ExpressionBase> _elist2 = null;
	
	private ExpressionBase _e1 = null;
	private ExpressionBase _e2 = null;
	
	private IdEstado _state = null;
	private List<String> _simList = null;
	
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
	
	private void procesar(List<ExpressionBase> listER1, List<ExpressionBase> listER2) {
		// variables globales en dos expresiones
		_elist1 = listER1;
		_elist2 = listER2;
		Set<String> simbolosSet = new HashSet<>();
		Set<Character> inis = new TreeSet<>();
		Set<Character> fins = new TreeSet<>();
		List<UnionRangos> rangos = new ArrayList<>();
		
		// parse lista de expresiones 1
		for (ExpressionBase er: listER1) {
			er.getSimbolosRangos(simbolosSet, rangos, inis, fins);
		}

		// parse lista de expresiones 2
		for (ExpressionBase er: listER2) {
			er.getSimbolosRangos(simbolosSet, rangos, inis, fins);
		}

		// interseccion y y obtener los nuevos simbolos
		intersecUR(simbolosSet, rangos, inis, fins);
				
		// conseguir todos los simbolos incluidos los rangos
		_simList = new ArrayList<>();
		Iterator<String> it = simbolosSet.iterator();
		while (it.hasNext()) {
			String c = it.next();
			_simList.add(c);
		}
		
		if (!rangos.isEmpty()) {
			// deshacer los Unionrangos
			for(int i = 0; i < _elist1.size(); i++) {
				_elist1.set(i, _elist1.get(i).buildTreeDefinitivo());
			}
			for(int i = 0; i < _elist2.size(); i++) {
				_elist2.set(i, _elist2.get(i).buildTreeDefinitivo());
			}
		}
	}
	
	/**
	 * OR de los elementos de la lista 
	 * @param elist: lista a OR
	 * @return Expresion resultante
	 */
	private ExpressionBase unionListaERs(List<ExpressionBase> elist) {
		Iterator<ExpressionBase> it = elist.iterator();
		ExpressionBase er = it.next();
		while (it.hasNext()) {
			er = new Union(er, it.next());
		}
		return er;
	}
	
	/**
	 * Ejecucion thomson
	 * @return mensaje de equivalencia
	 */
	private Equivalencia thomsonExec() {
		return Algoritmos.detHopKarp(_e1.ThomsonAFN(_state), _e2.ThomsonAFN(_state), _state, _simList, false);
	}
	
	/**
	 * Ejecucion seguidores
	 * @return mensaje de equivalencia
	 */
	private Equivalencia seguidoresExec() {
		return Algoritmos.detHopKarp(_e1.ThomsonSimplAFN(_state), _e2.ThomsonSimplAFN(_state), _state, _simList, true);
	}
	
	/**
	 * Ejecucion derivada
	 * @return mensaje de equivalencia
	 */
	private Equivalencia derivadaExec() {
		return  Algoritmos.equivalenciaDer(_e1, _e2, _state, _simList);
	}
	
	/**
	 * Ejecucion derivada parcial
	 * @return mensaje de equivalencia
	 */
	private Equivalencia derivadasParExec() {
		return Algoritmos.equivalenciaDerPar(_e1, _e2, _state, _simList);
	}
	
	/**
	 * Ejecucion berry-sethi
	 * @return mensaje de equivalencia
	 */
	private Equivalencia berrySethiExec() {
		return Algoritmos.equivalenciaBerrySethi(_e1, _e2, _state, _simList);
	}
	
	/**
	 * interseccionar las UnionRangos
	 * @param set: lista simbolos
	 * @param array: lista de UnionRangos
	 * @param inis: lista de iniciales de los elementos de <array>
	 * @param fins: lista de finales de los elementos de <array>
	 */
	private void intersecUR(Set<String> set, List<UnionRangos> array, Set<Character> inis, Set<Character> fins) {

		for (int i = 0; i < array.size(); i++) {
			array.get(i).intersec(set, inis, fins);
		}
	}
	
	/**
	 * set las dos listas de ERs
	 * @param listER1: list 1
	 * @param listER2: list 2
	 */
	public void setERs(List<ExpressionBase> listER1, List<ExpressionBase> listER2) {
		procesar(listER1, listER2);
	}
	
	/**
	 * set algoritmo
	 * @param algoritmoUsuario: algoritmo a aplicar
	 */
	public void setAlgoritmo(String algoritmoUsuario) {
		String algoritmo = algoritmoUsuario == null ? _defaultAlgoritmo: algoritmoUsuario;
		for (AlgoritmoExec ae : AlgoritmoExec.values()) {
			if (algoritmo.equalsIgnoreCase(ae.getAlgoritmo())) {
				_algoritmo = ae;
				break;
			}
		}
	}
	
	/**
	 * set mode
	 * @param m: modo a aplicar
	 */
	public void setMode(String m) {
		String modo = m == null ? _defaultMode: m;
		for (ModeExecution me : ModeExecution.values()) {
			if (modo.equalsIgnoreCase(me.getMode())) {
				_mode = me;
				break;
			}
		}
	}
	
	/**
	 * ejecutar eligiendo el algoritmo adecuado
	 * @return: String resultado de equivalencia
	 */
	private Equivalencia compare() {
		_state = new IdEstado();
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
	
	/**
	 * para el modo uno a uno
	 * @return: string resul
	 */
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
	
	/**
	 * ejecucion
	 * @return: String result
	 */
	public String run() {
		switch(_mode) {
		case TODOS: case SELECTED:
			_e1 = unionListaERs(_elist1);
			_e2 = unionListaERs(_elist2);
			return compare().getMsg();
		case UNOAUNO:
			return emparejar();
		default:
			break;
		}
		return "Algo ha ido mal";
	}
	
	/**
	 * settings
	 * @param s1: lista de ers
	 * @param s2: lista de ers
	 * @param algor: algoritmo usuario
	 * @param mode: modo seleccionado
	 * @return: String result
	 */
	public String compEquiv(List<ExpressionBase> s1, List<ExpressionBase> s2,
			String algor, String mode) {
		setAlgoritmo(algor);
		setMode(mode);
		setERs(s1, s2);
		return run();
	}

}
