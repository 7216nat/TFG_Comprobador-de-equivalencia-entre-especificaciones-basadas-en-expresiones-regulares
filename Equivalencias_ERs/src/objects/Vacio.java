package objects;

import java.util.regex.Pattern;

public class Vacio extends ExpressionBase {

	private static final String _regex = "\\\\o";

	private static final String CojVacio = "\\\\o";
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

}
