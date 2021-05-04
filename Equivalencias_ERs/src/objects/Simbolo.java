package objects;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import automata.*;

// Comentarios en ExpressionBase
public class Simbolo extends Lenguaje implements Comparable<Simbolo> {

	/**
	 * construtora por defecto
	 */
	public Simbolo() {
		super(null, Tipo.SIMB);
	}

	/**
	 * constructora
	 * 
	 * @param er: simbolo
	 */
	public Simbolo(String er) {
		super(er, Tipo.SIMB);
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
	public int hashCode() {
		return _sim.hashCode();
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
	public BerrySethiNode createBerrySethiNode(Map<Integer, BerrySethiNode> map, IdEstado id) {
		Set<Integer> tmp = new HashSet<>();
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
		
		map.put(iD, bs);
		return bs;
	}

	@Override
	public int compareTo(Simbolo o) {
		return _sim.compareTo(o._sim);
	}

	@Override
	public void getSimbolosRangos(Set<String> set, List<UnionRangos> array, Set<Character> inis, Set<Character> fins) {
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
	public Set<ExpressionBase> derivadaParcial(String sim) {
		Set<ExpressionBase> ret = new HashSet<>();
		if(this._sim.equals(sim))
				ret.add(new Lambdaa());
		else ret.add(new Vacio());
		return ret;
	}
}
