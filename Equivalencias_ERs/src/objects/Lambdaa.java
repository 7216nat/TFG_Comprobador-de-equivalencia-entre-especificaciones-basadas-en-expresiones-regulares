package objects;

import java.util.regex.Pattern;

import automata.Automata;
import automata.IdEstado;

public class Lambdaa extends ExpressionBase {

	private static final String _regex = "&";
	//private static final String _regex = "\\w\\*.*";
	private static final char lambda = '&';
	public Lambdaa(String er) {
		_sim = lambda;
		// TODO Auto-generated constructor stub
	}

	public Lambdaa() {
		// TODO Auto-generated constructor stub
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
	public Automata ThomsonAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId(), acept = id.nextId();
		Automata aut = new Automata(ini, acept);
		aut.addTransicion(ini, acept, _sim);
		return aut;
	}

	@Override
	public Automata ThomsonSimplAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId(), acept;
		acept = ini;
		Automata aut = new Automata(ini, acept);
		//aut.addTransicion(ini, acept, _sim);
		return aut;
	}

}
