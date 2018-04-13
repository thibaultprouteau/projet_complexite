package structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
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

		//h.evaluatePop(initPop(h, 20), 100, 100);
	}

	public static void geneticAlgo() {
		Graphe h = new Graphe();
		PartitionedGraph p = new PartitionedGraph();
		int alpha;
		int beta;
		
		int iterationNb = 0;
		int maxIter = 10;

		ArrayList<PartitionedGraph> init = initPop(h, 20);

		
		boolean stopCondition = true;
		while ( h.fitness(p,2,2) != 0 || iterationNb < maxIter) {
			ArrayList<PartitionedGraph> bestPop = evaluatePop(h, init, 2, 2); // selects best indiv. of pop
			ArrayList<PartitionedGraph> newGen = breed(bestPop); //Crossover to create children
			newPop(bestPop, newGen); //mix of new and old generation	
		}
	}

	// initialize population of n individuals from graph g
	public static ArrayList<PartitionedGraph> initPop(Graphe g, int n) {
		ArrayList<PartitionedGraph> pop = new ArrayList();
		PartitionedGraph pg;

		for (int i = 0; i < n; i++) {

			pg = g.randomPartition();
			pop.add(pg);
			System.out.println(pg);

		}
		return pop;
	}

	// returns best individuals apt for reprod (half of population)
	public static ArrayList<PartitionedGraph> evaluatePop(Graphe g, ArrayList<PartitionedGraph> pop, int alpha, int beta) {

		ArrayList<PartitionedGraph> bestIndivs = new ArrayList();

		int k = 0;
		for (int j = 0; j < pop.size() / 2; j++) {
			
			for (int i = 0; i < pop.size(); i++) {
				//fitns.add(g.fitness(pop.get(i), alpha, beta));
				if (g.fitness(pop.get(i), alpha, beta) < g.fitness(pop.get(k), alpha, beta)) { //if fitness smaller than
					k = i;
				}
				
			}
			bestIndivs.add(pop.get(k));
		}
		
		for(int i = 0; i < bestIndivs.size(); i ++) {
			System.out.println(pop.get(i));
		}

		return bestIndivs;
	}

	//new generation of children --> crossover and mutation 
	public static ArrayList<PartitionedGraph> breed(ArrayList<PartitionedGraph> pop) {
		
		ArrayList<PartitionedGraph> tmp = pop;
		PartitionedGraph papa;
		PartitionedGraph mama;
		
		ArrayList<PartitionedGraph> children = new ArrayList();
		
		
		if(!tmp.isEmpty()) {	
			Random rn = new Random();
			papa = tmp.remove(rn.nextInt(tmp.size()));
			if(!tmp.isEmpty()) {	
				Random rm = new Random();
				mama = tmp.remove(rm.nextInt(tmp.size()));
				
				children.add(crossover(papa,mama));
			}
		}
		
		return mutate(children);
		
	}

	public static PartitionedGraph crossover(PartitionedGraph pa, PartitionedGraph ma) {

		return null;
	}

	public static ArrayList<PartitionedGraph> mutate(ArrayList<PartitionedGraph> children) {

		return null;
	}

	public static ArrayList<PartitionedGraph> newPop(ArrayList<PartitionedGraph> oldGen, ArrayList<PartitionedGraph> newGen) {
		ArrayList<PartitionedGraph> newPop = new ArrayList();
		
		newPop.addAll(oldGen);
		newPop.addAll(newGen);
		
		return newPop;
	}

}
