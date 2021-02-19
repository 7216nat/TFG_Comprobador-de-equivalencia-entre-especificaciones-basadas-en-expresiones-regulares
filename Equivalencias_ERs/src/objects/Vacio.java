package objects;

import java.util.ArrayList;
import java.util.Set;
import java.util.SortedSet;
import java.util.regex.Pattern;

import automata.*;

public class Vacio extends ExpressionBase {

	private static final String _regex = "%";
	private static final String CojVacio = "%";
	
	public Vacio() {
		super(CojVacio, null, Tipo.VACIO);
		// TODO Auto-generated constructor stub
	}
	
	public Vacio(ExpressionBase padre) {
		super(CojVacio, padre, Tipo.LAMBDA);
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
	public void getSimbolosRangos(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> sort,
			SortedSet<Character> sortRango) {
		// TODO Auto-generated method stub
		set.add(_sim);
		sort.add('&');
	}

}
