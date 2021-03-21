package objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.regex.Pattern;

import automata.*;

public class Lambdaa extends Lenguaje {

	private static final String _regex = "&";
	//private static final String _regex = "\\w\\*.*";
	private static final String lambda = "&";
	
	public Lambdaa() {
		super(lambda, null, Tipo.LAMBDA);
	}
	
	public Lambdaa(ExpressionBase padre) {
		super(lambda, padre, Tipo.LAMBDA);
	}

	@Override
	public ExpressionBase cloneMe() {
		return new Lambdaa();
	}

	@Override
	public boolean match(String string) {
		return Pattern.matches(_regex, string);
	}

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		int ini = id.nextId(), acept = id.nextId();
		AutomataTS aut = new AutomataTS(ini, acept);
		aut.addTransicion(ini, acept, _sim);
		return aut;
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		int ini = id.nextId();
		return new AutomataTS(ini, ini);
	}
	
	@Override
	public BerrySethiNode createBerrySethiNode(IdEstado id) {
		BerrySethiNode bs = new BerrySethiNode();
		
		bs.setEmpty(true);
		bs.setSim(_sim);
		bs.setTipo(getType());
		
		bs.setFirst(new HashSet<>());
		
		bs.setLast(new HashSet<>());
		
		return bs;
	}
	
	@Override
	public void getSimbolosRangos(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> inis, SortedSet<Character> fins) {
		// nothing to add
	}
	
	@Override
	public boolean eqLambda() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		 if (o == this) {
			 return true;
		 }
	     if (!(o instanceof Lambdaa)) {
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
	public HashSet<ExpressionBase> derivadaParcial(String sim) {
		HashSet<ExpressionBase> ret = new HashSet<>();
		ret.add(new Vacio());
		return ret;
	}
}
