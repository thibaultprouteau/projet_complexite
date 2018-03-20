package struct;

/**
 * @author Thibault 
 * @category Object
 * 
 */

public class Arc {
	private Noeud x;
	private Noeud y;
	private int Cost;
	
	/**@param Noeud x source node
	 * @param Noeud y destination node
	 * @param int c cost*/
	public Arc(Noeud x, Noeud y, int c) {
		this.x=x;
		this.y=y;
		this.Cost=c;
	}


	public Noeud getX() {
		return x;
	}


	public void setX(Noeud x) {
		this.x = x;
	}


	public Noeud getY() {
		return y;
	}


	public void setY(Noeud y) {
		this.y = y;
	}
	
	/**
	 * @return int cost cost of the said arc
	 */
	public int getCost() {
		return Cost;
	}

	/**
	 * @param cost
	 */
	public void setCost(int cost) {
		this.Cost = cost;
	}
	
	
	/**Displays the node and its successors */
	public void printArc(Arc a, Noeud n) {
		if(a.getX() == n) {
			Noeud.printNoeud(a.getY());
			System.out.println("--" + a.getCost());
		}
		else {
			Noeud.printNoeud(a.getX());
			System.out.println("--" + a.getCost());
		}
		
	}

	
	
	
	
	
	
}
