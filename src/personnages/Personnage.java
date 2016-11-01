package personnages;
import java.awt.Image;
import java.awt.MediaTracker;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import jeux.Fenetre;
import personnages.ImageException;

/**
 * 
 * @author Meuleman
 * Class dont les Joueurs et les ennemis héritent
 */
public abstract class Personnage{
	protected int tailleCase;
	protected int x,y; //position dans la matrice carte
	protected int posX,posY; //position sur la fenétre
	Fenetre fen;
	protected ArrayList<Image> sprites = new ArrayList<Image>() ; 
	protected boolean deplacable=true,enVie=true;
	protected Image sprite;
	protected int delay;
	protected int pause; 
	protected String nomSprite;
	
	/**
	 * Constructeur par défaut de Personnage
	 */
	public Personnage(){
		this.tailleCase=10;
		this.x=0;
		this.y=0;
		this.pause=10;
		this.sprites.add(new ImageIcon("images/defaut.jpg").getImage());
		this.sprite=this.sprites.get(0);
		this.posX=x*tailleCase+tailleCase/8;
		this.posY=y*tailleCase+tailleCase/8;
	}
	
	/**
	 * 
	 * @param fen Fenetre de lancement du jeu
	 * @param couleur Couleur du joueur
	 * @param x	Coordonnée x sur le labyrinthe
	 * @param y Coordonnée y sur le labyrinthe
	 * @param tailleCase taille d'une case du labyrinthe en pixels
	 * @param delay temps entre deux mouvements
	 */
	public Personnage(Fenetre fen, String nomSprite, int x, int y, int tailleCase, int delay){
		this.tailleCase=tailleCase;
		this.nomSprite=nomSprite;
		ImageIcon im = null;
		for(int i=1;i<=3;i++){
			try{
				im=chargerImage("images/"+this.nomSprite+"/pos"+i+".png");
			}
			catch(ImageException e){
				e.printStackTrace();
				im = new ImageIcon("images/defaut.jpg");
			}
			finally{
				this.sprites.add(im.getImage());
			}
		}		
		this.sprite=this.sprites.get(0);
		this.fen=fen;
		this.x=x;
		this.y=y;
		this.posX=x*tailleCase+tailleCase/8;
		this.posY=y*tailleCase+tailleCase/8;
		this.delay=delay;
		this.pause=delay/tailleCase;
	}
	
	/**
	 * Lance le thread de déplacement
	 * @param dX Déplacement de case en x
	 * @param dY En y
	 */
	protected synchronized void deplacer(int dX, int dY){
		if(fen.moveIsOk(x,y,dX,dY)&&(deplacable)&&(enVie)){
			Thread t = new Thread(new ThreadDeplacer(dX, dY));
			t.start();
		}
	}
	
	//Thread de dÃ©placement
	class ThreadDeplacer implements Runnable{
		int dX, dY;
		
		
		public ThreadDeplacer(int dX, int dY){
			this.dX=dX;
			this.dY=dY;
		}
		
		public synchronized void run(){
			x+=dX;
			y+=dY;
			deplacable=false;
			for(int i=0; i<tailleCase; i++){
				posX+=dX;
				posY+=dY;
				sprite=sprites.get(i%3);
				fairePause(pause);
				if(i==tailleCase/4){
					deplacable=true;
				}
			}
		}
	}
	
	//thread de mort
	class ThreadMourir implements Runnable{
		int ind;
		
		public ThreadMourir(int ind){
			this.ind=ind;
		}
		
		public void run(){
			for(int i=3;i<8;i++){
				sprite=sprites.get(i);
				for(int j=0;j<3;j++){
					sprites.set(j, sprites.get(i));
				}
				fairePause(delay);
			}
			fen.retirerJoueur(ind);			
		}
	}
	
	/**
	 * fonction qui lance la mort d'un personnage
	 * @param ind indice du joueur é tuer
	 */
	public void mourir(int ind){
		enVie=false;
		Thread t = new Thread(new ThreadMourir(ind));
		t.start();
	}
	
	protected static ImageIcon chargerImage(String s) throws ImageException{
		ImageIcon imIc = new ImageIcon(s);
		if(imIc.getImageLoadStatus() != MediaTracker.COMPLETE){
			throw new ImageException(s);
		}
		return imIc;
	}
	
	protected void fairePause(int temps){
		try {
			Thread.sleep(temps);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * redéfinition de la méthode toString pour l'affichage
	 */
	public String toString(){
		return nomSprite;
	}
	
	/**
	 * 
	 * @return Position posX du personnage en pixel
	 */
	public int getPosX(){
		return posX;
	}
	
	/**
	 * 
	 * @return Position posY du personnage en pixel
	 */
	public int getPosY(){
		return posY;
	}
	
	public boolean getEnVie(){
		return enVie;
	}
	
	/**
	 * 
	 * @return Position x du personnage en case
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * 
	 * @return Position y du personnage en case
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * 
	 * @return sprite du personnage
	 */
	public Image getSprite(){
		return sprite;
	}

}

