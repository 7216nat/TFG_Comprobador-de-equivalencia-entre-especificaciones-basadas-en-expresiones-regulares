package objects;

import automata.*;

public class RangoSimbolos extends ExpressionBase {

	private ExpressionBase _e1;
	private String _str;
	
	public RangoSimbolos() {}
	public RangoSimbolos(String str) {
		_str = str;
		ExpressionBase tmp = new Simbolo("" + str.charAt(0));
		char c = str.charAt(0), ant;
		for (int i=1; i < str.length(); i++) {
			ant = c;
			c = str.charAt(i);
			if (c != '-')
				tmp = new Union(tmp, new Simbolo("" + str.charAt(i)));
			else {
				c = str.charAt(++i);
				if (ant > c) {
					System.out.println("ER inválido");
					System.exit(0);
				}
				while (ant < c) {
					tmp = new Union(tmp, new Simbolo("" + ++ant));
				}
			}
			
		}
		_e1 = tmp;
	}
	
	@Override
	public String toString() {
		return  "{" + _str + "}";
	}

	@Override
	public ExpressionBase cloneMe() {
		// TODO Auto-generated method stub
		return new RangoSimbolos();
	}

	@Override
	public boolean match(String string) {
		// TODO Auto-generated method stub
		return false; //Pattern.matches(_regex, string);
	}
	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		return _e1.ThomsonAFN(id);
	}
	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		// TODO Auto-generated method stub
		return _e1.ThomsonSimplAFN(id);
	}

}
