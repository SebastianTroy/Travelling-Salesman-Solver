package ShortestPath;

import java.awt.event.MouseEvent;

import tCode.Hub;
import tCode.Tools;


public class Node
	{
		public int x, y;

		private boolean selected = false;
		public boolean exists = true;

		public Node(int x, int y)
			{
				this.x = x;
				this.y = y;
			}

		public final void mousePressed(MouseEvent e)
			{
				if (Tools.getVectorLength(e.getX(), e.getY(), x, y) < Hub.tsp.nodeSize / 2)
					{
						selected = true;
					}
			}

		public final void mouseDragged(MouseEvent e)
			{
				if (selected)
					{
						x = e.getX();
						y = e.getY();
					}
				if (x < 0 || x > 785 || y < 0 || y > 560)
					exists = false;
			}

		public final void mouseReleased(MouseEvent e)
			{
				selected = false;
			}
	}