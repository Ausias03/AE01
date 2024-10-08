package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
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

	private static String imprimixEstructura(File dir, int subNivell, String paraula, boolean caseSensitive,
			boolean respAccents) {
		File[] subDirectoris = dir.listFiles();
		String estructura = "";
		for (int i = 0; i < subDirectoris.length; i++) {
			if (subDirectoris[i].isDirectory()) {
				if (subDirectoris[i].list().length != 0) {
					estructura += imprimixEspais(subNivell) + "\\" + subDirectoris[i].getName() + "\r\n";
					estructura += imprimixEstructura(subDirectoris[i], subNivell + 1, paraula, caseSensitive,
							respAccents);
				}
			} else {
				estructura += imprimixEspais(subNivell) + subDirectoris[i].getName() + " ("
						+ trobaParaula(paraula, subDirectoris[i].getAbsolutePath(), caseSensitive, respAccents)
						+ " coincidències)" + "\r\n";
			}
		}
		return estructura;
	}

	private static char llevaAccents(char car) {
		String charNormalitzat = Normalizer.normalize(car + "", Normalizer.Form.NFD);
		charNormalitzat = charNormalitzat.replace("[\\p{InCombiningDiacriticalMarks}]", "");
		return charNormalitzat.charAt(0);
	}

	private static String retornaString(File fitxer) {
		String textFitxer = "";
		try {
			FileReader fr = new FileReader(fitxer, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			while (linea != null) {
				textFitxer += linea + System.lineSeparator();
				linea = br.readLine();
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textFitxer;
	}

	private static int trobaParaula(String paraula, String fileRoute, boolean caseSensitive, boolean respAccents) {
		String textFitxer = retornaString(new File(fileRoute));
		
		if (textFitxer.isEmpty())
			return 0;
		
		int numCoincidencies = 0;
		char charParaula;
		char charText;
		int indexLletra = 0;
		for (int i = 0; i < textFitxer.length(); i++) {
			charText = textFitxer.charAt(i);
			charParaula = paraula.charAt(indexLletra);
			
			if (!caseSensitive) {
				charText = Character.toLowerCase(charText);
				charParaula = Character.toLowerCase(charParaula);
			}
			
			if (!respAccents) {
				charText = llevaAccents(charText);
				charParaula = llevaAccents(charParaula);
			}
			
			if (charText == charParaula) {
				indexLletra++;
			} else {
				indexLletra = 0;
			}
			
			if (indexLletra == paraula.length()) {
				numCoincidencies++;
				indexLletra = 0;
			}
		}
		return numCoincidencies;
	}

}
