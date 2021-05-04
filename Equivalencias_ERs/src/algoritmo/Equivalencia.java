package algoritmo;

public class Equivalencia {
	private boolean eq;
	private String msg;
	
	/**
	 * Construtora por defecto: Equivalentes
	 */
	public Equivalencia() {
		eq = true;
		msg = "Equivalentes";
	}
	
	/**
	 * Constructora
	 * @param esEq: bool equivalente
	 * @param message: mensaje a mostrar
	 */
	public Equivalencia(boolean esEq, String message) {
		eq = esEq;
		msg = message;
	}
	
	/**
	 * equivalente
	 * @return: boolean
	 */
	public boolean isEq() {
		return eq;
	}
	public void setEq(boolean eq) {
		this.eq = eq;
	}
	
	/**
	 * get mensaje
	 * @return: String
	 */
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
