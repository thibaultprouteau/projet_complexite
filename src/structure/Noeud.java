package structure;

public class Noeud {
	
	private int id;
	private int poids;
	
	
	public Noeud(int id, int poids) {
		super();
		this.id = id;
		this.poids = poids;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getPoids() {
		return poids;
	}


	public void setPoids(int poids) {
		this.poids = poids;
	}
	
	@Override
	public String toString() {
		return id + " " + poids;
	}

}
