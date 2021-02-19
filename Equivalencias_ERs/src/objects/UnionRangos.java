package objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

import automata.*;

/**
 * Clase envoltoria para la union/o no de rangos
 *
 */
public class UnionRangos extends ExpressionBase {
	
	private static final String _regex = "[";
	private static final String unionRangos = "[";
	private ExpressionBase _e1;
	private String _str;
	private ArrayList<RangoCharacter> _rangos;
	
	/**
	 * Clase constructora por defecto
	 */
	public UnionRangos() {
		super(unionRangos, null, Tipo.UNIONRANGOS);
	}
	
	/**
	 * Clase constructora
	 * @param str: string que contiene el rango 
	 */
	public UnionRangos(String str) {
		super(unionRangos, null, Tipo.UNIONRANGOS);
		_str = str;
		_rangos = new ArrayList<RangoCharacter>();
	}
	
	public UnionRangos(ExpressionBase padre , String str) {
		super(unionRangos, padre, Tipo.UNIONRANGOS);
		_str = str;
		_rangos = new ArrayList<RangoCharacter>();
	}
	
	/**
	 * parsear el string, se añaden los puntos de interseccion al set \
	 * antes de eso se interseccionan internamente para evitar repeticiones 
	 * @param ss
	 */
	public void parserRangos(SortedSet<Character> inis, SortedSet<Character> fins) {
		
		char c;
		int i = 0;
		while (i < _str.length()) { // Procesado de String
			c = _str.charAt(i);
			if (i == _str.length()-1 || _str.charAt(i+1) != '-') {
				_rangos.add(new RangoCharacter(c));
			}
			else {
				i +=2;
				if (_str.charAt(i) < c) {
					System.out.println("ER inválido, Rango incorrecto");
					System.exit(0);
				}
				_rangos.add(new RangoCharacter(c ,_str.charAt(i)));
			}
			i++;
		}
		
		selfIntersec(); 					// Intersecciones internas
		for (RangoCharacter rc: _rangos) {  // Añadidos de puntos de interseccion
			inis.add(rc.get_ini());
			fins.add(rc.get_fin());
		}
	}
	
	/**
	 * Convertir la lista de rangos en la union de ERs
	 */
	public void unirRangos() {
		if (_rangos.size() == 1) {
			_e1 = _rangos.get(0);
			_e1.setPadre(this);
		}
		else {
			Iterator<RangoCharacter> it = _rangos.iterator();
			_e1 = it.next();
			while (it.hasNext()) {
				_e1 = new Union(_e1, it.next());
			}
			_e1.setPadre(this);
		}
	}
	
	/**
	 * Interseccion de todos los rangos con los puntos de intersecciones dadas \
	 * y convertirlos en las uniones
	 * @param set: se añaden los nuevos "simbolos" al set de simbolos
	 * @param ss: lista de puntos de intersecciones dadas
	 */
	public void intersec(Set<String> set, SortedSet<Character> inis, SortedSet<Character> fins) {
		ArrayList<RangoCharacter> tmp = new ArrayList<RangoCharacter>();
		RangoCharacter rc, rctmp;
		
		Iterator<RangoCharacter> it = _rangos.iterator();
		Iterator<Character> it_c;
		char c;
		
		while (it.hasNext()) {
			rc = it.next();
			
			it_c = inis.iterator();
			while (it_c.hasNext()) {
				c = it_c.next();
				if (!rc.mayorQue(c))
					break;
				if (rc.contiene(c, true)) {
					rctmp = rc.interseccion(c, true);
					tmp.add(rctmp);
				}
			}
			tmp.add(rc);
			
		}
		_rangos = tmp;
		tmp = new ArrayList<RangoCharacter>();
		it = _rangos.iterator();
		while (it.hasNext()) {
			rc = it.next();
			
			it_c = fins.iterator();
			while (it_c.hasNext()) {
				c = it_c.next();
				if (!rc.mayorQue(c))
					break;
				if (rc.contiene(c, false)) {
					rctmp = rc.interseccion(c, false);
					tmp.add(rctmp);
					set.add(rctmp._sim);
				}
			}
			tmp.add(rc);
			set.add(rc._sim);
		}
		
		_rangos = tmp;
		unirRangos();
	}
	
	/**
	 * Quitar repeticiones, unir en caso de intersecciones
	 */
	private void selfIntersec() {
		ArrayList<RangoCharacter> tmp = new ArrayList<RangoCharacter>();
		Iterator<RangoCharacter> it = _rangos.iterator(), ittmp;
		RangoCharacter rc, rctmp;
		boolean existe;
		tmp.add(it.next());
		
		while (it.hasNext()) {
			rc = it.next();
			ittmp = tmp.iterator();
			existe = false;
			
			while(ittmp.hasNext()) {
				rctmp = ittmp.next();
				if(rc.contenida(rctmp)) {
					existe = true;
					break;
				}
				else if (rc.contiene(rctmp)) { 
					ittmp.remove();
				}
				else if (rc.isIntersec(rctmp)) {
					rc.union(rctmp);
					ittmp.remove();
				}
			}
			
			if (!existe) tmp.add(rc);
		}
		
		_rangos = tmp;
	}

	@Override
	public String toString() {
		return "[" + _e1.toString() + "]";
	}

	@Override
	public ExpressionBase cloneMe() {
		// TODO Auto-generated method stub
		return new UnionRangos();
	}

	@Override
	public boolean match(String string) {
		// TODO Auto-generated method stub
		return false; // Pattern.matches(_regex, string);
	}

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		return _e1.ThomsonAFN(id);
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		// TODO Auto-generated method stub
		return _e1.ThomsonSimplAFN(id);
	}

	@Override
	public void getSimbolosRangos(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> inis, SortedSet<Character> fins) {
		// TODO Auto-generated method stub
		parserRangos(inis, fins);
		array.add(this);
	}

}
