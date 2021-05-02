package gui;

import objects.ExpressionBase;

public class ElementoLista {
	private String nombre; 
	private ExpressionBase expresion;
	
	public ElementoLista(String name, ExpressionBase exp) {
		nombre = name;
		expresion = exp;
	}
	
	public String getNombre() {
		return nombre;
	}
	public String getNombreExpresion() {
		return expresion.toString();
	}
	public ExpressionBase getExpresion() {
		return expresion;
	}
}
