package structure;

import java.util.ArrayList;

public class Graphe {
	
	private ArrayList Noeuds = new ArrayList();
	private int[][] matriceAdj;
	
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

}
