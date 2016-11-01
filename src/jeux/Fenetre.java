package jeux;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import maze.MazeGenerator;
import personnages.Ennemi;
import personnages.Joueur;
import personnages.Personnage;

/**
 * Classe principale
 * @author Meuleman
 * Fenetre dans laquelle le jeu sera lancé 
 */
public class Fenetre extends JFrame{
	
	/**
	 * Constantes
	 */
	private static final long serialVersionUID = 1L;
	private static final int tailleCase = 4*14; //taille d'une case en pixels (doit être multiple de 4 pour placer les joueurs au centre)
	private static final int nbCases = 2*8+1; //nombre de cases du labyrinthe (doit être impaire si l'on souhaite placer le drapeau au centre)
	private static final int delay = 300; //temps entre deux mouvements des joueurs
	private static final int refresh=10; //temps entre deux raffrachissement de l'affichage
	private static final int[] flag={nbCases/2,nbCases/2}; //Emplacement du drapeau (ici au centre)
	
	
	private ArrayList<Personnage> tabPers = new ArrayList<Personnage>();
	private MazeGenerator lab = new MazeGenerator(nbCases,nbCases);
	private Panneau pan;
	private int nbJoueurs=0;
	private boolean running = true;
	private int tailleFenetre=tailleCase*nbCases;
	private String message="";
	
	public Fenetre(){
		this.setTitle("Capture-flag"); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Les personnages joueurs
		tabPers.add(new Joueur(this, 1, 0, 0, tailleCase, delay, 
				KeyEvent.VK_Z, KeyEvent.VK_S, KeyEvent.VK_Q, KeyEvent.VK_D));
		tabPers.add(new Joueur(this, 2, nbCases-1, nbCases-1, tailleCase, delay, 
				KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT));

		//Les ennemis non-joueurs
		tabPers.add(new Ennemi(this, "ennemi", 0, nbCases-1, tailleCase, delay));
		tabPers.add(new Ennemi(this, "ennemi", nbCases-1, 0, tailleCase, delay));
		
		//Initialisation du panneau pan d'affichage
		this.pan=new Panneau(tailleCase, nbCases, tailleFenetre, tabPers, lab, flag); 
		//Affichage du panneau pan sur la fenetre 
		this.setContentPane(pan); 
		this.setSize(tailleFenetre+16, tailleFenetre+38); 
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		//Boucle de raffrachissement de l'affichage
		while(running){ 
			try{
				Thread.sleep(refresh);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			//test si le jeu est fini ou si un joueur est hors-jeu à cause d'un ennemi ou parce qu'il a gangé
			for(int i=0;i<nbJoueurs;i++){ 
				testLost(i);
				testWon(i);
			}
			pan.rafraichir(tabPers);
		}
		if(!running){
			JOptionPane.showMessageDialog(null, message, "Fin du jeu", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/**
	 * Test si le joueur en position i a gagné et arrète le jeu dans ce cas
	 * @param i Indice du joueur
	 */
	private void testWon(int i){ 
		if((tabPers.get(i).getX()==flag[0])&&(tabPers.get(i).getY()==flag[1])){
			message=message +tabPers.get(i)+" a gagnÃ© \n";
			retirerJoueur(i);
		}
	}
	
	/**
	 * Test si le joueur en position i a perdu é cause d'un ennemi et le retire du jeu dans ce cas
	 * @param i Indice du joueur
	 */
	private void testLost(int i){
		if(tabPers.get(i).getEnVie()){
			for(int j=nbJoueurs;j<tabPers.size();j++){
				if((tabPers.get(i).getX()==tabPers.get(j).getX())&&(tabPers.get(i).getY()==tabPers.get(j).getY())&&(tabPers.get(i).getEnVie())){
					message=message + tabPers.get(i)+" a perdu \n";
					tabPers.get(i).mourir(i);
				}
			}
		}
	}
	
	/**
	 * Retire un joueur et met é jour le titre de la fenetre
	 * @param i Indice du joueur
	 */
	public void retirerJoueur(int i){
		tabPers.remove(i);
		nbJoueurs--;
		//Arret du jeu si il ne reste plus qu'un joueurs
		if(nbJoueurs<2){ 
			running=false;
		}
	}
	
	/**
	 * Ajoute un au nombre de joueurs
	 */
	public void addJoueur(){
		nbJoueurs++;
	}
	
	/**
	 * vérifie si un mouvement est valide
	 * @param x position en x sur le labyrinthe
	 * @param y Position en y
	 * @param dX Déplacement en x
	 * @param dY Déplacement en y
	 * @return vrai si le mouvement est valide, faux sinon
	 */
	public boolean moveIsOk(int x, int y, int dX, int dY){
		Boolean res = false;
		
		if(lab.belongsToMaze(x+dX,y+dY)){
			if((dX==-1)&&(!lab.isClosedOnLeft(x,y)))
				res=true;
			else if((dY==-1)&&(!lab.isClosedOnTop(x,y)))
				res=true;
			else if((dX==1)&&(!lab.isClosedOnLeft(x+1,y)))
				res=true;
			else if((dY==1)&&(!lab.isClosedOnTop(x,y+1)))
				res=true;
			else res=false;
		}
		else res=false;
		return res;
	}
	
	/**
	 * Lance la fenetre en créant l'objet fen
	 * @param args
	 */
	public static void main(String[] args){
		while(true){
			Fenetre fen = new Fenetre();
			fen.dispose();
		}
		
	}
}
