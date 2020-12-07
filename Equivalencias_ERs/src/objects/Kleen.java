package objects;
//import java.util.regex.*;  
public class Kleen extends ExpressionBase {
	
	//private static final String _regex = "\\w\\*[\\w\\+\\*\\(\\)]+|\\)\\*[\\w\\+\\*\\(\\)]*";
	//private static final String _regex = "\\w\\*.*";
	private ExpressionBase _e1;
	public Kleen(ExpressionBase e1) {
		_e1 = e1;
	}
	
	public Kleen() {}
	
	@Override
	public String toString() {
		return  "["+_e1.toString() + "]*";
	}

	@Override
	public ExpressionBase cloneMe() {
		// TODO Auto-generated method stub
		return new Kleen();
	}

	@Override
	public boolean match(String string) {
		// TODO Auto-generated method stub
		return false; //Pattern.matches(_regex, string);
	}

}
