package analizador.sintactico;

import objects.ExpressionBase;

public class UnidadParse {
	protected String str;
	protected ExpressionBase er;
	
	public UnidadParse(String str, ExpressionBase er) {
		this.str = str;
		this.er = er;
	}
	
	public String getStr() {
		return this.str;
	}
	
	public ExpressionBase getER() {
		return this.er;
	}
	
	@Override
	public String toString() {
		return str; //+ "  ";// + er.toString();
	}
}
