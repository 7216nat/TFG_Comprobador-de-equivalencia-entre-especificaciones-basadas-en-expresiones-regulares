package algoritmo;

public class Equivalencia {
	private boolean eq;
	private String msg;
	
	public Equivalencia() {
		eq = true;
		msg = "Equivalentes";
	}
	public Equivalencia(boolean esEq, String message) {
		eq = esEq;
		msg = message;
	}
	public boolean isEq() {
		return eq;
	}
	public void setEq(boolean eq) {
		this.eq = eq;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
