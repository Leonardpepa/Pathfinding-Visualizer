import java.util.LinkedList;
import java.util.Queue;

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

	}

	private void best(Node start) {
		// TODO Auto-generated method stub

	}

	private void dfs(Node start) {
		dfsUntill(start);

	}

	private void dfsUntill(Node node) {
		if (!MyUtils.solving) {
			return;
		}

		node.setType(2);
		node.setAlreadyVisited(true);
		panel.repaint();
		delay(MyUtils.delay);

		if (node.equals(grid.getFinish())) {
			MyUtils.solving = false;
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
		Node curretNode = null;
		frontier.add(startingNode);

		while (MyUtils.solving && !frontier.isEmpty() && !solutionFound) {
			curretNode = frontier.poll();
			curretNode.setType(2);
			panel.repaint();
			delay(MyUtils.delay);

			if (curretNode.equals(grid.getFinish())) {
				MyUtils.solving = false;
				extractSolution(curretNode);
				continue;
			} else {
				curretNode.setType(1);
				curretNode.setAlreadyVisited(true);
				for (Node neighbor : curretNode.getNeighbors(grid)) {
					frontier.add(neighbor);
					neighbor.setType(4);
					neighbor.setAlreadyVisited(true);
				}
			}

		}

		MyUtils.solving = false;
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
