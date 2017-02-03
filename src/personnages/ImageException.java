package personnages;

/*
 * Gestion d'exception au chargement d'une image
 */
class ImageException extends Exception {

	public ImageException(String s){
		System.out.println(s+" : Pas d'image correspondante");
	}
}
