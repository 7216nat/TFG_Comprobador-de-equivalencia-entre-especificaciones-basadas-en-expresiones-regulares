package objects;

import java.util.regex.Pattern;

import automata.AutomataTS;
import automata.IdEstado;

/**
 * Clase hoja
 */
public class RangoCharacter extends ExpressionBase implements Comparable<RangoCharacter> {
	
	private static final String _regex = "\\w";
	
	private char _ini;
	private char _fin;
	
	/**
	 * Constructora por defecto
	 */
	public RangoCharacter() {super(Tipo.RANGO);}
	
	/**
	 * Constructora para simbolo unico
	 * @param inifin: caracter ini y fin
	 */
	public RangoCharacter(char inifin) {
		super(Tipo.RANGO);
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
		super(Tipo.RANGO);
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
		if (_ini == _fin) _sim = _ini + "";
		else _sim = _ini + "-" + _fin;
	}
	
	/**
	 * @param rc
	 * @return el objeto de llamada contenido en rc
	 */
	public boolean contenida(RangoCharacter rc) {
		return (_ini > rc._ini && _fin < rc._fin);
	}
	
	/**
	 * @param c: char
	 * @return this contiene c
	 */
	public boolean contiene(char c) {
		return (_ini <= c && _fin >= c && _ini != _fin);
	}
	
	/**
	 * @param c: char
	 * @return this contiene c
	 */
	public boolean contieneRango(char c) {
		return (_ini < c && _fin > c && _ini != _fin);
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
	public RangoCharacter interseccion(char c) {
		char tmp = _ini;
		if (c == this._fin) {
			_fin = (char)(_fin - 1);
			actualizarSim();
			return new RangoCharacter(c);
		}
		this._ini = (char) (c+1);
		actualizarSim();
		return new RangoCharacter(tmp, c);
	}
	
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) return true;
	     if (!(o instanceof RangoCharacter)) {
	            return false;
	     }
	     RangoCharacter rc = (RangoCharacter) o;
	     return _ini == rc._ini && _fin == rc._fin;
	}

	@Override
	public int compareTo(RangoCharacter o) {
		// TODO Auto-generated method stub
		if (_ini > o._ini) return 1;
		else if (_ini < o._ini) return -1;
		return 0;
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId(), acept = id.nextId();
		AutomataTS aut = new AutomataTS(ini, acept);
		aut.addTransicion(ini, acept, _sim);
		return aut;
	}

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId(), acept = id.nextId();
		AutomataTS aut = new AutomataTS(ini, acept);
		aut.addTransicion(ini, acept, _sim);
		return aut;
	}

	@Override
	public ExpressionBase cloneMe() {
		// TODO Auto-generated method stub
		return new RangoCharacter();
	}

	@Override
	public boolean match(String string) {
		// TODO Auto-generated method stub
		return Pattern.matches(_regex, string);
	}
	
}
