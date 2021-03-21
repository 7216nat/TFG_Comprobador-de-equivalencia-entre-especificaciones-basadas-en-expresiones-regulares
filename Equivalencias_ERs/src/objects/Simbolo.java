package objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.regex.Pattern;

import automata.*;

// Comentarios en ExpressionBase
public class Simbolo extends Lenguaje implements Comparable<Simbolo> {

	private static final String _regex = "\\w";

	/**
	 * construtora por defecto
	 */
	public Simbolo() {
		super(null, null, Tipo.SIMB);
	}

	/**
	 * constructora
	 * 
	 * @param er
	 */
	public Simbolo(String er) {
		super(er, null, Tipo.SIMB);
	}

	public Simbolo(ExpressionBase padre, String er) {
		super(er, padre, Tipo.SIMB);
	}

	@Override
	public ExpressionBase cloneMe() {
		return new Simbolo();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Simbolo)) {
			return false;
		}
		Simbolo sim = (Simbolo) o;
		return _sim.equals(sim._sim);
	}

	@Override
	public boolean match(String string) {
		return Pattern.matches(_regex, string);
	}

	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		int ini = id.nextId(); 
		int acept = id.nextId();
		AutomataTS aut = new AutomataTS(ini, acept);
		aut.addTransicion(ini, acept, _sim);
		return aut;
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		int ini = id.nextId(); 
		int acept = id.nextId();
		AutomataTS aut = new AutomataTS(ini, acept);
		aut.addTransicion(ini, acept, _sim);
		return aut;
	}
	
	@Override
	public BerrySethiNode createBerrySethiNode(IdEstado id) {
		HashSet<Integer> tmp = new HashSet<>();
		BerrySethiNode bs = new BerrySethiNode();
		
		bs.setEmpty(false);
		bs.setSim(_sim);
		bs.setTipo(getType());
		
		int iD = id.nextId(); 
		tmp.add(iD);
		bs.setFirst(tmp);
		
		tmp = new HashSet<>();
		tmp.add(iD);
		bs.setLast(tmp);
		
		return bs;
	}

	@Override
	public int compareTo(Simbolo o) {
		return _sim.compareTo(o._sim);
	}

	@Override
	public void getSimbolosRangos(Set<String> set, ArrayList<UnionRangos> array, SortedSet<Character> inis,
			SortedSet<Character> fins) {
		set.add(_sim);
		inis.add(_sim.charAt(0));
		fins.add(_sim.charAt(0));
	}

	public boolean contiene(String sim) {
		return (sim.equals(this._sim));
	}

	@Override
	public String getVal() {
		return this._sim;
	}
	
	@Override
	public ExpressionBase derivada(String sim) {
		if(sim.equals(this._sim))
			return new Lambdaa();
		else
			return new Vacio();
	}
	@Override
	public HashSet<ExpressionBase> derivadaParcial(String sim) {
		HashSet<ExpressionBase> ret = new HashSet<>();
		if(this._sim.equals(sim))
				ret.add(new Lambdaa());
		else ret.add(new Vacio());
		return ret;
	}
}
