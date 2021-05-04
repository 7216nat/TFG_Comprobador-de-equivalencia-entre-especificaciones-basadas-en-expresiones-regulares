package gui;

import objects.ExpressionBase;

public class ElementoLista {
	private String nombre; 
	private ExpressionBase expresion;
	private String expresionInterfaz;
	
	public ElementoLista(String name, ExpressionBase exp, String expVista) {
		nombre = name;
		expresion = exp;
		expresionInterfaz = expVista;
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
