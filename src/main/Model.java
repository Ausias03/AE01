package main;

import java.io.File;
import java.text.SimpleDateFormat;

public class Model {
	
	public String getEstructuraFitxers(File rutaDir) {
		return rutaDir.getName() + "\r\n" + imprimeEstructura(rutaDir, 0);
	}
	
	private static String imprimeEspacios(int subNivel) {
		String marca = "|-- ";
		for (int i = 0; i < subNivel; i++) {
			marca = "|   " + marca;
		}
		return marca;
	}

	private static String imprimeEstructura(File dir, int subNivel) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		File[] subDirectorios = dir.listFiles();
		String estructura = "";
		for (int i = 0; i < subDirectorios.length; i++) {
			if (subDirectorios[i].isDirectory()) {
				if (subDirectorios[i].list().length != 0) {
					estructura += imprimeEspacios(subNivel) + "\\" + subDirectorios[i].getName() + "\r\n";
					estructura += imprimeEstructura(subDirectorios[i], subNivel + 1);
				}
			} else {
				estructura += imprimeEspacios(subNivel) + subDirectorios[i].getName() + " ("
						+ (Math.round(subDirectorios[i].length() / 1024.0f * 10.0f) / 10.0f) + " KB - "
						+ df.format(subDirectorios[i].lastModified()) + ")" + "\r\n";
			}
		}
		return estructura;
	}
	
}
