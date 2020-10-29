import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SacADos {
	private int POIDS_MAX;
	private int poids;
	private double prix;
	private ArrayList<Item> sac;
	private	int[] dansSac; // 1 si dedans 0 sinon
	
	private static final String GLOUT = "gloutonne";
	private static final String DYNA = "dynamique";
	private static final String PSE = "pse";
	
	public SacADos() {
		this.sac = new ArrayList<Item>();
	}
	
	public SacADos(String chemin, int pMax) {
		this();
		this.POIDS_MAX = pMax;
		try {
			InputStreamReader lecture = new InputStreamReader(new FileInputStream(chemin));
			BufferedReader buff = new BufferedReader(lecture);
			String ligne;
			String[] item;
			while ((ligne = buff.readLine()) != null) {
				item = ligne.split(" ; ");
				this.sac.add(new Item(item));
			}
			this.dansSac = new int[this.sac.size()];
			for(int i=0; i<this.dansSac.length; i++) this.dansSac[i] = 1;
			buff.close();
			lecture.close();
			if (this.sac.size() == 0) System.exit(3);
		
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
	
	public String toString() {
		String res = "";
		Item item;
		res += "Sac: pMax=" + this.POIDS_MAX + "\n";
		for (int i=0; i < this.sac.size(); i++) {
			if (this.dansSac[i] == 1) {
				item = this.sac.get(i);
				res += item.getNom() + " : poids= " + item.getPoids() + ", prix= " + item.getPrix() + ", ratio=" + item.getRatio() + "\n";
			}
		}
		return res;
	}
	
	public void resoudre(String choix) {
		switch(choix){
		case GLOUT:
			res_gloutonne();
			break;
		case DYNA:
			res_dynamique();
			break;
		case PSE:
			res_pse();
			break;
		default:
			System.out.println("Choix non valide !");
		}
	}
	
	public void res_gloutonne() {
		System.out.println("\nSolution Gloutonne:");
		Tri.quickSort(this.sac, 0, this.sac.size()-1);
		this.poids = 0;
		this.prix = 0;
		Item objet;
		
		for (int i=0; i<this.sac.size(); i++) {
			objet = this.sac.get(i);
			if (this.poids + objet.getPoids() > this.POIDS_MAX) this.sac.remove(i--);
			else {
				this.poids += objet.getPoids();
				this.prix += objet.getPrix();
				this.dansSac[i] = 1;
			}
		}
	}
	
	public void res_dynamique() {
		// Cette methode ne marche qu'avec des valeur entiere de poids
		System.out.println("Solution Dynamique");
		double[][] v = new double[this.sac.size()][this.POIDS_MAX+1];
		Item curItem = this.sac.get(0);
		int poids = curItem.getPoids();
		double value = curItem.getPrix();
		// Boucle pour initialiser la premiere ligne de mon tableau
		for(int i=0; i <= POIDS_MAX; i++) v[0][i] = i<poids ? 0 : value;
		int j=0;
		
		// Boucle pour remplir toute les autre cases de mon tableau
		for(int i=1; i<this.sac.size(); i++) {
			j=0;
			curItem = this.sac.get(i);
			poids = curItem.getPoids();
			value = curItem.getPrix();
			while (j <= this.POIDS_MAX && j < poids) v[i][j] = v[i-1][j++];
			for (j=j; j <= this.POIDS_MAX; j++) v[i][j] = max(v[i-1][j], v[i-1][j-poids] + value);
		}
		
		
		// Boucle pour recuperer les Items a mettre dans le sac en remontant dans le tableau
		this.poids = 0;
		int i = this.sac.size()-1;
		j = this.POIDS_MAX;
		this.prix = v[i][j];
		while (i > 0) {
			if (v[i][j] == v[i-1][j]) this.dansSac[i] = 0;
			else {
				j -= this.sac.get(i).getPoids();
				this.poids += this.sac.get(i).getPoids();
			}
			i--;
		}
		if (v[i][j] == 0) this.dansSac[i] = 0;
		else this.poids += this.sac.get(i).getPoids();
	}
	
	private static double max(double a, double b) {
		return a>b ? a : b;
	}
		
	public void res_pse() {
		System.out.println("PSE");
		// TODO
	}
	
	// Affichage de la solution apres resolution
	public void printSolution() {
		String res = "";
		res += "   Poids: " + this.poids + "\n";
		res += "   Prix: " + this.prix + "\n";
		res += "   Objets:";
		for(int i=0; i<this.sac.size(); i++) {
			if (this.dansSac[i] == 1) res += " " + this.sac.get(i).getNom() + ",";
		}
		System.out.println(res);
	}
	
	// Si les argument passes pour executer le programme ne sont pas valide
	private static void howToUse() {
		String res = "How to use :\n";
		res += "1. Run \"java SacADos path/to/itemfile.txt poidsMax resolution\" Gloutonne/Dynamique/PSE\n";
		res += "2. Just run \"java SacADos\" you'll have a walkthrought";
		System.out.println(res);
	}
	
	// Verifie si la methode passe en argument existe
	private static boolean validMethode(String methode) {
		if (methode.equals(GLOUT) || methode.equals(DYNA) || methode.equals(PSE)) return true;
		return false;
	}
	
	// Walktrought pour lancer le programme
	private static void walkTrought() {
		Scanner in= new Scanner(System.in);
		String file;
		System.out.println("Donnez le chemin du fichier des items:");
		System.out.print("> ");
		file = in.nextLine();
		while(!(new File(file)).exists()){
			System.out.println("Erreur: Fichier intouvable !");
			System.out.print("> ");
			file = in.nextLine();
		}
		
		System.out.println("Donnez le poids maximal du sac:");
		System.out.print("> ");
		int pMax = in.nextInt();
		in.nextLine();
		
		System.out.println("Choisissez la methode de resolution (" + GLOUT + "/" + DYNA + "/" + PSE + ") :");
		System.out.print("> ");
		String methode = in.nextLine().toLowerCase();
		while (!validMethode(methode)) {
			System.out.println("Erreur: Methode invalide !");
			System.out.print("> ");
			methode = in.nextLine().toLowerCase();
		}
		in.close();
		SacADos s = new SacADos(file, pMax);
		s.resoudre(methode);
		s.printSolution();
	}
	
	public static void main(String[] args) {
		if (args.length == 3) {
			if(!(new File(args[0]).exists())) {
				System.out.println("Erreur: Fichier '" + args[0] + "' introuvable !");
				System.exit(1);
			}
			SacADos s = new SacADos(args[0], Integer.parseInt(args[1]));
			args[2].toLowerCase();
			if (!validMethode(args[2])) {
				System.out.println("Erreur: Methode invalide");
				howToUse();
				System.exit(2);
			}
			s.resoudre(args[2]);
			s.printSolution();
		}
		else if(args.length > 0) {
			howToUse();
		}
		else {
			walkTrought();
		}
	}
}
