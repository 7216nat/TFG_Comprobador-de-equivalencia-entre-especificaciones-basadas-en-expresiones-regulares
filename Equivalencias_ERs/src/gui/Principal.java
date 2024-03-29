package gui;

import javax.swing.JFrame;
import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import analizador.lexico.AnalizadorLexico;
import analizador.sintactico.AnalizadorSintactico;
import control.Controller;
import control.ElementoLista;
import objects.ExpressionBase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JToggleButton;

public class Principal {
	private Controller ctrl;
	private JFrame frmComprobadorEquivalencia;
	private boolean nombre; // true si se ven los nombres, false si se ven las expresiones
	private List<ElementoLista> leng1;
	private List<ElementoLista> leng2;

	/**
	 * Create the application.
	 */
	public Principal(Controller ctrl) {
		this.ctrl = ctrl;
		nombre = true;
		initialize();
		leng1 = new ArrayList<>();
		leng2 = new ArrayList<>();

		frmComprobadorEquivalencia.setVisible(true);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmComprobadorEquivalencia = new JFrame();
		frmComprobadorEquivalencia.setTitle("Comprobador equivalencia");
		frmComprobadorEquivalencia.setBounds(100, 100, 520, 400);
		frmComprobadorEquivalencia.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 6, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		frmComprobadorEquivalencia.getContentPane().setLayout(gridBagLayout);

		JButton helpButton = new JButton("Ayuda");
		GridBagConstraints gbc_helpButton = new GridBagConstraints();
		gbc_helpButton.anchor = GridBagConstraints.NORTHEAST;
		gbc_helpButton.insets = new Insets(0, 0, 5, 5);
		gbc_helpButton.gridx = 3;
		gbc_helpButton.gridy = 0;
		frmComprobadorEquivalencia.getContentPane().add(helpButton, gbc_helpButton);

		JButton cargar1 = new JButton("Cargar lenguaje 1");
		cargar1.setBackground(UIManager.getColor("Button.darkShadow"));
		GridBagConstraints gbc_cargar1 = new GridBagConstraints();
		gbc_cargar1.fill = GridBagConstraints.HORIZONTAL;
		gbc_cargar1.insets = new Insets(0, 0, 5, 5);
		gbc_cargar1.gridx = 1;
		gbc_cargar1.gridy = 1;
		frmComprobadorEquivalencia.getContentPane().add(cargar1, gbc_cargar1);

		JButton cargar2 = new JButton("Cargar lenguaje 2");
		GridBagConstraints gbc_cargar2 = new GridBagConstraints();
		gbc_cargar2.fill = GridBagConstraints.HORIZONTAL;
		gbc_cargar2.insets = new Insets(0, 0, 5, 5);
		gbc_cargar2.gridx = 2;
		gbc_cargar2.gridy = 1;
		frmComprobadorEquivalencia.getContentPane().add(cargar2, gbc_cargar2);

		JList<String> list1 = new JList<String>();
		list1.setVisibleRowCount(5);
		DefaultListModel<String> strList = new DefaultListModel<String>();

		list1.setModel(strList);

		JToggleButton nombreExpr = new JToggleButton("Expresiones");
		GridBagConstraints gbc_nombreExpr = new GridBagConstraints();
		gbc_nombreExpr.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreExpr.insets = new Insets(0, 0, 5, 5);
		gbc_nombreExpr.gridx = 3;
		gbc_nombreExpr.gridy = 1;
		frmComprobadorEquivalencia.getContentPane().add(nombreExpr, gbc_nombreExpr);
		list1.setModel(strList);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 5;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		scrollPane.setViewportView(list1);
		frmComprobadorEquivalencia.getContentPane().add(scrollPane, gbc_scrollPane);

		JList<String> list2 = new JList<String>();
		list2.setVisibleRowCount(5);
		DefaultListModel<String> strList2 = new DefaultListModel<String>();

		list2.setModel(strList2);

		JScrollPane scrollPane2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane2 = new GridBagConstraints();
		gbc_scrollPane2.gridheight = 5;
		gbc_scrollPane2.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane2.gridx = 2;
		gbc_scrollPane2.gridy = 2;
		scrollPane2.setViewportView(list2);
		frmComprobadorEquivalencia.getContentPane().add(scrollPane2, gbc_scrollPane2);
		JTextPane res = new JTextPane();
		res.setEditable(false);

		JComboBox<String> metChoice = new JComboBox<String>();
		GridBagConstraints gbc_metChoice = new GridBagConstraints();
		gbc_metChoice.fill = GridBagConstraints.HORIZONTAL;
		gbc_metChoice.anchor = GridBagConstraints.WEST;
		gbc_metChoice.insets = new Insets(0, 0, 5, 5);
		gbc_metChoice.gridx = 3;
		gbc_metChoice.gridy = 5;
		metChoice.addItem("Seleccionados");
		metChoice.addItem("Todos");
		metChoice.addItem("Uno a uno");

		JComboBox<String> algChoice = new JComboBox<String>();
		GridBagConstraints gbc_algChoice = new GridBagConstraints();
		gbc_algChoice.anchor = GridBagConstraints.WEST;
		gbc_algChoice.fill = GridBagConstraints.HORIZONTAL;
		gbc_algChoice.insets = new Insets(0, 0, 5, 5);
		gbc_algChoice.gridx = 3;
		gbc_algChoice.gridy = 3;
		algChoice.addItem("Thompson");
		algChoice.addItem("Seguidores");
		algChoice.addItem("Derivadas");
		algChoice.addItem("Derivadas parciales");
		algChoice.addItem("Berry-Sethi");

		JLabel algLabel = new JLabel("Algoritmo");
		GridBagConstraints gbc_algLabel = new GridBagConstraints();
		gbc_algLabel.anchor = GridBagConstraints.WEST;
		gbc_algLabel.insets = new Insets(0, 0, 5, 5);
		gbc_algLabel.gridx = 3;
		gbc_algLabel.gridy = 2;
		frmComprobadorEquivalencia.getContentPane().add(algLabel, gbc_algLabel);
		frmComprobadorEquivalencia.getContentPane().add(algChoice, gbc_algChoice);

		JLabel metLabel = new JLabel("M\u00E9todo");
		GridBagConstraints gbc_metLabel = new GridBagConstraints();
		gbc_metLabel.anchor = GridBagConstraints.WEST;
		gbc_metLabel.insets = new Insets(0, 0, 5, 5);
		gbc_metLabel.gridx = 3;
		gbc_metLabel.gridy = 4;
		frmComprobadorEquivalencia.getContentPane().add(metLabel, gbc_metLabel);

		frmComprobadorEquivalencia.getContentPane().add(metChoice, gbc_metChoice);
		JButton EqButton = new JButton("Comprobar");

		GridBagConstraints gbc_EqButton = new GridBagConstraints();
		gbc_EqButton.anchor = GridBagConstraints.SOUTHEAST;
		gbc_EqButton.insets = new Insets(0, 0, 5, 5);
		gbc_EqButton.gridx = 3;
		gbc_EqButton.gridy = 6;
		frmComprobadorEquivalencia.getContentPane().add(EqButton, gbc_EqButton);

		EqButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String metodo = (String) metChoice.getSelectedItem();
				String algoritmo = (String) algChoice.getSelectedItem();

				List<ElementoLista> expr1 = new ArrayList<>();
				List<ElementoLista> expr2 = new ArrayList<>();

				if (metodo.equalsIgnoreCase("Seleccionados")) {
					int[] selInd1 = list1.getSelectedIndices();
					int[] selInd2 = list2.getSelectedIndices();

					if (selInd1.length == 0)
						res.setText("Selecciona el m�todo \"Todos\" o alguna expresi�n en el lenguaje 1");
					else if (selInd2.length == 0)
						res.setText("Selecciona el m�todo \"Todos\" o alguna expresi�n en el lenguaje 2");
					else {
						for (int i : selInd1)
							expr1.add(new ElementoLista(leng1.get(i)));
								
						for (int i : selInd2)
							expr2.add(new ElementoLista(leng2.get(i)));

						String info = mensaje(algoritmo, metodo);
						String resul = ctrl.compEquiv(expr1, expr2, algoritmo, metodo);
						res.setText(resul + "\n" + info);
					}
				} 
				// Si se selecciona Todos
				else {
					for (ElementoLista el : leng1)
						expr1.add(new ElementoLista(el));	
					for (ElementoLista el : leng2)
						expr2.add(new ElementoLista(el));
					String info = mensaje(algoritmo, metodo);
					String resul = ctrl.compEquiv(expr1, expr2, algoritmo, metodo);
					res.setText(resul + "\n" + info);
				}
			}

			private String mensaje(String algoritmo, String metodo) {
				String resul = "Se ha comprobado la uni�n ";
				if (metodo.equalsIgnoreCase("todos"))
					resul += "de todas las expresiones ";
				else if (metodo.equalsIgnoreCase("seleccionados"))
					resul += "de las expresiones seleccionadas ";
				else
					resul = "Se han comprobado todas las expresiones una a una ";

				resul += "con aut�matas creados siguiendo el algoritmo de ";

				if (algoritmo.equalsIgnoreCase("Thompson"))
					resul += "Thompson";
				else if (algoritmo.equalsIgnoreCase("Seguidores"))
					resul += "los seguidores";
				else if (algoritmo.equalsIgnoreCase("derivadas"))
					resul += "las derivadas";
				else if (algoritmo.equalsIgnoreCase("derivadas parciales"))
					resul += "las derivadas parciales";
				else if (algoritmo.equalsIgnoreCase("berry-sethi"))
					resul += "Berry-Sethi";
				return resul;

			}

		});

		JLabel resulLabel = new JLabel("Resultado:");
		GridBagConstraints gbc_resulLabel = new GridBagConstraints();
		gbc_resulLabel.anchor = GridBagConstraints.WEST;
		gbc_resulLabel.insets = new Insets(0, 0, 5, 5);
		gbc_resulLabel.gridx = 1;
		gbc_resulLabel.gridy = 7;
		frmComprobadorEquivalencia.getContentPane().add(resulLabel, gbc_resulLabel);

		JScrollPane scrollPane3 = new JScrollPane(res);
		GridBagConstraints gbc_scrollPane3 = new GridBagConstraints();
		gbc_scrollPane3.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane3.gridwidth = 3;
		gbc_scrollPane3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane3.gridx = 1;
		gbc_scrollPane3.gridy = 8;
		frmComprobadorEquivalencia.getContentPane().add(scrollPane3, gbc_scrollPane3);

		cargar1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser elegir = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt only", "txt");
				elegir.setFileFilter(filter);
				int returnVal = elegir.showOpenDialog(frmComprobadorEquivalencia);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						Reader input = new InputStreamReader(new FileInputStream(elegir.getSelectedFile().getAbsolutePath()));
						AnalizadorLexico alex = new AnalizadorLexico(input);
						AnalizadorSintactico asint = new AnalizadorSintactico(alex);
						
						leng1 = (ArrayList<ElementoLista>) asint.parse().value;
						
						
						strList.clear();
						if (nombre) {
							Iterator<ElementoLista> a1 = leng1.iterator();
							while (a1.hasNext()) {
								strList.addElement(((ElementoLista) a1.next()).getNombre());
							}
						} else {
							Iterator<ElementoLista> a1 = leng1.iterator();
							while (a1.hasNext()) {
								strList.addElement(((ElementoLista) a1.next()).getNombreExpresion());
							}
						}
						res.setText("");

					} catch (FileNotFoundException e1) {
						
					}catch(RuntimeException e1) {
						res.setText(e1.getMessage());
					}
					catch (Exception e1) {
						res.setText(e1.getMessage());
					}

				}
			}

		});
		cargar2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser elegir = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt only", "txt");
				elegir.setFileFilter(filter);
				int returnVal = elegir.showOpenDialog(frmComprobadorEquivalencia);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						Reader input = new InputStreamReader(new FileInputStream(elegir.getSelectedFile().getAbsolutePath()));
						AnalizadorLexico alex = new AnalizadorLexico(input);
						AnalizadorSintactico asint = new AnalizadorSintactico(alex);

						leng2 = (ArrayList<ElementoLista>) asint.parse().value;
						
						strList2.clear();
						if (nombre) {
							Iterator<ElementoLista> a2 = leng2.iterator();
							while (a2.hasNext()) {
								strList2.addElement(((ElementoLista) a2.next()).getNombre());
							}
						} else {
							Iterator<ElementoLista> a2 = leng2.iterator();
							while (a2.hasNext()) {
								strList2.addElement(((ElementoLista) a2.next()).getNombreExpresion());
							}
						}
						res.setText("");

					} catch (FileNotFoundException e1) {

					} catch(RuntimeException e1) {
						res.setText(e1.getMessage());
					}
					catch (Exception e1) {
						res.setText(e1.getMessage());
					}
				}
			}

		});
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ayuda help = new Ayuda();
				help.setVisible(true);
			}

		});
		nombreExpr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selInd1 = list1.getSelectedIndices();
				int[] selInd2 = list2.getSelectedIndices();
				strList.clear();
				strList2.clear();
				if (nombre) {
					nombre = false;
					Iterator<ElementoLista> a1 = leng1.iterator();
					while (a1.hasNext()) {
						strList.addElement(((ElementoLista) a1.next()).getNombreExpresion());
					}
					Iterator<ElementoLista> a2 = leng2.iterator();
					while (a2.hasNext()) {
						strList2.addElement(((ElementoLista) a2.next()).getNombreExpresion());
					}

				} else {
					nombre = true;
					
					Iterator<ElementoLista> a1 = leng1.iterator();
					while (a1.hasNext()) {
						strList.addElement(((ElementoLista) a1.next()).getNombre());
					}
					Iterator<ElementoLista> a2 = leng2.iterator();
					while (a2.hasNext()) {
						strList2.addElement(((ElementoLista) a2.next()).getNombre());
					}
				}
				list1.setSelectedIndices(selInd1);
				list2.setSelectedIndices(selInd2);
			}

		});

	}

}