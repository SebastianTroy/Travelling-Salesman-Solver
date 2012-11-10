package ShortestPath;

import java.util.Arrays;
import java.util.Collections;

import TroysCode.Tools;
import TroysCode.hub;

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

				switch (Tools.randInt(0, 3))
					{
					case (0):
						twoOpt();
						break;
					case (1):
						threeOpt();
						break;
					case (2):
						swapNeighbors();
						break;
					case (3):
						fourOpt();
						break;
					}

				calculateTourLength();
			}

		private final void swapNeighbors()
			{
				Node[] oldSol = solution.clone();

				// Choose two nodes to swap
				int swap = Tools.randInt(0, oldSol.length - 1);

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

		private final void twoOpt()
			{
				Node[] oldSol = solution.clone();

				// Choose two nodes to swap
				int swapOne = Tools.randInt(0, oldSol.length - 1);
				int swapTwo = Tools.randInt(0, oldSol.length - 1);

				// Swap two nodes around
				solution[swapOne] = oldSol[swapTwo];
				solution[swapTwo] = oldSol[swapOne];
			}

		private final void threeOpt()
			{
				Node[] oldSol = solution.clone();

				int swapOne = 0;
				int swapTwo = 0;
				int swapThree = 0;

				// Choose two nodes to swap
				swapOne = Tools.randInt(0, oldSol.length - 1);
				while (swapTwo != swapOne)
					swapTwo = Tools.randInt(0, oldSol.length - 1);
				while (swapThree != swapOne && swapThree != swapTwo)
					swapThree = Tools.randInt(0, oldSol.length - 1);

				// Swap two nodes around
				solution[swapOne] = oldSol[swapThree];
				solution[swapTwo] = oldSol[swapTwo];
				solution[swapThree] = oldSol[swapOne];
			}

		private final void fourOpt()
			{
				Node[] oldSol = solution.clone();

				int swapOne = 0;
				int swapTwo = 0;
				int swapThree = 0;
				int swapFour = 0;

				// Choose two nodes to swap
				swapOne = Tools.randInt(0, oldSol.length - 1);
				while (swapTwo != swapOne)
					swapTwo = Tools.randInt(0, oldSol.length - 1);
				while (swapThree != swapOne && swapThree != swapTwo)
					swapThree = Tools.randInt(0, oldSol.length - 1);
				while (swapFour != swapOne && swapFour != swapTwo && swapFour != swapThree)
					swapFour = Tools.randInt(0, oldSol.length - 1);

				// Swap two nodes around
				solution[swapOne] = oldSol[swapThree];
				solution[swapTwo] = oldSol[swapTwo];
				solution[swapThree] = oldSol[swapOne];
			}

		public final void reset()
			{
				solution = hub.tsp.getNodes();
				Collections.shuffle(Arrays.asList(solution));

				calculateTourLength();
			}

		// calculate fitness
		public final void calculateTourLength()
			{
				tourLength = 0;
				for (int i = 0; i < solution.length - 1; i++)
					tourLength += Tools.getVectorLength(solution[i].x, solution[i].y, solution[i + 1].x, solution[i + 1].y);
			}
	}