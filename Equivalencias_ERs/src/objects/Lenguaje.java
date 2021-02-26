package objects;

import java.util.ArrayList;
import java.util.Set;
import java.util.SortedSet;

public abstract class Lenguaje extends ExpressionBase {
	
	protected String _sim;
	
	public Lenguaje(String sim, ExpressionBase padre, Tipo tipo) {
		super(padre, tipo);
		_sim = sim;
		// TODO Auto-generated constructor stub
	}
	
	public boolean esSimb(String a) {
		return (this._sim.equals(a));
	}
	
	public String get_sim() {return _sim;}
	
	public void set_sim(String sim) { _sim = sim; }
	
	@Override
	public String toString() { return _sim; }
	
	@Override
	public ExpressionBase buildTreeDefinitivo() {
		// TODO Auto-generated method stub
		return this;
	}
	
}
