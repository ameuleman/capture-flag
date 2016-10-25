package personnages;

import jeux.Fenetre;

/**
 * Class h�ritant de Personnage
 * @author Meuleman
 * Repr�sente un ennemi non-joueur que le joueur doit �viter
 */
public class Ennemi extends Personnage implements Comportement{
	
	/**
	 *  Constructeur d'Ennemi, lance un thread propre � chaque ennemi
	 * @param fen La fenetre d'affichage
	 * @param nomSprite
	 * @param x
	 * @param y
	 * @param tailleCase
	 * @param delay
	 */
	public Ennemi(Fenetre fen, String nomSprite, int x, int y, int tailleCase, int delay){
		super(fen, nomSprite, x, y, tailleCase, delay);
		Thread t = new Thread(new LancerEnnemi());
		t.start();
	}
	
	/**
	 * Comportement de l'ennemi al�atoire
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
