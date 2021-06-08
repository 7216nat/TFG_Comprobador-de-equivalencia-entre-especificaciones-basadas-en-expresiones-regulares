package main;

import java.awt.EventQueue;
import control.Controller;
import gui.Principal;;

public class Main {

	// Test paso de String a expresion regular
	public static void main(String[] args) {
		
		Controller ctrl = new Controller();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Principal ventana = new Principal(ctrl);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	
}
