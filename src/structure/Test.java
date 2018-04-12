package structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Test {

	public static void main(String[] args) {
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
		
		initPop(h, 20);
	}
	
	
	public static void geneticAlgo() {
		Graphe h = new Graphe();
		PartitionedGraph p;
		int alpha;
		int beta;
		
		initPop(h, 20);
		
		//evaluatePop();
		boolean stopCondition = true;
		while(!stopCondition) {
			//selectBestIndiv();
			breed();
			//for all the children produced
				//fitness(p,alpha,beta);
			replacePop();
		}
	}
	
	//initialize population of n individuals from graph g
	public static ArrayList initPop(Graphe g, int n) {
		ArrayList<PartitionedGraph> pop = new ArrayList();
		PartitionedGraph pg;
		
		for(int i = 0; i < n; i++) {
			
			pg = g.randomPartition();
			pop.add(pg);
			System.out.println(pg);
			
		}
		return pop;
	}
	
	
	
	
	
	public static void breed() {
		crossover();
		mutate();
	}
	
	public static void crossover() {
		
	}
	
	public static void mutate() {
		
	}
	
	
	public static void replacePop() {
		
	}

}
