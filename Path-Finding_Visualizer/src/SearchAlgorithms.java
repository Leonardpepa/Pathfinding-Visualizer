import java.awt.Cursor;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

import javax.swing.JPanel;

public class SearchAlgorithms extends Thread {

	private Grid grid;
	private JPanel panel;
	private boolean solutionFound = false;

	public SearchAlgorithms(Grid grid, JPanel panel) {
		this.grid = grid;
		this.panel = panel;
	}

	@Override
	public void run() {
		if (MyUtils.solving) {
			MyUtils.breakAlgo = false;
			solutionFound = false;
			switch (MyUtils.algorithm) {
			case 0:
				bfs(grid.getStart());
				break;
			case 1:
				dfs(grid.getStart());
				break;
			case 2:
				best(grid.getStart());
				break;
			case 3:
				astar(grid.getStart());
				break;
			}
		}
		MyUtils.solving = false;
		if (MyUtils.breakAlgo) {
			grid.initialiseGrid();
		}
		panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		panel.revalidate();
		panel.repaint();
	}

	private void astar(Node start) {
		TreeSet<Node> queue = new TreeSet<>();
		queue.add(start);
		start.setAlreadyVisited(true);

		while (MyUtils.solving && !solutionFound && !queue.isEmpty()) {
			Node current = queue.pollFirst();
			current.setType(Type.CURRENT);

			panel.revalidate();
			panel.repaint();
			delay(MyUtils.delay);

			if (current.equals(grid.getFinish())) {
				extractSolution(current);
				MyUtils.solving = false;
				solutionFound = true;
				return;
			} else {
				current.setType(Type.VISITED);
				for (Node child : current.getNeighbors(grid)) {
					queue.add(child);
					child.setAlreadyVisited(true);
					child.setType(Type.FRONTIER);
				}
			}

		}
	}

	private void best(Node start) {
		TreeSet<Node> queue = new TreeSet<>();
		queue.add(start);
		start.setAlreadyVisited(true);

		while (MyUtils.solving && !solutionFound && !queue.isEmpty()) {
			Node current = queue.pollFirst();
			current.setType(Type.CURRENT);

			panel.revalidate();
			panel.repaint();
			delay(MyUtils.delay);

			if (current.equals(grid.getFinish())) {
				extractSolution(current);
				MyUtils.solving = false;
				solutionFound = true;
				return;
			} else {
				current.setType(Type.VISITED);
				for (Node child : current.getNeighbors(grid)) {
					queue.add(child);
					child.setAlreadyVisited(true);
					child.setType(Type.FRONTIER);
				}
			}

		}
	}

	private void dfs(Node start) {
		dfsUntill(start);
	}

	private void dfsUntill(Node node) {
		if (!MyUtils.solving || solutionFound) {
			return;
		}
		node.setType(Type.CURRENT);
		node.setAlreadyVisited(true);

		panel.revalidate();
		panel.repaint();
		delay(MyUtils.delay);

		if (node.equals(grid.getFinish())) {
			extractSolution(node);
			MyUtils.solving = false;
			solutionFound = true;
			return;
		} else {
			node.setType(Type.VISITED);
			LinkedList<Node> children = node.getNeighbors(grid);
			for (Node child : children) {
				if (!solutionFound) {
					for (Node temp : children) {
						if (temp.equals(child)) {
							continue;
						}
						if (!temp.getType().equals(Type.VISITED)) {
							temp.setType(Type.FRONTIER);
						}
					}
				}

				dfsUntill(child);
			}

		}

	}

	private void bfs(Node startingNode) {
		Queue<Node> frontier = new LinkedList<Node>();
		Node currentNode = null;
		frontier.add(startingNode);

		while (MyUtils.solving && !frontier.isEmpty() && !solutionFound) {
			currentNode = frontier.poll();
			currentNode.setType(Type.CURRENT);

			panel.revalidate();
			panel.repaint();
			delay(MyUtils.delay);

			if (currentNode.equals(grid.getFinish())) {
				extractSolution(currentNode);
				MyUtils.solving = false;
				solutionFound = true;
				continue;
			} else {
				currentNode.setType(Type.VISITED);
				for (Node neighbor : currentNode.getNeighbors(grid)) {
					frontier.add(neighbor);
					neighbor.setType(Type.FRONTIER);
					neighbor.setAlreadyVisited(true);
				}
			}

		}
	}

	public void extractSolution(Node node) {
		if (!MyUtils.solving) {
			return;
		}

		Node parent = node.getParent();

		while (!grid.getStart().equals(parent)) {
			parent.setType(Type.PATH);
			panel.revalidate();
			panel.repaint();
			delay(10);
			parent = parent.getParent();
		}
		panel.revalidate();
		panel.repaint();
	}

	public void delay(int delay) {
		try {
			Thread.sleep(delay);
			panel.repaint();
		} catch (InterruptedException e) {
		}
	}
}
