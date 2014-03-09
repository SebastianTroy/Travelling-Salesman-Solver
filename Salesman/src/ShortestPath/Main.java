package ShortestPath;

import java.io.IOException;

import javax.imageio.ImageIO;

import tCode.TCode;

public class Main extends TCode
	{
		public static final TSP tsp = new TSP();

		/**
		 * The main method, the very first method to be called when the program is run.
		 */
		public static void main(String[] args)
			{
				new Main(true, true);
			}

		public Main(boolean framed, boolean resizable)
			{
				super(framed, resizable);

				DEBUG = true;
				FORCE_SINGLE_THREAD = true;
				programName = "Traveling Salesman solver";
				
				try
					{
						frame.addIconImage(ImageIO.read(Main.class.getResource("/assets/icon.png")));
					}
				catch (IOException e)
					{
						e.printStackTrace();
					}
				
				begin(tsp);
			}
	}
