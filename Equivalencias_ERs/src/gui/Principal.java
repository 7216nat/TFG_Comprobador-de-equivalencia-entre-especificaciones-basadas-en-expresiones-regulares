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

import control.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class Principal {
	private Controller ctrl;
	private JFrame frmComprobadorEquivalencia;

	/**
	 * Create the application.
	 */
	public Principal(Controller ctrl) {
		this.ctrl = ctrl;
		initialize();

		frmComprobadorEquivalencia.setVisible(true);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmComprobadorEquivalencia = new JFrame();
		frmComprobadorEquivalencia.setTitle("Comprobador equivalencia");
		frmComprobadorEquivalencia.setBounds(100, 100, 520, 314);
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
		strList.add(0, "0|1");
		strList.add(1, "0*");
		strList.add(2, "(0*1*)*");
		strList.add(3, "(01|0)*0");
		strList.add(4, "(a|b)*");
		strList.add(5, "b*a*|a*b*");
		strList.add(6, "(cb*c|cb*b)*");
		strList.add(7, "[a-cde-tx]*");
		strList.add(8, "[a-c]|[d-h]");
		strList.add(9, "%|gh");
		
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
		strList2.add(0, "01");
		strList2.add(1, "0");
		strList2.add(2, "%");
		strList2.add(3, "0(10|0)*");
		strList2.add(4, "a*(ba*)*");
		strList2.add(5, "a*|b*");
		strList2.add(6, "(cc)*|(cc)*(cb)(b|c)*");
		strList2.add(7, "[a-bcd-tx]*");
		strList2.add(8, "[a-c]|[d-h]");
		strList2.add(9, "%abc|gh");
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
						metChoice.addItem("Todos");
						metChoice.addItem("Seleccionados");
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
									public void actionPerformed (ActionEvent e) {				
						
										
										String metodo = (String) metChoice.getSelectedItem();
										System.out.println(metodo);
										String algoritmo = (String) algChoice.getSelectedItem();
										System.out.println(algoritmo);
										
										List<String> expr1 = new ArrayList<>();
										List<String> expr2 = new ArrayList<>();
										
										if (!metodo.equalsIgnoreCase("Todos")) {
											int[] selInd1 = list1.getSelectedIndices();
											int[] selInd2 = list2.getSelectedIndices();
											
											if (selInd1.length == 0)
												res.setText("Selecciona el método \"Todos\" o alguna expresión en el lenguaje 1");
											else if (selInd2.length == 0)
												res.setText("Selecciona el método \"Todos\" o alguna expresión en el lenguaje 2");
											else {
												for (int i : selInd1) {
													String aux = list1.getModel().getElementAt(i);
													expr1.add(aux);
													System.out.println(aux);
												}
												for (int i : selInd2) {
													String aux = list2.getModel().getElementAt(i);
													expr2.add(aux);
													System.out.println(aux);
												}
												String info = mensaje(algoritmo, metodo);
												String resul = ctrl.compEquiv(expr1, expr2, algoritmo, metodo);
												res.setText(resul + "\n" + info);
											}
										} else {
											for (int i = 0; i < list1.getModel().getSize(); i++)
												expr1.add(list1.getModel().getElementAt(i));
											for (int i = 0; i < list2.getModel().getSize(); i++)
												expr2.add(list2.getModel().getElementAt(i));
											String info = mensaje(algoritmo, metodo);
											String resul = ctrl.compEquiv(expr1, expr2, algoritmo, metodo);
											res.setText(resul + "\n" + info);
										}
									}
									
									private String mensaje(String algoritmo, String metodo) {
										String resul = "Se ha comprobado la unión ";
										if (metodo.equalsIgnoreCase("todos"))
											resul += "de todas las expresiones ";
										else if (metodo.equalsIgnoreCase("seleccionados"))
											resul += "de las expresiones seleccionadas ";
										else
											resul = "Se han comprobado todas las expresiones una a una ";
										
										resul += "con autómatas creados siguiendo el algoritmo de ";
										
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
			public void actionPerformed (ActionEvent e) {				
				JFileChooser elegir = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt only", "txt");
				elegir.setFileFilter(filter);
				int returnVal = elegir.showOpenDialog(frmComprobadorEquivalencia);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You choose" + elegir.getSelectedFile().getName());
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
					System.out.println("You choose" + elegir.getSelectedFile().getName());
				}
			}

		});
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ayuda help = new Ayuda();
				help.setVisible(true);
			}

		});

	}

}