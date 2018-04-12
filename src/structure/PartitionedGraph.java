package structure;

import java.util.ArrayList;

public class PartitionedGraph extends Graphe {
	private ArrayList<Graphe> sousGraphes = new ArrayList<>();
	
	public ArrayList<Graphe> getSousGraphes() {
		return sousGraphes;
	}

	public void setSousGraphes(ArrayList<Graphe> sousGraphes) {
		this.sousGraphes = sousGraphes;
	}

	
	
	public PartitionedGraph(Graphe g) {
		super(g);
	}

	public void addSousGraphe() {
		
	}
	
}
