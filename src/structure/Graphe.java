package structure;


import java.lang.reflect.Array;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;


public class Graphe {
	
	private ArrayList<Noeud> Noeuds = new ArrayList();
	protected int[][] matriceAdj;
	
	//Ajouter un noeud Ã  la liste de noeuds
	public void ajoutNoeud(Noeud n) {
		Noeuds.add(n);
	}
	
	//init graph sans arcs
	public Graphe() {
		matriceAdj = new int[Noeuds.size()][Noeuds.size()];
		
		for(int i = 0; i < matriceAdj.length; i++) {
			for(int j = 0; j < matriceAdj[i].length; j++) {
				matriceAdj[i][j] = -1;
			}
		}
	}
	
	/**
	 * genere un graphe possèdent nb_noeud noeuds de poid 1 et 0 arcs
	 * @param nb_noeud le nombre de noeud du graphe a générer
	 */
	public Graphe(int nb_noeud) {
		matriceAdj = new int[nb_noeud][nb_noeud];
		for (int i = 0; i < nb_noeud; i++) {
			Noeuds.add(new Noeud(i, 1));
		}
		for(int i = 0; i < nb_noeud; i++) {
			for(int j = 0; j < nb_noeud; j++) {
				matriceAdj[i][j] = -1;
			}
		}
	}
	
	public Graphe(Graphe graph) {
		for (Noeud noeud : graph.Noeuds) {
			Noeuds.add(noeud);
		}
		matriceAdj = new int[graph.matriceAdj.length][graph.matriceAdj.length];
		for (int i = 0; i < graph.matriceAdj.length; i++) {
			for (int j = 0; j < graph.matriceAdj[i].length; j++) {
				matriceAdj[i][j] = graph.matriceAdj[i][j];
			}
		}
	}
	
	//ajout arc entre noeud n1 et noeud n2 avec un cout = ct
	public void ajoutArc(Noeud n1, Noeud n2, int ct) {
		
		int id1 = Noeuds.indexOf(n1);
		int id2 = Noeuds.indexOf(n2);
		
		//car graphe non-orirentÃ©
		matriceAdj[id1][id2] = ct;
		matriceAdj[id2][id1] = ct;
	}
	
	//suppression d'un arc entre le noeud n1 et n2
	public void supprArc(Noeud n1, Noeud n2) {
		int id1 = Noeuds.indexOf(n1);
		int id2 = Noeuds.indexOf(n2);
		
		//car graphe non-orirentÃ©
		matriceAdj[id1][id2] = -1;
		matriceAdj[id2][id1] = -1;
	}
	
	//combien de sommets dans mon graphe
	public int nombreSommets() {
		return Noeuds.size();
	}
	
	//combien d'arcs dans le graphe
	public int nombreArcs() {
		int k = 0;
		
		for(int i = 0; i < matriceAdj.length; i++) {
			for(int j = 0; j < matriceAdj[i].length; j++) {
				if(matriceAdj[i][j] >= 0)
					k = k + 1;
			}
		}
		
		return k/2; // matrice symmetrique par rapport a i = j car non-orientÃ©e.
	}
	
	//existe-t-il un arc entre n1 et n2?
	public boolean hasArc(Noeud n1, Noeud n2) {
		boolean isThere = false;
		
		int id1 = Noeuds.indexOf(n1);
		int id2 = Noeuds.indexOf(n2);
		
		if (matriceAdj[id1][id2] >= 0 && matriceAdj[id2][id1] >= 0)
			isThere = true;
		
		return isThere;
	}

	/**
	 * Genère aléatoirement un graphe connexe avec nb_node noeuds et (nb_node-1) arcs de poid [1 ; poid_max]
	 * @param nb_noeud le nombre de noeud
	 * @param poid_max la valeure maximum qu'un poid peut prendre
	 * @return si nb_noeud > 0  && poid_max > 0: retourne le graphe </br> sinon : retourne null
	 */
	public static Graphe generateSimpleGraph(int nb_noeud, int poid_max) {
		if (nb_noeud <= 0 || poid_max <= 0) return null;
		
		Graphe res = new Graphe(nb_noeud);
		for (int i = 1; i < nb_noeud; i++) {
			int noeud2 = (int)(Math.random()*(i));
			int poid = (int)(Math.random()*(poid_max))+1;
			res.matriceAdj[i][noeud2] = poid;
			res.matriceAdj[noeud2][i] = poid;
		}
		return res;
		
	}
	
	/**
	 * Genère aléatoirement un graphe connexe avec nb_noeud et [ nb_noeud-1 ; ( nb_noeud * ( nb_noeud - 1 ) )/ 2 ] arcs 
	 * de poid [1 ; poid_max]
	 * @param nb_noeud le nombre de noeud
	 * @param poid_max la valeure maximum qu'un poid peut prendre
	 * @return si nb_noeud > 0 && poid_max > à : retourne le graphe </br> sinon : retourne null
	 */
	public static Graphe generateGraph(int nb_noeud, int poid_max) {
		Graphe res = generateSimpleGraph(nb_noeud, poid_max);
		if (res == null) return null;
		int min = nb_noeud-1;
		int max = (nb_noeud * (nb_noeud -1) )/ 2;
		int nb_arc = (int)(Math.random()*(max-min)+min);
		ArrayList<SimpleEntry<Integer,Integer>> l = new ArrayList();
		for (int i = 1; i < nb_noeud; i++) {
			for (int j = 0; j < i; j++) {
				l.add(new SimpleEntry(i,j) );
			}
			
		}
		for (int i = 0; i < nb_arc; i++) {
			SimpleEntry<Integer, Integer> n = l.get((int)(Math.random()*l.size()));
			int poid = (int)(Math.random()*poid_max)+1;
			res.matriceAdj[n.getKey()][n.getValue()] = poid;
			res.matriceAdj[n.getValue()][n.getKey()] = poid;
			l.remove(n);	
		}
		return res;	
	}
	/**
	 * Trouve les voisins d'un noeud d'id n
	 * @param n l'id du noeud
	 * @return la liste des voisins du noeud
	 */
	public ArrayList<Integer> neighboorsOf(int n){
		ArrayList<Integer> res = new ArrayList<>();
		for (int j = 0; j < matriceAdj.length; j++) {
			if (matriceAdj[n][j] != -1) {
				res.add(j);
			}
		}
		return res;
	}
	
	/**
	 * Trouve les voisins d'un ensemble de noeud
	 * @param l la liste des id des noeuds dont on veut les voisins
	 * @return la liste des voisins des noeuds, si l est vide, renvoie la l'ensemble des noeuds.
	 */
	public ArrayList<Integer> neighboorsOf(ArrayList<Integer> l){
		ArrayList<Integer> neighboors = new ArrayList<>();
		if (l.size() == 0) {
			for (Noeud n : Noeuds) {
				neighboors.add(n.getId());
			}
			return neighboors;
		}
		for (int n : l) {
			ArrayList<Integer> temp = neighboorsOf(n);
			for (Integer integer : temp) {
				if (!neighboors.contains(integer) && !l.contains(integer)) neighboors.add(integer);
			}
		}
		return neighboors;
	}
	
	
	/**
	 * Trouve toutes les coupes du graphes donnant une ou deux partitions (une partition = graphe de départ)
	 * @return une les de PartitionnedGraph
	 */
	public ArrayList<PartitionedGraph> partitionGraph() {
		ArrayList<ArrayList<Integer>> partitions = new ArrayList<>();
		//HashMap<ArrayList<Integer>, PartitionedGraph> t = new HashMap<>();
		ArrayList<Integer> current = new ArrayList<>();
		ArrayList<ArrayList<Integer>> buffer = new ArrayList<>();
		partitions.add(current);
		ArrayList<PartitionedGraph> res = new ArrayList<>();
		do {
			
			partitions.addAll(buffer);
			buffer.clear();
			for (ArrayList<Integer> l : partitions) {
				ArrayList<Integer> neighboors = neighboorsOf(l);
				PartitionedGraph p = new PartitionedGraph(this);
				for (Integer i : neighboors) {
					for (Integer j : l) {
						p.matriceAdj[i][j] = -1;
						p.matriceAdj[j][i] = -1;
					}
					ArrayList<Integer> a = new ArrayList<>(l);
					a.add(i);
					a.sort(new Comparator<Integer>() {

						@Override
						public int compare(Integer o1, Integer o2) {
							return o1 - o2;
						}
						
					});
					if (!buffer.contains(a))buffer.add(a);
				}
				res.add(p);
				
			}
			//System.out.println(buffer.size());
			partitions.clear();
			System.out.println(buffer);
		}while(buffer.size() != 0);
		return res;
	}
	
	/*
	// pour tester partitionGraph()
	public static void main(String[] args) {
		Graphe g = new Graphe(5);
		g.matriceAdj[0][1] = 1;
		g.matriceAdj[1][0] = 1;
		g.matriceAdj[0][3] = 1;
		g.matriceAdj[3][0] = 1;
		g.matriceAdj[1][2] = 1;
		g.matriceAdj[2][1] = 1;
		g.matriceAdj[2][3] = 1;
		g.matriceAdj[3][2] = 1;
		g.matriceAdj[3][4] = 1;
		g.matriceAdj[4][3] = 1;
		ArrayList<PartitionedGraph> l =g.partitionGraph();
		for (PartitionedGraph partitionedGraph : l) {
			System.out.println(l);
		}
	}*/
	
	@Override
	public String toString() {
		String res = "";
		for (int i = 0; i < matriceAdj.length; i++) {
			for (int j = 0; j < matriceAdj[i].length; j++) {
				res+= matriceAdj[i][j] + "\t";
			}
			res+= "\n";
		}
		res+="\n";
		return res;
	}
}
