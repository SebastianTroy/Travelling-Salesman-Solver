package tsp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import tCode.RenderableObject;
import tCode.TCode;
import tComponents.components.TButton;
import tComponents.components.TCheckBox;
import tComponents.components.TLabel;
import tComponents.components.TMenu;
import tComponents.components.TRadioButton;
import tComponents.components.TRadioButtonCollection;
import tools.DrawTools;
import tools.NumTools;
import tools.Rand;
import tools.WindowTools;

/**
 * A city knows only its location and the cities before and after it in the travelling salesman's tour.
 * 
 * @author Sebastian Troy
 */
public class TSP extends RenderableObject
	{
		// Constants
		private static final int MENU_HEIGHT = 50;

		private static final int CITY_DIAMETER = 20;
		private static final int CITY_RADIUS = CITY_DIAMETER / 2;

		private static final Color BACKGROUND_COLOUR = Color.BLACK;
		private static final Color CITY_COLOUR = Color.BLUE;
		private static final Color ROUTE_COLOUR = Color.WHITE;
		private static final Color SELECTED_COLOUR = Color.YELLOW;

		// Types of mutation
		private enum MutationType
			{
				SWAP_TWO, RANDOMISE_ONE, RANDOMISE_SOME, REVERSE_SECTION, MOVE_ONE_END;// , MOVE_BOTH_ENDS
			}

		// Menu variables @formatter:off
		private TMenu cityMenu;
		private TMenu interactionMenu;
		private TMenu mutationMenu;
		private final TButton clearButton = new TButton("Remove All Cities"){ @Override public void pressed(){tour.clear(); redraw = true;numGenerations = imrovements = 0;}};
		private final TButton resetButton = new TButton("Reset Solution"){ @Override public void pressed(){shuffleCities();numGenerations = imrovements = 0;}};
		private final TButton randomCityButton = new TButton("Add Random City"){ @Override public void pressed(){addCity(new City(Rand.int_(0 + CITY_RADIUS, Main.canvasWidth - CITY_RADIUS),Rand.int_((int)(1.5 * MENU_HEIGHT) + CITY_RADIUS, Main.canvasHeight - CITY_RADIUS)));numGenerations = imrovements = 0;}};
		private final TButton tenRandomCityButton = new TButton("Add 10 Random Cities"){ @Override public void pressed(){for (int i = 0; i < 10; i++) addCity(new City(Rand.int_(0 + CITY_RADIUS, Main.canvasWidth - CITY_RADIUS),Rand.int_((int)(1.5 * MENU_HEIGHT) + CITY_RADIUS, Main.canvasHeight - CITY_RADIUS)));numGenerations = imrovements = 0;}};
		private final TButton addSmallGridButton = new TButton("Add 6 * 6 Grid"){ @Override public void pressed(){for (int x = 0; x < 6; x++) for (int y = 0; y < 6; y++) addCity(new City(50 + (x * ((Main.canvasWidth - 100)/5)),50 + (int)(1.5*MENU_HEIGHT) + (y * ((Main.canvasHeight - 100 - (2*MENU_HEIGHT))/5))));numGenerations = imrovements = 0;}};
		private final TButton addLargeGridButton = new TButton("Add 10 * 10 Grid"){ @Override public void pressed(){for (int x = 0; x < 10; x++) for (int y = 0; y < 10; y++) addCity(new City(50 + (x * ((Main.canvasWidth - 100)/9)),50 + (int)(1.5*MENU_HEIGHT) + (y * ((Main.canvasHeight - 100 - (2*MENU_HEIGHT))/9))));numGenerations = imrovements = 0;}};
		private final TRadioButtonCollection mouseControlRadioButtons = new TRadioButtonCollection();
		private final TRadioButton newCityButton = new TRadioButton("New City");
		private final TRadioButton moveCityButton = new TRadioButton("Move City");
		private final TRadioButton removeCityButton = new TRadioButton("Remove City");
		private final TLabel currentMethodLabel = new TLabel("Swap cities");
		private final TCheckBox loopTourCheckbox = new TCheckBox("Loop TOur");
		private final TCheckBox playPauseCheckbox = new TCheckBox("Pause");
		private final TCheckBox autoMutateCheckbox = new TCheckBox("Automatic?");
		private final TButton nextMutationMethodButton = new TButton("Next Method"){ @Override public final void pressed(){incrementMutationType();}};

		// Salesman Solver Variables @formatter:on
		private ArrayList<City> tour = new ArrayList<City>();
		private MutationType currentType = MutationType.SWAP_TWO;
		private long numGenerations = 0, imrovements = 0;
		private int generationsSinceLastMutationType = 0;
		private double timeSincelastImprovement = 0;

		// Only set to true if the tour has changed
		private boolean redraw = true;

		@Override
		public final void initiate()
			{
				// Set the size of the window
				TCode.frame.setBounds(TCode.frame.getX() + 50, TCode.frame.getY() + 50, TCode.frame.getWidth() - 100, TCode.frame.getHeight() - 100);

				// Set up the radio buttons
				mouseControlRadioButtons.add(newCityButton);
				mouseControlRadioButtons.add(moveCityButton);
				mouseControlRadioButtons.add(removeCityButton);

				// Prepare
				currentMethodLabel.setFontSize(15);
				currentMethodLabel.setBackgroundColour(BACKGROUND_COLOUR);
				currentMethodLabel.setTextColour(ROUTE_COLOUR);
				moveCityButton.setChecked(true);
				autoMutateCheckbox.setChecked(true);

				// Set up the menu
				cityMenu = new TMenu(0, 0, Main.canvasWidth, MENU_HEIGHT, TMenu.HORIZONTAL);
				cityMenu.setTComponentAlignment(TMenu.ALIGN_START);
				cityMenu.setBorderSize(2);
				cityMenu.setBackgroundColour(BACKGROUND_COLOUR);

				interactionMenu = new TMenu(0, MENU_HEIGHT, 320, MENU_HEIGHT / 2, TMenu.HORIZONTAL);
				interactionMenu.setTComponentAlignment(TMenu.ALIGN_START);
				interactionMenu.setBorderSize(2);
				interactionMenu.setBackgroundColour(BACKGROUND_COLOUR);

				mutationMenu = new TMenu(0, Main.canvasHeight - (MENU_HEIGHT / 2), Main.canvasWidth, MENU_HEIGHT / 2, TMenu.HORIZONTAL);
				mutationMenu.setTComponentAlignment(TMenu.ALIGN_START);
				mutationMenu.setBorderSize(2);
				mutationMenu.setBackgroundColour(BACKGROUND_COLOUR);

				// add the menus
				add(cityMenu);
				cityMenu.add(clearButton);
				cityMenu.add(resetButton);
				cityMenu.add(randomCityButton);
				cityMenu.add(tenRandomCityButton);
				cityMenu.add(addSmallGridButton);
				cityMenu.add(addLargeGridButton);
				cityMenu.add(loopTourCheckbox, false);

				add(interactionMenu);
				interactionMenu.add(newCityButton, false);
				interactionMenu.add(moveCityButton, false);
				interactionMenu.add(removeCityButton, false);

				add(mutationMenu);
				mutationMenu.add(playPauseCheckbox, false);
				mutationMenu.add(autoMutateCheckbox, false);
				mutationMenu.add(nextMutationMethodButton, false);
				mutationMenu.add(currentMethodLabel, false);
			}

		@Override
		public final void tick(double secondsPassed)
			{
				// < 3 cities cannot be improved as only one tour is possible
				if (!playPauseCheckbox.isChecked() && tour.size() > 2)
					{
						// increase the time elapsed since the last improvement
						numGenerations++;
						generationsSinceLastMutationType++;
						timeSincelastImprovement += secondsPassed;

						// Copy and mutate the tour
						ArrayList<City> tourCopy = getTourCopy();
						mutateTour(tourCopy);

						// Calculate how much better the new tour is
						double distanceImproved = calculateTourLength(tour) - calculateTourLength(tourCopy);

						// If the mutant tour is better, keep it
						if (distanceImproved >= 0)
							{
								if (distanceImproved > 0)
									imrovements++;

								// clear the old tour
								tour.clear();
								// add the new tour
								for (City c : tourCopy)
									tour.add(c);

								// reset the time elapsed since the last improvement
								if (distanceImproved > 0)
									timeSincelastImprovement = 0;

								// draw the new tour
								redraw = true;
							}

						else if (autoMutateCheckbox.isChecked())// check to see how long since the last improvement and adapt strategy if necessary.
							{
								if (generationsSinceLastMutationType > 2000)
									{
										incrementMutationType();
										generationsSinceLastMutationType = 0;
									}
							}
					}
			}

		@Override
		protected void render(Graphics2D g)
			{
				// only if we need to update the tour
				if (redraw)
					{
						// clear the screen
						g.setColor(BACKGROUND_COLOUR);
						g.fillRect(0, 0, Main.canvasWidth, Main.canvasHeight);

						// Draw cities
						g.setColor(CITY_COLOUR);
						for (City c : tour)
							{
								g.fillOval(c.x - CITY_RADIUS, c.y - CITY_RADIUS, CITY_DIAMETER, CITY_DIAMETER);
								if (c.selected)
									{
										g.setColor(SELECTED_COLOUR);
										g.drawOval(c.x - CITY_RADIUS, c.y - CITY_RADIUS, CITY_DIAMETER, CITY_DIAMETER);
										g.setColor(CITY_COLOUR);
									}
							}
						// Draw the tour
						g.setColor(ROUTE_COLOUR);
						for (City c : tour)
							if (c.hasNext())
								// g.drawLine(c.x, c.y, c.getNext().x, c.getNext().y);
								DrawTools.drawArrow(c.x, c.y, c.getNext().x, c.getNext().y, g, 10);
							else if (loopTourCheckbox.isChecked())
								{
									City c2 = getFirstInTour(tour);
									DrawTools.drawArrow(c.x, c.y, c2.x, c2.y, g, 10);
								}

						redraw = false;
					}
				g.setColor(BACKGROUND_COLOUR);
				g.fillRect(330, MENU_HEIGHT, 500, 15);

				g.setColor(ROUTE_COLOUR);
				g.drawString("Improved " + (int) timeSincelastImprovement + "s ago.", 335, MENU_HEIGHT+ 15);
				g.drawString("Generations: " + numGenerations, 485, MENU_HEIGHT + 15);
				g.drawString("Improvements: " + imrovements, 635, MENU_HEIGHT + 15);
			}

		/**
		 * Adds a city to the tour, so long as it is within valid bounds
		 * 
		 * @param c
		 *            - The city to be added
		 */
		private final void addCity(City c)
			{
				if (c.x < 0 || c.x > Main.canvasWidth || c.y < (1.5 * MENU_HEIGHT) + CITY_RADIUS || c.y > (Main.canvasHeight - CITY_RADIUS) - (MENU_HEIGHT / 2))
					{
						if (Main.DEBUG)
							WindowTools.informationWindow("City out of bounds!", "addCity in TSP");
						return;
					}

				if (tour.size() > 0)
					{
						if (Rand.bool())
							c.insertBefore(tour.get(Rand.int_(0, tour.size())));
						else
							c.insertAfter(tour.get(Rand.int_(0, tour.size())));

						tour.add(c);
					}
				else
					tour.add(c);

				redraw = true;
			}

		/**
		 * @return - A hard copy of the tour
		 */
		private final ArrayList<City> getTourCopy()
			{
				City city = null;

				ArrayList<City> tourCopy = new ArrayList<City>(tour.size());

				// Find the first City in the tour
				for (City c : tour)
					if (!c.hasPrevious())
						city = c;

				// Add the first city
				tourCopy.add(new City(city.x, city.y));
				tourCopy.get(0).selected = city.selected;
				// Add each sequential City and make sure it is linked in the correct order
				for (int i = 1; i < tour.size(); i++)
					{
						city = city.getNext();
						tourCopy.add(new City(city.x, city.y, tourCopy.get(i - 1), null, city.selected));
					}

				return tourCopy;
			}

		/**
		 * Shuffles the cities into a random order, taking advantage of how {@link TSP#addCity(City)} adds a city into a random slot in the tour.
		 */
		private final void shuffleCities()
			{
				City[] cities = new City[tour.size()];
				tour.toArray(cities);
				tour.clear();

				for (City c : cities)
					addCity(c);
			}

		/**
		 * Detects the current type of mutation and switches it to the next in the list
		 */
		private final void incrementMutationType()
			{
				switch (currentType)
					{
						case SWAP_TWO:
							currentType = MutationType.RANDOMISE_ONE;// MOVE_BOTH_ENDS;
							currentMethodLabel.setLabelText("Randomise city position");// "Move tour ends");
							break;

						// case MOVE_BOTH_ENDS:
						// currentType = MutationType.RANDOMISE_ONE;
						// currentMethodLabel.setLabelText("Randomise city position");
						// break;

						case RANDOMISE_ONE:
							currentType = MutationType.RANDOMISE_SOME;
							currentMethodLabel.setLabelText("Randomise city positions");
							break;

						case RANDOMISE_SOME:
							currentType = MutationType.REVERSE_SECTION;
							currentMethodLabel.setLabelText("Reverse direction of tour section");
							break;

						case REVERSE_SECTION:
							currentType = MutationType.MOVE_ONE_END;
							currentMethodLabel.setLabelText("Move one end of tour");
							break;

						case MOVE_ONE_END:
							currentType = MutationType.SWAP_TWO; // and back to the start
							currentMethodLabel.setLabelText("Swap two cities");
							break;
					}
				/* We have resized a menu item and currently the menu will not be notified automatically so this causes the menu to adjust itself */
				mutationMenu.setWidth(mutationMenu.getWidthD());
			}

		/**
		 * Mutates the tour in one of the following ways:
		 * <ul>
		 * <li>Two cities swap their positions in the tour,</li>
		 * <li>The start is joined to the end and a new start & end point are chosen,</li>
		 * <li>The position of one city is randomised,</li>
		 * <li>The position of a number of cities is randomised,</li>
		 * <li>A section of the tour is removed and the order is reversed, it is then re-attached at opposing ends (good at removing big crossover points)</li>
		 * <li>Remove a section from the tour and add it to the start or the end,</li>
		 * </ul>
		 * 
		 * @param tour
		 *            - The tour to be changed in a random way (Note that changes are unlikely to be beneficial to tour length)
		 */
		private final void mutateTour(ArrayList<City> tour)
			{
				switch (currentType)
					{
						case SWAP_TWO: // Two cities swap their positions in the tour
							tour.get(Rand.int_(0, tour.size())).swapWith(tour.get(Rand.int_(0, tour.size())));
							break;

						// case MOVE_BOTH_ENDS: // The start is joined to the end, new start & end points are chosen
						// // Find the old ends
						// City oldStart = tour.get(0),
						// oldEnd = tour.get(tour.size() - 1),
						// newStart = null;
						//
						// // Join the tour in a loop
						// oldEnd.next = oldStart;
						// oldStart.previous = oldEnd;
						//
						// // Find a random connection and break it
						// boolean connectionBroken = false;
						// do
						// {
						// newStart = tour.get(Rand.int_(0, tour.size()));
						// if (newStart.hasPrevious())
						// {
						// newStart.getPrevious().next = null;
						// newStart.previous = null;
						// connectionBroken = true;
						// }
						// }
						// while (!connectionBroken);
						// break;

						case RANDOMISE_ONE: // The position of one city is randomised
							if (Rand.bool())
								tour.get(Rand.int_(0, tour.size())).insertBefore(tour.get(Rand.int_(0, tour.size())));
							else
								tour.get(Rand.int_(0, tour.size())).insertAfter(tour.get(Rand.int_(0, tour.size())));
							break;

						case RANDOMISE_SOME: // The position of a number of cities is randomised
							for (int i = Rand.int_(0, tour.size()); i > 0; i--)
								if (Rand.bool())
									tour.get(Rand.int_(0, tour.size())).insertBefore(tour.get(Rand.int_(0, tour.size())));
								else
									tour.get(Rand.int_(0, tour.size())).insertAfter(tour.get(Rand.int_(0, tour.size())));
							break;

						case REVERSE_SECTION: // A section of the tour is removed and the order is reversed, it is then re-attached at opposing ends
							if (tour.size() < 5) // Too short to work
								return;

							int loopSize = Rand.int_(4, tour.size() - 1),
							loopStart = Rand.int_(0, tour.size() - loopSize);
							City startingCity = null,
							endingCity = null;

							// Set the start of the loop
							startingCity = tour.get(0);
							for (int i = 0; i < loopStart; i++)
								startingCity = startingCity.getNext();
							// Set the end of the loop
							endingCity = startingCity;
							for (int i = 0; i < loopSize; i++)
								endingCity = endingCity.getNext();

							// Swap the cities sequentially from closest to the loop ends to the farthest
							for (int i = 0; i <= (loopSize - 2) / 2; i++)
								{
									startingCity = startingCity.next;
									endingCity = endingCity.previous;
									startingCity.swapWith(endingCity);
								}

							break;

						case MOVE_ONE_END: // Remove a section from the tour and add it to the start or the end
							int sectionSize = Rand.int_(2, tour.size() - 2),
							sectionStart = Rand.int_(0, tour.size() - sectionSize);
							City temp = tour.get(0);

							for (int i = 0; i < sectionStart; i++)
								temp = temp.getNext();

							if (Rand.bool())
								// insert at start
								for (int i = 0; i < sectionSize; i++)
									temp.getNext().insertBefore(tour.get(0));
							else
								// insert at end
								for (int i = 0; i < sectionSize; i++)
									temp.getNext().insertAfter(tour.get(tour.size() - 1));

							break;

						default:
							System.out.println("Unrecognised Mutation Method");
							break;
					}
			}

		/**
		 * @param tour
		 *            - This tour to be calculated
		 * @return - The length of the tour provided
		 */
		private final double calculateTourLength(ArrayList<City> tour)
			{
				double distance = 0;
				// We don't even need to go through the tour from start to end
				for (City c : tour)
					if (c.hasNext())
						distance += NumTools.distance(c.x, c.y, c.next.x, c.next.y);
					else if (loopTourCheckbox.isChecked())
						{
							City c2 = getFirstInTour(tour);
							distance += NumTools.distance(c.x, c.y, c2.x, c2.y);
						}

				return distance;
			}

		/**
		 * When the tour is being wrapped there is still a 'first' and 'last' city, they simply have a line drawn between them and their distance is used in the
		 * tour distance calculation.
		 * 
		 * @return - The first city in the tour
		 */
		private final City getFirstInTour(ArrayList<City> tour)
			{
				City city = null;
				for (City c : tour)
					if (!c.hasPrevious())
						return c;

				return city;
			}

		@Override
		public final void mousePressed(MouseEvent e)
			{
				Point p = e.getPoint();

				// Add a new city at the mouse point
				if (newCityButton.isChecked())
					{
						addCity(new City(p.x, p.y));
						numGenerations = imrovements = 0;
					}
				// Select A single city under the mouse point
				else if (moveCityButton.isChecked())
					{
						// Make sure we don't select more than 1 city at a time
						boolean citySelected = false;

						for (City c : tour)
							if (!citySelected && c.contains(p))
								c.selected = citySelected = true;
							else
								c.selected = false;

						redraw = true;
					}
				// Remove any cities under the mouse point
				else if (removeCityButton.isChecked())
					{
						for (int i = 0; i < tour.size(); i++)
							if (tour.get(i).contains(p))
								{
									tour.get(i).removeFromTour();
									tour.remove(i);
									i--;
									redraw = true;
									numGenerations = imrovements = 0;
								}
					}
			}

		@Override
		public final void mouseDragged(MouseEvent e)
			{
				// Move the currently selected city and delete it if it is dragged out of bounds
				if (moveCityButton.isChecked())
					{
						for (int i = 0; i < tour.size(); i++)
							{
								if (tour.get(i).selected == true)
									{
										numGenerations = imrovements = 0;
										if (e.getX() < 0 || e.getX() > Main.canvasWidth || e.getY() < MENU_HEIGHT * 1.5 || e.getY() > Main.canvasHeight - (MENU_HEIGHT / 2))
											{
												tour.get(i).removeFromTour();
												tour.remove(i);
												i--;
												redraw = true;
												return;
											}
										else
											{
												tour.get(i).x = e.getX();
												tour.get(i).y = e.getY();
												redraw = true;
												return;
											}
									}
							}
					}
				// Remove any cities under the mouse point
				else if (removeCityButton.isChecked())
					{
						for (int i = 0; i < tour.size(); i++)
							if (tour.get(i).contains(e.getPoint()))
								{
									tour.get(i).removeFromTour();
									tour.remove(i);
									i--;
									redraw = true;
								}
					}
			}

		@Override
		public final void componentResized(ComponentEvent e)
			{
				redraw = true;
				cityMenu.setWidth(Main.canvasWidth);
				interactionMenu.setWidth(Math.min(Main.canvasWidth, 320));
				mutationMenu.setWidth(Main.canvasWidth);
				mutationMenu.setY(Main.canvasHeight - (MENU_HEIGHT / 2));
				for (int i = 0; i < tour.size(); i++)
					if (tour.get(i).x < 0 || tour.get(i).x > Main.canvasWidth || tour.get(i).y < MENU_HEIGHT || tour.get(i).y > Main.canvasHeight)
						{
							tour.get(i).removeFromTour();
							tour.remove(i);
							i--;
							numGenerations = imrovements = 0;
						}
			}

		/**
		 * My first attempt at a double linked list, in this case representing cities. A city knows only if it is selected, the previous city in the tour to
		 * which it belongs and the next city in the tour to which it belongs.
		 * <p>
		 * It can be inferred that the city is at the start of the tour if its previous city is null.
		 * <p>
		 * It can be inferred that the city is at the end of the tour if its next city is null.
		 * 
		 * @author Sebastian Troy
		 */
		private class City
			{
				private City previous;
				private City next;

				private boolean selected = false;

				private int x, y;

				/**
				 * Used to create the very first city of a tour.
				 * 
				 * @param x
				 *            - x location of the city
				 * @param y
				 *            - y location of the city
				 */
				private City(int x, int y)
					{
						this.x = x;
						this.y = y;
						this.previous = null;
						this.next = null;
					}

				/**
				 * Hard copies the specified city.
				 *
				 * @param city
				 *            - city to be copied.
				 */
				private City(City city)
					{
						x = city.x;
						y = city.y;
						previous = city.previous;
						next = city.next;
						selected = city.selected;
					}

				/**
				 * Creates a new city and adds it to the tour
				 * 
				 * @param x
				 *            - x location of the city
				 * @param y
				 *            - y location of the city
				 * @param previous
				 *            - the city that this city will be inserted after
				 * @param next
				 *            - the city that this city will be inserted before
				 */
				private City(int x, int y, City previous, City next, boolean selected)
					{
						this.x = x;
						this.y = y;
						this.previous = previous;
						this.next = next;
						this.selected = selected;

						if (hasPrevious())
							insertAfter(previous);
						else if (hasNext())
							{
								insertBefore(next);
							}
						else
							new Exception("Cannot add new city to existing tour without reference to position in tour");
					}

				/**
				 * Returns false if this is the first city in the tour
				 * 
				 * @return - true if there is a previous city in the tour
				 */
				private final boolean hasPrevious()
					{
						return previous != null;
					}

				/**
				 * Returns false if this is the last city in the tour
				 * 
				 * @return - true if there is a next city in the tour
				 */
				private final boolean hasNext()
					{
						return next != null;
					}

				/**
				 * @return - null if there is no previous city
				 */
				private final City getPrevious()
					{
						return previous;
					}

				/**
				 * @return - null if there is no next city
				 */
				private final City getNext()
					{
						return next;
					}

				/**
				 * This method will insert this {@link City} into the tour before a desired City.
				 * 
				 * @param city
				 *            - city to be inserted into the tour before this one
				 */
				private final void insertBefore(City city)
					{
						// No need to do anything if we are inserting this before this
						if (this == city)
							return;

						// If this city was previously linked, link up previous with next
						removeFromTour();

						// Link to our neighbouring cities
						previous = city.getPrevious();
						next = city;

						// Tell our neighbouring cities to link to us
						if (city.hasPrevious())
							city.previous.next = this;
						city.previous = this;
					}

				/**
				 * This method will insert this {@link City} into the tour after a desired City.
				 * 
				 * @param city
				 *            - city to be inserted into the tour after this one
				 */
				private final void insertAfter(City city)
					{
						// No need to do anything if we are inserting this before this
						if (this == city)
							return;

						// If this city was previously linked, link up previous with next
						removeFromTour();

						// Link to our neighbouring cities
						next = city.getNext();
						previous = city;

						// Tell our neighbouring cities to link to us
						if (city.hasNext())
							city.next.previous = this;
						city.next = this;
					}

				/**
				 * Swap the tour position of this city with another (swapping locations has the same affect as swapping )
				 * 
				 * @param city
				 */
				private final void swapWith(City city)
					{
						// No need to swap positions of this with this
						if (city == this)
							return;

						int tempX = x, tempY = y;
						boolean tempSelected = selected;

						x = city.x;
						y = city.y;
						selected = city.selected;

						city.x = tempX;
						city.y = tempY;
						city.selected = tempSelected;
					}

				/**
				 * Removes this city from the tour and connects its previous city to its next.
				 */
				private final void removeFromTour()
					{
						if (hasPrevious())
							previous.next = next;
						if (hasNext())
							next.previous = previous;
					}

				/**
				 * @param p
				 *            - a point
				 * @return - true if the point is within the cities bounds, see {@link TSP#CITY_RADIUS}.
				 */
				private final boolean contains(Point p)
					{
						return NumTools.distance(x, y, p.x, p.y) < CITY_RADIUS;
					}
			}
	}