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
import TroysCode.T.TScrollEvent;

public class SimWindow extends RenderableObject
	{
		private static final long serialVersionUID = 1L;

		private ArrayList<Node> nodes = new ArrayList<Node>();
		private ArrayList<Node> nodesToAdd = new ArrayList<Node>();
		protected int nodeSize = 20;

		private int populationSize = 100;
		private ArrayList<Solution> population = new ArrayList<Solution>();
		private Solution bestSolution;

		@Override
		protected void initiate()
			{
				for (int i = 0; i < populationSize; i++)
					population.add(new Solution());

				for (int x = 0; x < 7; x++)
					for (int y = 0; y < 5; y++)
						addNode(new Node((x * 100) + 100, (y * 100) + 75));
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

							for (Solution s : population)
								s.reset();

							bestSolution = new Solution();
						}

				// add new nodes
				if (nodesToAdd.size() > 0)
					{
						for (Node n : nodesToAdd)
							nodes.add(n);

						for (Solution s : population)
							s.reset();

						bestSolution = new Solution();

						nodesToAdd.clear();
					}

//				// Basically there are a number of hillclimbers competing
//				// against each other
//				for (Solution s : population)
//					{
//						Solution offspring = new Solution(s);
//						if (offspring.fitness < s.fitness)
//							{
//								s = offspring;
//								if (s.fitness < bestSolution.fitness)
//									bestSolution = s;
//							}
//					}

				// Find two parent solutions
				Solution sol1 = getFittest(population.get(Tools.randInt(0, populationSize - 1)), population.get(Tools.randInt(0, populationSize - 1)));
				Solution sol2 = getFittest(population.get(Tools.randInt(0, populationSize - 1)), population.get(Tools.randInt(0, populationSize - 1)));

				// Create a new gene from the parents
				Solution newSolution = new Solution(sol1, sol2);
				newSolution.mutate();

				// Find a member of the population and replace it if the
				// new gene is better
				Solution replacement = getFittest(population.get(Tools.randInt(0, populationSize - 1)), population.get(Tools.randInt(0, populationSize - 1)));
				bestSolution = getFittest(bestSolution, getFittest(newSolution, replacement));
			}

		@Override
		protected void renderObject(Graphics g)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 600);

				g.setColor(Color.BLUE);
				for (Node n : nodes)
					g.fillOval(n.x - (nodeSize / 2), n.y - (nodeSize / 2), nodeSize, nodeSize);

				g.setColor(Color.WHITE);
				for (int i = 0; i < bestSolution.solution.length - 1; i++)
					g.drawLine(bestSolution.solution[i].x, bestSolution.solution[i].y, bestSolution.solution[i + 1].x, bestSolution.solution[i + 1].y);

				g.drawString("Press 'h' for help", 20, 20);
			}

		private final Solution getFittest(Solution one, Solution two)
			{
				return one.fitness < two.fitness ? one : two;
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
					{
						for (Node n : nodes)
							n.mouseDragged(event);
						for (Solution s : population)
							s.reCalculate();
						bestSolution.reCalculate();
					}
			}

		@Override
		protected void mouseMoved(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mouseWheelScrolled(MouseWheelEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void keyPressed(KeyEvent event)
			{
				if (event.getKeyCode() == 32)
					for (Solution s : population)
						{
							s.reset();
							bestSolution.reset();
						}
				else if (event.getKeyChar() == 'h')
					changeRenderableObject(hub.info);
			}

		@Override
		protected void keyReleased(KeyEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void keyTyped(KeyEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mouseClicked(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mouseEntered(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mouseExited(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void programGainedFocus(WindowEvent event)
			{
			}

		@Override
		protected void programLostFocus(WindowEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void frameResized(ComponentEvent event)
			{
			}

		@Override
		public void tScrollBarScrolled(TScrollEvent event)
			{
				// TODO Auto-generated method stub

			}
	}
