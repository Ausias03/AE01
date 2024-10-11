package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Controlador {
	private Vista vista;
	private Model model;

	public Controlador(Vista vista, Model model) {
		this.vista = vista;
		this.model = model;

		initEventHandlers();
	}

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

	private boolean txtFieldsEmplenats(JTextField[] campsText) {
		for (int i = 0; i < campsText.length; i++) {
			if (campsText[i].getText().isBlank()) {
				return false;
			}
		}
		return true;
	}
}
