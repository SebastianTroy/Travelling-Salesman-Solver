package ShortestPath;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import TroysCode.RenderableObject;
import TroysCode.Tools;
import TroysCode.hub;
import TroysCode.T.TButton;
import TroysCode.T.TScrollEvent;

public class TSP extends RenderableObject
	{
		private static final long serialVersionUID = 1L;

		// Nodes can be added and moved and removed using the mouse, which runs
		// on a seperate thread, therefore to stop concurrent modification
		// exceptions they are first added to a temporary array, and copied
		// across into the main arraylist by the main thread afterwards
		private ArrayList<Node> nodes = new ArrayList<Node>();
		private ArrayList<Node> nodesToAdd = new ArrayList<Node>();
		protected int nodeSize = 20;

		// The population of solutions are all independant and compete in a hill
		// climber esque manner
		private final Solution[] solutionsArray = new Solution[1000];
		private int bestSolution;

		// A small graph to show the % solutions that were improved upon each
		// tick
		private double percentImprovedLastSecond = 0;
		private double averageImprovement = 0;

		private TButton showBestButton = new TButton(10, 0, "Showing [Best] Solution");
		private boolean showBest = true;

		@Override
		protected void initiate()
			{
				addTComponent(showBestButton);

				for (int x = 0; x < 6; x++)
					addNode(new Node(Tools.randInt(50, 750), Tools.randInt(50, 500)));

				for (int i = 0; i < solutionsArray.length; i++)
					solutionsArray[i] = new Solution();
			}

		@Override
		protected void refresh()
			{
			}

		@Override
		protected void tick(double secondsPassed)
			{
				// remove unwanted nodes
				Node[] nodeCopy = new Node[nodes.size()];
				nodes.toArray(nodeCopy);
				for (Node n : nodeCopy)
					if (!n.exists)
						{
							nodes.remove(n);

							for (Solution s : solutionsArray)
								s.reset();

							bestSolution = Tools.randInt(0, solutionsArray.length - 1);
						}

				// add new nodes
				if (nodesToAdd.size() > 0)
					{
						for (Node n : nodesToAdd)
							nodes.add(n);

						for (Solution s : solutionsArray)
							s.reset();

						bestSolution = Tools.randInt(0, solutionsArray.length - 1);

						nodesToAdd.clear();
					}

				if (nodes.size() > 0)
					{

						// Basically there are a number of hillclimbers
						// competing
						// against each other
						int numImproved = 0;
						for (int i = 0; i < solutionsArray.length; i++)
							{
								Solution mutant = new Solution(solutionsArray[i]);

								if (mutant.tourLength < solutionsArray[i].tourLength)
									{
										numImproved++;
										solutionsArray[i] = mutant;
									}
								else if (mutant.tourLength == solutionsArray[i].tourLength)
									{
										solutionsArray[i] = mutant;
									}
								if (showBest)
									{
										if (solutionsArray[i].tourLength < solutionsArray[bestSolution].tourLength)
											bestSolution = i;
									}
								else if (solutionsArray[i].tourLength > solutionsArray[bestSolution].tourLength)
									bestSolution = i;
							}

						percentImprovedLastSecond = ((double) numImproved / (double) solutionsArray.length) / secondsPassed;

						if (averageImprovement != percentImprovedLastSecond)
							averageImprovement -= (averageImprovement - percentImprovedLastSecond) * secondsPassed;
					}
			}

		@Override
		protected void renderObject(Graphics g)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 600);

				g.setColor(Color.RED);
				int barLength = (int) (Math.log10(averageImprovement + 1) * 300);
				g.fillRect(0, 0, 10, (int) barLength);

				g.setColor(Color.WHITE);
				g.drawString("Press 'h' for help", 10, 40);

				g.setColor(Color.BLUE);
				for (Node n : nodes)
					g.fillOval(n.x - (nodeSize / 2), n.y - (nodeSize / 2), nodeSize, nodeSize);

				g.setColor(Color.WHITE);
				for (int i = 0; i < solutionsArray[bestSolution].solution.length - 1; i++)
					g.drawLine(solutionsArray[bestSolution].solution[i].x, solutionsArray[bestSolution].solution[i].y,
							solutionsArray[bestSolution].solution[i + 1].x, solutionsArray[bestSolution].solution[i + 1].y);
			}

		public Node[] getNodes()
			{
				Node[] nodesCopy = new Node[nodes.size()];
				nodes.toArray(nodesCopy);
				return nodesCopy;
			}

		private void addNode(Node n)
			{
				nodesToAdd.add(n);
			}

		@Override
		protected void actionPerformed(ActionEvent event)
			{
				if (event.getSource() == showBestButton)
					showBest = !showBest;

				showBestButton.setLabel(showBest ? "Showing [Best] Solution" : "Showing [Worst] Solution");
			}

		@Override
		protected void mousePressed(MouseEvent event)
			{
				if (event.getButton() == MouseEvent.BUTTON1)
					{
						for (Node n : nodes)
							n.mousePressed(event);
					}

				else if (event.getButton() == MouseEvent.BUTTON3)
					addNode(new Node(event.getX(), event.getY()));
			}

		@Override
		protected void mouseReleased(MouseEvent event)
			{
				if (event.getButton() == MouseEvent.BUTTON1)
					for (Node n : nodes)
						n.mouseReleased(event);
			}

		@Override
		protected void mouseDragged(MouseEvent event)
			{
				for (Node n : nodes)
					n.mouseDragged(event);
				for (Solution s : solutionsArray)
					s.calculateTourLength();
			}

		@Override
		protected void mouseMoved(MouseEvent event)
			{
			}

		@Override
		protected void mouseWheelScrolled(MouseWheelEvent event)
			{
			}

		@Override
		protected void keyPressed(KeyEvent event)
			{
				if (event.getKeyCode() == 32)
					for (Solution s : solutionsArray)
						{
							s.reset();
						}
				else if (event.getKeyChar() == 'h')// view help
					changeRenderableObject(hub.info);
				else if (event.getKeyChar() == 'g')// add grid
					{
						for (int x = 0; x < 7; x++)
							for (int y = 0; y < 5; y++)
								addNode(new Node(75 + (x * 100), 75 + (y * 100)));
					}
				else if (event.getKeyChar() == 'c')// clear nodes
					{
						for (Node n : nodes)
							n.exists = false;
					}
				else if (event.getKeyChar() == 'r')// random node
					{
						addNode(new Node(Tools.randInt(50, 750), Tools.randInt(50, 500)));
					}
				else if (event.getKeyChar() == 's')// shuffle nodes
					{
						int numNodes = nodes.size();
						for (Node n : nodes)
							n.exists = false;

						for (int i = 0; i < numNodes; i++)
							addNode(new Node(Tools.randInt(50, 750), Tools.randInt(50, 500)));
					}
				else if (event.getKeyChar() == 'd')// delete random node
					{
						nodes.get(Tools.randInt(0, nodes.size() - 1)).exists = false;
					}
			}

		@Override
		protected void keyReleased(KeyEvent event)
			{
			}

		@Override
		protected void keyTyped(KeyEvent event)
			{
			}

		@Override
		protected void mouseClicked(MouseEvent event)
			{
			}

		@Override
		protected void mouseEntered(MouseEvent event)
			{
			}

		@Override
		protected void mouseExited(MouseEvent event)
			{
			}

		@Override
		protected void programGainedFocus(WindowEvent event)
			{
			}

		@Override
		protected void programLostFocus(WindowEvent event)
			{
			}

		@Override
		protected void frameResized(ComponentEvent event)
			{
			}

		@Override
		public void tScrollBarScrolled(TScrollEvent event)
			{
			}
	}
