package control;

import objects.ExpressionBase;

public class ElementoLista {
	protected String nombre; 
	protected ExpressionBase expresion;
	protected String expresionInterfaz;
	
	public ElementoLista(String name, ExpressionBase exp, String expVista) {
		nombre = name;
		expresion = exp;
		expresionInterfaz = expVista;
	}
	
	public ElementoLista (ElementoLista el) {
		nombre = el.nombre;
		expresion = el.expresion.copy();
		expresionInterfaz = el.expresionInterfaz;
	}
	/**
	 * 
	 * @return la clave de la expresion
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * 
	 * @return la expresion como string para mostrar en la lista
	 */
	public String getNombreExpresion() {
		return expresionInterfaz;
	}
	/**
	 * @return expresión regular para enseñar al usuario
	 */
	public ExpressionBase getExpresion() {
		return expresion;
	}
}
