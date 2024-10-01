package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

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

				File rutaDir = new File(vista.getTxtBusDir().getText());

				if (!rutaDir.exists()) {
					JOptionPane.showMessageDialog(null, "El directori introduït no existix", "ACTION BUTTON SEARCH",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
//					vista.getTxaMostraSubfitxers().setText(model.getEstructuraFitxers(rutaDir));
				}
			}
		});

	}
}
