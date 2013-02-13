package ShortestPath;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import tCode.Hub;
import tCode.RenderableObject;


public class Info extends RenderableObject
	{
		private boolean backToTSP = false;

		@Override
		protected void initiate()
			{}

		@Override
		public void tick(double secondsPassed)
			{
				if (backToTSP)
					{
						backToTSP = false;
						Hub.renderer.changeRenderableObject(Hub.tsp);
					}
			}

		@Override
		protected void render(Graphics g)
			{
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 800, 600);

				g.setColor(Color.BLACK);
				g.setFont(new Font(g.getFont().toString(), 0, 25));
				g.drawString("This program will try to calculate the shortest Path between", 10, 25);
				g.drawString("all of the 'Nodes' on the screen.", 10, 50);
				g.drawString("Left Click and drag to move Nodes", 10, 100);
				g.drawString("Right click to add a new Node", 10, 125);

				g.drawString("Press 'space' reset all solutions", 10, 175);
				g.drawString("Press 'g' to add a grid of Nodes", 10, 200);
				g.drawString("Press 'r' to add a randomly placed Nodes", 10, 225);
				g.drawString("Press 'd' to delete a random Node", 10, 250);
				g.drawString("Press 's' to shuffle the positions of the current Nodes", 10, 275);
				g.drawString("Press 'c' to clear all Nodes", 10, 300);
			}

		@Override
		public void mousePressed(MouseEvent event)
			{
				backToTSP = true;
			}
	}