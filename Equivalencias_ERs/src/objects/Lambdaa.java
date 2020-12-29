package objects;

import java.util.regex.Pattern;

import automata.*;

public class Lambdaa extends ExpressionBase {

	private static final String _regex = "&";
	//private static final String _regex = "\\w\\*.*";
	private static final String lambda = "&";
	
	/**
	 * constructora 
	 * @param er
	 */
	public Lambdaa(String er) {
		_sim = lambda;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * construtora por defecto
	 */
	public Lambdaa() {
		// TODO Auto-generated constructor stub
		_sim = lambda;
	}

	@Override
	public ExpressionBase cloneMe() {
		// TODO Auto-generated method stub
		return new Lambdaa();
	}

	@Override
	public boolean match(String string) {
		// TODO Auto-generated method stub
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
