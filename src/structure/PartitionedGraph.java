package structure;

import java.util.ArrayList;

public class PartitionedGraph extends Graphe {

	private ArrayList<ArrayList<Integer>> sousGraphes = new ArrayList<>();
	private ArrayList<Coupure> coupures = new ArrayList<>();
	private PartitionedGraph(Graphe g) {
		super(g);
	}
	
	/**
	 * Construit un graphe suivants la liste de partitions partitions
	 * @param g le graphe d'origine
	 * @param partitions une ArrayList<ArrayList<Integer>> ou chaque ArrayList<Integer> correspont a une partition
	 */
	public static PartitionedGraph PartitionnedGraphFromPartitions(Graphe g, ArrayList<ArrayList<Integer>> partitions) {
		PartitionedGraph pg = new PartitionedGraph(g);
		ArrayList<Integer> toCut = new ArrayList<>();
		for (ArrayList<Integer> partition : partitions) {
			toCut.addAll(g.neighboorsOf(partition));			
			for (Integer i : partition) {
				for (Integer j : toCut) {
					if (pg.matriceAdj[i][j] != -1) {					
						pg.matriceAdj[i][j] = -1;
						pg.matriceAdj[j][i] = -1;
						pg.coupures.add(new Coupure(i, j));
					}
					
				}
			}
		}
		pg.findPartitions();
		return pg;
	}
	
	public static PartitionedGraph PartitionnedGraphFromCuts(Graphe g, ArrayList<Integer> cuts) {
		PartitionedGraph pg = new PartitionedGraph(g);
		return pg;
	}
	
	
	private void findPartitions() {
		ArrayList<Integer> total = new ArrayList<>();
		ArrayList<Integer> currentPartition;
		while(total.size() != Noeuds.size()) {
			currentPartition= new ArrayList<>();
			for (Noeud n : Noeuds) {
				if (!total.contains(n.getId())){
					currentPartition.add(n.getId());
					total.add(n.getId());
					break;
				}
			}
			ArrayList<Integer> currentPartitionNeighboors;
			while((currentPartitionNeighboors = neighboorsOf(currentPartition)).size() != 0) {
				boolean boo = false;
				for (Integer integer : currentPartitionNeighboors) {
					if(!total.contains(integer)) {
						currentPartition.add(integer);
						total.add(integer);
					}
				}
				//currentPartitionNeighboors.clear();
			}
			sousGraphes.add(currentPartition);
		}
	}
	
	public ArrayList<ArrayList<Integer>> getSousGraphes() {
		return sousGraphes;
	}
	
	@Override
	public String toString() {
		String res = "[";
		for (int i = 0; i < sousGraphes.size(); i++) {
			res+="[";
			for (int j = 0; j < sousGraphes.get(i).size(); j++) {
				res+= sousGraphes.get(i).get(j) + (j != sousGraphes.get(i).size()-1 ? ", ": "");				
			}
			res+= (i != sousGraphes.size()-1 ? "], ": "]");
		}
		res+="]\n";
		return res;
	}
	
}
