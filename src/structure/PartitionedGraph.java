package structure;

import java.util.ArrayList;

public class PartitionedGraph extends Graphe {
	private ArrayList<Graphe> sousGraphes = new ArrayList<>();
	public PartitionedGraph(Graphe g) {
		super(g);
	}
	
	public void addSousGraphe() {
		
	}
}
