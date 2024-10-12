package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import java.io.File;

/**
 * Classe que emmagatzema els components de la interfície gràfica del programa
 * 
 * @author Ausiàs
 * @version 1.0
 */
public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * JPanel Àrea on es visualitzen i organitzen els components de la GUI
	 */
	private JPanel contentPane;

	/**
	 * JTextField txtBox on s'introduïx la ruta del directori a cercar
	 */
	private JTextField txtBusDir;

	/**
	 * JButton button que es polsa quan l'usuari vol cercar l'estructura del
	 * directori proporcionat
	 */
	private JButton btnBusSubFiles;

	/**
	 * JTextField txtBox on s'introduïx la paraula a cercar
	 */
	private JTextField txtParBus;

	/**
	 * JCheckBox checkBox que indica si la cerca respectarà les majúscules
	 */
	private JCheckBox chkRespectarMajus;

	/**
	 * JCheckBox checkBox que indica si la cerca respectarà els accents
	 */
	private JCheckBox chkRespectarAccents;

	/**
	 * JButton button que es polsa quan l'usuari vol trobar les coincidències d'una
	 * paraula en els subfitxers del directori proporcionat
	 */
	private JButton btnParBus;

	/**
	 * JTextField txtBox on s'introduïx la paraula a reemplaçar
	 */
	private JTextField txtParRem;

	/**
	 * JButton button que es polsa quan l'usuari vol reemplaçar una paraula en els
	 * subfitxers del directori proporcionat
	 */
	private JButton btnParRem;

	/**
	 * JTextArea El component txtArea on es carregarà l'estructura dels
	 * subdirectoris i subfitxers
	 */
	private JTextArea txaMostraSubfitxers;

	/**
	 * Mètode principal de la classe vista on es llança la GUI del programa
	 * 
	 * @param args Paràmetres subministrats com a arguments al programa
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista frame = new Vista();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor de la classe on es configuren aspectes de la GUI i s'inicien els
	 * components
	 */
	public Vista() {
		setTitle("AE01");
		File icona = new File("resources/imgs/icon.png");
		if (icona.exists()) {
			setIconImage(new ImageIcon(icona.getAbsolutePath()).getImage());
		} else {
			System.out.println("Error, no s'ha pogut trobar la icona en la ruta especificada.");
		}
		initComponents();
	}

	/**
	 * Mètode que inicia i aplica estils a tots els components que conformen la
	 * classe Vista i la GUI del programa
	 */
	public void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblBusDir = new JLabel("Escriu el directori a buscar:");
		lblBusDir.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblBusDir.setBounds(40, 11, 231, 48);
		contentPane.add(lblBusDir);

		txtBusDir = new JTextField();
		txtBusDir.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtBusDir.setBounds(40, 59, 461, 28);
		contentPane.add(txtBusDir);
		txtBusDir.setColumns(10);

		btnBusSubFiles = new JButton("Buscar subfitxers");
		btnBusSubFiles.setBackground(new Color(187, 250, 255));
		btnBusSubFiles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBusSubFiles.setBounds(40, 98, 148, 33);
		contentPane.add(btnBusSubFiles);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 149, 588, 180);
		contentPane.add(scrollPane);

		txaMostraSubfitxers = new JTextArea();
		txaMostraSubfitxers.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPane.setViewportView(txaMostraSubfitxers);

		JLabel lblParBus = new JLabel("Paraula a buscar:");
		lblParBus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblParBus.setBounds(30, 345, 112, 28);
		contentPane.add(lblParBus);

		txtParBus = new JTextField();
		txtParBus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtParBus.setColumns(10);
		txtParBus.setBounds(152, 346, 102, 28);
		contentPane.add(txtParBus);

		btnParBus = new JButton("Buscar");
		btnParBus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnParBus.setBackground(new Color(187, 250, 255));
		btnParBus.setBounds(268, 345, 81, 28);
		contentPane.add(btnParBus);

		chkRespectarMajus = new JCheckBox("Respectar majúscules");
		chkRespectarMajus.setBounds(363, 345, 154, 28);
		contentPane.add(chkRespectarMajus);

		chkRespectarAccents = new JCheckBox("Respectar accents");
		chkRespectarAccents.setBounds(520, 345, 138, 28);
		contentPane.add(chkRespectarAccents);

		JLabel lblParRem = new JLabel("Paraula a reemplaçar:");
		lblParRem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblParRem.setBounds(30, 390, 148, 28);
		contentPane.add(lblParRem);

		txtParRem = new JTextField();
		txtParRem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtParRem.setColumns(10);
		txtParRem.setBounds(175, 391, 119, 28);
		contentPane.add(txtParRem);

		btnParRem = new JButton("Reemplaçar");
		btnParRem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnParRem.setBackground(new Color(187, 250, 255));
		btnParRem.setBounds(315, 390, 112, 28);
		contentPane.add(btnParRem);

		setVisible(true);
	}

	/**
	 * Getter simple
	 * 
	 * @return JTextField El component textBox on s'introduïx la ruta del directori
	 *         a buscar
	 */
	public JTextField getTxtBusDir() {
		return txtBusDir;
	}

	/**
	 * Getter simple
	 * 
	 * @return JTextField El component textBox on s'introduïx la paraula a cercar
	 */
	public JTextField getTxtParBus() {
		return txtParBus;
	}

	/**
	 * Getter simple
	 * 
	 * @return JTextField El component textBox on s'introduïx la paraula a
	 *         reemplaçar
	 */
	public JTextField getTxtParRem() {
		return txtParRem;
	}

	/**
	 * Getter simple
	 * 
	 * @return JButton El component button que l'usuari polsarà per iniciar la cerca
	 *         de subdirectoris i subfitxers
	 */
	public JButton getBtnBusSubFiles() {
		return btnBusSubFiles;
	}

	/**
	 * Getter simple
	 * 
	 * @return JButton El component button que l'usuari polsarà per iniciar la cerca
	 *         d'una paraula en els subfitxers del directori
	 */
	public JButton getBtnParBus() {
		return btnParBus;
	}

	/**
	 * Getter simple
	 * 
	 * @return JButton El component button que l'usuari polsarà per iniciar el
	 *         reemplaçament d'una paraula en els subfitxers del directori
	 */
	public JButton getBtnParRem() {
		return btnParRem;
	}

	/**
	 * Getter simple
	 * 
	 * @return JTextArea El component txtArea on es carregarà l'estructura dels
	 *         subdirectoris i subfitxers
	 */
	public JTextArea getTxaMostraSubfitxers() {
		return txaMostraSubfitxers;
	}

	/**
	 * Getter simple
	 * 
	 * @return JCheckBox El component checkBox que indica si la cerca respectarà les
	 *         majúscules
	 */
	public JCheckBox getChkRespectarMajus() {
		return chkRespectarMajus;
	}

	/**
	 * Getter simple
	 * 
	 * @return JCheckBox El component checkBox que indica si la cerca respectarà els
	 *         accents
	 */
	public JCheckBox getChkRespectarAccents() {
		return chkRespectarAccents;
	}
}
