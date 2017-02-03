package personnages;

import jeux.Fenetre;

/**
 * Class héritant de Personnage
 * @author Meuleman
 * Représente un ennemi non-joueur que le joueur doit éviter
 */
public class Ennemi extends Personnage implements Comportement{
	
	/**
	 *  Constructeur d'Ennemi, lance un thread propre à chaque ennemi
	 * @param fen Fenetre de lancement du jeu
	 * @param couleur Couleur du joueur
	 * @param x Coordonnée x sur le labyrinthe
	 * @param y Coordonnée y sur le labyrinthe
	 * @param tailleCase taille d'une case du labyrinthe en pixels
	 * @param delay temps entre deux mouvements
	 */
	public Ennemi(Fenetre fen, String nomSprite, int x, int y, int tailleCase, int delay){
		super(fen, nomSprite, x, y, tailleCase, delay);
		Thread t = new Thread(new LancerEnnemi());
		t.start();
	}
	
	/**
	 * Comportement de l'ennemi aléatoire
	 */
	public synchronized void comportement(){
		int alea;
		while(true){
			alea = (int)(Math.random()*5);
			if(alea==0) deplacer(1,0);
			else if(alea==1) deplacer(0,1);
			else if(alea==2) deplacer(-1,0);
			else if(alea==3) deplacer(0,-1);
			fairePause(delay);
		}
	}
	
	/**
	 * 
	 * @author Meuleman
	 * Thread de lancement 
	 */
	class LancerEnnemi implements Runnable{
		public void run() {
			comportement();
		}
	}
	
}
