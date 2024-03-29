package objects;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import automata.AutomataTS;
import automata.IdEstado;

/**
 * Clase hoja
 */
public class RangoCharacter extends Lenguaje implements Comparable<RangoCharacter> {
	
	private char _ini;
	private char _fin;
	
	/**
	 * Constructora por defecto
	 */
	public RangoCharacter() {
		super(null, Tipo.RANGO);
	}
	
	/**
	 * Constructora para simbolo unico
	 * @param inifin: caracter ini y fin
	 */
	public RangoCharacter(char inifin) {
		super(null, Tipo.RANGO);
		_ini = inifin;
		_fin = inifin;
		actualizarSim();
	}
	
	/**
	 * Constructora para rangos
	 * @param ini: caracter inicial
	 * @param fin: caracter final
	 */
	public RangoCharacter(char ini, char fin) {
		super(null, Tipo.RANGO);
		_ini = ini;
		_fin = fin;
		actualizarSim();
	}

	/**
	 * getter ini
	 * @return
	 */
	public char get_ini() { return _ini; }
	/**
	 * getter fin
	 * @return
	 */
	public char get_fin() { return _fin; }
	
	/**
	 * Actualizar toString()
	 */
	public void actualizarSim() {
		_sim = _ini + "";
	}
	
	@Override
	public String toString() { 
		if 	(_ini != _fin)
			return  "[" + _ini + "-" + _fin + "]"; 
		else
			return "[" + _ini + "]";
	}
	/**
	 * @param rc
	 * @return el objeto de llamada contenido en rc
	 */
	public boolean contenida(RangoCharacter rc) {
		return (_ini >= rc._ini && _fin <= rc._fin);
	}
	
	/**
	 * @param c: char
	 * @return this contiene c
	 */
	public boolean contiene(char c, boolean ini) {
		return (_ini != _fin && ((_ini < c && _fin > c) || (_fin == c && ini) || (_ini == c && !ini)) );
	}
	
	/**
	 * @param rc: rango
	 * @return this contiene rc
	 */
	public boolean contiene(RangoCharacter rc) {
		return (_ini <= rc._ini && _fin >= rc._fin && _ini != _fin);
	}
	
	/**
	 * @param rc: rango
	 * @return si this intersecciona con rc 
	 */
	public boolean isIntersec(RangoCharacter rc) {
		return (rc._ini < _ini && _ini <= rc._fin && rc._fin <= _fin) || ( _fin >= rc._ini && rc._fin > _fin && rc._ini >= _ini)
				|| (rc._ini == (char) (_fin+1)) || (rc._fin == (char) (_ini-1));
	}
	
	/**
	 * union de this con rc y actualizar toString
	 * @param rc: rango
	 */
	public void union(RangoCharacter rc) {
		_ini = (_ini < rc._ini) ? _ini : rc._ini;
		_fin = (_fin > rc._fin) ? _fin : rc._fin;
		actualizarSim();
	}
	
	/**
	 * @param c: char
	 * @return fin de rango de this mayor que c
	 */
	public boolean mayorQue(char c) {
		return _fin >= c;
	}
	
	/**
	 * Interseccion con this hasta c incluido
	 * @param c: char
	 * @return RangoCharacter(this._ini, c)
	 */
	public RangoCharacter interseccion(char c, boolean ini) {
		char tmp = _ini;
		if (c == this._fin) {
			if (ini) {
				_ini = _fin;
				actualizarSim();
				return new RangoCharacter(tmp, (char)(c-1));
			}
			else return null;
		} 
		else if (c == this._ini) {
			if (!ini) {
				_ini = (char)(c+1);
				actualizarSim();
				return new RangoCharacter(c);
			}
			else return null;
		}
		else {
			if (ini) {
				this._ini = c;
				actualizarSim();
				return new RangoCharacter(tmp, (char)(c-1)); 
			} else {
				this._ini = (char)(c+1);
				actualizarSim();
				return new RangoCharacter(tmp, c); 
			}
		}
	}
	
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
			 return true;
		 }
	     if (!(o instanceof RangoCharacter)) {
	            return false;
	     }
	     RangoCharacter rc = (RangoCharacter) o;
	     return _ini == rc._ini && _fin == rc._fin;
	}
	
	@Override
	public int hashCode() {
		return _sim.hashCode();
	}


	@Override
	public int compareTo(RangoCharacter o) {
		if (_ini > o._ini) return 1;
		else if (_ini < o._ini) return -1;
		return 0;
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		int ini = id.nextId();
		int acept = id.nextId();
		AutomataTS aut = new AutomataTS(ini, acept);
		aut.addTransicion(ini, acept, _sim);
		return aut;
	}

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		int ini = id.nextId();
		int acept = id.nextId();
		AutomataTS aut = new AutomataTS(ini, acept);
		aut.addTransicion(ini, acept, _sim);
		return aut;
	}
	
	@Override
	public BerrySethiNode createBerrySethiNode(Map<Integer, BerrySethiNode> map, IdEstado id) {
		Set<Integer> tmp = new HashSet<>();
		BerrySethiNode bs = new BerrySethiNode();
		
		bs.setEmpty(false);
		bs.setSim(_sim);
		bs.setTipo(getType());
		
		int iD = id.nextId(); 
		tmp.add(iD);
		bs.setFirst(tmp);
		
		tmp = new HashSet<>();
		tmp.add(iD);
		bs.setLast(tmp);
		
		map.put(iD, bs);
		return bs;
	}

	@Override
	public void getSimbolosRangos(Set<String> set, List<UnionRangos> array, Set<Character> inis, Set<Character> fins) {
		inis.add(_ini);
		fins.add(_fin);
	}	

	@Override
	public String getVal() {
		return this._sim;
	}

	@Override
	public ExpressionBase derivada(String sim) {
		if(sim.equals(this._sim))
			return new Lambdaa();
		else
			return new Vacio();
	}
	@Override
	public Set<ExpressionBase> derivadaParcial(String sim) {
		Set<ExpressionBase> ret = new HashSet<>();
		if(this._sim.equals(sim))
				ret.add(new Lambdaa());
		else ret.add(new Vacio());
		return ret;
	}

	@Override
	public ExpressionBase copy() {
		return new RangoCharacter(_ini, _fin);
	}
}
