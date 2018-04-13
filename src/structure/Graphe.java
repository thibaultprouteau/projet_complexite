package structure;

import java.lang.reflect.Array;
import java.util.AbstractMap.SimpleEntry;

//import graph.PartitionedGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

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

	// Ajouter un noeud à la liste de noeuds
	public void ajoutNoeud(Noeud n) {
		Noeuds.add(n);
	}

	// init graph sans arcs
	public Graphe() {
		matriceAdj = new int[Noeuds.size()][Noeuds.size()];

		for (int i = 0; i < matriceAdj.length; i++) {
			for (int j = 0; j < matriceAdj[i].length; j++) {
				matriceAdj[i][j] = -1;
			}
		}
	}

	/**
	 * genere un graphe poss�dent nb_noeud noeuds de poid 1 et 0 arcs
	 * 
	 * @param nb_noeud
	 *            le nombre de noeud du graphe a g�n�rer
	 */
	public Graphe(int nb_noeud) {
		matriceAdj = new int[nb_noeud][nb_noeud];
		for (int i = 0; i < nb_noeud; i++) {
			Noeuds.add(new Noeud(i, 1));
		}
		for (int i = 0; i < nb_noeud; i++) {
			for (int j = 0; j < nb_noeud; j++) {
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

	// ajout arc entre noeud n1 et noeud n2 avec un cout = ct
	public void ajoutArc(Noeud n1, Noeud n2, int ct) {

		int id1 = Noeuds.indexOf(n1);
		int id2 = Noeuds.indexOf(n2);

		// car graphe non-orirenté
		matriceAdj[id1][id2] = ct;
		matriceAdj[id2][id1] = ct;
	}

	// suppression d'un arc entre le noeud n1 et n2
	public void supprArc(Noeud n1, Noeud n2) {
		int id1 = Noeuds.indexOf(n1);
		int id2 = Noeuds.indexOf(n2);

		// car graphe non-orirenté
		matriceAdj[id1][id2] = -1;
		matriceAdj[id2][id1] = -1;
	}

	// combien de sommets dans mon graphe
	public int nombreSommets() {
		return Noeuds.size();
	}

	// combien d'arcs dans le graphe
	public int nombreArcs() {
		int k = 0;

		for (int i = 0; i < matriceAdj.length; i++) {
			for (int j = 0; j < matriceAdj[i].length; j++) {
				if (matriceAdj[i][j] >= 0)
					k = k + 1;
			}
		}

		return k / 2; // matrice symmetrique par rapport a i = j car non-orientée.
	}

	// existe-t-il un arc entre n1 et n2?
	public boolean hasArc(Noeud n1, Noeud n2) {
		boolean isThere = false;

		int id1 = Noeuds.indexOf(n1);
		int id2 = Noeuds.indexOf(n2);

		if (matriceAdj[id1][id2] >= 0 && matriceAdj[id2][id1] >= 0)
			isThere = true;

		return isThere;
	}

	/**
	 * Gen�re al�atoirement un graphe connexe avec nb_node noeuds et (nb_node-1)
	 * arcs de poid [1 ; poid_max]
	 * 
	 * @param nb_noeud
	 *            le nombre de noeud
	 * @param poid_max
	 *            la valeure maximum qu'un poid peut prendre
	 * @return si nb_noeud > 0 && poid_max > 0: retourne le graphe </br>
	 *         sinon : retourne null
	 */
	public static Graphe generateSimpleGraph(int nb_noeud, int poid_max) {
		if (nb_noeud <= 0 || poid_max <= 0)
			return null;

		Graphe res = new Graphe(nb_noeud);
		for (int i = 1; i < nb_noeud; i++) {
			int noeud2 = (int) (Math.random() * (i));
			int poid = (int) (Math.random() * (poid_max)) + 1;
			res.matriceAdj[i][noeud2] = poid;
			res.matriceAdj[noeud2][i] = poid;
		}
		return res;

	}

	/**
	 * Gen�re al�atoirement un graphe connexe avec nb_noeud et [ nb_noeud-1 ; (
	 * nb_noeud * ( nb_noeud - 1 ) )/ 2 ] arcs de poid [1 ; poid_max]
	 * 
	 * @param nb_noeud
	 *            le nombre de noeud
	 * @param poid_max
	 *            la valeure maximum qu'un poid peut prendre
	 * @return si nb_noeud > 0 && poid_max > � : retourne le graphe </br>
	 *         sinon : retourne null
	 */
	public static Graphe generateGraph(int nb_noeud, int poid_max) {
		Graphe res = generateSimpleGraph(nb_noeud, poid_max);
		if (res == null)
			return null;
		int min = nb_noeud - 1;
		int max = (nb_noeud * (nb_noeud - 1)) / 2;
		int nb_arc = (int) (Math.random() * (max - min) + min);
		ArrayList<SimpleEntry<Integer, Integer>> l = new ArrayList();
		for (int i = 1; i < nb_noeud; i++) {
			for (int j = 0; j < i; j++) {
				l.add(new SimpleEntry(i, j));
			}

		}
		for (int i = 0; i < nb_arc; i++) {
			SimpleEntry<Integer, Integer> n = l.get((int) (Math.random() * l.size()));
			int poid = (int) (Math.random() * poid_max) + 1;
			res.matriceAdj[n.getKey()][n.getValue()] = poid;
			res.matriceAdj[n.getValue()][n.getKey()] = poid;
			l.remove(n);
		}
		return res;
	}

	/**
	 * Trouve les voisins d'un noeud d'id n
	 * 
	 * @param n
	 *            l'id du noeud
	 * @return la liste des voisins du noeud
	 */
	public ArrayList<Integer> neighboorsOf(int n) {
		ArrayList<Integer> res = new ArrayList<>();
		for (int j = 0; j < matriceAdj.length; j++) {
			if (matriceAdj[n][j] != -1) {
				res.add(j);
			}
		}
		return res;
	}

	/**
	 * O(n) Trouve les voisins d'un ensemble de noeud
	 * 
	 * @param l
	 *            la liste des id des noeuds dont on veut les voisins
	 * @return la liste des voisins des noeuds, si l est vide, renvoie la l'ensemble
	 *         des noeuds.
	 */
	public ArrayList<Integer> neighboorsOf(ArrayList<Integer> l) {
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
				if (!neighboors.contains(integer) && !l.contains(integer))
					neighboors.add(integer);
			}
		}
		return neighboors;
	}

	/**
	 * O(2^n) <-- pas top top Trouve toutes les coupes du graphes donnant une ou
	 * deux partitions (une partition = graphe de d�part)
	 * 
	 * @return une les de PartitionnedGraph
	 */
	/*
	 * public ArrayList<PartitionedGraph> partitionGraph() {
	 * ArrayList<ArrayList<Integer>> partitions = new ArrayList<>();
	 * //HashMap<ArrayList<Integer>, PartitionedGraph> t = new HashMap<>();
	 * ArrayList<Integer> current = new ArrayList<>(); ArrayList<ArrayList<Integer>>
	 * buffer = new ArrayList<>(); partitions.add(current);
	 * ArrayList<PartitionedGraph> res = new ArrayList<>(); do {
	 * 
	 * partitions.addAll(buffer); buffer.clear(); for (ArrayList<Integer> l :
	 * partitions) { ArrayList<Integer> neighboors = neighboorsOf(l);
	 * PartitionedGraph p = new PartitionedGraph(this); for (Integer i : neighboors)
	 * { for (Integer j : l) { p.matriceAdj[i][j] = -1; p.matriceAdj[j][i] = -1; }
	 * ArrayList<Integer> a = new ArrayList<>(l); a.add(i); a.sort(new
	 * Comparator<Integer>() {
	 * 
	 * @Override public int compare(Integer o1, Integer o2) { return o1 - o2; }
	 * 
	 * }); if (!buffer.contains(a))buffer.add(a); } res.add(p);
	 * 
	 * } //System.out.println(buffer.size()); partitions.clear();
	 * System.out.println(buffer); }while(buffer.size() != 0); return res; }
	 */

	public PartitionedGraph randomPartition() {
		ArrayList<Integer> partition = new ArrayList<>();
		for (int i = 0; ((double) i / (Noeuds.size())) <= Math.random(); i++) {
			ArrayList<Integer> temp = neighboorsOf(partition);
			partition.add(temp.get((int) (Math.random() * temp.size())));
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

	public ArrayList<PartitionedGraph> voisins(PartitionedGraph p) {
		ArrayList<ArrayList<Integer>> partitions = p.getSousGraphes();
		ArrayList<ArrayList<ArrayList<Integer>>> res = new ArrayList<>();
		for (int i = 0; i < partitions.size(); i++) { // pour chaque partition
			ArrayList<Integer> a = partitions.get(i); // a = une partition
			for (int j = 0; j < a.size(); j++) { // pour chaque noeud de a
				int n = a.get(j); // n = un noeud de a

				for (int k = 0; k < partitions.size(); k++) { // la partition qui gagne un noeud
					ArrayList<ArrayList<Integer>> b = new ArrayList<>();
					boolean boo = true;
					if (k != i) {

						for (int l = 0; l < partitions.size(); l++) {
							ArrayList<Integer> c = new ArrayList<>(partitions.get(l));
							if (l == i)
								c.remove((Integer) n);
							else if (l == k) {
								c.add((Integer) n);
							}

							if (canBeAPart(c)) {
								if (c.size() != 0) b.add(c);								
							}
							else {
								// System.out.println("~"+c);
								boo = false;
							}
						}

						if (boo)
							res.add(b);

					}

					// if (boo)res.add(b);
				}
				boolean boo = true;
				ArrayList<ArrayList<Integer>> b = new ArrayList<>();

				for (int l = 0; l < partitions.size() + 1; l++) {
					ArrayList<Integer> c;
					try {
						c = new ArrayList<>(partitions.get(l));
					} catch (Exception e) {
						c = new ArrayList<>();
					}
					if (l == i)
						c.remove((Integer) n);
					else if (l == partitions.size()) {
						c.add((Integer) n);
					}

					if (canBeAPart(c))
						b.add(c);
					else {
						// System.out.println("~"+c);
						boo = false;
					}
				}

				if (boo)
					res.add(b);

			}
		}
		ArrayList<PartitionedGraph> parts = new ArrayList<>();
		for (ArrayList<ArrayList<Integer>> arrayList : res) {
			parts.add(PartitionedGraph.PartitionnedGraphFromPartitions(this, arrayList));
		}
		// System.out.println(res);
		// System.out.println(parts);

		return parts;
	}

	public boolean canBeAPart(ArrayList<Integer> part) {
		if (part.size() == 0)
			return true;
		int n = 0;
		ArrayList<Integer> connected = new ArrayList<>();
		connected.add(part.get(0));
		boolean boo = true;
		while (boo) {
			boo = false;
			ArrayList<Integer> temp = neighboorsOf(connected);
			for (Integer integer : temp) {
				if (part.contains(integer)) {
					boo = true;
					connected.add(integer);
				}
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
		Graphe f = generateGraph(50, 10);
		f.geneticAlgo(1, 0);
		
		/*PartitionedGraph p;// = g.randomPartition();

		PartitionedGraph q = g.randomPartition();
		ArrayList<ArrayList<Integer>> a = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			ArrayList<Integer> buff = new ArrayList<>();
			buff.add(i);
			a.add(buff);
		}
		p = PartitionedGraph.PartitionnedGraphFromPartitions(g, a);
		System.out.println(g.voisins(p));
		//PartitionedGraph q = PartitionedGraph.PartitionnedGraphFromPartitions(g,new ArrayList<>());
		//System.out.println(p);
		//System.out.println("Balance de p = " + g.balance(p));
		//g.n(p);

		
		System.out.println(p);
		System.out.println("Balance de p = " + g.balance(p));
		// g.n(p);
		
		f();*/

		// -------------------------------------------------------------------------------------------------------------------

		
	}
	public static void f() {
		ArrayList<Graphe> listeGraphes = new ArrayList<>();
		ArrayList<PartitionedGraph> listePartitionGraphes = new ArrayList<>();

		Chrono chrono = new Chrono();

		chrono.start();

		int nb = 0;
		
		for (int i = 10; i <= 10; i += 5) {
			for (int j = 0; j < 1; j++) {
				Graphe graphe = generateGraph(i, 5);

				listeGraphes.add(graphe);
				listePartitionGraphes.add(graphe.randomPartition());
				nb++;
			}

		}
		chrono.stop();
		System.out.println(chrono.getDureeNs());
		
		System.out.println(nb);
		// System.out.println(chrono.getDureeNs());

		ArrayList<Long> listeChrono = new ArrayList<>();

		for (int i = 0; i < listeGraphes.size(); i++) {
			chrono.start();
			listeGraphes.get(i).tabou(listePartitionGraphes.get(i));
			chrono.stop();

			listeChrono.add(chrono.getDureeNs());
		}

		Long res;
		int count = 5;
		Long temp1 =(long) 0;
		for (int i = 0; i < listeChrono.size(); i++) {
			System.out.println(listeChrono.get(i));
			temp1 = temp1 + listeChrono.get(i);
			if ((i+1) % 10 == 0) {
				res = temp1 / 10;
				count = count + 5;
				System.out.println("Temps moyen pour un graphe de " + count + ": " + res + " ns");
				temp1 = (long) 0;
			}

		}

		System.out.println("Temps moyen: " + temp1 / listeChrono.size());


	}

	@Override
	public String toString() {
		String res = "";
		for (int i = 0; i < matriceAdj.length; i++) {
			for (int j = 0; j < matriceAdj[i].length; j++) {
				res += matriceAdj[i][j] + "\t";
			}
			res += "\n";
		}
		res += "\n";
		return res;
	}

	public int cut(Graphe v1, Graphe v2) {

		int somme = 0;

		for (int i = 0; i < v1.Noeuds.size(); i++) {
			for (int j = 0; j < v2.Noeuds.size(); j++) {

				if ((hasArc(v1.Noeuds.get(i), v2.Noeuds.get(j))) == true) {
					somme = somme + matriceAdj[v1.Noeuds.get(i).getId()][v2.Noeuds.get(j).getId()];
				}

			}

		}

		return somme;

	}

	public int valueArc(int n1, int n2) {
		int value = 0;

		if (n1 > n2) {
			if (matriceAdj[n1][n2] > 0)
				value = matriceAdj[n1][n2];

		} else {
			if (matriceAdj[n2][n1] > 0)
				value = matriceAdj[n2][n1];
		}
		return value;
	}

	public float ratioCut(PartitionedGraph p1) {

		float ratio = 0;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		ArrayList<Integer> noeudsPapa = new ArrayList<Integer>();
		int value = 0;

		for (int k = 0; k < this.Noeuds.size(); k++) {
			noeudsPapa.add(this.Noeuds.get(k).getId());
		}

		for (int i = 0; i < p1.getSousGraphes().size(); i++) {
			temp = neighboorsOf(p1.getSousGraphes().get(i));

			for (int j = 0; j < noeudsPapa.size(); j++) {
				for (int h = 0; h < temp.size(); h++) {
					value = value + valueArc(noeudsPapa.get(j), temp.get(h));
				}

			}
			ratio = ratio + (value / sumPoidsSousGraphe(p1, i));
		}

		return ratio;
	}


	public PartitionedGraph quand_un_papa_et_une_maman_s_aime_beaucoup_le_papa_met_un_petit_milliard_de_graine_dans_la_maman_et_neuf_mois_plustard_un_bebe(PartitionedGraph maman, PartitionedGraph papa){
		
		int [][] matriceBebe = new int [maman.matriceAdj.length][maman.matriceAdj.length];
		double prob = 0;
		
		for(int i = 0; i < maman.matriceAdj.length; i++){
			for(int j = 0; j < i; j++){
				prob = ((((maman.matriceAdj[i][j] == -1)? 0:1) + ((papa.matriceAdj[i][j] == -1)? 0:1))) / 2.0;
				int temp = (prob > Math.random()? maman.matriceAdj[i][j]:-1 );
				matriceBebe[i][j] = temp;
				matriceBebe[j][i] = temp;
			}
		}
		
		
		return new PartitionedGraph(matriceBebe, this);
		
	}


	

	// Fitness as mean of absolute difference of Balance to 1 and ratioCut
	public double fitness(PartitionedGraph pg, int alpha, int beta) {
		double balance = pg.balance(pg);
		double ratioCut = pg.ratioCut(pg);
		if (balance < alpha && ratioCut < beta) {
			return 0;
		} else {
			double absBalance = Math.abs(alpha - balance);
			double absRatio = Math.abs(beta - ratioCut);
			return (absBalance + absRatio) / 2;
		}
	}

	public static int sumPoidsGraphe(Graphe g) {

		int weight = 0;

		for (int i = 0; i < g.getNoeuds().size(); i++) {
			weight = ((Noeud) g.getNoeuds().get(i)).getPoids();
		}

		return weight;
	}

	public int sumPoidsSousGraphe(PartitionedGraph pg, int i) {
		int weight = 0;

		for (int j = 0; j < pg.getSousGraphes().get(i).size(); j++) {
			weight = weight + Noeuds.get(pg.getSousGraphes().get(i).get(j)).getPoids();
		}

		return weight;
	}

	public double balance(PartitionedGraph pg) {
		float bal = 0;
		float max = 0;
		float pdbig = 0;
		float nb = pg.getSousGraphes().size();

		for (int i = 0; i < pg.getSousGraphes().size(); i++) {

			float pdi = sumPoidsSousGraphe(pg, i); // poids du sous graphe i
			// System.out.println("pd i : "+pdi);
			if (pdi > max) {
				max = pdi;
			}

			pdbig = pdbig + pdi;// poids du gros graphe
		}
		// System.out.println("pd big : "+pdbig);
		float denom = pdbig / nb;

		bal = max / denom;

		return bal;
	}

	public PartitionedGraph tabou(PartitionedGraph pt) {

		ArrayList<Double> doubleList = new ArrayList<>();

		PartitionedGraph tempGraph = pt;
		//Double scoreABattre = fitness(pt, 2, 2);

		int n = 0;

		while (fitness(pt, 2, 2) != 0 || n < 10) {
			// System.out.println(pt);

			ArrayList<PartitionedGraph> partionList = voisins(pt); // recupere l'ensemble des partitions a 1 de
																	// diff�rence

			for (PartitionedGraph p : partionList) {
				doubleList.add(fitness(p, 2, 2)); // ajoute le score de la partition � la liste des scores
			}

			double temp = doubleList.get(0);

			for (int i = 1; i < doubleList.size(); i++) {

				if (temp >= doubleList.get(i)) {

					temp = doubleList.get(i); // recupere le meilleur score
					tempGraph = partionList.get(i); // recupere la partition graph correspondant

				}
			}

			/*if (temp < scoreABattre) {
				 // change la partition de graphe par la meilleure trouv�
				scoreABattre = temp; // change la valeur du meilleur score !
			}*/
			pt = tempGraph;
			doubleList.clear();
			n++;
			

		}
		
		if(fitness(pt, 2, 2) == 0){
			System.out.println("YOUPIIII");
		}

		return pt;
	}

	public PartitionedGraph geneticAlgo(int alpha, int beta) {
        //PartitionedGraph p = new PartitionedGraph();
        
        int iterationNb = 0;
        int maxIter = 100;

        ArrayList<PartitionedGraph> init = initPop(40);

        ArrayList<PartitionedGraph> bestPop;
        ArrayList<PartitionedGraph> newGen;
        ArrayList<PartitionedGraph> newPop = new ArrayList();
        
        
        while ( !hasFitest(init, alpha, beta) && iterationNb < maxIter) {
        	
                bestPop = evaluatePop(init, alpha, beta); // selects best indiv. of pop
                newGen = breed(bestPop); //Crossover to create children
                newPop = newPop(bestPop, newGen); //mix of new and old generation     
                
                System.out.println(iterationNb++ +" "+ newPop.size());
        }
        
        PartitionedGraph bestest = newPop.get(0);
        
        
        for (int i = 0; i < newPop.size(); i++) {
                    //fitns.add(g.fitness(pop.get(i), alpha, beta));
        	if (fitness(newPop.get(i), alpha, beta) < fitness(bestest, alpha, beta)) { //if fitness smaller than
        		bestest = newPop.get(i);
           }
            
        }
        
        System.out.println("The best solution is : " + bestest);
        System.out.println("Its fitness value is : " + fitness(bestest, alpha,beta));
        return bestest;
	}
	
	public boolean hasFitest(ArrayList<PartitionedGraph> pop,int alpha, int beta){
		ArrayList<Double> fitnesses = new ArrayList();
		
		for(int i = 0; i < pop.size(); i++){
			fitnesses.add(fitness(pop.get(i),alpha,beta));
		}
		
		if(fitnesses.contains(0)){
			return true;
		}
		
		return false;
	}

// initialize population of n individuals from graph g
	public ArrayList<PartitionedGraph> initPop(int n) {
        ArrayList<PartitionedGraph> pop = new ArrayList();
        PartitionedGraph pg;

        for (int i = 0; i < n; i++) {

                pg = randomPartition();
                pop.add(pg);
                System.out.println(pg);

        }
        return pop;
	}

// returns best individuals apt for reprod (half of population)
	public ArrayList<PartitionedGraph> evaluatePop(ArrayList<PartitionedGraph> pop, int alpha, int beta) {

        ArrayList<PartitionedGraph> bestIndivs = new ArrayList();
        ArrayList<PartitionedGraph> tmp = new ArrayList<>(pop);
        System.out.println(pop.size());
        
        
        for (int j = 0; j < pop.size() / 2; j++) {
        	int k = 0;        
                int i = 0;
                while(i < tmp.size()){
                        //fitns.add(g.fitness(pop.get(i), alpha, beta));
                        if (fitness(tmp.get(i), alpha, beta) < fitness(tmp.get(k), alpha, beta)) { //if fitness smaller than
                                k = i;
                        }
                        i++;        
                }
                if(tmp.size() != 0)
                	bestIndivs.add(tmp.remove(k));
        }
        
     /*   for(int i = 0; i < bestIndivs.size(); i ++) {
                tmp.get(i);
        }
*/
        return bestIndivs;
	}

//new generation of children --> crossover and mutation
	public ArrayList<PartitionedGraph> breed(ArrayList<PartitionedGraph> pop) {
        
        ArrayList<PartitionedGraph> tmp = new ArrayList<>(pop);
        PartitionedGraph papa;
        PartitionedGraph mama;
        
        ArrayList<PartitionedGraph> children = new ArrayList();
        
        
        while(!tmp.isEmpty()) {        
                Random rn = new Random();
                papa = tmp.remove(rn.nextInt(tmp.size()));
                if(!tmp.isEmpty()) {        
                        Random rm = new Random();
                        mama = tmp.remove(rm.nextInt(tmp.size()));
                        
                        
                        PartitionedGraph p1 = quand_un_papa_et_une_maman_s_aime_beaucoup_le_papa_met_un_petit_milliard_de_graine_dans_la_maman_et_neuf_mois_plustard_un_bebe(papa,mama);
                        PartitionedGraph p2= quand_un_papa_et_une_maman_s_aime_beaucoup_le_papa_met_un_petit_milliard_de_graine_dans_la_maman_et_neuf_mois_plustard_un_bebe(papa,mama);
                        children.add(p1);
                        children.add(p2);
                }
        }
        
        return children;
        
	}

	public static ArrayList<PartitionedGraph> newPop(ArrayList<PartitionedGraph> oldGen, ArrayList<PartitionedGraph> newGen) {
        ArrayList<PartitionedGraph> newPop = new ArrayList();
        
        newPop.addAll(oldGen);
        newPop.addAll(newGen);
        
        return newPop;
}
	
	
}
