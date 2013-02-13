package tCode;

import ShortestPath.Info;
import ShortestPath.TSP;

public class Hub
	{
		public static final boolean DEBUG = false;
		public static int screenWidth, screenHeight, frameWidth, frameHeight, canvasWidth, canvasHeight;

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		public static ResourceLoader loader = new ResourceLoader();

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		public static InputListener input = new InputListener();

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		public static Renderer renderer = new Renderer();

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		public static String programName = "Traveling Salesman";
		public static String versionNumber = "0.0";
		private static final boolean FRAMED = true, RESIZABLE = true;
		public static Frame frame = new Frame(800, 600, FRAMED, RESIZABLE);

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		public static TSP tsp = new TSP();
		public static Info info = new Info();
		
		public static void main(String[] args)
			{
				new LoadingScreen();
				frame.setFrame();
				renderer.beginMainThread(tsp);
			}
	}
