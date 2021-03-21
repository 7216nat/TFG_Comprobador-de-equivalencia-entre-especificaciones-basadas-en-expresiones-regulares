package objects;


public abstract class Lenguaje extends ExpressionBase {
	
	protected String _sim;
	
	protected Lenguaje(String sim, ExpressionBase padre, Tipo tipo) {
		super(padre, tipo);
		_sim = sim;
		// Auto-generated constructor stub
	}
	
	public boolean esSimb(String a) {
		return (this._sim.equals(a));
	}
	
	public String get_sim() {return _sim;}
	
	public void set_sim(String sim) { _sim = sim; }
	
	@Override
	public int hashCode() {
		return _sim.hashCode();
	}
	
	@Override
	public String toString() { return _sim; }
	
	@Override
	public ExpressionBase buildTreeDefinitivo() {
		// Auto-generated method stub
		return this;
	}
	
}
