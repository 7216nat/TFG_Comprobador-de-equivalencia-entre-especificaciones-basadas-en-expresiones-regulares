package objects;


public abstract class Lenguaje extends ExpressionBase {
	
	protected String _sim;
	
	protected Lenguaje(String sim, Tipo tipo) {
		super(tipo);
		_sim = sim;
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
		return this;
	}
	
}
