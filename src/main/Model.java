package main;

import java.io.File;
import java.io.FileReader;
import java.text.Normalizer;
import java.text.SimpleDateFormat;

public class Model {

	public String getEstructuraFitxers(File rutaDir) {
		return rutaDir.getName() + "\r\n" + imprimixEstructura(rutaDir, 0);
	}

	public String getCoincidenciesFitxers(File rutaDir, String paraula, boolean caseSensitive, boolean respAccents) {
		return rutaDir.getName() + "\r\n" + imprimixEstructura(rutaDir, 0, paraula, caseSensitive, respAccents);
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

	private static String imprimixEstructura(File dir, int subNivell, 
			String paraula, boolean caseSensitive, boolean respAccents) {
		File[] subDirectoris = dir.listFiles();
		String estructura = "";
		for (int i = 0; i < subDirectoris.length; i++) {
			if (subDirectoris[i].isDirectory()) {
				if (subDirectoris[i].list().length != 0) {
					estructura += imprimixEspais(subNivell) + "\\" + subDirectoris[i].getName() + "\r\n";
					estructura += imprimixEstructura(subDirectoris[i], subNivell + 1, paraula, caseSensitive, respAccents);
				}
			} else {
				estructura += imprimixEspais(subNivell) + subDirectoris[i].getName() + " ("
						+ trobaParaula(paraula, subDirectoris[i].getAbsolutePath(), caseSensitive, respAccents) + " coincidències)" + "\r\n";
			}
		}
		return estructura;
	}

	private static int trobaParaula(String word, String fileRoute, boolean caseSensitive, boolean respAccents) {
		int numOfWords = 0;
		try {
			FileReader fr = new FileReader(fileRoute);
			int valor = fr.read();
			char wordChar;
			int indice = 0;

			while (valor != -1) {
				wordChar = word.charAt(indice);
				if (!caseSensitive) {
					valor = Character.toLowerCase((char) valor);
					wordChar = Character.toLowerCase(wordChar);
				}
				
				if (!respAccents) {
					valor = llevaAccents((char) valor);
					wordChar = llevaAccents(wordChar);
				}

				if ((char) valor == wordChar) {
					indice++;
				} else {
					indice = 0;
				}

				if (indice == word.length()) {
					numOfWords++;
					indice = 0;
				}
				valor = fr.read();
			}

			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numOfWords;
	}
	
	private static char llevaAccents(char car) {
		String charNormalitzat = Normalizer.normalize(car + "", Normalizer.Form.NFD);
		charNormalitzat = charNormalitzat.replace("[\\p{InCombiningDiacriticalMarks}]", "");
		return charNormalitzat.charAt(0);
	}

}
