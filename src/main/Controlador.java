package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Classe que incorpora els gestors d'esdeveniments dels components implementats
 * en la vista del programa
 * 
 * @author Ausiàs
 * @version 1.0
 */
public class Controlador {
	/**
	 * Vista Propietat que guarda la instància de la vista del programa
	 */
	private Vista vista;
	
	/**
	 * Model Propietat que guarda la instància del model del programa
	 */
	private Model model;

	/**
	 * Constructor de la classe on s'inicialitzen la vista i el model a controlar
	 * 
	 * @param vista Vista Instància de la vista del programa
	 * @param model Model Instància del model del programa
	 */
	public Controlador(Vista vista, Model model) {
		this.vista = vista;
		this.model = model;

		initEventHandlers();
	}

	/**
	 * Mètode que inicia els gestors d'esdeveniments que executaran la lògica del
	 * programa quan l'usuari faça clic sobre els botons de la GUI
	 */
	public void initEventHandlers() {
		vista.getBtnBusSubFiles().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (txtFieldsEmplenats(new JTextField[] { vista.getTxtBusDir() })) {
					File rutaDir = new File(vista.getTxtBusDir().getText());

					if (!rutaDir.exists()) {
						JOptionPane.showMessageDialog(null, "El directori introduït no existix", "ACTION BUTTON SEARCH",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						vista.getTxaMostraSubfitxers().setText(model.getEstructuraFitxers(rutaDir));
					}

				} else {
					JOptionPane.showMessageDialog(null, "Ompli els camps necesaris abans de polsar el botó.", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		vista.getBtnParBus().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (txtFieldsEmplenats(new JTextField[] { vista.getTxtBusDir(), vista.getTxtParBus() })) {
					File rutaDir = new File(vista.getTxtBusDir().getText());
					String paraula = vista.getTxtParBus().getText();

					if (!rutaDir.exists()) {
						JOptionPane.showMessageDialog(null, "El directori introduït no existix", "ACTION BUTTON SEARCH",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						vista.getTxaMostraSubfitxers()
								.setText(model.getCoincidenciesFitxers(rutaDir, paraula,
										vista.getChkRespectarMajus().isSelected(),
										vista.getChkRespectarAccents().isSelected()));
					}

				} else {
					JOptionPane.showMessageDialog(null, "Ompli els camps necesaris abans de polsar el botó.", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		vista.getBtnParRem().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (txtFieldsEmplenats(
						new JTextField[] { vista.getTxtBusDir(), vista.getTxtParBus(), vista.getTxtParRem() })) {

					File rutaDir = new File(vista.getTxtBusDir().getText());
					String paraula = vista.getTxtParBus().getText();
					String parReemplacar = vista.getTxtParRem().getText();

					if (!rutaDir.exists()) {
						JOptionPane.showMessageDialog(null, "El directori introduït no existix", "ACTION BUTTON SEARCH",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						vista.getTxaMostraSubfitxers()
								.setText(model.getReemplacosFitxers(rutaDir, paraula, parReemplacar,
										vista.getChkRespectarMajus().isSelected(),
										vista.getChkRespectarAccents().isSelected()));
					}

				} else {
					JOptionPane.showMessageDialog(null, "Ompli els camps necesaris abans de polsar el botó.", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}

	/**
	 * Mètode que comprova si els JTextFields subministrats estan buits o si
	 * contenen text
	 * 
	 * @param campsText JTextField[] Array de longitud n amb tots els JTextField a
	 *                  comprovar
	 * @return boolean false si n'hi ha algun JTextField buit true si estan tots
	 *         emplenats
	 */
	private boolean txtFieldsEmplenats(JTextField[] campsText) {
		for (int i = 0; i < campsText.length; i++) {
			if (campsText[i].getText().isBlank()) {
				return false;
			}
		}
		return true;
	}
}
