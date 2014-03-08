package ShortestPath;

import tCode.TCode;

public class Hub extends TCode
	{
		public static TSP tsp = new TSP();
		public static Info info = new Info();

		public static void main(String[] args)
			{
				new Hub(800, 600, true, false);
			}

		public Hub(int width, int height, boolean framed, boolean resizable)
			{
				super(width, height, framed, resizable);

				DEBUG = true;
				FORCE_SINGLE_THREAD = true;
				programName = "Travelling Salesman Solver";
				
				begin(tsp);
			}
	}
