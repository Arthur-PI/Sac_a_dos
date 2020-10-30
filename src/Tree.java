
public class Tree {

	private Item[] valeur;
	// tableau correspondant � la valeur trouv�e dans l'arbre
	private Tree LeftBranch, RightBranch;
	// attributs priv�s correspondant aux branches gauches et droites de l'arbre
	private int profondeur;
	private static double BorneInferieure; 
	private double BorneSuperieure;
	private static Item[] tabMeilleureValeur; 
	// tableau correspondant � la meilleure valeur trouv�e dans l'arbre
	
	
	/*
	 * Constrcuteur r�cursif
	 * Construction des combinaisons r�alisables et int�ressantes pour r�soudre le probl�me
	*/
	
	public Tree(Item[] listeItemsSac, double pMax, Item[] tabItem, int i){
		if (i <= listeItemsSac.length) {
			//importation dans this.valeur du tableau d'objets
			this.valeur = new Item[listeItemsSac.length];
			for (int m =0; m<listeItemsSac.length; ++m){
				if (tabItem[m] != null){
					this.valeur[m] = tabItem[m];
				}
			}
			
			this.profondeur = i;
			this.calculBorneSuperieure(listeItemsSac);
			this.calculBorneInferieure();
			
			if (i != listeItemsSac.length){
				this.LeftBranch = new Tree(listeItemsSac, pMax, tabItem, i+1);
			
				tabItem[i] = listeItemsSac[i];
				if (this.poidsListeItems(tabItem)<=pMax && this.BorneSuperieure>Tree.BorneInferieure){
					// v�rification pour rendre l'arbre le plus court possible 
					this.RightBranch = new Tree(listeItemsSac, pMax, tabItem, i+1);
				}
				tabItem[i] = null; // pour supprimer le dernier objet dans tabItem et dans this.valeur (car r�f�rence)
			}
			
		}
	}
	
	  /*
	   * fonction r�cursive permettant de trouver la combinaison (en initialisant l'attribut statique tabMeilleureValeur)
	   * � partir de la meilleure valeur le tableau (qui est obtenue avec borneInferieure en construisant l'arbre)
	 */
			
	public void chercherSolution(){
		if (this.valeurListeItems() == Tree.BorneInferieure){
			Tree.tabMeilleureValeur = this.valeur;
		}
		else {
			if (this.LeftBranch==null && this.RightBranch==null){
				return;
			}
			if (this.LeftBranch==null){
				this.RightBranch.chercherSolution();
			}
			if (this.RightBranch==null){
				this.LeftBranch.chercherSolution();
			}
			if (this.RightBranch!=null && this.LeftBranch!=null){
				this.RightBranch.chercherSolution();
				this.LeftBranch.chercherSolution();
			}
		}	
	}
	
	
	
	//retourne la borne inf�rieure (statique) de la branche en question
	public double getBorneInferieure(){
		return Tree.BorneInferieure;
	}
	
	//retourne la borne sup�rieure de la branche en question
	public double getBorneSuperieure(){
		return this.BorneSuperieure;
	}
	
	
	 // mis � jour de l'attribut statique BorneInferieure lorsqu' on trouve une meilleure valeur (une combinaison) 
	 // mis � jour lors de la construction de l'arbre
	 
	public void calculBorneInferieure(){
		if (this.valeurListeItems() > Tree.BorneInferieure){
			Tree.BorneInferieure = this.valeurListeItems();
		}
	}
	

	//calcul pour chaque noeud (Tree) la valeur maximale que pourra avoir la combinaison finale � partir d'un noeud
	public void calculBorneSuperieure(Item[] listeItemsSac){
		double R�sultat = 0.0;
		R�sultat += this.valeurListeItems(); // valeur (prix) du noeud courant
		for (int i=this.profondeur; i<listeItemsSac.length; ++i){
			R�sultat += listeItemsSac[i].getPrix(); // ajout des valeurs (prix) des objets restants
		}
		this.BorneSuperieure = R�sultat;
	}
	
	//retourne la valeur (prix) totale de this.valeur (tableau d'objets)
	public double valeurListeItems(){
		double R�sultat=0.0;
		for(int i=0; i<this.valeur.length; ++i){
			if (this.valeur[i] != null){
				R�sultat += this.valeur[i].getPrix();
			}
		}
		return R�sultat;
	}
	
	//retourne le poids total de this.valeur (tableau d'objets)
	public double poidsListeItems(){
		double R�sultat=0.0;
		for(int i=0; i<this.valeur.length; ++i){
			if (this.valeur[i] != null){
				R�sultat += this.valeur[i].getPrix();
			}
		}
		return R�sultat;
	}
	
	//retourne la valeur (prix) totale d'un tableau d'objets
	public double valeurListeItems(Item[] dansSac){
		double R�sultat=0.0;
		for(int i=0; i<dansSac.length; ++i){
			if (dansSac[i] != null){
				R�sultat += dansSac[i].getPrix();
			}
		}
		return R�sultat;
	}
	
	//retourne le poids total d'un tableau d'objets
	public double poidsListeItems(Item[] dansSac){
		double R�sultat=0.0;
		for(int i=0; i<dansSac.length; ++i){
			if (dansSac[i] != null){
				R�sultat += dansSac[i].getPoids();
			}
		}
		return R�sultat;
	}

	public int getProfondeur(){
		return this.profondeur;
	}
	
	public Item[] getTabMeilleureValeur(){
		return Tree.tabMeilleureValeur;
	}
	
}
