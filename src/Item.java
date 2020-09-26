
public class Item {
	private String nom;
	private double prix;
	private int poids;
	
	public Item(String nom, int poids, double prix) {
		this.nom = nom;
		this.prix = prix;
		this.poids = poids;
	}
	
	public Item(String item[]) {
		this.nom = item[0];
		this.poids = Integer.parseInt(item[1]);
		this.prix = Double.parseDouble(item[2]);
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public double getPrix() {
		return this.prix;
	}
	
	public int getPoids() {
		return this.poids;
	}
	
	public double getRatio() {
		return this.prix / this.poids;
	}
	
	public boolean superiorTo(Item item2) {
		return this.getRatio() > item2.getRatio();
	}
	
	public boolean inferiorTo(Item item2) {
		return this.getRatio() < item2.getRatio();
	}
	
	public boolean superiorTo(double ratio) {
		return this.getRatio() > ratio;
	}
	
	public boolean inferiorTo(double ratio) {
		return this.getRatio() < ratio;
	}
}
