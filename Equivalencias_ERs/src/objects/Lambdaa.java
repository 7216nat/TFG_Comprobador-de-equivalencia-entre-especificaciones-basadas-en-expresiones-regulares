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
		// TODO Auto-generated method stub
		return new Lambdaa();
	}

	@Override
	public boolean match(String string) {
		// TODO Auto-generated method stub
		return Pattern.matches(_regex, string);
	}

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId(), acept = id.nextId();
		AutomataTS aut = new AutomataTS(ini, acept);
		aut.addTransicion(ini, acept, _sim);
		return aut;
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		// TODO Auto-generated method stub
		int ini = id.nextId(), acept = id.nextId();
		AutomataTS aut = new AutomataTS(ini, acept);
		aut.addTransicion(ini, acept, _sim);
		return aut;
	}
	
	@Override
	public BerrySethiNode createBerrySethiNode(IdEstado id) {
		// TODO Auto-generated method stub
		HashSet<Integer> tmp = new HashSet<Integer>();
		BerrySethiNode bs = new BerrySethiNode();
		
		bs.setEmpty(true);
		bs.setSim(_sim);
		bs.setTipo(getType());
		
//		int iD = id.nextId(); 
//		tmp.add(iD);
		bs.setFirst(tmp);
		
		tmp = new HashSet<Integer>();
//		tmp.add(iD);
		bs.setLast(tmp);
		
		return bs;
	}
	
	@Override
	public void getSimbolosRangos(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> inis, SortedSet<Character> fins) {
		// TODO Auto-generated method stub
		set.add(lambda);
	}
	
	@Override
	public boolean eqLambda() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		 if (o == this) return true;
	     if (!(o instanceof Lambdaa)) {
	            return false;
	     }
	     return true;
	}

	@Override
	public String getVal() {
		return this._sim;
	}

}
