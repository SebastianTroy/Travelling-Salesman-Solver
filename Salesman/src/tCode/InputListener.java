package tCode;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

public class InputListener implements MouseInputListener, MouseWheelListener, KeyListener, ComponentListener
	{
		public int mouseX, mouseY;
		private final boolean[] keys = new boolean[65536], mouseButtons = new boolean[3];

		public InputListener()
			{
				for (int i = 0; i < keys.length; i++)
					{
						keys[i] = false;
					}
				for (int i = 0; i < mouseButtons.length; i++)
					{
						mouseButtons[i] = false;
					}
			}

		@Override
		public void mouseClicked(MouseEvent e)
			{
				Hub.renderer.getRenderableObject().mouseClicked(e);
			}

		@Override
		public void mousePressed(MouseEvent e)
			{
				mouseButtons[e.getButton()] = true;
				
				Hub.renderer.getRenderableObject().mousePressed(e);
			}

		@Override
		public void mouseReleased(MouseEvent e)
			{
				mouseButtons[e.getButton()] = false;
				
				Hub.renderer.getRenderableObject().mouseReleased(e);
			}

		@Override
		public void mouseDragged(MouseEvent e)
			{
				mouseX = e.getX();
				mouseY = e.getY();
				
				Hub.renderer.getRenderableObject().mouseDragged(e);
			}

		@Override
		public void mouseMoved(MouseEvent e)
			{
				mouseX = e.getX();
				mouseY = e.getY();
				
				Hub.renderer.getRenderableObject().mouseMoved(e);
			}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e)
			{
				Hub.renderer.getRenderableObject().mouseWheelMoved(e);
			}

		@Override
		public void mouseEntered(MouseEvent e)
			{
				Hub.renderer.getRenderableObject().mouseEntered(e);
			}

		@Override
		public void mouseExited(MouseEvent e)
			{
				Hub.renderer.getRenderableObject().mouseExited(e);
			}

		@Override
		public final void keyPressed(KeyEvent e)
			{
				int code = e.getKeyCode();
				if (code > 0 && code < keys.length)
					keys[code] = true;

				if (e.getKeyCode() == 27) // 'Esc' key
					{
						Tools.exitConfirmationWindow("Do you want to Quit?");
					}

				Hub.renderer.getRenderableObject().keyPressed(e);
			}

		@Override
		public final void keyReleased(KeyEvent e)
			{
				int code = e.getKeyCode();
				if (code > 0 && code < keys.length)
					keys[code] = false;

				Hub.renderer.getRenderableObject().keyReleased(e);
			}

		@Override
		public void keyTyped(KeyEvent e)
			{
				Hub.renderer.getRenderableObject().keyTyped(e);
			}

		@Override
		public void componentResized(ComponentEvent e)
			{
				Hub.canvasWidth = Hub.frame.getWidth() - Hub.frame.getInsets().left - Hub.frame.getInsets().right;
				Hub.canvasHeight = Hub.frame.getHeight() - Hub.frame.getInsets().top - Hub.frame.getInsets().bottom;

				Hub.renderer.getRenderableObject().componentResized(e);
			}

		@Override
		public void componentMoved(ComponentEvent e)
			{
				Hub.renderer.getRenderableObject().componentMoved(e);
			}

		@Override
		public void componentShown(ComponentEvent e)
			{
				Hub.renderer.getRenderableObject().componentShown(e);
			}

		@Override
		public void componentHidden(ComponentEvent e)
			{
				Hub.renderer.getRenderableObject().componentHidden(e);
			}
	}