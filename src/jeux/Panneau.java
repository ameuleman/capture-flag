package jeux;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import maze.MazeGenerator;
import personnages.Personnage;

/**
 * 
 * @author Meuleman
 * Panneau sur lequel tout sera dessin�
 */
public class Panneau extends JPanel{

	private static final long serialVersionUID = 1L;
	private int[] flag;
	private int tailleCase;
	private double angle=0.5,pas=0.0005;
	private BufferedImage buffImg, buffPortal;
	private Graphics2D gbi,gPortal;
	private ArrayList<Personnage> tabPers;
	private Image fond, portal;
	private AffineTransform transform;
	
	/**
	 * Constructeur de Panneau, stocke l'image de fond du labyrinthe dans buffImg puis l'affiche avec les personnages
	 * @param tailleCase Taille d'une case du labyrinthe en pixels
	 * @param nbCases nombre de cases de la longueur du labyrinthe
	 * @param tailleFenetre Taille de la longueure du labyrinthe en pixels
	 * @param tabP Tableau contennant tous les personnages en jeu
	 * @param lab Le labyrinthe au format MazeGenerator
	 * @param flag Position du drapeau
	 */
	public Panneau(int tailleCase, int nbCases, int tailleFenetre, ArrayList<Personnage> tabP, MazeGenerator lab, int[] flag){
		this.tailleCase=tailleCase;
		this.flag=flag;
		this.transform = new AffineTransform();
		buffImg = new BufferedImage(tailleFenetre , tailleFenetre,BufferedImage.TYPE_INT_ARGB);
		gbi = buffImg.createGraphics();
		portal=new ImageIcon("images/portal.png").getImage();
		buffPortal = new  BufferedImage(tailleFenetre , tailleFenetre,BufferedImage.TYPE_INT_ARGB);
		gPortal = buffPortal.createGraphics();
		gPortal.drawImage(portal,flag[0]*tailleCase, flag[1]*tailleCase, null);
		fond=new ImageIcon("images/fond.jpg").getImage();
		gbi.drawImage(fond, 0, 0, null);
		genererCarte(nbCases, lab);
		rafraichir(tabP);
	}
	
	/**
	 * Dessine la carte de fond du labyrinthe
	 * @param nbCases nombre de cases de la longueur du labyrinthe
	 * @param lab Le labyrinthe au format MazeGenerator
	 * @param flag Position du drapeau
	 */
	private void genererCarte(int nbCases, MazeGenerator lab){
		int x,y;
		gbi.setPaint(Color.gray);
		for(int j=0;j<nbCases;j++){
			for(int i=0;i<nbCases;i++){
				x=i*tailleCase;
				y=j*tailleCase;
				if(lab.isClosedOnLeft(i, j)){
					gbi.drawLine(x, y, x, y+tailleCase);
				}
				if(lab.isClosedOnTop(i, j)){
					gbi.drawLine(x, y, x+tailleCase, y);
				}
			}
		}
	}
	
	/**
	 * Redessine le panneau
	 * @param tabP Tableau de tous les personnages en jeu
	 */
	public synchronized void rafraichir(ArrayList<Personnage> tabP){
		angle+=pas;
		this.tabPers=tabP;
		this.repaint();
	}
	
	/**
	 * La fa�on dont les �l�ments doivent �tre dessin�s sur le panneau
	 */
	public synchronized void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.drawImage(buffImg, 0, 0, null);
		for(Personnage p : tabPers){
			g2d.drawImage(p.getSprite(),p.getPosX(), p.getPosY(),null);
		}
		transform.rotate(Math.toRadians(angle), flag[0]*tailleCase+tailleCase/2-2, flag[1]*tailleCase+tailleCase/2-2);
		g2d.drawImage(buffPortal,transform, null);
	}
}
	

