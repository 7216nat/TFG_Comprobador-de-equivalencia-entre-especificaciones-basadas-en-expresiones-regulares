package objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.regex.Pattern;

import automata.*;

public class Vacio extends Lenguaje {

	private static final String _regex = "%";
	private static final String CojVacio = "%";
	
	public Vacio() {
		super(CojVacio, null, Tipo.VACIO);
		// TODO Auto-generated constructor stub
	}
	
	public Vacio(ExpressionBase padre) {
		super(CojVacio, padre, Tipo.VACIO);
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
	public BerrySethiNode createBerrySethiNode(IdEstado id) {
		// TODO Auto-generated method stub
		HashSet<Integer> tmp = new HashSet<Integer>();
		BerrySethiNode bs = new BerrySethiNode();
		
		bs.setEmpty(false);
		bs.setSim(_sim);
		bs.setTipo(getType());
		
		int iD = id.nextId(); 
		tmp.add(iD);
		bs.setFirst(tmp);
		
		tmp = new HashSet<Integer>();
		tmp.add(iD);
		bs.setLast(tmp);
		
		return bs;
	}

	@Override
	public void getSimbolosRangos(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> sort,
			SortedSet<Character> sortRango) {
		// TODO Auto-generated method stub
		set.add(_sim);
	}
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) return true;
	     if (!(o instanceof Vacio)) {
	            return false;
	     }
	     return true;
	}

	@Override
	public String getVal() {
		// TODO Auto-generated method stub
		return this._sim;
	}
		
}
