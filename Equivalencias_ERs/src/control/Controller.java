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
	
	private List<ElementoLista> elemList1 = null;
	private List<ElementoLista> elemList2 = null;
			
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
	
	private void procesar(List<ElementoLista> listER1, List<ElementoLista> listER2) {
		// variables globales en dos expresiones
		elemList1 = listER1;
		elemList2 = listER2;
		List<ExpressionBase> elist1 = new ArrayList<>();
		List<ExpressionBase> elist2 = new ArrayList<>();
		Set<String> simbolosSet = new HashSet<>();
		Set<Character> inis = new TreeSet<>();
		Set<Character> fins = new TreeSet<>();
		List<UnionRangos> rangos = new ArrayList<>();
		_simList = new ArrayList<>();
		ExpressionBase er;
		
		// recolectar simbolos y rangos
		for (ElementoLista el: elemList1) {
			el.expresion.getSimbolosRangos(simbolosSet, rangos, inis, fins);
		}

		// recolectar simbolos y rangos
		for (ElementoLista el: elemList2) {
			el.expresion.getSimbolosRangos(simbolosSet, rangos, inis, fins);
		}

		// interseccion y y obtener los nuevos simbolos
		intersecUR(simbolosSet, rangos, inis, fins);
				
		// conseguir todos los simbolos incluidos los rangos
		Iterator<String> it = simbolosSet.iterator();
		while (it.hasNext()) {
			String c = it.next();
			_simList.add(c);
		}
		if (!rangos.isEmpty()) {
			// deshacer los Unionrangos
			for (ElementoLista el: elemList1) {
				er = el.expresion.buildTreeDefinitivo();
				elist1.add(er);
			}
			for (ElementoLista el: elemList2) {
				er = el.expresion.buildTreeDefinitivo();
				elist2.add(er);
			}
		}else {
			for (ElementoLista el: elemList1) {
				elist1.add(el.expresion);
			}
			for (ElementoLista el: elemList2) {
				elist2.add(el.expresion);
			}
		}
		
		_e1 = unionListaERs(elist1);
		_e2 = unionListaERs(elist2);
	}
	
	private void procesar(ExpressionBase ER1, ExpressionBase ER2) {
		// variables globales en dos expresiones
		Set<String> simbolosSet = new HashSet<>();
		Set<Character> inis = new TreeSet<>();
		Set<Character> fins = new TreeSet<>();
		List<UnionRangos> rangos = new ArrayList<>();
		_simList = new ArrayList<>();
		
		// recolectar simbolos y rangos
		ER1.getSimbolosRangos(simbolosSet, rangos, inis, fins);
		

		// recolectar simbolos y rangos
		ER2.getSimbolosRangos(simbolosSet, rangos, inis, fins);
			
		// interseccion y y obtener los nuevos simbolos
		intersecUR(simbolosSet, rangos, inis, fins);
				
		// conseguir todos los simbolos incluidos los rangos
		Iterator<String> it = simbolosSet.iterator();
		while (it.hasNext()) {
			String c = it.next();
			_simList.add(c);
		}
		
		// deshacer los Unionrangos
		if (!rangos.isEmpty()) {
			_e1 = ER1.buildTreeDefinitivo();
			_e2 = ER2.buildTreeDefinitivo();
		}
		else {
			_e1 = ER1;
			_e2 = ER2;
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
	public void setERs(List<ElementoLista> listER1, List<ElementoLista> listER2) {
		// procesar(listER1, listER2);
		elemList1 = listER1;
		elemList2 = listER2;
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
		StringBuilder bld = new StringBuilder();
		ElementoLista elem1, elem2;
		ExpressionBase er1, er2;
		Iterator<ElementoLista> it1 = elemList1.iterator();
		while (it1.hasNext()) {
			elem1 = it1.next();
			er1 = elem1.expresion;
			Iterator<ElementoLista> it2 = elemList2.iterator();
			while (it2.hasNext()) {
				elem2 = it2.next();
				er2 = elem2.expresion;
				procesar(er1, er2);
				if (compare().isEq()) {
					bld.append( "[" + elem1.nombre+ " => " + elem1.expresionInterfaz + "]" +
								" equivalente a " + "[" + elem2.nombre+ " => " + elem2.expresionInterfaz + "]" + "\n"); 
					it2.remove();
					it1.remove();
					break;
				}
			}
		}
		
		StringBuilder unpair1 = new StringBuilder("\nSin emparejar de la lista 1:\n");
		StringBuilder unpair2 = new StringBuilder("\nSin emparejar de la lista 2:\n");
		for (ElementoLista el: elemList1) unpair1.append("[" + el.nombre+ " => " + el.expresionInterfaz + "]" + "\n");
		for (ElementoLista el: elemList2) unpair2.append("[" + el.nombre+ " => " + el.expresionInterfaz + "]" + "\n");
		if (!elemList1.isEmpty()) bld.append(unpair1);
		if (!elemList2.isEmpty()) bld.append(unpair2);
		return bld.toString();
	}
	
	/**
	 * ejecucion
	 * @return: String result
	 */
	public String run() {
		switch(_mode) {
		case TODOS: case SELECTED:
			procesar(elemList1, elemList2);
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
	public String compEquiv(List<ElementoLista> s1, List<ElementoLista> s2,
			String algor, String mode) {
		setAlgoritmo(algor);
		setMode(mode);
		setERs(s1, s2);
		return run();
	}

}
