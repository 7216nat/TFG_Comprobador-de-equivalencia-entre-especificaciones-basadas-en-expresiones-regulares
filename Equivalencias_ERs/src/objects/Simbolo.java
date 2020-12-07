package objects;

import java.util.regex.Pattern;

// Comentarios en ExpressionBase
public class Simbolo extends ExpressionBase {

	private static final String _regex = "\\w";
	
	public Simbolo() {}
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

}
