package objects;

import java.util.ArrayList;
// import java.util.regex.*;

import automata.*;
import excepciones.LambdaException;
import excepciones.VacioException;

public class Concat extends ExpressionBase {

	// private static final String _regex = "\\w\\w[\\w\\+\\*\\(\\)]+";
	private ExpressionBase _e1;
	private ExpressionBase _e2;

	public Concat() {
		super(Tipo.CONCAT);
	}

	public Concat (ExpressionBase e1, ExpressionBase e2) throws VacioException, LambdaException {
		super(Tipo.CONCAT);
		_e1 = e1;
		_e2 = e2;
		_e1.setPadre(this);
		_e2.setPadre(this);
		if(_e1.getType() == Tipo.VACIO || _e2.getType() == Tipo.VACIO)
			throw new VacioException();
		
		if(_e1.getType() == Tipo.LAMBDA)
			throw new LambdaException();
		if(_e2.getType() == Tipo.LAMBDA)
			throw new LambdaException();
	}

	@Override
	public String toString() {
		return _e1.toString() + _e2.toString();
	}

	@Override
	public ExpressionBase cloneMe() {
		// TODO Auto-generated method stub
		return new Concat();
	}

	@Override
	public boolean match(String string) {
		// TODO Auto-generated method stub
		return false; // Pattern.matches(_regex, string);
	}

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		// TODO Auto-generated method stub
		Estado iniPrev;
		ArrayList<Estado> aceptPrev;
		AutomataTS a1 = _e1.ThomsonAFN(id);
		AutomataTS a2 = _e2.ThomsonAFN(id);
		
		// deshago estado inicial de a2 y finales de a1
		a2.quitarIni();
		a1.quitarTodosFin();
		iniPrev = a2.getIni();
		aceptPrev = a1.getFin();
		a1.copyAll(a2);
		for (Estado e: aceptPrev)
			a1.addTransicion(e.getId(), iniPrev.getId(), "&");
		a1.finClear();	
		a1.copyFinals(a2);
		// a�adir una lambda-transicion de a1.estadofinal a a2.estadoinicial 
		return a1;
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		// TODO Auto-generated method stub
		Estado iniPrev;
		ArrayList<Estado> aceptPrev1;
		AutomataTS a1 = _e1.ThomsonSimplAFN(id);
		AutomataTS a2 = _e2.ThomsonSimplAFN(id);

		iniPrev = a2.getIni();
		aceptPrev1 = a1.getFin();
		a1.copyAll(a2);
		
		// a�adir a los estados finales de a1 todos las transiciones de a2.estadoinicial
		for (Estado e: aceptPrev1)
			a1.unirSinEliminar(e, iniPrev);
		
		// si a2.estado inicial no es final, se elimina la lista de estado finales
		if (!iniPrev.esFin()) {
			a1.quitarTodosFin();
		}
		
		// estado inicial deja de ser final, y borrar los estados finales anteriories
		a1.IniFinalEs(false);
		a1.finClear();
		
		a1.copyFinals(a2);
		a1.eliminarEstado(iniPrev.getId());
		return a1;
	}
	
	public ExpressionBase getExpr1() {
		return this._e1;
	}
	public ExpressionBase getExpr2() {
		return this._e2;
	}
	

	public void insertarVacio() {
		if (this.getPadre() != null) {
			this.getPadre().cambiarHijo(this, null);
		}
		/////////////////////////////////////
		else this.setPadre(new Vacio());
	}

	@Override
	public void cambiarHijo(ExpressionBase sust, ExpressionBase nueva) {
		if(nueva.getType() == Tipo.VACIO)
			insertarVacio();
		
		else if(this._e1 == sust)
			this._e1 = nueva;
		else
			this._e2 = nueva;
	}

	@Override
	public boolean equals(Object o) {
		 if (o == this) return true;
	     if (!(o instanceof Concat)) {
	            return false;
	     }
	     Concat t = (Concat) o;
	     return t._e1.equals(this._e1) && t._e2.equals(this._e2);
	}

}
