package personnages;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;

import jeux.Fenetre;

/**
 * Class héritant de Personnage
 * @author Meuleman
 * Représente un personnage controlable par un joueur
 */
public class Joueur extends Personnage {
	private int up, left, right, down;
	
	/**
	 * Constructeur de Joueur
	 * @param fen
	 * @param couleur
	 * @param x
	 * @param y
	 * @param tailleCase
	 * @param delay
	 * @param up Code de la touche qui permettra de déplacer le personnage vers le haut
	 * @param down Vers le bas
	 * @param left é gauche
	 * @param right é droite
	 */
	public Joueur(Fenetre fen, int num, int x, int y, int tailleCase, int delay, int up, int down, int left, int right){
		super(fen, "joueur"+num, x,y,tailleCase, delay);
		this.fen.addJoueur();
		this.up=up;
		this.down=down;
		this.right=left;
		this.left=right;
		ImageIcon im = null;
		for(int i=1;i<=5;i++){
			try{
				im=chargerImage("images/"+this.nomSprite+"/mort"+i+".png");
			}
			catch(ImageException e){
				e.printStackTrace();
				im = new ImageIcon("images/defaut.jpg");
			}
			finally{
				this.sprites.add(im.getImage());
			}
		}
		this.fen.addKeyListener(new ClavierListener());
	}
	
	/**
	 * 
	 * @author Meuleman
	 * Permet de contrôler le personnage avec le clavier
	 */
	class ClavierListener implements KeyListener{
		public void keyReleased(KeyEvent arg0){}

		public void keyTyped(KeyEvent arg0){}

		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==left){
				deplacer(1,0);
			}else if(e.getKeyCode()==down){
				deplacer(0,1);
			}else if(e.getKeyCode()==right){
				deplacer(-1,0);
			}else if(e.getKeyCode()==up){
				deplacer(0,-1);
			}
		}
	}
	
}
