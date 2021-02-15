package objects;

import automata.*;

public class LambdaExp extends ExpressionBase {
	
	private ExpressionBase _e1;

	public LambdaExp(ExpressionBase e1) {
		super(Tipo.LAMBDAEXP);
		_e1 = new Union(e1, new Lambdaa());
		e1.setPadre(this);
	}

	public LambdaExp() {super(Tipo.LAMBDAEXP);}

	@Override
	public String toString() {
		return  _e1.toString();
	}

	@Override
	public ExpressionBase cloneMe() {
		// TODO Auto-generated method stub
		return new LambdaExp();
	}

	@Override
	public boolean match(String string) {
		// TODO Auto-generated method stub
		return false; // Pattern.matches(_regex, string);
	}

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		return _e1.ThomsonAFN(id);
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		return _e1.ThomsonSimplAFN(id);
	}

}
