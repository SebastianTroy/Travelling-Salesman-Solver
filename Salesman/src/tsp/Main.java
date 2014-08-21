package tsp;

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

				FORCE_SINGLE_THREAD = true;
				
				versionNumber = "2.1";
				programName = "Traveling Salesman solver";
				
				try
					{
						frame.addIconImage(ImageIO.read(Main.class.getResource("icon.png")));
					}
				catch (IOException e)
					{
						e.printStackTrace();
					}
				
				begin(tsp);
			}
	}
