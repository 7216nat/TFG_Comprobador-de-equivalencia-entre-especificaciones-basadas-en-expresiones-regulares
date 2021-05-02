package objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import automata.*;

/**
 * Clase envoltoria para la union/o no de rangos
 *
 */
public class UnionRangos extends ExpressionBase {
	
	private static final String _regex = "[";
	private static final String unionRangosS = "[";
	private ExpressionBase _e1;
	private String _str;
	private List<RangoCharacter> _rangos;
	
	/**
	 * Clase constructora por defecto
	 */
	public UnionRangos() {
		this((String)null);
	}
	
	/**
	 * Clase constructora
	 * @param str: string que contiene el rango 
	 */
	public UnionRangos(String str) {
		super(Tipo.UNIONRANGOS);
		_str = str;
		_rangos = new ArrayList<>();
	}
	
	public UnionRangos(RangoCharacter rc) {
		super(Tipo.UNIONRANGOS);
		_e1 = rc;
		_rangos = new ArrayList<>();
		_rangos.add(rc);
	}
	/**
	 * parsear el string, se añaden los puntos de interseccion al set \
	 * antes de eso se interseccionan internamente para evitar repeticiones 
	 * @param ss
	 */
	public void parserRangos(Set<Character> inis, Set<Character> fins) {
		
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
					System.exit(1);
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
		}
		else {
			Iterator<RangoCharacter> it = _rangos.iterator();
			_e1 = it.next();
			while (it.hasNext()) {
				_e1 = new Union(_e1, it.next());
			}
		}
	}
	
	/**
	 * Interseccion de todos los rangos con los puntos de intersecciones dadas \
	 * y convertirlos en las uniones
	 * @param set: se añaden los nuevos "simbolos" al set de simbolos
	 * @param inis: lista de puntos iniciales de intersecciones dadas
	 * @param fins: lista de puntos finales de intersecciones dadas
	 */
	public void intersec(Set<String> set, Set<Character> inis, Set<Character> fins) {
		List<RangoCharacter> tmp = new ArrayList<>();
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
					if (rctmp != null) 
						tmp.add(rctmp);
				}
			}
			tmp.add(rc);
			
		}
		_rangos = tmp;
		tmp = new ArrayList<>();
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
					if (rctmp != null) {
						tmp.add(rctmp);
						set.add(rctmp._sim);
					}
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
		List<RangoCharacter> tmp = new ArrayList<>();
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
	
	/**
	 * Devuelve si esta union contiene el sim
	 * @param sim
	 * @return boolean
	 */
	public boolean contiene(String sim) {
		for (RangoCharacter rc: _rangos) {
			if (rc._sim.equals(sim))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "{" + _e1.toString() + "}";
	}

	@Override
	public ExpressionBase cloneMe() {
		return new UnionRangos();
	}

	@Override
	public boolean match(String string) {
		return false; // Pattern.matches(_regex, string);
	}

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		// return _e1.ThomsonAFN(id);
		throw new UnsupportedOperationException();
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		// return _e1.ThomsonSimplAFN(id);
		throw new UnsupportedOperationException();
	}
	
	@Override
	public BerrySethiNode createBerrySethiNode(Map<Integer, BerrySethiNode> map, IdEstado id) {
		// exception
		throw new UnsupportedOperationException();
	}

	@Override
	public void getSimbolosRangos(Set<String> set, List<UnionRangos> array, Set<Character> inis, Set<Character> fins) {
		// parserRangos(inis, fins);
		_e1.getSimbolosRangos(set, array, inis, fins);
		array.add(this);
	}
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
			 return true;
		 }
	     if (!(o instanceof UnionRangos)) {
	            return false;
	     }
	     UnionRangos t = (UnionRangos) o;
	     return t._e1.equals(this._e1);
	}
	
	@Override
	public int hashCode() {
		return _e1.hashCode() + this.getType().getValor();
	}
	
	@Override
	public ExpressionBase buildTreeDefinitivo() {
		_e1 = _e1.buildTreeDefinitivo();
		return _e1;
	}

	@Override
	public String getVal() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ExpressionBase derivada(String sim) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<ExpressionBase> derivadaParcial(String sim) {
		throw new UnsupportedOperationException();
	}
}
