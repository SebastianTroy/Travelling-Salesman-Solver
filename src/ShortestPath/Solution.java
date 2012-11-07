package ShortestPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import TroysCode.Tools;
import TroysCode.hub;

public class Solution
	{
		Node[] solution;
		double fitness;

		// create new random solution
		public Solution()
			{
				reset();
			}

		// Create a mutated solution from a parent
		public Solution(Solution parent)
			{
				solution = parent.solution.clone();

				mutate();

				reCalculate();
			}

		// Create a mutated solution from two parents
		public Solution(Solution par1, Solution par2)
			{
				solution = new Node[par1.solution.length];

				int crossA = Tools.randInt(0, par1.solution.length);
				int crossB = Tools.randInt(0, par1.solution.length);

				boolean isPar1 = true;

				for (int i = 0; i < par1.solution.length; i++)
					{
						if (i == crossA || i == crossB)
							isPar1 = !isPar1;

						if (isPar1)
							solution[i] = par1.solution[i];
						else
							solution[i] = par2.solution[i];
					}

				//fix();
				reCalculate();
			}

		public final void fix()
			{
				ArrayList<Node> unUsedNodes = new ArrayList<Node>();
				ArrayList<Node> reUsedNodes = new ArrayList<Node>();
				for (Node n : hub.simulation.getNodes())
					unUsedNodes.add(n);

				for (Node n : solution)
					{
						if (!unUsedNodes.remove(n))
							reUsedNodes.add(n);
					}

				for (Node n : solution)
					{
						if (reUsedNodes.contains(n))
							{
								reUsedNodes.remove(n);
								n = unUsedNodes.get(0);
								unUsedNodes.remove(0);
							}
					}
			}

		public final void mutate()
			{
				Node[] oldSol = solution.clone();
				
				// Choose two nodes to swap
				int swapOne = Tools.randInt(0, oldSol.length - 1);
				int swapTwo = Tools.randInt(0, oldSol.length - 1);

				for (int i = 0; i < Tools.randInt(1, solution.length / 2); i++)
					{
						// Swap two nodes around
						solution[swapOne] = oldSol[swapTwo];
						solution[swapTwo] = oldSol[swapOne];
					}
			}

		public final void reset()
			{
				solution = hub.simulation.getNodes();
				Collections.shuffle(Arrays.asList(solution));

				reCalculate();
			}

		// calculate fitness
		public final void reCalculate()
			{
				fitness = 0;
				for (int i = 0; i < solution.length - 1; i++)
					fitness += Tools.getVectorLength(solution[i].x, solution[i].y, solution[i + 1].x, solution[i + 1].y);
			}
	}