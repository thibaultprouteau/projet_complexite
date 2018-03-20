package struct;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Graphe {
	/**
	 * Graphe is a HashMap containing the number of the node as key and the Node as value.
	 * */
	public Graphe() {
		this.graphe = new HashMap<Integer, Noeud>();
	}

	HashMap<Integer, Noeud> graphe;

	/**Adds an Arc to the node if the said node isn't already in its collection. */
	public void ajouterArc(Noeud x, Noeud y, int c) {
		Arc monArc = new Arc(x, y , c);
		if (!x.getSuccesseurs().contains(monArc)) {
			x.getSuccesseurs().add(monArc);
		}
	}

	/**
	 * Adds a node to Graphe
	 * @param n1 Node to be added
	 */
	public void ajouterNoeud(Noeud n1) {
		graphe.put(n1.getNoeudId(), n1);
	}
	
	/**
	 * Prints the Arc.
	 *
	 * @param a Arc
	 * @param n Node
	 */
	public void printArc(Arc a, Noeud n) {
		if(a.getX() == n) {
			System.out.print("Noeud : ");
			Noeud.printNoeud(a.getY());
			System.out.println(" - Cout : " + a.getCost());
		}
		else {
			System.out.print("Noeud : ");
			Noeud.printNoeud(a.getX());
			System.out.println(" - Cout : " + a.getCost());
		}
	}
	
	/**
	 * Prints the graphe.
	 */
	public void print() {
		for (Noeud value : graphe.values()) {
			if (value.getSuccesseurs().isEmpty()) {
				System.out.print(value.getNoeudId());
			} 
			else {
				System.out.println(value.getNoeudId() + " -->  ");
				for(int i = 0; i < value.getSuccesseurs().size(); i++) {
					System.out.print(" - ");
					printArc(value.getSuccesseurs().get(i),value);
				}
					
				
			}
			
			System.out.println();

			
		}

	}
}
