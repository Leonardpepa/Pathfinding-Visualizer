import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Objects;

import javax.swing.JPanel;

public class Node implements Comparable<Node> {
	private int x;
	private int y;
	private int g;
	private int h;
	private int f;
	private int type = -1; // default value
	// type 0: starting node
	// type 1: visited node
	// type 2: current node
	// type 3: ending node
	// type 4: frontier node
	// type 5: path node

	private boolean isWall = false;
	private boolean isStart = false;
	private boolean isFinish = false;

	private boolean alreadyVisited;

	public static int size = 30;// default size

	private Node parent;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public LinkedList<Node> getNeighbors(Grid grid) {

		LinkedList<Node> neighbors = new LinkedList<Node>();

		if (this.y - 1 >= 0 && this.y < grid.getCols()) {
			Node node = grid.getNode(this.x, this.y - 1);
			if (!node.alreadyVisited && !node.isWall) {
				node.setParent(this);
				node.setG(this.g + 1);
				node.setH(heuristic(grid));
				neighbors.add(node);
			}
		}

		if (this.x + 1 < grid.getRows() && x >= 0) {
			Node node = grid.getNode(this.x + 1, this.y);
			if (!node.alreadyVisited && !node.isWall) {
				node.setParent(this);
				node.setG(this.g + 1);
				node.setH(heuristic(grid));
				neighbors.add(node);
			}
		}
		if (this.y + 1 < grid.getCols() && this.y >= 0) {
			Node node = grid.getNode(this.x, this.y + 1);
			if (!node.alreadyVisited && !node.isWall) {
				node.setParent(this);
				node.setG(this.g + 1);
				node.setH(heuristic(grid));
				neighbors.add(node);
			}
		}

		if (this.x - 1 >= 0 && this.x < grid.getRows()) {
			Node node = grid.getNode(this.x - 1, this.y);
			if (!node.alreadyVisited && !node.isWall) {
				node.setParent(this);
				node.setG(this.g + 1);
				node.setH(heuristic(grid));
				neighbors.add(node);
			}
		}

		return neighbors;
	}

	public int heuristic(Grid grid) {

		int distance = (int) Point2D.distance(this.x, this.y, grid.getFinish().getX(), grid.getFinish().getY());
		return distance;
	}

	public void draw(Graphics2D g, JPanel panel) {
		
		
		g.setColor(Color.black);
		if(isWall) {
			g.fillRect(x * size, y * size, size, size);
		}
		
		switch (type) {
		case 0:
			g.setColor(Color.blue);
			break;
		case 1:
			g.setColor(Color.cyan);
			break;
		case 2:
			g.setColor(Color.magenta);
			break;
		case 3:
			g.setColor(Color.red);
			break;
		case 4:
			g.setColor(Color.GREEN);
			break;
		case 5:
			g.setColor(Color.yellow);
			break;

		default:
			return;
		}
		g.setStroke(new BasicStroke(1.5f));
		g.fillRect(x * size, y * size, size, size);
		panel.repaint();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Node newNode = new Node(this.x, this.y);
		return newNode;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "x: " + this.x + " y: " + this.y;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		if (isStart) {
			this.type = 0;
		} else {
			this.type = -1;
		}
		this.isStart = isStart;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		if (isFinish) {
			this.type = 3;
		} else {
			this.type = -1;
		}
		this.isFinish = isFinish;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getF() {
		return f;
	}

	public void setF() {
		this.f = this.g + this.h;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		if (isStart || isFinish) {
			return;
		}
		this.type = type;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public boolean isWall() {
		return isWall;
	}

	public void setWall(boolean isWall) {
		this.isWall = isWall;
	}
	
	public void toggleWall() {
		this.isWall = !this.isWall;
	}
	public static int getSize() {
		return size;
	}

	public static void setSize(int size) {
		Node.size = size;
	}

	public boolean isAlreadyVisited() {
		return alreadyVisited;
	}

	public void setAlreadyVisited(boolean alreadyVisited) {
		this.alreadyVisited = alreadyVisited;
	}

	@Override
	public int compareTo(Node o) {
		if (this.equals(o)) {
			return 0;
		}

		if (MyUtils.algorithm == 2) {
			if (this.h > o.getH()) {
				return 1;
			} else if (this.h < o.getH()) {
				return -1;
			} else {
				if (this.g > o.getG()) {
					return 1;
				}
				return -1;
			}
		}else if(MyUtils.algorithm == 3) {
			if (this.f > o.getF()) {
				return 1;
			} else if (this.f < o.getF()) {
				return -1;
			} else {
				if (this.g > o.getG()) {
					return 1;
				}
				return -1;
			}
		}
		
		return 0;

	}

}
