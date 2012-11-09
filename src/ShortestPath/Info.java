package ShortestPath;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;

import TroysCode.RenderableObject;
import TroysCode.hub;
import TroysCode.T.TScrollEvent;

public class Info extends RenderableObject
	{
		private static final long serialVersionUID = 1L;
		private boolean backToTSP = false;

		@Override
		protected void initiate()
			{
			}

		@Override
		protected void refresh()
			{
			}

		@Override
		protected void tick(double secondsPassed)
			{
				if (backToTSP)
					{
						backToTSP = false;
						changeRenderableObject(hub.tsp);
					}
			}

		@Override
		protected void renderObject(Graphics g)
			{
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 800, 600);

				g.setColor(Color.BLACK);
				g.setFont(new Font(g.getFont().toString(), 0, 30));
				g.drawString("This program should calculate", 10, 25);
				g.drawString("the shortest Path between all the points.", 10, 50);
				g.drawString("Left Click and drag to move Nodes", 10, 75);
				g.drawString("Right click to add a new Node", 10, 100);
				g.drawString("Press Space to re-calculate the path", 10, 125);
				g.drawString("For some reason the simulation becomes stale after", 10, 150);
				g.drawString("a while, if so, press space", 10, 175);
			}

		@Override
		protected void mousePressed(MouseEvent event)
			{
				backToTSP = true;
			}

		@Override
		protected void mouseReleased(MouseEvent event)
			{
			}

		@Override
		protected void mouseDragged(MouseEvent event)
			{
			}

		@Override
		protected void mouseMoved(MouseEvent event)
			{
			}

		@Override
		protected void mouseWheelScrolled(MouseWheelEvent event)
			{
			}

		@Override
		protected void actionPerformed(ActionEvent event)
			{
			}

		@Override
		protected void keyPressed(KeyEvent event)
			{
			}

		@Override
		protected void keyReleased(KeyEvent event)
			{
			}

		@Override
		protected void keyTyped(KeyEvent event)
			{
			}

		@Override
		protected void mouseClicked(MouseEvent event)
			{
			}

		@Override
		protected void mouseEntered(MouseEvent event)
			{
			}

		@Override
		protected void mouseExited(MouseEvent event)
			{
			}

		@Override
		protected void programGainedFocus(WindowEvent event)
			{
			}

		@Override
		protected void programLostFocus(WindowEvent event)
			{
			}

		@Override
		protected void frameResized(ComponentEvent event)
			{
			}

		@Override
		public void tScrollBarScrolled(TScrollEvent event)
			{
			}
	}