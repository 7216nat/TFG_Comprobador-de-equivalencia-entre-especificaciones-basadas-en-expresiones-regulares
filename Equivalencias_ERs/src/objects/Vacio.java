package objects;

import java.util.regex.Pattern;

import automata.*;

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
	public AutomataTS ThomsonAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId();
		AutomataTS aut = new AutomataTS(ini);
		return aut;
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId();
		AutomataTS aut = new AutomataTS(ini);
		return aut;
	}

}
