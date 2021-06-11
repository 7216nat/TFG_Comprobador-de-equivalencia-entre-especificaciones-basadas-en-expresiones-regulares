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
		infLenguaje = "El lenguaje se extraerá de un archivo de texto con extensión \".txt\".\n"
				+ "El lenguaje tendrá una sección de auxiliares y una de definiciones (siempre en ese orden).\n"
				+ "Los auxiliares se definirán como \" aux <clave> = expresion_regular\", en clave se escribirá el identificador del auxiliar "
				+ "y en expresion_regular se anota la expresión que define el auxiliar.\n"
				+ "Las definiciones de definirán como \" def <clave> expresion_regular\".\n"
				+ "Tanto en los auxiliares como en las definiciones se podrán escribir claves definidas en los auxiliares ya definidos anteriormente, pero no en las definiciones.\n"
				+ "En la aplicación no podrá verse la definición de los auxiliares, tan solo de las definiciones, aquellas partes que se definan como auxiliares, se mostrará la expresión del auxiliar.\n\n"
				+ "Las expresiones regulares se escribirán utilizando los caracteres:\n"
				+ " · & para una cadena vacía.\n"
				+ " · % para el lenguaje vacío.\n"
				+ " · | para la unión \n"
				+ " · * para el cierre de Kleen.\n"
				+ " · + para el cierre de Kleen positivo.\n"
				+ " · ? para la posibilidad.\n"
				+ " · Dos símbolos seguidos implica la concatenación entre ambos lados\n"
				+ " · ( y ) para encerrar una parte de una expresión.\n"
				+ " · [rangos] para rangos de símbolos.\n"
				+ "Entre los corchetes se podrán definir varios rangos separados por coma, y los rangos se definirán como a-b, siendo a el primero elemento del rango y b el segundo, a siempre debe ser menor que b.\n"
				+ "Si se quiere escribir, como parte del lenguaje, un símbolo del metalenguaje, deberá escribirse con la barra de escape delante, es decir, para añadir \"(\" a la expresión como un símbolo, habrá que escribir \"\\(\".\n"
				+ "Los espacios en blanco, retornos de carro, tabuladores y saltos de línea se ignoran, para añadirlos como símbolo en una expresión regular, hay que añadirlos como "
				+ "\\b, \\r, \\t o \\n \n"
				+ "Todos los caracteres a los que hay que preceder de una barra de escape para que vayan dentro del lenguaje: &, %, def, aux, =, <, >, |, *, +, ?, (, ), [, ], - y ,";
		
		infPrograma = "Los lenguajes se cargarán utilizando los botones \"cargar lenguaje\" sobre las listas. \n"
				+ "Para seleccionar varias expresiones regulares simultáneamente, se deben seleccionar pulsando el botón control.\n"
				+ "Para seleccionar varias expresiones en un intervalo, se puede pulsar el botón shift.\n"
				+ "El botón Expresiones varía la vista de la lista entre nombres y expresiones regulares.\n"
				+ "En algoritmo se seleccionará el algoritmo con el que se hará la comprobación de equivalencia.\n"
				+ "En método se seleccionará el método de selección de las expresiones regulares (más en la pestaña info de ayuda).\n"
				+ "Pulsar equivalencia para que se haga la comprobación y ver el resultado en el cuadro inferior.\n"
				+ "Posibles resultados:\n"
				+ "Si son equivalentes: el método Seleccionados mostrará equivalentes si solo se ha seleccionado una expresión de cada lenguaje o posiblemente equivalentes si se seleccionaron más;"
				+ " el método Todos devolverá posiblemente equivalentes y el método Uno a Uno mostrará la lista de parejas equivalentes de cada lenguaje y la lista de expresiones sin pareja.\n"
				+ "Si no son equivalentes: cualquiera de los métodos mostrará una cadena de ejemplo que es aceptada o puede crearse por uno de los lenguajes y no por el otro.\n\n"
				+ "\"Posiblemente equivalentes\" significa que la comprobación de equivalencia entre la unión de todas las definiciones de un lenguaje con la del otro ha resultado positiva, pero esa unión puede dar lugar a diferentes lenguajes, la forma más segura de ver la equivalencia es usando el método \"Uno a uno \".";
		info = "Secuencia de algoritmos utilizados para crear los autómatas y hacer la comprobación de equivalencia:\\n"
				+ " · Thompson: se sigue un algoritmo de Thompson + determinación con lambda-transiciones.\n"
				+ " · Seguidores: se siguen el algoritmo de Thompson, se eliminan las lambda-transiciones y luego se sigue el proceso de determinación.\n"
				+ " · Derivadas: se sigue el algoritmo de derivadas.\n"
				+ " · Derivadas parciales: se siguen los algoritmos de derivadas parciales y determinación simultáneamente.\n"
				+ " · Berry-Sethi: se sigue el algoritmo de Berry-Sethi y luego se hace la determinación.\n\n"
				
				+ "Métodos de entrada:\n"
				+ " · Todos: se hace la unión de todas las expresiones regulares de cada lenguaje.\nNota: si se elige esta opción, se ignoran qué expresiones regulares se han marcado.\n"
				+ " · Seleccionados: se hace la unión de todas las expresiones regulares marcadas en cada lenguaje.\n"
				+ " · Uno a uno: se compara cada expresión del lenguaje 1 con las del lenguaje 2, emparejando las que sean equivalentes. Como resultado, "
				+ "se muestran las parejas de expresiones equivalentes y las definiciones que no han podido emparejarse.\n\n"
				
				+ "Método para la comprobación de equivalencia: Hopcroft-Karp siguiendo independientemente del algoritmo y método de entrada\n";
	}

}
