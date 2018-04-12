package structure;


import java.lang.reflect.Array;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;


public class Graphe {
	
	protected ArrayList<Noeud> Noeuds = new ArrayList();
	protected int[][] matriceAdj;
	
	public ArrayList<Noeud> getNoeuds() {
		return Noeuds;
	}

	public void setNoeuds(ArrayList<Noeud> noeuds) {
		Noeuds = noeuds;
	}

	public int[][] getMatriceAdj() {
		return matriceAdj;
	}

	public void setMatriceAdj(int[][] matriceAdj) {
		this.matriceAdj = matriceAdj;
	}

	//Ajouter un noeud à la liste de noeuds
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
	 * genere un graphe poss�dent nb_noeud noeuds de poid 1 et 0 arcs
	 * @param nb_noeud le nombre de noeud du graphe a g�n�rer
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
		
		//car graphe non-orirenté
		matriceAdj[id1][id2] = ct;
		matriceAdj[id2][id1] = ct;
	}
	
	//suppression d'un arc entre le noeud n1 et n2
	public void supprArc(Noeud n1, Noeud n2) {
		int id1 = Noeuds.indexOf(n1);
		int id2 = Noeuds.indexOf(n2);
		
		//car graphe non-orirenté
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
		
		return k/2; // matrice symmetrique par rapport a i = j car non-orientée.
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
	 * Gen�re al�atoirement un graphe connexe avec nb_node noeuds et (nb_node-1) arcs de poid [1 ; poid_max]
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
	 * Gen�re al�atoirement un graphe connexe avec nb_noeud et [ nb_noeud-1 ; ( nb_noeud * ( nb_noeud - 1 ) )/ 2 ] arcs 
	 * de poid [1 ; poid_max]
	 * @param nb_noeud le nombre de noeud
	 * @param poid_max la valeure maximum qu'un poid peut prendre
	 * @return si nb_noeud > 0 && poid_max > � : retourne le graphe </br> sinon : retourne null
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
	 * O(n)
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
	 * O(2^n) <-- pas top top
	 * Trouve toutes les coupes du graphes donnant une ou deux partitions (une partition = graphe de d�part)
	 * @return une les de PartitionnedGraph
	 */
	/*
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
	*/
	
	public PartitionedGraph randomPartition() {
		ArrayList<Integer> partition = new ArrayList<>();
		for (int i = 0; ((double)i / (Noeuds.size()) ) <= Math.random(); i++) {
			ArrayList<Integer> temp =neighboorsOf(partition);
			partition.add(temp.get((int)(Math.random()*temp.size())));
		}
		ArrayList<ArrayList<Integer>> res = new ArrayList<>();
		res.add(partition);
		return PartitionedGraph.PartitionnedGraphFromPartitions(this, res);
	}
	
	public ArrayList<ArrayList<Integer>> NeighbooringPartition(ArrayList<Integer> partition) {
		ArrayList<Integer> t = neighboorsOf(partition);
		ArrayList<ArrayList<Integer>> res = new ArrayList<>();
		for (Integer i : t) {
			ArrayList<Integer> temp = new ArrayList<>(partition);
			temp.add(i);
			res.add(temp);
		}
		for (Integer i : partition) {
			ArrayList<Integer> temp = new ArrayList<>(partition);
			temp.remove(i);
			res.add(temp);
		}
		for (ArrayList<Integer> arrayList : res) {
			System.out.println(arrayList);
		}
		return res;
	}
	
	public void voisins(PartitionedGraph p) {
		ArrayList<ArrayList<Integer>> partitions = p.getSousGraphes();
		ArrayList<ArrayList<ArrayList<Integer>>> res = new ArrayList<>();
		for (int i = 0; i < partitions.size(); i++) { // pour chaque partition
			ArrayList<Integer> a = partitions.get(i);  //  a = une partition
			for (int j = 0; j < a.size(); j++) { // pour chaque noeud de a
				int n = a.get(j); // n = un noeud de a
				for (int k = 0; k < partitions.size(); k++) { // la partition qui gagne un noeud
					if (k != i) {
					ArrayList<ArrayList<Integer>> b = new ArrayList<>();
					for (int l = 0; l < partitions.size(); l++) {						
						ArrayList<Integer> c = new ArrayList<>(partitions.get(l));
						if (l == i) c.remove((Integer) n);
						else if (l == k) {
							c.add((Integer) n);
						}
						
						if (canBeAPart(c)) b.add(c);
						System.out.println("-"+c);
					}
					ArrayList<Integer> c = new ArrayList<>();
					c.add((Integer)n);
					if (canBeAPart(c)) b.add(c);
					System.out.println("-"+c);
					res.add(b);
					}
				}
			}
		}
		System.out.println(res);
	}
	
	public boolean canBeAPart(ArrayList<Integer> part) {
		if (part.size() == 0) return true;
		int n = 0;
		ArrayList<Integer> connected = new ArrayList<>();
		connected.add(part.remove(0));
		boolean boo = false;
		while (boo);
			boo = false;
			ArrayList<Integer> temp = neighboorsOf(connected);
			for (Integer integer : temp) {
				if (part.contains(integer)) {
					boo = true;
					connected.add(integer);
				}
		}
		return part.size() == connected.size();
	}
	
	// pour tester partitionGraph()
	public static void main(String[] args) {
		Graphe g = new Graphe(6);
		g.matriceAdj[0][1] = 1;
		g.matriceAdj[1][0] = 1;
		g.matriceAdj[0][3] = 1;
		g.matriceAdj[3][0] = 1;
		g.matriceAdj[0][5] = 1;
		g.matriceAdj[5][0] = 1;
		g.matriceAdj[1][2] = 1;
		g.matriceAdj[2][1] = 1;
		g.matriceAdj[2][3] = 1;
		g.matriceAdj[3][2] = 1;
		g.matriceAdj[3][4] = 1;
		g.matriceAdj[4][3] = 1;
		Graphe h = new Graphe(5);
		h.matriceAdj[0][1] = 1;
		h.matriceAdj[0][2] = 1;
		h.matriceAdj[0][3] = 1;
		h.matriceAdj[0][4] = 1;
		h.matriceAdj[1][2] = 1;
		h.matriceAdj[1][3] = 1;
		h.matriceAdj[1][4] = 1;
		h.matriceAdj[2][3] = 1;
		h.matriceAdj[2][4] = 1;
		h.matriceAdj[3][4] = 1;
		h.matriceAdj[1][0] = 1;
		h.matriceAdj[2][0] = 1;
		h.matriceAdj[3][0] = 1;
		h.matriceAdj[4][0] = 1;
		h.matriceAdj[2][1] = 1;
		h.matriceAdj[3][1] = 1;
		h.matriceAdj[4][1] = 1;
		h.matriceAdj[3][2] = 1;
		h.matriceAdj[4][2] = 1;
		h.matriceAdj[4][3] = 1;
		PartitionedGraph p = g.randomPartition();
		PartitionedGraph q = PartitionedGraph.PartitionnedGraphFromPartitions(g,new ArrayList<>());
		System.out.println(p);
		g.n(p);
	}
	
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
	
	public int cut(Graphe v1, Graphe v2){
		
		int somme = 0;
		
		for(int i = 0; i < v1.Noeuds.size(); i++){
			for (int j = 0; j < v2.Noeuds.size(); j++) {
				
				if((hasArc(v1.Noeuds.get(i), v2.Noeuds.get(j))) == true){
					somme = somme + matriceAdj[v1.Noeuds.get(i).getId()][v2.Noeuds.get(j).getId()];
				}
				
			}
			
		}
		
		return somme;
		
	}
	
	public static int sumPoids(Graphe g) {
		int weight = 0;
		
		for(int i = 0; i < g.getNoeuds().size(); i++) {
			weight = ((Noeud) g.getNoeuds().get(i)).getPoids();
		}
		
		return weight;
	}
	
	public static int balance(PartitionedGraph pg) {
		int bal = 0;
		int max = 0;
		int pdbig = 0;
		int nb = 0;
		for(int i = 0; i < pg.getSousGraphes().size(); i++) {
			int pdi = sumPoids(pg.getSousGraphes().get(i)); //poids du sous graphe i
			
			if(pdi > max) {
				max = pdi;
			}
			
			pdbig = pdbig + pdi;
			nb = nb + pg.getSousGraphes().get(i).nombreSommets();
		}
		
		int denom = pdbig/nb;
		
		bal = max/denom;
		
		return bal;
	}
	
	
	
	

	
	
}
