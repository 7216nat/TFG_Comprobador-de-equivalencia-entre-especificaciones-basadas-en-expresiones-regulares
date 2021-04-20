package objects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import automata.*;

public class Vacio extends Lenguaje {

	private static final String _regex = "%";
	private static final String CojVacio = "%";
	
	public Vacio() {
		super(CojVacio, Tipo.VACIO);
	}

	@Override
	public ExpressionBase cloneMe() {
		return new Vacio();
	}

	@Override
	public boolean match(String string) {
		return Pattern.matches(_regex, string);
	}

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		int ini = id.nextId();
		return new AutomataTS(ini);
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		int ini = id.nextId();
		return new AutomataTS(ini);
	}
	
	@Override
	public BerrySethiNode createBerrySethiNode(IdEstado id) {
		BerrySethiNode bs = new BerrySethiNode();
		
		bs.setEmpty(false);
		bs.setSim(_sim);
		bs.setTipo(getType());
		
		bs.setFirst(new HashSet<>());
		
		bs.setLast(new HashSet<>());
		
		return bs;
	}

	@Override
	public void getSimbolosRangos(Set<String> set, List<UnionRangos> array, Set<Character> sort, Set<Character> sortRango) {
		// no needed
	}
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
			 return true;
		 }
	     if (!(o instanceof Vacio)) {
	            return false;
	     }
	     return true;
	}

	@Override
	public String getVal() {
		return this._sim;
	}

	@Override
	public ExpressionBase derivada(String sim) {
		return new Vacio();
	}

	@Override
	public Set<ExpressionBase> derivadaParcial(String sim) {
		Set<ExpressionBase> ret = new HashSet<>();
		ret.add(new Vacio());
		return ret;
	}
		
}
