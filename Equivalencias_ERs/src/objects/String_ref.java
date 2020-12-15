package objects;

/**
 *  En sustitucion de String 
 *  para conseguir un efecto de paso por referencia 
 */
public class String_ref {
	
	private String _er;
	
	/**
	 * constructora
	 * @param er
	 */
	public String_ref(String er) {
		set_er(er);
	}
	/**
	 * getter
	 * @return
	 */
	public String get_er() {
		return _er;
	}
	/**
	 * setter
	 * @param _er
	 */
	public void set_er(String _er) {
		this._er = _er;
	}
	/**
	 * get longitud del string
	 */
	public int get_len() {
		return _er.length();
	}
	/**
	 * la primera letra
	 * @return
	 */
	public char ini() {
		return _er.charAt(0);
	}
	/**
	 * truncar lon primeras letras
	 * @param lon
	 * @return
	 */
	
	public String sub(int lon) {
		_er = _er.substring(lon);
		return _er;
	}
}
