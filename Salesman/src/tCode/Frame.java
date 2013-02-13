package tCode;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Frame extends JFrame implements ComponentListener
	{
		private static final long serialVersionUID = 1L;

		private boolean framed = true, resizable = true;
		public ArrayList<BufferedImage> icons = new ArrayList<BufferedImage>();

		public Frame(boolean framed, boolean resizable)
			{
				this.framed = framed;
				this.resizable = resizable;
			}

		public Frame(int width, int height, boolean framed, boolean resizable)
			{
				Hub.frameWidth = width;
				Hub.frameHeight = height;
				this.framed = framed;
				this.resizable = resizable;
			}

		public final void setFrame()
			{
				addComponentListener(this);

				if (Hub.frameWidth == 0)
					Hub.frameWidth = Hub.screenWidth;
				if (Hub.frameHeight == 0)
					Hub.frameHeight = Hub.screenHeight;

				setTitle(Hub.programName + ",   version: " + Hub.versionNumber + "   | http://troyscode.blogspot.com |   " + (Hub.DEBUG ? "[DEBUG MODE]" : ""));
				setBounds((Hub.screenWidth / 2) - (Hub.frameWidth / 2), (Hub.screenHeight / 2) - (Hub.frameHeight / 2), Hub.frameWidth, Hub.frameHeight);
				setIconImages(icons);
				setUndecorated(!framed);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				setResizable(resizable);

				setVisible(true);

				Hub.canvasWidth = getWidth() - getInsets().left - getInsets().right;
				Hub.canvasHeight = getHeight() - getInsets().top - getInsets().bottom;
			}

		@Override
		public void componentResized(ComponentEvent e)
			{
				Hub.frameWidth = this.getWidth();
				Hub.frameHeight = this.getHeight();
				Hub.canvasWidth = getWidth() - getInsets().left - getInsets().right;
				Hub.canvasHeight = getHeight() - getInsets().top - getInsets().bottom;
			}

		@Override
		public void componentMoved(ComponentEvent e)
			{}

		@Override
		public void componentShown(ComponentEvent e)
			{}

		@Override
		public void componentHidden(ComponentEvent e)
			{}
	}