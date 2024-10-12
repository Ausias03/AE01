package main;

/**
 * Classe principal de l'aplicació on s'instancien les 3 classes necessàries per
 * a fer-lo funcionar
 * 
 * @author Ausiàs
 * @version 1.0
 */
public class Principal {

	/**
	 * Mètode principal que instància la vista, el model i el controlador de
	 * l'aplicació
	 * 
	 * @param args Paràmetres subministrats com a arguments al programa
	 */
	public static void main(String[] args) {
		Vista vista = new Vista();
		Model model = new Model();
		Controlador controlador = new Controlador(vista, model);
	}

}
