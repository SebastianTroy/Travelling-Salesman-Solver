package tCode;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import tComponents.TComponent;
import tComponents.TComponentContainer;
import tComponents.utils.events.TActionEvent;
import tComponents.utils.events.TScrollEvent;

public abstract class RenderableObject extends TComponentContainer
	{
		private boolean initiated = false;

		public final void prepare()
			{
				if (!initiated)
					{
						initiated = true;
						setParent(Hub.renderer);
						initiate();
					}
			}

		protected abstract void initiate();

		public abstract void tick(double secondsPassed);

		public final void renderObject(Graphics g)
			{
				render(g);
				renderTComponents(g);
			}

		protected abstract void render(Graphics g);

		protected final void renderTComponents(Graphics g)
			{
				for (TComponent component : getTComponents())
					{
						component.render((Graphics2D) g);
					}
			}

		@Override
		public void tActionPerformed(TActionEvent e)
			{}

		@Override
		public void tScrollEvent(TScrollEvent e)
			{}

		public void keyTyped(KeyEvent e)
			{}

		public void keyPressed(KeyEvent e)
			{}

		public void keyReleased(KeyEvent e)
			{}

		public void mouseWheelMoved(MouseWheelEvent e)
			{}

		public void mouseDragged(MouseEvent e)
			{}

		public void mouseMoved(MouseEvent e)
			{}

		public void mouseClicked(MouseEvent e)
			{}

		public void mousePressed(MouseEvent e)
			{}

		public void mouseReleased(MouseEvent e)
			{}

		public void mouseEntered(MouseEvent e)
			{}

		public void mouseExited(MouseEvent e)
			{}

		public void componentResized(ComponentEvent e)
			{}

		public void componentMoved(ComponentEvent e)
			{}

		public void componentShown(ComponentEvent e)
			{}

		public void componentHidden(ComponentEvent e)
			{}
	}