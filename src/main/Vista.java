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

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtBusDir;
	private JButton btnBusSubFiles;
	private JButton btnParBus;
	private JTextArea txaMostraSubfitxers;
	private JTextField txtParBus;
	private JTextField txtParRem;
	private JCheckBox chkRespectarMajus;
	private JCheckBox chkRespectarAccents;

	/**
	 * Launch the application.
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
	 * Create the frame.
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
		
		JButton btnParRem = new JButton("Reemplaçar");
		btnParRem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnParRem.setBackground(new Color(187, 250, 255));
		btnParRem.setBounds(315, 390, 112, 28);
		contentPane.add(btnParRem);
		
		setVisible(true);
	}
	
	public JTextField getTxtBusDir() {
		return txtBusDir;
	}
	
	public JTextField getTxtParBus() {
		return txtParBus;
	}
	
	public JButton getBtnBusSubFiles() {
		return btnBusSubFiles;
	}
	
	public JButton getBtnParBus() {
		return btnParBus;
	}
	
	public JTextArea getTxaMostraSubfitxers() {
		return txaMostraSubfitxers;
	}
	
	public JCheckBox getChkRespectarMajus() {
		return chkRespectarMajus;
	}
	
	public JCheckBox getchkRespectarAccents() {
		return chkRespectarAccents;
	}
}
