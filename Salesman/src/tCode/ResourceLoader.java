package tCode;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourceLoader
	{

		public final BufferedImage loadImage(String imageName)
			{
				try
					{
						return ImageIO.read(ResourceLoader.class.getResource(imageName));
					}
				catch (IOException e)
					{
						e.printStackTrace();
					}
				catch (IllegalArgumentException e)
					{
						e.printStackTrace();
					}
				return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
			}
	}
