package tCode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Renderer extends java.awt.Canvas
	{
		private static final long serialVersionUID = 1L;

		private RenderableObject currentRenderableObject;
		private int fps;
		private Font font;

		public void beginMainThread(RenderableObject firstObject)
			{
				changeRenderableObject(firstObject);

				setSize(Hub.canvasWidth, Hub.canvasHeight);

				addMouseListener(Hub.input);
				addMouseMotionListener(Hub.input);
				addMouseWheelListener(Hub.input);
				addKeyListener(Hub.input);

				Hub.frame.add(this);

				font = new Font(getFont().getName(), 0, 12);

				this.requestFocus();

				run();
			}

		public final void changeRenderableObject(RenderableObject newObject)
			{
				newObject.prepare();
				currentRenderableObject = newObject;
			}

		public final RenderableObject getRenderableObject()
			{
				return this.currentRenderableObject;
			}

		public final void run()
			{
				long startNanos;
				double lastFrame = 0.001, secondCounter = 0;
				while (true)
					{
						startNanos = System.nanoTime();
						tick(lastFrame);
						render();
						secondCounter += lastFrame = (System.nanoTime() - startNanos) / 1000000000.0;
						if (secondCounter > 1)
							{
								fps = (int) (1.0 / lastFrame);
								secondCounter = 0;
							}
						if (lastFrame > 80)
							{
								try
									{
										Thread.sleep(10);
									}
								catch (Exception e)
									{}
							}
					}
			}

		private void tick(double secondsPassed)
			{
				currentRenderableObject.tick(secondsPassed);
			}

		private void render()
			{
				BufferStrategy bs = getBufferStrategy();
				if (bs == null)
					{
						createBufferStrategy(2);
						bs = getBufferStrategy();
					}

				if (bs != null)
					{
						Graphics g = bs.getDrawGraphics();

						currentRenderableObject.renderObject(g);

						/*
						 * This section draws the square in the top right corner
						 * of the program, it displays the frames per second, it
						 * will be grey if the window does not have focus.
						 */
						if (Hub.DEBUG)
							{
								g.setColor(Hub.renderer.isFocusOwner() ? Color.black : Color.gray);
								g.fillRect(Hub.frameWidth - 70, 0, 70, 25);

								g.setColor(Color.white);
								g.setFont(font);
								g.drawString("FPS: " + fps, Hub.frameWidth - 68, 15);
							}

						g.dispose();
						bs.show();
					}
			}
	}