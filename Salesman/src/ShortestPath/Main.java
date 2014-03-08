package ShortestPath;

import tCode.TCode;

public class Main extends TCode
	{
		public static TSP tsp = new TSP();
		public static Info info = new Info();

		public static void main(String[] args)
			{
				new Main(800, 600, true, false);
			}

		public Main(int width, int height, boolean framed, boolean resizable)
			{
				super(width, height, framed, resizable);

				DEBUG = true;
				FORCE_SINGLE_THREAD = true;
				programName = "Travelling Salesman Solver";
				
				begin(tsp);
			}
	}
