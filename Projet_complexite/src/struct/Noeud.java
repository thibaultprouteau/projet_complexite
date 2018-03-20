package struct;

import java.util.LinkedList;

public class Noeud {
	private LinkedList<Arc> successeurs;
	private int nodeId;

	/**
	 * Noeud of Graphe.
	 * @param nodeId id of the node in the graph
	 */
	public Noeud(int nodeId) {
		super();
		this.successeurs = new LinkedList<Arc>();
		this.nodeId = nodeId;
	}
	

	public LinkedList<Arc> getSuccesseurs() {
		return successeurs;
	}

	public void setSuccesseurs(LinkedList<Arc> successeurs) {
		this.successeurs = successeurs;
	}

	public int getNoeudId() {
		return nodeId;
	}

	public void setNoeudId(int nodeId) {
		this.nodeId = nodeId;
	}
	/** 
	 * Creates a new Node
	 * @param id id of the node
	 * @return Noeud
	 */
	public Noeud creerNoeud(int nodeId) {
		return new Noeud(nodeId);
	}
	
	/**
	 * Prints the node in parameter.
	 * @param n Noeud
	 */
	public static void printNoeud(Noeud n) {
		System.out.print(n.getNoeudId());
	}
	
}
