package objects;

import automata.*;
import excepciones.VacioException;

public class LambdaExp extends ExpressionBase {

	private ExpressionBase _e1;

	public LambdaExp(ExpressionBase e1) {
		super(Tipo.LAMBDAEXP);
		try {
		_e1 = new Union(e1, new Lambdaa());
		}
		catch(VacioException e) {
			_e1 = new Lambdaa();
		}
		finally {
		e1.setPadre(this);
		}
	}

	public LambdaExp() {
		super(Tipo.LAMBDAEXP);
	}

	@Override
	public String toString() {
		return _e1.toString();
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

	@Override
	public boolean eqLambda() {
		return true;
	}
	@Override
	public void insertarVacio() {
		this.getPadre().cambiarHijo(this,  new Lambdaa());
	}
	
	@Override
	public void cambiarHijo(ExpressionBase sust, ExpressionBase nueva) {
		if(nueva.getType() == Tipo.VACIO)
			insertarVacio();
		this._e1 = nueva;
	}

	@Override
	public boolean equals(Object o) {
		 if (o == this) return true;
	     if (!(o instanceof LambdaExp)) {
	            return false;
	     }
	     LambdaExp t = (LambdaExp) o;
	     return t._e1.equals(this._e1);
	}


}
