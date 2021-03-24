package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Choice;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;
import java.awt.Window.Type;
import javax.swing.UIManager;

import control.Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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

		JLabel algLabel = new JLabel("Algoritmo");
		GridBagConstraints gbc_algLabel = new GridBagConstraints();
		gbc_algLabel.anchor = GridBagConstraints.WEST;
		gbc_algLabel.insets = new Insets(0, 0, 5, 5);
		gbc_algLabel.gridx = 3;
		gbc_algLabel.gridy = 1;
		frmComprobadorEquivalencia.getContentPane().add(algLabel, gbc_algLabel);

		JList list1 = new JList();
		list1.setVisibleRowCount(5);
		DefaultListModel leng1 = new DefaultListModel();
		leng1.add(0, "abc");
		leng1.add(1, "ab");
		leng1.add(2, "cd");
		leng1.add(3, "a|b");
		leng1.add(4, "a*");
		leng1.add(5, "a*b");
		leng1.add(6, "bc");
		leng1.add(7, "ab");
		list1.setModel(leng1);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 5;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		scrollPane.setViewportView(list1);
		frmComprobadorEquivalencia.getContentPane().add(scrollPane, gbc_scrollPane);

		JList list2 = new JList();
		list2.setVisibleRowCount(5);
		DefaultListModel leng2 = new DefaultListModel();
		leng2.add(0, "ab");
		leng2.add(1, "bc");
		leng2.add(2, "cd");
		leng2.add(3, "a*");
		leng2.add(4, "a?");
		list2.setModel(leng2);

		JScrollPane scrollPane2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane2 = new GridBagConstraints();
		gbc_scrollPane2.gridheight = 5;
		gbc_scrollPane2.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane2.gridx = 2;
		gbc_scrollPane2.gridy = 2;
		scrollPane2.setViewportView(list2);
		frmComprobadorEquivalencia.getContentPane().add(scrollPane2, gbc_scrollPane2);

		JComboBox algChoice = new JComboBox();
		GridBagConstraints gbc_algChoice = new GridBagConstraints();
		gbc_algChoice.anchor = GridBagConstraints.WEST;
		gbc_algChoice.fill = GridBagConstraints.HORIZONTAL;
		gbc_algChoice.insets = new Insets(0, 0, 5, 5);
		gbc_algChoice.gridx = 3;
		gbc_algChoice.gridy = 2;
		algChoice.addItem("Thompson");
		algChoice.addItem("Seguidores");
		algChoice.addItem("Derivadas");
		algChoice.addItem("Derivadas parciales");
		algChoice.addItem("Berry-Sethi");
		frmComprobadorEquivalencia.getContentPane().add(algChoice, gbc_algChoice);

		JLabel metLabel = new JLabel("M\u00E9todo");
		GridBagConstraints gbc_metLabel = new GridBagConstraints();
		gbc_metLabel.anchor = GridBagConstraints.WEST;
		gbc_metLabel.insets = new Insets(0, 0, 5, 5);
		gbc_metLabel.gridx = 3;
		gbc_metLabel.gridy = 3;
		frmComprobadorEquivalencia.getContentPane().add(metLabel, gbc_metLabel);

		JComboBox metChoice = new JComboBox();
		GridBagConstraints gbc_metChoice = new GridBagConstraints();
		gbc_metChoice.fill = GridBagConstraints.HORIZONTAL;
		gbc_metChoice.anchor = GridBagConstraints.WEST;
		gbc_metChoice.insets = new Insets(0, 0, 5, 5);
		gbc_metChoice.gridx = 3;
		gbc_metChoice.gridy = 4;
		metChoice.addItem("Todos");
		metChoice.addItem("Seleccionados");
		metChoice.addItem("Uno a uno");

		frmComprobadorEquivalencia.getContentPane().add(metChoice, gbc_metChoice);

		JButton EqButton = new JButton("Comprobar");

		GridBagConstraints gbc_EqButton = new GridBagConstraints();
		gbc_EqButton.anchor = GridBagConstraints.SOUTHEAST;
		gbc_EqButton.insets = new Insets(0, 0, 5, 5);
		gbc_EqButton.gridx = 3;
		gbc_EqButton.gridy = 5;
		frmComprobadorEquivalencia.getContentPane().add(EqButton, gbc_EqButton);

		JLabel resulLabel = new JLabel("Resultado:");
		GridBagConstraints gbc_resulLabel = new GridBagConstraints();
		gbc_resulLabel.anchor = GridBagConstraints.WEST;
		gbc_resulLabel.insets = new Insets(0, 0, 5, 5);
		gbc_resulLabel.gridx = 1;
		gbc_resulLabel.gridy = 7;
		frmComprobadorEquivalencia.getContentPane().add(resulLabel, gbc_resulLabel);

		JTextPane res = new JTextPane();

		JScrollPane scrollPane3 = new JScrollPane(res);
		GridBagConstraints gbc_scrollPane3 = new GridBagConstraints();
		gbc_scrollPane3.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane3.gridwidth = 3;
		gbc_scrollPane3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane3.gridx = 1;
		gbc_scrollPane3.gridy = 8;
		frmComprobadorEquivalencia.getContentPane().add(scrollPane3, gbc_scrollPane3);

		EqButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String metodo = (String) metChoice.getSelectedItem();
				System.out.println(metodo);
				String algoritmo = (String) algChoice.getSelectedItem();
				System.out.println(algoritmo);
				int[] selInd1 = list1.getSelectedIndices();
				ArrayList<String> expr1 = new ArrayList<String>();
				for (int i : selInd1) {
					String aux = list1.getModel().getElementAt(i).toString();
					expr1.add(aux);
					System.out.println(aux);
				}
				int[] selInd2 = list2.getSelectedIndices();
				ArrayList<String> expr2 = new ArrayList<String>();
				for (int i : selInd2) {
					String aux = list2.getModel().getElementAt(i).toString();
					expr2.add(aux);
					System.out.println(aux);
				}
				String resul = ctrl.compEquiv(expr1, expr2, algoritmo, metodo);
				res.setText(resul);

			}
		});

	}

}
