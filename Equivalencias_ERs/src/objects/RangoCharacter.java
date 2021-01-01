package objects;

import automata.AutomataTS;
import automata.IdEstado;

public class RangoCharacter extends ExpressionBase implements Comparable<RangoCharacter> {
	
	private char _ini;
	private char _fin;
	
	public RangoCharacter() {}
	public RangoCharacter(char inifin) {	
		_ini = inifin;
		_fin = inifin;
		_sim = "" + _ini;
	}
	
	public RangoCharacter(char ini, char fin) {
		_ini = ini;
		_fin = fin;
		actualizarSim();
	}
	
	public char get_ini() { return _ini; }
	public char get_fin() { return _fin; }
	
	public void actualizarSim() {
		if (_ini == _fin) _sim = _ini + "";
		else _sim = _ini + "-" + _fin;
	}
	
	public boolean contenida(RangoCharacter rc) {
		return (_ini > rc._ini && _fin < rc._fin);
	}
	
	public boolean contiene(RangoCharacter rc) {
		return (_ini <= rc._ini && _fin >= rc._fin);
	}
	
	public boolean isIntersec(RangoCharacter rc) {
		return (rc._ini < _ini && _ini <= rc._fin && rc._fin <= _fin) || ( _fin >= rc._ini && rc._fin > _fin && rc._ini >= _ini);
	}
	
	public void union(RangoCharacter rc) {
		_ini = (_ini < rc._ini) ? _ini : rc._ini;
		_fin = (_fin > rc._fin) ? _fin : rc._fin;
		actualizarSim();
	}
	
	public RangoCharacter interseccion(RangoCharacter rc) {
		char tmp;
		if (_ini >= rc._ini) {
			tmp = _ini;
			this._ini = (char) (rc._fin + 1);
			actualizarSim();
			return new RangoCharacter(tmp, rc._fin);
		}
		else if(_fin <= rc._fin) {
			tmp = _fin;
			this._fin = (char) (rc._ini - 1);
			actualizarSim();
			return new RangoCharacter(rc._ini, tmp);
		}
		return null;
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
		return false;
	}

}
