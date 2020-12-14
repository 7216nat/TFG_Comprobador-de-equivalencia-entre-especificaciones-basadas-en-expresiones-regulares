package objects;

import java.util.regex.Pattern;

import automata.Automata;
import automata.IdEstado;

public class Vacio extends ExpressionBase {

	private static final String _regex = "%";

	private static final String CojVacio = "%";
	public Vacio() {
		_sim = CojVacio;
		// TODO Auto-generated constructor stub
	}

	@Override
	public ExpressionBase cloneMe() {
		// TODO Auto-generated method stub
		return new Vacio();
	}

	@Override
	public boolean match(String string) {
		// TODO Auto-generated method stub
		return Pattern.matches(_regex, string);
	}

	@Override
	public Automata ThomsonAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId();
		Automata aut = new Automata(ini);
		return aut;
	}

	@Override
	public Automata ThomsonSimplAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId();
		int acept = ini;
		Automata aut = new Automata(ini, acept);
		return aut;
	}

}
