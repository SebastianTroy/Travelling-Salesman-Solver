package tCode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class LoadingScreen extends JFrame
	{
		private static final long serialVersionUID = 1L;

		private final BufferedImage logo = Hub.loader.loadImage("/res/images/logo.png");

		private String currentTask = "loading";
		private double percentLoaded = 0;

		public LoadingScreen()
			{
				Toolkit kit = Toolkit.getDefaultToolkit();
				GraphicsConfiguration config = getGraphicsConfiguration();
				Hub.screenWidth = kit.getScreenSize().width - kit.getScreenInsets(config).left - kit.getScreenInsets(config).right;
				Hub.screenHeight = kit.getScreenSize().height - kit.getScreenInsets(config).top - kit.getScreenInsets(config).bottom;

				setBounds((Hub.screenWidth / 2) - 200, (Hub.screenHeight / 2) - 150, 400, 300);
				setResizable(false);
				setUndecorated(true);
				setVisible(true);

				loadResources();

				this.dispose();
			}

		private final void loadResources()
			{
				Hub.frame.icons.add(Hub.loader.loadImage("/res/images/titleBar.png"));
				Hub.frame.icons.add(Hub.loader.loadImage("/res/images/taskBar.png"));
				
				while (percentLoaded < 100)
					{
						percentLoaded += Tools.randDouble(0, 0.00008);
						repaint();
					}
			}

		@Override
		public final void paint(Graphics g)
			{
				g.drawImage(logo, 0, 0, 400, 300, this);
				g.setColor(Color.BLACK);
				g.fillRect(0, 250, 400, 30);
				g.setColor(Color.YELLOW);
				g.fillRect(0, 250, (int) (percentLoaded * 4), 28);
				g.setColor(Color.WHITE);
				g.drawString(currentTask, 2, 248);
			}
	}
