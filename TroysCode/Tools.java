package TroysCode;

import java.awt.Color;
import java.awt.Point;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import TroysCode.T.TPoint;
import TroysCode.T.TVector;

/**
 * This class holds static methods only. These methods are for convinience only,
 * it saves re-writing them in situ again and again.
 * <p>
 * These methods can be reached from any class by calling
 * <code> Tools.methodName() </code>.
 * 
 * @author Sebastian Troy
 * 
 */
public class Tools
	{
		/**
		 * Warning, no instances of this class should ever be made!
		 */
		private Tools()
			{
			}

		/**
		 * @return A random boolean value. (either true or false)
		 */
		public final static Boolean randBool()
			{
				Boolean bool;
				int rnd = (int) (Math.random() * 2) + 1;
				if (rnd == 2)
					bool = true;
				else
					bool = false;
				return bool;
			}

		/**
		 * @return A random double between 0 and 100
		 */
		public static final double randPercent()
			{
				double rnd = (Math.random() * 100.001);
				return rnd > 100 ? 100 : rnd;
			}

		/**
		 * Returns a random integer within a specified range.
		 * 
		 * @param low
		 *            - the lower end of the return range
		 * @param high
		 *            - the upper end of the return range
		 * @return An integer greater than or equal to the low parameter, and
		 *         less than or equal to the high parameter.
		 */
		public static final int randInt(int low, int high)
			{
				return (int) (Math.random() * (high - low + 1)) + low;
			}

		/**
		 * Returns a random float within a specified range.
		 * 
		 * @param low
		 *            - the lower end of the return range
		 * @param high
		 *            - the upper end of the return range
		 * @return A float greater than or equal to the low parameter, and less
		 *         than or equal to the high parameter.
		 */
		public static final float randFloat(float low, float high)
			{
				float rnd = (float) (Math.random() * (high - low + 0.001)) + low;
				return rnd > high ? high : rnd;
			}

		/**
		 * Returns a random double within a specified range.
		 * 
		 * @param low
		 *            - the lower end of the return range
		 * @param high
		 *            - the upper end of the return range
		 * @return A double greater than or equal to the low parameter, and less
		 *         than or equal to the high parameter.
		 */
		public static final double randDouble(double low, double high)
			{
				double rnd = (Math.random() * (high - low + 0.001)) + low;
				return rnd > high ? high : rnd;
			}

		/**
		 * Returns a random {@link long} within a specified range.
		 * 
		 * @param low
		 *            - the lower end of the return range
		 * @param high
		 *            - the upper end of the return range
		 * @return A long greater than or equal to the low parameter, and less
		 *         than or equal to the high parameter.
		 */
		public static final long randLong(long low, long high)
			{
				return (long) ((Math.random() * (high - low + 1)) + low);
			}

		/**
		 * Returns a completely random {@link Color} with no alpha value.
		 * 
		 * @return a {@link Color} object which holds a random colour.
		 */
		public static final Color randColour()
			{
				int red = (int) (Math.random() * 256);
				int green = (int) (Math.random() * 256);
				int blue = (int) (Math.random() * 256);
				Color randomColour = new Color(red, green, blue);
				return randomColour;
			}

		/**
		 * Returns a completely random {@link Color} with an alpha value.
		 * 
		 * @return a {@link Color} object which holds a random colour.
		 */
		public static final Color randAlphaColour()
			{
				int red = (int) (Math.random() * 256);
				int green = (int) (Math.random() * 256);
				int blue = (int) (Math.random() * 256);
				int alpha = (int) (Math.random() * 256);
				Color randomColour = new Color(red, green, blue, alpha);
				return randomColour;
			}

		/**
		 * This method takes three integer values and checks that they are
		 * within 0 - 255 before returning a {@link Color} composed of these
		 * values.
		 * 
		 * @param red
		 *            - the red component to be checked.
		 * @param green
		 *            - the green component to be checked.
		 * @param blue
		 *            - the blue component to be checked.
		 * @return - A RGB {@link Color}, composed of the above integers.
		 */
		public static final Color checkColour(int red, int green, int blue)
			{
				if (red < 0)
					red = 0;
				else if (red > 255)
					red = 255;

				if (green < 0)
					green = 0;
				else if (green > 255)
					green = 255;

				if (blue < 0)
					blue = 0;
				else if (blue > 255)
					blue = 255;

				return new Color(red, green, blue);
			}

		/**
		 * This method takes four integer values and checks that they are within
		 * 0 - 255 before returning a {@link Color} composed of these values.
		 * 
		 * @param red
		 *            - the red component to be checked.
		 * @param green
		 *            - the green component to be checked.
		 * @param blue
		 *            - the blue component to be checked.
		 * @param alpha
		 *            - the alpha component to be checked.
		 * @return - A RGBA {@link Color}, composed of the above integers.
		 */
		public static final Color checkAlphaColour(int red, int green, int blue, int alpha)
			{
				if (red < 0)
					red = 0;
				else if (red > 255)
					red = 255;

				if (green < 0)
					green = 0;
				else if (green > 255)
					green = 255;

				if (blue < 0)
					blue = 0;
				else if (blue > 255)
					blue = 255;

				if (alpha < 0)
					alpha = 0;
				else if (alpha > 255)
					alpha = 255;

				return new Color(red, green, blue, alpha);
			}

		public static Color interpolateColours(Color colOne, Color colTwo)
			{
				return new Color(((colOne.getRed() + colTwo.getRed()) / 2), ((colOne.getGreen() + colTwo.getGreen()) / 2),
						((colOne.getBlue() + colTwo.getBlue()) / 2), ((colOne.getAlpha() + colTwo.getAlpha()) / 2));
			}

		/**
		 * This method calculates the vector between two points.
		 * 
		 * @param startX
		 *            - X coordinate of the starting point.
		 * @param startY
		 *            - Y coordinate of the starting point.
		 * @param endX
		 *            - X coordinate of the end point.
		 * @param endY
		 *            - Y coordinate of the end point.
		 * @return A {@link TVector} Representing the vector between the two
		 *         points.
		 */
		public static final TVector getVector(double startX, double startY, double endX, double endY)
			{
				return new TVector(endX - startX, endY - startY);
			}

		/**
		 * This method calculates the vector between two points.
		 * 
		 * @param startX
		 *            - X coordinate of the starting point.
		 * @param startY
		 *            - Y coordinate of the starting point.
		 * @param endX
		 *            - X coordinate of the end point.
		 * @param endY
		 *            - Y coordinate of the end point.
		 * @return A {@link TVector} Representing the vector between the two
		 *         points.
		 */
		public static final TVector getVector(double startX, double startY, double endX, double endY, double startZ, double endZ)
			{
				return new TVector(endX - startX, endY - startY, endZ - startZ);
			}

		/**
		 * This method calculates a vector when only the angle of the vector and
		 * it's length are known, the vector returned is in {@link Integer}
		 * accuracy.
		 * 
		 * @param vectorAngle
		 *            - The angle in which the line is pointing, 0 is North, 90
		 *            is East, 180 is South, 270 is West and 360 is North again.
		 * @param vectorLength
		 *            - The length of the vector.
		 * @return A {@link TVector} Representing a vector of length
		 *         <code>vectorLength</code> and at an angle of
		 *         <code>vectorAngle</code>
		 */
		public static final TVector getVector(double vectorAngle, double vectorLength)
			{
				double vectorAngleRadians = Math.toRadians(vectorAngle);

				return new TVector(Math.sin(vectorAngleRadians) * vectorLength, Math.cos(vectorAngleRadians) * vectorLength);
			}

		/**
		 * This method calculates the vector between two {@link Point}s.
		 * 
		 * @param startPoint
		 *            - The start point of the vector.
		 * @param endPoint
		 *            - The end point of the vector.
		 * 
		 * @return A {@link Point} Representing the vector between the two
		 *         points.
		 */
		public static final Point getVector(Point startPoint, Point endPoint)
			{
				Point point = new Point(0, 0);

				point.setLocation(endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());

				return point;
			}

		/**
		 * This method calculates the vector between two {@link TPoint}s.
		 * 
		 * @param startPoint
		 *            - The start point of the vector.
		 * @param endPoint
		 *            - The end point of the vector.
		 * 
		 * @return A {@link Point} Representing the vector between the two
		 *         points.
		 */
		public static final TPoint getVector(TPoint startPoint, TPoint endPoint)
			{
				TPoint point = new TPoint();

				point.setLocation(endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());

				return point;
			}

		/**
		 * This method returns the angle of an existing vector.
		 * 
		 * @param vector
		 *            - The vector you wish to know the angle of.
		 * 
		 * @return The angle, in Degrees, of the vector.
		 */
		public static final double getVectorAngle(Point vector)
			{
				return getVectorAngle(new Point(0, 0), vector);
			}

		/**
		 * This method returns the angle of an existing vector.
		 * 
		 * @param vector
		 *            - The vector you wish to know the angle of.
		 * 
		 * @return The angle, in Degrees, of the vector.
		 */
		public static final double getVectorAngle(TPoint vector)
			{
				return getVectorAngle(new TPoint(0, 0), vector);
			}

		/**
		 * This method calculates the angle of the line between two
		 * {@link Point}s.
		 * 
		 * @param startPoint
		 *            - The start point of the vector.
		 * @param endPoint
		 *            - The end point of the vector.
		 * 
		 * @return The angle, in Degrees, of the line betweent the two points
		 */
		public static final double getVectorAngle(Point startPoint, Point endPoint)
			{
				double oppositeLength = endPoint.x - startPoint.x;
				double adjacentLength = startPoint.y - endPoint.y;

				double angle = Math.toDegrees(Math.atan2(oppositeLength, adjacentLength));

				if (angle < 0)
					angle += 360;

				return angle;
			}

		/**
		 * This method calculates the angle of the line between two
		 * {@link TPoint}s.
		 * 
		 * @param startPoint
		 *            - The start point of the vector.
		 * @param endPoint
		 *            - The end point of the vector.
		 * 
		 * @return A {@link Point} Representing the vector between the two
		 *         points.
		 */
		public static final double getVectorAngle(TPoint startPoint, TPoint endPoint)
			{
				double oppositeLength = endPoint.getX() - startPoint.getX();
				double adjacentLength = startPoint.getY() - endPoint.getY();

				double angle = Math.toDegrees(Math.atan2(oppositeLength, adjacentLength));

				if (angle < 0)
					angle += 360;

				return angle;
			}

		/**
		 * This method returns the distance between two points
		 * 
		 * @param startX
		 *            - X coordinate of the starting point.
		 * @param startY
		 *            - Y coordinate of the starting point.
		 * @param endX
		 *            - X coordinate of the end point.
		 * @param endY
		 *            - Y coordinate of the end point.
		 * @return The distance between the two points as a double.
		 */
		public static final double getVectorLength(double startX, double startY, double endX, double endY)
			{
				return Math.sqrt(Math.pow(startY - endY, 2) + Math.pow(startX - endX, 2));
			}

		/**
		 * This method returns the distance between two {@link Point}'s
		 * 
		 * @param start
		 *            - The first {@link Point}.
		 * @param end
		 *            - The second {@link Point}.
		 * @return The distance between the two points as a double.
		 */
		public static final double getVectorLength(Point start, Point end)
			{

				return Math.sqrt(Math.pow(start.getY() - end.getY(), 2) + Math.pow(start.getX() - end.getX(), 2));
			}

		/**
		 * This method returns the distance between two {@link TPoint}'s
		 * 
		 * @param start
		 *            - The first {@link TPoint}.
		 * @param end
		 *            - The second {@link TPoint}.
		 * @return The distance between the two points as a double.
		 */
		public static final double getVectorLength(TPoint start, TPoint end)
			{

				return Math.sqrt(Math.pow(start.getY() - end.getY(), 2) + Math.pow(start.getX() - end.getX(), 2));
			}

		/**
		 * This method returns the square of the distance between two points.
		 * This method is faster than <code>the getVectorLength()</code> method.
		 * 
		 * @param startX
		 *            - X coordinate of the starting point.
		 * @param startY
		 *            - Y coordinate of the starting point.
		 * @param endX
		 *            - X coordinate of the end point.
		 * @param endY
		 *            - Y coordinate of the end point.
		 * @return The distance between the two points, squared, as a double.
		 */
		public static final double getVectorLengthSquared(double startX, double startY, double endX, double endY)
			{
				return Math.pow(startY - endY, 2) + Math.pow(startX - endX, 2);
			}

		/**
		 * This method returns the square of the distance between two
		 * {@link Point}'s. This method is faster than
		 * <code>the getVectorLength()</code> method.
		 * 
		 * @param start
		 *            - The first {@link Point}.
		 * @param end
		 *            - The second {@link Point}.
		 * @return The distance between the two points, squared, as a double.
		 */
		public static final double getVectorLengthSquared(Point start, Point end)
			{

				return Math.pow(start.getY() - end.getY(), 2) + Math.pow(start.getX() - end.getX(), 2);
			}

		/**
		 * This method returns the square of the distance between two
		 * {@link TPoint}'s. This method is faster than
		 * <code>the getVectorLength()</code> method.
		 * 
		 * @param start
		 *            - The first {@link TPoint}.
		 * @param end
		 *            - The second {@link TPoint}.
		 * @return The distance between the two points, squared, as a double.
		 */
		public static final double getVectorLengthSquared(TPoint start, TPoint end)
			{

				return Math.pow(start.getY() - end.getY(), 2) + Math.pow(start.getX() - end.getX(), 2);
			}

		/**
		 * This method calculates the angle, in degrees, at which an object will
		 * be travelling after it has bounced off a flat surface.
		 * 
		 * @param objectAngle
		 *            - The angle which the object is travelling at when it hits
		 *            the surface, in degrees.
		 * @param surfaceAngle
		 *            - The angle of the surface which the object is hitting, in
		 *            degrees.
		 * @return The angle of the object after it has bounced off the surface,
		 *         in degrees.
		 */
		public static final double getBounceAngle(double objectAngle, double surfaceAngle)
			{

				return 0;
			}

		/**
		 * This method calculates the dot product of two vectors
		 * 
		 * @param vector1
		 * @param vector2
		 * @return the dot product of the two vectors.
		 */
		public static final double getDotProduct(Point vector1, Point vector2)
			{
				return (vector1.x * vector2.x) + (vector1.y * vector2.y);
			}

		/**
		 * This method calculates the dot product of two vectors
		 * 
		 * @param vector1
		 * @param vector2
		 * @return the dot product of the two vectors.
		 */
		public static final double getDotProduct(TPoint vector1, TPoint vector2)
			{
				return (vector1.getX() * vector2.getX()) + (vector1.getY() * vector2.getY());
			}

		/**
		 * This method creates a window and prints details of an
		 * {@link Exception} to it.
		 * 
		 * @param exception
		 *            - The exception that has been caught.
		 * @param location
		 *            - A string describing the location of the
		 *            {@link Exception} within the code.
		 */
		public static final void errorWindow(Exception exception, String location)
			{
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				exception.printStackTrace(pw);
				String errorTrace = sw.toString();

				String newLine = System.getProperty("line.separator");

				JTextArea area = new JTextArea(errorTrace + newLine + "VERSION: " + hub.versionNumber);
				area.setRows(20);
				area.setColumns(65);
				area.setLineWrap(true);
				JScrollPane pane = new JScrollPane(area);

				if (hub.DEBUG)
					JOptionPane.showMessageDialog(hub.frame, pane, location, JOptionPane.ERROR_MESSAGE);
				else
					JOptionPane.showMessageDialog(hub.frame, "DEBUG is turned off!", location, JOptionPane.ERROR_MESSAGE);
				exitWindow("Would you like to Exit?");
			}

		/**
		 * This method creates a yes/no confirmation pop-up, asking the user if
		 * they intended to exit.
		 * 
		 * @param message
		 *            - A message to display to the user.
		 */
		public static final void exitWindow(String message)
			{
				try
					{
						int answer = JOptionPane.showConfirmDialog(hub.frame, message, "Exit program?", JOptionPane.YES_NO_OPTION);
						if (answer == JOptionPane.YES_OPTION)
							{
								System.exit(0);
							}
					}
				catch (Exception e)
					{
						errorWindow(e, "exitWindow in Tools.java");
					}
			}

		/**
		 * This method creates a yes/no pop-up which returns the users answer.
		 * 
		 * @param message
		 *            - A question to ask the user.
		 * @param titleBar
		 *            - A message to place in the pop-up's title bar.
		 * @return <code>true</code> if the user selects 'Yes', else returns
		 *         <code>false</code>.
		 */
		public static final boolean confirmationBox(String message, String titleBar)
			{
				int answer = JOptionPane.showConfirmDialog(hub.frame, message, titleBar, JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION)
					return true;

				return false;
			}

		/**
		 * Displays a message to the user in a pop-up.
		 * 
		 * @param infoMessage
		 *            - The message to show the user.
		 * @param titleBar
		 *            - A message to place in the pop-up's title bar.
		 */
		public static final void infoBox(String infoMessage, String titleBar)
			{
				JOptionPane.showMessageDialog(hub.frame, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
			}

	}
