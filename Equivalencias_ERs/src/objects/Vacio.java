package objects;

import java.util.regex.Pattern;

import automata.*;

public class Vacio extends ExpressionBase {

	private static final String _regex = "%";

	private static final String CojVacio = "%";
	
	public Vacio() {
		super(Tipo.VACIO);
		_sim = CojVacio;
		if(this.getPadre() != null)
			this.getPadre().insertarVacio();
		
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

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId();
		AutomataTS aut = new AutomataTS(ini);
		return aut;
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId();
		AutomataTS aut = new AutomataTS(ini);
		return aut;
	}
	@Override
	public void cambiarHijo(ExpressionBase sust, ExpressionBase nueva) {	}

	@Override
	public boolean equals(Object o) {
		 if (o == this) return true;
	     if (!(o instanceof CierrePositivo)) {
	            return false;
	     }
	     return true;
	}

}
