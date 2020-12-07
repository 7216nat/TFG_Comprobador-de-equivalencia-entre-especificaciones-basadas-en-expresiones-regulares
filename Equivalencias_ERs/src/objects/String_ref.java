package objects;

public class String_ref {
	
	/* En sustitucion de String */
	/* para conseguir un efecto de paso por referencia */
	
	private String _er;
	public String_ref(String er) {
		set_er(er);
	}
	// getter
	public String get_er() {
		return _er;
	}
	// setter
	public void set_er(String _er) {
		this._er = _er;
	}
	// get longitud del string
	public int get_len() {
		return _er.length();
	}
	// la primera letra
	public char ini() {
		return _er.charAt(0);
	}
	// truncar lon primeras letras
	public String sub(int lon) {
		_er = _er.substring(lon);
		return _er;
	}
}
