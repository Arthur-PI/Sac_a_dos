import java.io.*;
import java.util.ArrayList;

public class SacADos {
	private float poids_maximal;
	private ArrayList<Item> sac;
	
	private static final String GLOUT = "gloutonne";
	private static final String DYNA = "dynamique";
	private static final String PSE = "pse";
	
	public SacADos() {
		this.sac = new ArrayList<Item>();
	}
	
	public SacADos(String chemin, float pMax) {
		this();
		this.poids_maximal = pMax;
		try {
			InputStreamReader lecture = new InputStreamReader(new FileInputStream(chemin));
			BufferedReader buff = new BufferedReader(lecture);
			String ligne;
			String[] item;
			while ((ligne = buff.readLine()) != null) {
				item = ligne.split(" ; ");
				this.sac.add(new Item(item));
			}
			buff.close();
			lecture.close();
		
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		
	}
	
	public String toString() {
		String res = "";
		res += "Sac: pMax=" + this.poids_maximal + "\n";
		for (Item item : this.sac) {
			res += item.getNom() + " : poids= " + item.getPoids() + ", prix= " + item.getPrix() + ", ratio=" + item.getRatio() + "\n";
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
		System.out.println(this);
	}
	
	public void res_gloutonne() {
		System.out.println("Methode Gloutonne:\n");
		this.trier();
		System.out.println(this);
		int poids = 0;
		int prix = 0;
		Item tmp;
		
		for (int i=0; i<this.sac.size(); i++) {
			tmp = this.sac.get(i);
			if (poids + tmp.getPoids() > this.poids_maximal) this.sac.remove(i--);
			else {
				poids += tmp.getPoids();
				prix += tmp.getPrix();
			}
		}
		System.out.println(this);
		System.out.println("Poids total: " + poids + "\nPrix totale: " + prix);
	}
	
	public void res_dynamique() {
		System.out.println("Dynamique");
		// TODO
	}
	public void res_pse() {
		System.out.println("PSE");
		// TODO
	}
	
	private void trier() {
		// Methode de Trie par selection ou je fais remonter les elements les plus petit a la fin
		double minv;
		int mini;
		int length = this.sac.size()-1;
		
		for(int j=0; j < this.sac.size(); j++){
			mini = length;
			minv = 1000000;
			for (int i=0; i <= length; i++) {
				if (this.sac.get(i).inferiorTo(minv)) {
					minv = this.sac.get(i).getRatio();
					mini = i;
				}
			}
			Item tmp = this.sac.get(length);
			this.sac.set(length, this.sac.get(mini));
			this.sac.set(mini, tmp);
			length--;
		}
	}
	
	public static void main(String[] args) {
		SacADos s = new SacADos("items.txt", 5);
		System.out.println(s);
		s.res_gloutonne();
		
	}
}
