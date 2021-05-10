package objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import automata.*;

/**
 * Clase envoltoria para la union/o no de rangos
 *
 */
public class UnionRangos extends ExpressionBase {
	
	private ExpressionBase _e1;
	private List<RangoCharacter> _rangos;
	
	/**
	 * Clase constructora por defecto
	 */
	public UnionRangos() {
		this(null);
	}
	
	public UnionRangos(ExpressionBase expressionBase) {
		super(Tipo.UNIONRANGOS);
		_e1 = expressionBase;
		_rangos = new ArrayList<>();
		_rangos.add((RangoCharacter)expressionBase);
	}
	
	/**
	 * Convertir la lista de rangos en la union de ERs
	 */
	public void unirRangos() {
		if (_rangos.size() == 1) {
			_e1 = _rangos.get(0);
		}
		else {
			Iterator<RangoCharacter> it = _rangos.iterator();
			_e1 = it.next();
			while (it.hasNext()) {
				_e1 = new Union(_e1, it.next());
			}
		}
	}
	
	/**
	 * Interseccion de todos los rangos con los puntos de intersecciones dadas \
	 * y convertirlos en las uniones
	 * @param set: se añaden los nuevos "simbolos" al set de simbolos
	 * @param inis: lista de puntos iniciales de intersecciones dadas
	 * @param fins: lista de puntos finales de intersecciones dadas
	 */
	public void intersec(Set<String> set, Set<Character> inis, Set<Character> fins) {
		List<RangoCharacter> tmp = new ArrayList<>();
		RangoCharacter rc, rctmp;
		
		Iterator<RangoCharacter> it = _rangos.iterator();
		Iterator<Character> it_c;
		char c;
		
		while (it.hasNext()) {
			rc = it.next();
			
			it_c = inis.iterator();
			while (it_c.hasNext()) {
				c = it_c.next();
				if (!rc.mayorQue(c))
					break;
				if (rc.contiene(c, true)) {
					rctmp = rc.interseccion(c, true);
					if (rctmp != null) 
						tmp.add(rctmp);
				}
			}
			tmp.add(rc);
			
		}
		_rangos = tmp;
		tmp = new ArrayList<>();
		it = _rangos.iterator();
		while (it.hasNext()) {
			rc = it.next();
			
			it_c = fins.iterator();
			while (it_c.hasNext()) {
				c = it_c.next();
				if (!rc.mayorQue(c))
					break;
				if (rc.contiene(c, false)) {
					rctmp = rc.interseccion(c, false);
					if (rctmp != null) {
						tmp.add(rctmp);
						set.add(rctmp._sim);
					}
				}
			}
			tmp.add(rc);
			set.add(rc._sim);
		}
		
		_rangos = tmp;
		unirRangos();
	}


	@Override
	public AutomataTS ThomsonAFN(IdEstado id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AutomataTS ThomsonSimplAFN(IdEstado id) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public BerrySethiNode createBerrySethiNode(Map<Integer, BerrySethiNode> map, IdEstado id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void getSimbolosRangos(Set<String> set, List<UnionRangos> array, Set<Character> inis, Set<Character> fins) {
		_e1.getSimbolosRangos(set, array, inis, fins);
		array.add(this);
	}
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
			 return true;
		 }
	     if (!(o instanceof UnionRangos)) {
	            return false;
	     }
	     UnionRangos t = (UnionRangos) o;
	     return t._e1.equals(this._e1);
	}
	
	@Override
	public int hashCode() {
		return _e1.hashCode() + this.getType().getValor();
	}
	
	@Override
	public ExpressionBase buildTreeDefinitivo() {
		_e1 = _e1.buildTreeDefinitivo();
		return _e1;
	}

	@Override
	public String getVal() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ExpressionBase derivada(String sim) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<ExpressionBase> derivadaParcial(String sim) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ExpressionBase copy() {
		return new UnionRangos(_e1.copy());
	}
}
