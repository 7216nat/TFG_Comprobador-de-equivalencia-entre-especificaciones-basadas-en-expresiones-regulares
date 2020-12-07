package objects;

import java.util.regex.Pattern;

public class Lambdaa extends ExpressionBase {

	private static final String _regex = "\\\\e";
	//private static final String _regex = "\\w\\*.*";
	private static final String lambda = "\\\\e";
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

}
