package excepciones;

public class VacioException extends Exception {
	public VacioException() {
		super("Nieto vacio");
	}
	
	public VacioException(String s) {
		super(s);
	}
}
