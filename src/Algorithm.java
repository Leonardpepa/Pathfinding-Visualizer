import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;

import javax.swing.JPanel;

public class Algorithm extends Thread {

	private Grid grid;
	private JPanel panel;
	private boolean solutionFound = false;

	public Algorithm(Grid grid, JPanel panel) {
		this.grid = grid;
		this.panel = panel;
	}

	@Override
	public void run() {
		if (MyUtils.solving) {
			MyUtils.breakAlgo = false;
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
	}

	private void astar(Node start) {
		TreeSet<Node> queue = new TreeSet<Node>();
		queue.add(start);
		Node currentNode = null;
		while (MyUtils.solving && !solutionFound && !queue.isEmpty()) {
			currentNode = queue.pollLast();
			
			currentNode.setType(2);
			currentNode.setAlreadyVisited(true);
			panel.repaint();
			delay(MyUtils.delay);

			if (currentNode.equals(grid.getFinish())) {
				MyUtils.solving = false;
				solutionFound = true;
				extractSolution(currentNode);
				continue;
			} else {
				currentNode.setType(1);
				for (Node neighbor : currentNode.getNeighbors(grid)) {
					queue.add(neighbor);
					neighbor.setAlreadyVisited(true);
				}
			}

		}
		MyUtils.solving = false;
		if (MyUtils.breakAlgo) {
			grid.initialiseGrid();
		}
		panel.repaint();
	}

	private void best(Node start) {
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(start);
		Node currentNode = null;
		while (MyUtils.solving && !solutionFound && !queue.isEmpty()) {
			currentNode = queue.pollLast();
			
			currentNode.setType(2);
			currentNode.setAlreadyVisited(true);
			panel.repaint();
			delay(MyUtils.delay);

			if (currentNode.equals(grid.getFinish())) {
				MyUtils.solving = false;
				solutionFound = true;
				extractSolution(currentNode);
				continue;
			} else {
				currentNode.setType(1);
				for (Node neighbor : currentNode.getNeighbors(grid)) {
					queue.add(neighbor);
					neighbor.setAlreadyVisited(true);
				}
				Collections.sort(queue);
			}

		}
		MyUtils.solving = false;
		if (MyUtils.breakAlgo) {
			grid.initialiseGrid();
		}
		panel.repaint();
	}

	private void dfs(Node start) {
		dfsUntill(start);
		MyUtils.solving = false;
	}

	private void dfsUntill(Node node) {
		if (!MyUtils.solving || solutionFound) {
			return;
		}
		node.setType(2);
		node.setAlreadyVisited(true);
		panel.repaint();
		delay(MyUtils.delay);

		if (node.equals(grid.getFinish())) {
			MyUtils.solving = false;
			solutionFound = true;
			extractSolution(node);
			return;
		} else {
			node.setType(1);
			for (Node child : node.getNeighbors(grid)) {
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
			currentNode.setType(2);
			currentNode.setAlreadyVisited(true);
			panel.repaint();
			delay(MyUtils.delay);

			if (currentNode.equals(grid.getFinish())) {
				MyUtils.solving = false;
				extractSolution(currentNode);
				continue;
			} else {
				currentNode.setType(1);
				for (Node neighbor : currentNode.getNeighbors(grid)) {
					frontier.add(neighbor);
					neighbor.setType(4);
					neighbor.setAlreadyVisited(true);
				}
			}

		}

		MyUtils.solving = false;
		if (MyUtils.breakAlgo) {
			grid.initialiseGrid();
		}
		panel.repaint();
	}

	public void extractSolution(Node node) {
		Node parent = node.getParent();

		while (parent != null) {
			parent.setType(5);
			panel.repaint();
			delay(10);
			parent = parent.getParent();
		}
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
