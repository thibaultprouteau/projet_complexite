package structure;

public class Coupure {
	int noeud1;
	int noeud2;
	
	public Coupure(int noeud1, int noeud2) {
		this.noeud1 = noeud1;
		this.noeud2 = noeud2;
	}
	
	
	public boolean equals(Coupure c) {
		return (noeud1 == c.noeud1 && noeud2 == c.noeud2) 
				|| (noeud1 == c.noeud2 && noeud2 == c.noeud1);
	}
	
	@Override
	public String toString() {
		return "("+noeud1+", "+noeud2+")";
	}
}
