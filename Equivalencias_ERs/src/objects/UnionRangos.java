package objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

import automata.*;
import excepciones.VacioException;

/**
 * Clase envoltoria para la union/o no de rangos
 *
 */
public class UnionRangos extends ExpressionBase {

	private ExpressionBase _e1;
	private String _str;
	private ArrayList<RangoCharacter> _rangos;
	
	/**
	 * Clase constructora por defecto
	 */
	public UnionRangos() {
		super(Tipo.UNIONRANGOS);
	}
	
	/**
	 * Clase constructora
	 * @param str: string que contiene el rango 
	 */
	public UnionRangos(String str) {
		super(Tipo.UNIONRANGOS);
		_str = str;
		_rangos = new ArrayList<RangoCharacter>();
	}
	
	/**
	 * parsear el string, se añaden los puntos de interseccion al set \
	 * antes de eso se interseccionan internamente para evitar repeticiones 
	 * @param ss
	 */
	public void parserRangos(SortedSet<Character> ss) {
		
		char c;
		int i = 0;
		while (i < _str.length()) { // Procesado de String
			c = _str.charAt(i);
			if (i == _str.length()-1 || _str.charAt(i+1) != '-') {
				_rangos.add(new RangoCharacter(c));
			}
			else {
				i +=2;
				if (_str.charAt(i) < c) {
					System.out.println("ER inválido, Rango incorrecto");
					System.exit(0);
				}
				_rangos.add(new RangoCharacter(c ,_str.charAt(i)));
			}
			i++;
		}
		
		selfIntersec(); 					// Intersecciones internas
		for (RangoCharacter rc: _rangos) {  // Añadidos de puntos de interseccion
			ss.add(rc.get_ini());
			ss.add(rc.get_fin());
		}
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
				try {
				_e1 = new Union(_e1, it.next());
				}
				catch(VacioException e) {
					
				}
				
			}
		}
	}
	
	/**
	 * Interseccion de todos los rangos con los puntos de intersecciones dadas \
	 * y convertirlos en las uniones
	 * @param set: se añaden los nuevos "simbolos" al set de simbolos
	 * @param ss: lista de puntos de intersecciones dadas
	 */
	public void intersec(Set<String> set, SortedSet<Character> ss, boolean rango) {
		ArrayList<RangoCharacter> tmp = new ArrayList<RangoCharacter>();
		RangoCharacter rc, rctmp;
		
		Iterator<RangoCharacter> it = _rangos.iterator();
		Iterator<Character> it_c;
		char c;
		
		while (it.hasNext()) {
			rc = it.next();
			
			it_c = ss.iterator();
			do {
				c = it_c.next();
				if (rango && rc.contieneRango(c)) {
					rctmp = rc.interseccion(c);
					tmp.add(rctmp);
					set.add(rctmp._sim);
				} else if (!rango && rc.contiene(c)) {
					rctmp = rc.interseccion(c);
					tmp.add(rctmp);
					set.add(rctmp._sim);
				}
			} while (it_c.hasNext() && rc.mayorQue(c));
			tmp.add(rc);
			set.add(rc._sim);
		}
		
		_rangos = tmp;
		unirRangos();
	}
	
	/**
	 * Quitar repeticiones, unir en caso de intersecciones
	 */
	private void selfIntersec() {
		ArrayList<RangoCharacter> tmp = new ArrayList<RangoCharacter>();
		Iterator<RangoCharacter> it = _rangos.iterator(), ittmp;
		RangoCharacter rc, rctmp;
		boolean existe;
		tmp.add(it.next());
		
		while (it.hasNext()) {
			rc = it.next();
			ittmp = tmp.iterator();
			existe = false;
			
			while(ittmp.hasNext()) {
				rctmp = ittmp.next();
				if(rc.contenida(rctmp)) {
					existe = true;
					break;
				}
				else if (rc.contiene(rctmp)) {
					if (!existe) {
						rctmp = rc;
						existe = true;
					} 
					else 
						ittmp.remove();
				}
				else if (rctmp.isIntersec(rc)) {
					if (!existe) {
						rctmp.union(rc);
						rc = rctmp;
						existe = true;
					} 
					else { 
						rc.union(rctmp);
						ittmp.remove();
					}
				}
			}
			
			if (!existe) tmp.add(rc);
		}
		
		_rangos = tmp;
	}

	@Override
	public String toString() {
		return "[" + _e1.toString() + "]";
	}

	@Override
	public ExpressionBase cloneMe() {
		// TODO Auto-generated method stub
		return new UnionRangos();
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
		// TODO Auto-generated method stub
		return _e1.ThomsonSimplAFN(id);
	}

	@Override
	public boolean equals(Object o) {
		 if (o == this) return true;
	     if (!(o instanceof UnionRangos)) {
	            return false;
	     }
	     UnionRangos t = (UnionRangos) o;
	     return t._e1.equals(this._e1);
	}

}
