/**
 * 
 */
package maze;
import java.util.*;

/**
 * @author Nathan ROUXELIN
 * Génère un labyrinthe
 */
public class MazeGenerator {
	
	//Attributs
	private int lignes;
	private int colonnes;
	private byte[][] maze;
	
	/**
	 * Constructeur de MazeGenerator
	 * @param lignes Nombre de lignes
	 * @param colonnes Nombre de colonnes
	 */
	public MazeGenerator(int lignes, int colonnes){
		this.lignes = lignes;
		this.colonnes = colonnes;
		this.maze = new byte[lignes][colonnes];
		
		
		generateMaze(lignes/2,colonnes/2);
		
	}
	/**
	 * Génère un labyrinthe en utilisant l'algorithme Growing Tree
	 * @param dx abscisse d'entrée
	 * @param dy abscisse de sortie
	 */
	private void generateMaze(int dx, int dy){
		boolean[] voisins = {false,false,false,false};
		
		ArrayList<Integer> choix = new ArrayList<Integer>();
		choix.add(0);
		choix.add(1);
		choix.add(2);
		choix.add(3);
		
		int i = 0;
		int col;
		int lig;
		
		Stack<int[]> pile = new Stack<int[]>();
		int[] cellule = {dx,dy};
		pile.push(cellule);
		
		
		//Boucle principale
		while(!pile.empty()){
			//On récupère la dernière cellule de la pile
			cellule = pile.peek();
			lig = cellule[1];
			col = cellule[0];

			//On visite la case courante
			if((this.maze[lig][col] & 16) == 0){
				this.maze[lig][col] += 16;
			}

			//Recherche les voisins non visités
			voisins[0] = wasVisited(col,lig-1);
			voisins[1] = wasVisited(col,lig+1);
			voisins[2] = wasVisited(col-1,lig);
			voisins[3] = wasVisited(col+1,lig);

			//Si il n'y a pas de voisins non visitées, on dépile
			if(voisins[0] && voisins[1] && voisins[2] && voisins[3]){
				pile.pop();
			}else{//Sinon, on choisit une direction au hasard
				Collections.shuffle(choix);//Mélange des directions
				i = 0;//Choix d'une direction
				while(voisins[choix.get(i)]){
					i++;
				}
				
				switch(choix.get(i)){
				case 0://Case au-dessus
					this.maze[lig][col] += 2;
					this.maze[lig-1][col] += 1;
					lig -= 1;
					break;
					
				case 1://Case en-dessous
					this.maze[lig][col] += 1;
					this.maze[lig+1][col] += 2;
					lig += 1;
					break;
					
				case 2://Case à gauche
					this.maze[lig][col] += 4;
					this.maze[lig][col-1] += 8;
					col -= 1;
					break;
					
				case 3://Case à droite
					this.maze[lig][col] += 8;
					this.maze[lig][col+1] += 4;
					col += 1;
					break;
				}
				//on empile la case choisie
				int[] tmp = {col,lig};
				pile.push(tmp);
			}

		}
	}
	

	/**
	 * Teste si une cellule appartient au labyrinthe
	 * @param x bascisse
	 * @param y ordonée
	 * @return booléen
	 */
	public boolean belongsToMaze(int x, int y){
		return (x >= 0) && (x <= this.colonnes-1) && (y >= 0) && (y <= this.lignes-1);
	}
	
	/**
	 * Renvoie vrai si la cellule a été visitée ou si elle n'appartient pas au labyrinthe
	 * @param x abscisse
	 * @param y ordonnée
	 * @return booléen
	 */
	private boolean wasVisited(int x, int y){
		return (belongsToMaze(x,y) && (this.maze[y][x] & 16)!=0) || !belongsToMaze(x,y);
	}
	
	/**
	 * Affiche le labyrinthe dans le terminal
	 */
	public void display(){
		for(int lig=0; lig<this.lignes; lig++){
			for(int col=0;col<this.colonnes; col++){
				System.out.print(isClosedOnTop(col,lig) ? "+---": "+   ");
			}
			System.out.println("+");
			for(int col=0;col<this.colonnes; col++){
				System.out.print(isClosedOnLeft(col,lig) ? "|   ": "    ");
			}
			System.out.println("|");
			
		}
		for(int col=0;col<this.colonnes; col++){
			System.out.print("+---");
		}
		System.out.println("+");
	}
	
	/**
	 * Renvoie le labyrinthe
	 * @return
	 */
	public byte[][] getMaze(){
		return(this.maze);
	}
	
	/**
	 * Teste si le mur du haut est ouvert
	 * @param x
	 * @param y
	 * @return booléen
	 */
	public boolean isClosedOnTop(int x, int y){
		return((this.maze[y][x] & 2)==0);
	}
	
	/**
	 * Teste si le mur de gauche est ouvert
	 * @param x
	 * @param y
	 * @return booléen
	 */
	public boolean isClosedOnLeft(int x, int y){
		return((this.maze[y][x] & 4) == 0);
	}
}