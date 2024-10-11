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

/**
 * Classe amb els mètodes que realitzen les operacions i la lògica de l'aplicació
 * @author Ausiàs
 * @version 1.0
 */
public class Model {

	/**
	 * Mètode públic que registra l'estructura de subdirectoris i fitxers d'un directori de forma gràfica en un string
	 * @param rutaDir File amb el pathname del directori del qual es vol obtindre l'estructura
	 * @return String Estructura amb informació de cada fitxer
	 */
	public String getEstructuraFitxers(File rutaDir) {
		return rutaDir.getName() + "\r\n" + imprimixEstructura(rutaDir, 0);
	}

	/**
	 * Mètode públic que registra l'estructura d'un directori i que indica les coincidències d'una paraula en el text de cada fitxer del directori
	 * @param rutaDir File amb el pathname del directori del qual es vol obtindre les coincidències
	 * @param paraula String de la paraula a cercar
	 * @param caseSensitive boolean que indica si la cerca respectarà majúscules o no
	 * @param respAccents boolean que indica si la cerca respectarà els accents o no
	 * @return String Estructura amb les coincidències trobades
	 */
	public String getCoincidenciesFitxers(File rutaDir, String paraula, boolean caseSensitive, boolean respAccents) {
		return rutaDir.getName() + "\r\n" + imprimixEstructura(rutaDir, 0, paraula, caseSensitive, respAccents);
	}

	/**
	 * Mètode públic que registra l'estructura d'un directori i que indica el número de reemplaçaments que s'han produït en cada fitxer del directori
	 * @param rutaDir File amb el pathname del directori on es vol produïr els reemplaçaments
	 * @param paraula String de la paraula a cercar
	 * @param reemplac String de la paraula a reemplaçar
	 * @param caseSensitive boolean que indica si la cerca respectarà majúscules o no
	 * @param respAccents boolean que indica si la cerca respectarà els accents o no
	 * @return String Estructura amb els reemplaçaments que s'han produït
	 */
	public String getReemplacosFitxers(File rutaDir, String paraula, String reemplac, boolean caseSensitive,
			boolean respAccents) {
		return rutaDir.getName() + "\r\n"
				+ imprimixEstructura(rutaDir, 0, paraula, reemplac, caseSensitive, respAccents);
	}

	/**
	 * Mètode privat que itera sobre els elements fills d'un directori i registra el seu subnivell i les seues dades
	 * @param dir File amb el pathname del directori sobre el qual es va a iterar
	 * @param subNivell int Nivell de profunditat del directori a iterar (0 es el directori arrel)
	 * @return String Estructura de directoris i fitxers fills del directori proporcionat
	 */
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

	/**
	 * Mètode privat sobrecarregat que itera sobre els elements fills d'un directori i registra les coincidències d'una paraula en cadascun
	 * @param dir File amb el pathname del directori sobre el qual es va a iterar
	 * @param subNivell int Nivell de profunditat del directori a iterar (0 es el directori arrel)
	 * @param paraula String de la paraula a cercar
	 * @param caseSensitive boolean que indica si la cerca respectarà majúscules o no
	 * @param respAccents boolean que indica si la cerca respectarà els accents o no
	 * @return String Estructura de directoris i fitxers fills del directori proporcionat amb el número de coincidències trobades
	 */
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

	/**
	 * Mètode privat sobrecarregat que itera sobre els elements fills d'un directori i registra els reemplaçaments produïts en cadascun
	 * @param dir File amb el pathname del directori sobre el qual es va a iterar
	 * @param subNivell int Nivell de profunditat del directori a iterar (0 es el directori arrel)
	 * @param paraula String de la paraula a cercar
	 * @param reemplac String de la paraula a reemplaçar
	 * @param caseSensitive boolean que indica si la cerca respectarà majúscules o no
	 * @param respAccents boolean que indica si la cerca respectarà els accents o no
	 * @return String Estructura de directoris i fitxers fills del directori proporcionat amb el número de reemplaçaments produïts
	 */
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

	/**
	 * Mètode que imprimix el número d'espais corresponent al subnivell d'un element dins d'un directori
	 * @param subNivell int Nivell de profunditat del element
	 * @return String Marca amb espais corresponents al subnivell subministrat
	 */
	private static String imprimixEspais(int subNivell) {
		String marca = "|-- ";
		for (int i = 0; i < subNivell; i++) {
			marca = "|   " + marca;
		}
		return marca;
	}

	/**
	 * Mètode que llig el text d'un fitxer, el normalitza si cal, i troba el número de coincidències d'una paraula en el text recuperat
	 * @param paraula String de la paraula a cercar
	 * @param rutaFitxer String de la ruta del fitxer del qual es va a extraure el text
	 * @param caseSensitive boolean que indica si la cerca respectarà majúscules o no
	 * @param respAccents boolean que indica si la cerca respectarà els accents o no
	 * @return int Número de coincidències trobades al text
	 */
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

	/**
	 * Mètode que reemplaça totes les ocurrències d'una paraula per altra subministrada en el fitxer proporcionat,
	 * i escriu en un nou fitxer el text modificat
	 * @param paraula String de la paraula a cercar
	 * @param reemplac String de la paraula a reemplaçar
	 * @param rutaFitxer String de la ruta del fitxer del qual es va a extraure el text
	 * @param caseSensitive boolean que indica si la cerca respectarà majúscules o no
	 * @param respAccents boolean que indica si la cerca respectarà els accents o no
	 * @return int El número de reemplaçaments que s'han produït
	 */
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

	/**
	 * Mètode que itera sobre tots els caràcters del text proporcionat i els compara amb els caràcters de la paraula subministrada
	 * Registra quantes vegades es repetix la paraula en el text
	 * @param paraula String de la paraula a cercar
	 * @param text String del text on es va a cercar la paraula
	 * @return int El número de vegades que la paraula es repetix en el text
	 */
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

	/**
	 * Mètode que retorna l'extensió d'un fitxer proporcionat
	 * @param fitxer File fitxer del qual es vol extraure l'extensiò
	 * @return String L'extensió del fitxer amb el punt inclòs
	 */
	private static String getExtensioFitxer(File fitxer) {
		String nom = fitxer.getName();
		int ultimIndex = nom.lastIndexOf(".");
		return ultimIndex == -1 ? "" : nom.substring(ultimIndex);
	}

	/**
	 * Mètode que lleva els accents i normalitza un text proporcionat
	 * @param text String Text que es vol normalitzar
	 * @return String Text normalitzat sense accents
	 */
	private static String llevaAccents(String text) {
		String textNormalitzat = Normalizer.normalize(text, Normalizer.Form.NFD);
		textNormalitzat = textNormalitzat.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return textNormalitzat;
	}

	/**
	 * Mètode que llig el contingut d'un fitxer proporcionat i el retorna com a String
	 * @param fitxer File fitxer del qual es vol extraure el contingut
	 * @return String Text extret del fitxer, buit si no s'ha pogut llegir
	 */
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

	/**
	 * Mètode que llig el contingut d'un fitxer PDF proporcionat i el retorna com a String
	 * @param fitxer File fitxer PDF del qual es vol extraure el contingut
	 * @return String Text extret del fitxer, buit si no s'ha pogut llegir
	 */
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

	/**
	 * Mètode que escriu una modificació d'un fitxer proporcionat amb el text subministrat
	 * @param rutaFitxer String Ruta del fitxer a partir de la qual es crearà una modificació
	 * @param textFitxer String Text a introduïr en la modificació del fitxer
	 * @return boolean Éxit a l'escriure el nou fitxer
	 */
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
