package objects;

import java.util.ArrayList;
import java.util.Iterator;

import automata.*;

public class UnionRangos extends ExpressionBase {

	private ExpressionBase _e1;
	private String _str;
	private ArrayList<RangoCharacter> _rangos;

	public UnionRangos() {}
	
	public UnionRangos(String str) {
		_str = str;
		_rangos = new ArrayList<RangoCharacter>();
		parserRangos();
		selfIntersec();
	}
	
	public void parserRangos() {
		
		char c = _str.charAt(0);
		int i = 0;
		while (i < _str.length()) {
			c = _str.charAt(i);
			if (i==_str.length()-1 || _str.charAt(i+1) != '-')
				_rangos.add(new RangoCharacter(c));
			else {
				i +=2;
				if (_str.charAt(i) < c) {
					System.out.println("ER inválido");
					System.exit(0);
				}
				_rangos.add(new RangoCharacter(c ,_str.charAt(i)));
			}
			i++;
		}
	}
	
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
	
	public void intersec(UnionRangos ur) {
		ArrayList<RangoCharacter> tmp = new ArrayList<RangoCharacter>();
		RangoCharacter rc, rctmp;
		
		for(int i= 0; i < ur._rangos.size(); i++) {
			rc = ur._rangos.get(i);
			for(int j = 0; j < _rangos.size(); j++) {
				rctmp = _rangos.get(j);
				if (rc.contenida(rctmp)) {
					tmp.add(rctmp.interseccion(rc));
					tmp.add(rctmp.interseccion(rc));
				}
				else if (rc.isIntersec(rctmp)) {
					tmp.add(rctmp.interseccion(rc));
				}
			}
			_rangos.addAll(tmp);
			tmp.clear();	
		}
	}
	
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

}
