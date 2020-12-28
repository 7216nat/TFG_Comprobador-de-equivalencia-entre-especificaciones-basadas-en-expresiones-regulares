package objects;

import java.util.regex.Pattern;

import automata.*;

// Comentarios en ExpressionBase
public class Simbolo extends ExpressionBase {

	private static final String _regex = "\\w";
	
	/**
	 * construtora por defecto
	 */
	public Simbolo() {}
	
	/**
	 * constructora
	 * @param er
	 */
	public Simbolo(String er) {
		_sim = er;
	}

	@Override
	public ExpressionBase cloneMe() {
		return new Simbolo();
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

}
