package objects;

import java.util.ArrayList;
import java.util.Set;
import java.util.SortedSet;

import automata.*;

public class LambdaExp extends ExpressionBase {
	
	private static final String _regex = "?";
	private static final String lambdaExp = "?";
	private ExpressionBase _e1;

	public LambdaExp() {
		super(lambdaExp, null, Tipo.LAMBDA);
	}

	public LambdaExp(ExpressionBase e1) {
		super(lambdaExp, null, Tipo.LAMBDA);
		_e1 = new Union(e1, new Lambdaa());
		_e1.setPadre(this);
	}
	
	public LambdaExp(ExpressionBase padre, ExpressionBase e1) {
		super(lambdaExp, padre, Tipo.LAMBDA);
		_e1 = new Union(e1, new Lambdaa());
	}

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

	@Override
	public void getSimbolosRangos(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> inis, SortedSet<Character> fins) {
		// TODO Auto-generated method stub
		_e1.getSimbolosRangos(set, array, inis, fins);
	}

}
