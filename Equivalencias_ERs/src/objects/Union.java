package objects;

import java.util.ArrayList;

import automata.*;
import excepciones.VacioException;

//import java.util.regex.*; 

public class Union extends ExpressionBase {
	
	//private static final String _regex = "\\w+[+][\\w\\+\\*\\(\\)]+";
	
	private ExpressionBase _e1;
	private ExpressionBase _e2;
	
	public Union() {super(Tipo.UNION);}
	public Union(ExpressionBase e1, ExpressionBase e2) throws VacioException{
		super(Tipo.UNION);
		_e1 = e1;
		_e2 = e2;
		_e1.setPadre(this);
		_e2.setPadre(this);
		if(_e1.getType() == Tipo.VACIO)
			throw new VacioException();
		if(_e2.getType() == Tipo.VACIO)
			throw new VacioException();
	
	}
	
	@Override
	public String toString() {
		return  "( " + _e1.toString() + "|" +  _e2.toString() + " )";
	}

	@Override
	public ExpressionBase cloneMe() {
		// TODO Auto-generated method stub
		return new Union();
	}

	@Override
	public boolean match(String string) {
		// TODO Auto-generated method stub
		return false; //Pattern.matches(_regex, string);
	}
	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId(), acept, iniPrev1, iniPrev2;
		ArrayList<Estado> aceptPrev1, aceptPrev2;
		AutomataTS a1 = _e1.ThomsonAFN(id);
		AutomataTS a2 = _e2.ThomsonAFN(id);
		
		// deshacer todos los estados finales e iniciales
		a1.quitarTodosFin();
		a1.quitarIni();
		a2.quitarTodosFin();
		a2.quitarIni();
		
		// referencias a estos
		iniPrev1 = a1.getIni().getId();
		aceptPrev1 = a1.getFin();
		iniPrev2 = a2.getIni().getId();
		aceptPrev2 = a2.getFin();
		
		// nueva automata
		acept = id.nextId();
		AutomataTS aut = new AutomataTS(ini, acept);
		aut.copyAll(a1);
		aut.copyAll(a2);
		
		// consulte memoria
		aut.addTransicion(ini, iniPrev1, "&");
		aut.addTransicion(ini, iniPrev2, "&");
		for (Estado e: aceptPrev1)
			aut.addTransicion(e.getId(), acept, "&");
		for (Estado e: aceptPrev2)
			aut.addTransicion(e.getId(), acept, "&");
		return aut;
	}
	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		// TODO Auto-generated method stub
		Estado iniPrev1, iniPrev2;
		AutomataTS a1 = _e1.ThomsonSimplAFN(id);
		AutomataTS a2 = _e2.ThomsonSimplAFN(id);
		
		iniPrev1 = a1.getIni();
		iniPrev2 = a2.getIni();
		
		// copio todas las transiciones de otra automata y sus estados finales
		a1.copyAll(a2);
		a1.copyFinals(a2);
		
		// en caso de que algun estado inicial sea final, el estado inicial que queda es final 
		if (iniPrev1.esFin() || iniPrev2.esFin()) a1.IniFinalEs(true);
		
		// a�adir las transiciones del a2.estadoinicial a a1.estadoinicila y eliminar el primero
		a1.unirSinEliminar(iniPrev1, iniPrev2);
		a1.eliminarEstado(iniPrev2.getId());
		
		return a1;
	}
	public ExpressionBase getExpr1() {
		return this._e1;
	}
	public ExpressionBase getExpr2() {
		return this._e2;
	}
	
	@Override
	public boolean eqLambda() {
		return (_e1.eqLambda() || _e2.eqLambda());
	}
	
	@Override
	public void cambiarHijo(ExpressionBase sust, ExpressionBase nueva) {
		if(nueva.getType() == Tipo.VACIO) {
			if(this._e1 == sust)
				this.getPadre().cambiarHijo(this,  this._e1);
			else
				this.getPadre().cambiarHijo(this,  this._e2);
		}
		
		else if(this._e1 == sust)
			this._e1 = nueva;
		else
			this._e2 = nueva;
	}
	@Override
	public boolean equals(Object o) {
		 if (o == this) return true;
	     if (!(o instanceof Union)) {
	            return false;
	     }
	     Union t = (Union) o;
	     return t._e1.equals(this._e1) && t._e2.equals(this._e2);
	}
	
}
