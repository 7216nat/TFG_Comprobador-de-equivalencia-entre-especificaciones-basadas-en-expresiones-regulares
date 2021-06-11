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
		this.setTitle("Ayuda");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
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
		infLenguaje = "El lenguaje se extraer� de un archivo de texto con extensi�n \".txt\".\n"
				+ "El lenguaje tendr� una secci�n de auxiliares y una de definiciones (siempre en ese orden).\n"
				+ "Los auxiliares se definir�n como \" aux <clave> = expresion_regular\", en clave se escribir� el identificador del auxiliar "
				+ "y en expresion_regular se anota la expresi�n que define el auxiliar.\n"
				+ "Las definiciones de definir�n como \" def <clave> expresion_regular\".\n"
				+ "Tanto en los auxiliares como en las definiciones se podr�n escribir claves definidas en los auxiliares ya definidos anteriormente, pero no en las definiciones.\n"
				+ "En la aplicaci�n no podr� verse la definici�n de los auxiliares, tan solo de las definiciones, aquellas partes que se definan como auxiliares, se mostrar� la expresi�n del auxiliar.\n\n"
				+ "Las expresiones regulares se escribir�n utilizando los caracteres:\n"
				+ " � & para una cadena vac�a.\n"
				+ " � % para el lenguaje vac�o.\n"
				+ " � | para la uni�n \n"
				+ " � * para el cierre de Kleen.\n"
				+ " � + para el cierre de Kleen positivo.\n"
				+ " � ? para la posibilidad.\n"
				+ " � Dos s�mbolos seguidos implica la concatenaci�n entre ambos lados\n"
				+ " � ( y ) para encerrar una parte de una expresi�n.\n"
				+ " � [rangos] para rangos de s�mbolos.\n"
				+ "Entre los corchetes se podr�n definir varios rangos separados por coma, y los rangos se definir�n como a-b, siendo a el primero elemento del rango y b el segundo, a siempre debe ser menor que b.\n"
				+ "Si se quiere escribir, como parte del lenguaje, un s�mbolo del metalenguaje, deber� escribirse con la barra de escape delante, es decir, para a�adir \"(\" a la expresi�n como un s�mbolo, habr� que escribir \"\\(\".\n"
				+ "Los espacios en blanco, retornos de carro, tabuladores y saltos de l�nea se ignoran, para a�adirlos como s�mbolo en una expresi�n regular, hay que a�adirlos como "
				+ "\\b, \\r, \\t o \\n \n"
				+ "Todos los caracteres a los que hay que preceder de una barra de escape para que vayan dentro del lenguaje: &, %, def, aux, =, <, >, |, *, +, ?, (, ), [, ], - y ,";
		
		infPrograma = "Los lenguajes se cargar�n utilizando los botones \"cargar lenguaje\" sobre las listas. \n"
				+ "Para seleccionar varias expresiones regulares simult�neamente, se deben seleccionar pulsando el bot�n control.\n"
				+ "Para seleccionar varias expresiones en un intervalo, se puede pulsar el bot�n shift.\n"
				+ "El bot�n Expresiones var�a la vista de la lista entre nombres y expresiones regulares.\n"
				+ "En algoritmo se seleccionar� el algoritmo con el que se har� la comprobaci�n de equivalencia.\n"
				+ "En m�todo se seleccionar� el m�todo de selecci�n de las expresiones regulares (m�s en la pesta�a info de ayuda).\n"
				+ "Pulsar equivalencia para que se haga la comprobaci�n y ver el resultado en el cuadro inferior.\n"
				+ "Posibles resultados:\n"
				+ "Si son equivalentes: el m�todo Seleccionados mostrar� equivalentes si solo se ha seleccionado una expresi�n de cada lenguaje o posiblemente equivalentes si se seleccionaron m�s;"
				+ " el m�todo Todos devolver� posiblemente equivalentes y el m�todo Uno a Uno mostrar� la lista de parejas equivalentes de cada lenguaje y la lista de expresiones sin pareja.\n"
				+ "Si no son equivalentes: cualquiera de los m�todos mostrar� una cadena de ejemplo que es aceptada o puede crearse por uno de los lenguajes y no por el otro.\n\n"
				+ "\"Posiblemente equivalentes\" significa que la comprobaci�n de equivalencia entre la uni�n de todas las definiciones de un lenguaje con la del otro ha resultado positiva, pero esa uni�n puede dar lugar a diferentes lenguajes, la forma m�s segura de ver la equivalencia es usando el m�todo \"Uno a uno \".";
		info = "Secuencia de algoritmos utilizados para crear los aut�matas y hacer la comprobaci�n de equivalencia:\\n"
				+ " � Thompson: se sigue un algoritmo de Thompson + determinaci�n con lambda-transiciones.\n"
				+ " � Seguidores: se siguen el algoritmo de Thompson, se eliminan las lambda-transiciones y luego se sigue el proceso de determinaci�n.\n"
				+ " � Derivadas: se sigue el algoritmo de derivadas.\n"
				+ " � Derivadas parciales: se siguen los algoritmos de derivadas parciales y determinaci�n simult�neamente.\n"
				+ " � Berry-Sethi: se sigue el algoritmo de Berry-Sethi y luego se hace la determinaci�n.\n\n"
				
				+ "M�todos de entrada:\n"
				+ " � Todos: se hace la uni�n de todas las expresiones regulares de cada lenguaje.\nNota: si se elige esta opci�n, se ignoran qu� expresiones regulares se han marcado.\n"
				+ " � Seleccionados: se hace la uni�n de todas las expresiones regulares marcadas en cada lenguaje.\n"
				+ " � Uno a uno: se compara cada expresi�n del lenguaje 1 con las del lenguaje 2, emparejando las que sean equivalentes. Como resultado, "
				+ "se muestran las parejas de expresiones equivalentes y las definiciones que no han podido emparejarse.\n\n"
				
				+ "M�todo para la comprobaci�n de equivalencia: Hopcroft-Karp siguiendo independientemente del algoritmo y m�todo de entrada\n";
	}

}
