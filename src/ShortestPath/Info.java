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
		private boolean continue_ = false;

		@Override
		protected void initiate()
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void refresh()
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void tick(double secondsPassed)
			{
				if (continue_)
					{
						continue_ = false;
						changeRenderableObject(hub.simulation);
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
				continue_ = true;
			}

		@Override
		protected void mouseReleased(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mouseDragged(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mouseMoved(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mouseWheelScrolled(MouseWheelEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void actionPerformed(ActionEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void keyPressed(KeyEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void keyReleased(KeyEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void keyTyped(KeyEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mouseClicked(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mouseEntered(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mouseExited(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void programGainedFocus(WindowEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void programLostFocus(WindowEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void frameResized(ComponentEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		public void tScrollBarScrolled(TScrollEvent event)
			{
				// TODO Auto-generated method stub

			}

	}
