package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.text.SimpleDateFormat;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Model {

	public String getEstructuraFitxers(File rutaDir) {
		return rutaDir.getName() + "\r\n" + imprimixEstructura(rutaDir, 0);
	}

	public String getCoincidenciesFitxers(File rutaDir, String paraula, boolean caseSensitive, boolean respAccents) {
		return rutaDir.getName() + "\r\n" + imprimixEstructura(rutaDir, 0, paraula, caseSensitive, respAccents);
	}

	public String getReemplacosFitxers(File rutaDir, String paraula, String reemplac, boolean caseSensitive,
			boolean respAccents) {
		return rutaDir.getName() + "\r\n"
				+ imprimixEstructura(rutaDir, 0, paraula, reemplac, caseSensitive, respAccents);
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

	private static String imprimixEstructura(File dir, int subNivell, String paraula, String reemplac,
			boolean caseSensitive, boolean respAccents) {
		File[] subDirectoris = dir.listFiles();
		String estructura = "";
		for (int i = 0; i < subDirectoris.length; i++) {
			if (subDirectoris[i].isDirectory()) {
				if (subDirectoris[i].list().length != 0) {
					estructura += imprimixEspais(subNivell) + "\\" + subDirectoris[i].getName() + "\r\n";
					estructura += imprimixEstructura(subDirectoris[i], subNivell + 1, paraula, reemplac, caseSensitive,
							respAccents);
				}
			} else {
				estructura += imprimixEspais(subNivell)
						+ subDirectoris[i].getName() + " (" + reemplacaParaules(paraula, reemplac,
								subDirectoris[i].getAbsolutePath(), caseSensitive, respAccents)
						+ " reemplaços)" + "\r\n";
			}
		}
		return estructura;
	}

	private static String imprimixEspais(int subNivell) {
		String marca = "|-- ";
		for (int i = 0; i < subNivell; i++) {
			marca = "|   " + marca;
		}
		return marca;
	}

	private static int trobaParaula(String paraula, String rutaFitxer, boolean caseSensitive, boolean respAccents) {
		File fitxer = new File(rutaFitxer);
		String textFitxer = getExtensioFitxer(fitxer).equals(".pdf") ? retornaStringPDF(fitxer) : retornaString(fitxer);

		if (textFitxer.isEmpty())
			return 0;

		if (!caseSensitive) {
			textFitxer = textFitxer.toLowerCase();
			paraula = paraula.toLowerCase();
		}

		if (!respAccents) {
			textFitxer = llevaAccents(textFitxer);
			paraula = llevaAccents(paraula);
		}

		return numCoincidencies(paraula, textFitxer);
	}

	private static int reemplacaParaules(String paraula, String reemplac, String rutaFitxer, boolean caseSensitive,
			boolean respAccents) {
		File fitxer = new File(rutaFitxer);
		String textFitxerOriginal = retornaString(fitxer);

		if (textFitxerOriginal.isEmpty())
			return 0;

		String textModificat = textFitxerOriginal;

		if (!caseSensitive) {
			textModificat = textModificat.toLowerCase();
			paraula = paraula.toLowerCase();
		}

		if (!respAccents) {
			textModificat = llevaAccents(textModificat);
			paraula = llevaAccents(paraula);
		}

		int numReemplacos = getExtensioFitxer(fitxer).equals(".pdf") ? 0 : numCoincidencies(paraula, textModificat);

		if (numReemplacos != 0) {
			int indexCoincidencia = textModificat.indexOf(paraula);

			while (indexCoincidencia != -1) {
				textFitxerOriginal = textFitxerOriginal.substring(0, indexCoincidencia) + reemplac
						+ textFitxerOriginal.substring(indexCoincidencia + paraula.length());

				textModificat = textModificat.replaceFirst(paraula, reemplac);

				indexCoincidencia = textModificat.indexOf(paraula);
			}
			escriuModFitxer(rutaFitxer, textFitxerOriginal);
		}

		return numReemplacos;
	}

	private static int numCoincidencies(String paraula, String text) {
		int numCoincidencies = 0;
		char charParaula;
		char charText;
		int indexLletra = 0;
		for (int i = 0; i < text.length(); i++) {
			charText = text.charAt(i);
			charParaula = paraula.charAt(indexLletra);

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

	private static String getExtensioFitxer(File fitxer) {
		String nom = fitxer.getName();
		int ultimIndex = nom.lastIndexOf(".");
		return ultimIndex == -1 ? "" : nom.substring(ultimIndex);
	}

	private static String llevaAccents(String text) {
		String textNormalitzat = Normalizer.normalize(text, Normalizer.Form.NFD);
		textNormalitzat = textNormalitzat.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return textNormalitzat;
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

	private static String retornaStringPDF(File fitxer) {
		String textFitxer = "";
		try {
			PDDocument doc = Loader.loadPDF(fitxer);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			textFitxer = pdfStripper.getText(doc);
			doc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return textFitxer;
	}

	private static boolean escriuModFitxer(String rutaFitxer, String textFitxer) {
		boolean fitxerEscrit = true;
		try {
			FileWriter fw = new FileWriter(rutaFitxer.substring(0, rutaFitxer.lastIndexOf("\\") + 1) + "MOD_"
					+ rutaFitxer.substring(rutaFitxer.lastIndexOf("\\") + 1));
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(textFitxer);
			bw.close();
			fw.close();
		} catch (Exception e) {
			fitxerEscrit = false;
			e.printStackTrace();
		}
		return fitxerEscrit;
	}
}
