package objects;

import java.util.regex.Pattern;

import automata.*;

// Comentarios en ExpressionBase
public class Simbolo extends ExpressionBase implements Comparable<Simbolo> {

	private static final String _regex = "\\w";
	
	/**
	 * construtora por defecto
	 */
	public Simbolo() {super(Tipo.SIMB);}
	
	/**
	 * constructora
	 * @param er
	 */
	public Simbolo(String er) {
		super(Tipo.SIMB);
		_sim = er;
	}

	@Override
	public ExpressionBase cloneMe() {
		return new Simbolo();
	}
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) return true;
	     if (!(o instanceof Simbolo)) {
	            return false;
	     }
	     Simbolo sim = (Simbolo) o;
	     return _sim.equals(sim._sim);
	}
	
	@Override
	public boolean match(String string) {
		return Pattern.matches(_regex, string);
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
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId(), acept = id.nextId();
		AutomataTS aut = new AutomataTS(ini, acept);
		aut.addTransicion(ini, acept, _sim);
		return aut;
	}

	@Override
	public int compareTo(Simbolo o) {
		// TODO Auto-generated method stub
		return _sim.compareTo(o._sim);
	}
	
}
