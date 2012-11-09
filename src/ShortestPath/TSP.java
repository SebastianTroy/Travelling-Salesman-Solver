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
		private final Solution[] tourPaths = new Solution[10];
		private int bestSolution;

		// A small graph to show the % solutions that were improved upon each
		// tick
		private int graphXPos;
		private int improvmentsThisTick = 0;
		private final int[] graphData = new int[100];

		private TButton showBestButton = new TButton(5, 5, 80, 30, "Show Best");
		private boolean showBest = true;

		@Override
		protected void initiate()
			{
				addTComponent(showBestButton);

				for (int x = 0; x < 6; x++)
					addNode(new Node(Tools.randInt(50, 750), Tools.randInt(50, 500)));

				for (int i = 0; i < tourPaths.length; i++)
					tourPaths[i] = new Solution();
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

							for (Solution s : tourPaths)
								s.reset();

							bestSolution = Tools.randInt(0, tourPaths.length);
						}

				// add new nodes
				if (nodesToAdd.size() > 0)
					{
						for (Node n : nodesToAdd)
							nodes.add(n);

						for (Solution s : tourPaths)
							s.reset();

						bestSolution = Tools.randInt(0, tourPaths.length);

						nodesToAdd.clear();
					}

				// Basically there are a number of hillclimbers competing
				// against each other
				for (int i = 0; i < tourPaths.length; i++)
					{
						Solution s = tourPaths[i];
						Solution mutant = new Solution(s);
						
						if (mutant.tourLength < s.tourLength)
							{
								improvmentsThisTick++;
								s = mutant;
								if (showBest)
									{
										if (s.tourLength < tourPaths[bestSolution].tourLength)
											bestSolution = i;
									}
								else if (s.tourLength > tourPaths[bestSolution].tourLength)
									bestSolution = i;

							}
					}

				graphData[graphXPos] = improvmentsThisTick;

				graphXPos++;
				if (graphXPos > graphData.length - 1)
					graphXPos = 0;
				improvmentsThisTick = 0;
			}

		@Override
		protected void renderObject(Graphics g)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 600);

				g.setColor(Color.RED);
				for (int i = 0; i < graphData.length; i++)
					g.drawLine(0, i, (int) ((graphData[i] / (double) tourPaths.length) * 200), i);

				g.setColor(Color.WHITE);
				g.drawString("Press 'h' for help", 10, 120);

				g.setColor(Color.BLUE);
				for (Node n : nodes)
					g.fillOval(n.x - (nodeSize / 2), n.y - (nodeSize / 2), nodeSize, nodeSize);

				g.setColor(Color.WHITE);
				for (int i = 0; i < tourPaths[bestSolution].solution.length - 1; i++)
					g.drawLine(tourPaths[bestSolution].solution[i].x, tourPaths[bestSolution].solution[i].y, tourPaths[bestSolution].solution[i + 1].x,
							tourPaths[bestSolution].solution[i + 1].y);
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

				showBestButton.setLabel(showBest ? "Show Best" : "Show Worst");
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
				for (Solution s : tourPaths)
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
					for (Solution s : tourPaths)
						{
							s.reset();
						}
				else if (event.getKeyChar() == 'h')
					changeRenderableObject(hub.info);
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
