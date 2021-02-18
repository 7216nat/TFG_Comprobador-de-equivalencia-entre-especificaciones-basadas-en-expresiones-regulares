package excepciones;

public class LambdaException extends Exception {
	public LambdaException() {
		super("Nieto lambda");
	}
	
	public LambdaException(String s) {
		super(s);
	}
}
