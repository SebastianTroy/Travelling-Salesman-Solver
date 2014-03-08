package ShortestPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import tools.NumTools;
import tools.RandTools;

public class Solution
	{
		Node[] solution;
		double tourLength;

		// create new random solution
		public Solution()
			{
				reset();
			}

		// Create a mutated solution from a parent
		public Solution(Solution parent)
			{
				solution = new Node[parent.solution.length];
				int i = 0;
				for (Node n : parent.solution)
					{
						solution[i] = n;
						i++;
					}

				calculateTourLength();

				if (RandTools.getBool())
					swapNeighbors();
				else
					nOpt(RandTools.getInt(2, solution.length / 2));

				calculateTourLength();
			}

		private final void swapNeighbors()
			{
				Node[] oldSol = solution.clone();

				// Choose two nodes to swap
				int swap = RandTools.getInt(0, oldSol.length - 1);

				if (swap == oldSol.length - 1)
				// swap first and last nodes around
					{
						solution[swap] = oldSol[0];
						solution[0] = oldSol[swap];
					}
				else
					{
						// Swap two nodes around
						solution[swap] = oldSol[swap + 1];
						solution[swap + 1] = oldSol[swap];
					}
			}

		private final void nOpt(int numSwaps)
			{
				if (numSwaps >= solution.length)
					{
						reset();
						return;
					}

				Node[] oldSol = solution.clone();

				ArrayList<Integer> swapPoints = new ArrayList<Integer>();
				while (swapPoints.size() < numSwaps)
					{
						int newSwap = RandTools.getInt(0, solution.length - 1);
						boolean unique = true;
						for (Integer swapPoint : swapPoints)
							if (swapPoint == newSwap)
								{
									unique = false;
									break;
								}
						if (unique)
							swapPoints.add(newSwap);
					}

				Integer[] swapPointsCopy = new Integer[swapPoints.size()];
				swapPoints.toArray(swapPointsCopy);
				Collections.shuffle(Arrays.asList(swapPointsCopy));

				for (int i = 0; i < swapPoints.size(); i++)
					{
						solution[swapPoints.get(i)] = oldSol[swapPointsCopy[i]];
					}
			}

		public final void reset()
			{
				solution = Main.tsp.getNodes();
				Collections.shuffle(Arrays.asList(solution));

				calculateTourLength();
			}

		// calculate fitness
		public final void calculateTourLength()
			{
				tourLength = 0;
				for (int i = 0; i < solution.length - 1; i++)
					tourLength += NumTools.distance(solution[i].x, solution[i].y, solution[i + 1].x, solution[i + 1].y);
			}
	}