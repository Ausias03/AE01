package main;

import java.io.File;
import java.text.SimpleDateFormat;

public class Model {

	public String getEstructuraFitxers(File rutaDir) {
		return rutaDir.getName() + "\r\n" + imprimixEstructura(rutaDir, 0);
	}

	private static String imprimixEspais(int subNivell) {
		String marca = "|-- ";
		for (int i = 0; i < subNivell; i++) {
			marca = "|   " + marca;
		}
		return marca;
	}

	private static String imprimixEstructura(File dir, int subNivell) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		File[] subDirectoris = dir.listFiles();
		String estructura = "";
		for (int i = 0; i < subDirectoris.length; i++) {
			if (subDirectoris[i].isDirectory()) {
				if (subDirectoris[i].list().length != 0) {
					estructura += imprimixEspais(subNivell) + "\\" + subDirectoris[i].getName() + "\r\n";
					estructura += imprimixEstructura(subDirectoris[i], subNivell + 1);
				}
			} else {
				estructura += imprimixEspais(subNivell) + subDirectoris[i].getName() + " ("
						+ (Math.round(subDirectoris[i].length() / 1024.0f * 10.0f) / 10.0f) + " KB - "
						+ df.format(subDirectoris[i].lastModified()) + ")" + "\r\n";
			}
		}
		return estructura;
	}

}
