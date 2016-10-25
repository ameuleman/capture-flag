package personnages;

/*
 * Gestion d'exception au chargement d'une image
 */
class ImageException extends Exception {
	private static final long serialVersionUID = 1L;

	public ImageException(String s){
		System.out.println(s+" : Pas d'image correspondante");
	}
}
