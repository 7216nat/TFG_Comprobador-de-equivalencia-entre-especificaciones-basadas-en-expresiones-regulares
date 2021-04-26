package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import javax.swing.JTextPane;
import java.awt.Insets;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Ayuda extends JFrame {

	private JPanel contentPane;
	private String infLenguaje;
	private String infPrograma;
	private String info;

	/**
	 * Create the frame.
	 */
	public Ayuda() {
		setTextos();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		contentPane.add(tabbedPane, gbc_tabbedPane);
		
		JScrollPane LengPanel = new JScrollPane();
		LengPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("Lenguaje", null, LengPanel, null);
		
		JTextPane LengText = new JTextPane();
		LengText.setText(infLenguaje);
		LengText.setEditable(false);
		LengPanel.setViewportView(LengText);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("Programa", null, scrollPane_1, null);
		
		JTextPane ProgText = new JTextPane();
		ProgText.setText(infPrograma);
		ProgText.setEditable(false);
		scrollPane_1.setViewportView(ProgText);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("Info", null, scrollPane_2, null);
		
		JTextPane infoText = new JTextPane();
		infoText.setText(info);
		infoText.setEditable(false);
		scrollPane_2.setViewportView(infoText);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
	private void setTextos() {
		infLenguaje = "El lenguaje se abrir� a partir de un archivo de texto con extensi�n \".txt\".\n"
				+ "M�s informaci�n en pr�ximas entregas.";
		infPrograma = "Los lenguajes se cargar�n utilizando los botones \"cargar lenguaje\" sobre las listas. \n"
				+ "Para seleccionar varias expresiones regulares simult�neamente, se deben seleccionar pulsando el bot�n control.\n"
				+ "Para seleccionar varias expresiones en un intervalo, se puede pulsar el bot�n shift.\n"
				+ "El bot�n Expresiones var�a la vista de la lista entre nombres y expresiones regulares.\n"
				+ "En algoritmo se seleccionar� el algoritmo con el que se har� la comprobaci�n de equivalencia.\n"
				+ "En m�todo se seleccionar� el m�todo de selecci�n de las expresiones regulares (m�s en la pesta�a info de ayuda).\n"
				+ "Pulsar equivalencia para que se haga la comprobaci�n y ver el resultado en el cuadro inferior.";
		info = "Algoritmos utilizados para crear los aut�matas:\n"
				+ " � Thompson: se sigue un algoritmo de Thompson + determinaci�n con lambda-transiciones.\n"
				+ " � Seguidores: se siguen el algoritmo de Thompson, se eliminan las lambda-transiciones y luego se sigue el proceso de determinaci�n.\n"
				+ " � Derivadas: se sigue el algoritmo de derivadas.\n"
				+ " � Derivadas parciales: se siguen los algoritmos de derivadas parciales y determinaci�n simult�neamente."
				+ " � Berry-Sethi: se sigue el algoritmo de Berry-Sethi y luego se hace la determinaci�n.\n\n"
				
				+ "M�todos de entrada:\n"
				+ " � Todos: se hace la uni�n de todas las expresiones regulares de cada lenguaje.\nNota: si se elige esta opci�n, se ignoran qu� expresiones regulares se hayan marcado o no.\n"
				+ " � Seleccionados: se hace la uni�n de todas las expresiones regulares marcadas en cada lenguaje.\n"
				+ " � Uno a uno: se compara cada expresi�n del lenguaje 1 con las del lenguaje 2, emparejando las que sean equivalentes. Como resultado, se muestran las parejas desemparejadas si ha quedado alguna.\n\n"
				
				+ "M�todo para la comprobaci�n de equivalencia: Hopcroft-Karp siguiendo independientemente del algoritmo y m�todo de entrada\n";
	}

}