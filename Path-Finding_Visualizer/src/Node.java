import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Objects;

import javax.swing.JPanel;

public class Node implements Comparable<Node> {
	private int x;
	private int y;
	private int g;
	private double h;
	private double f;
	private Type type = Type.DEFAULT; // default value
	// type of node is responsible for the color

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

		if (this.x + 1 < grid.getRows() && x >= 0) {
			Node node = grid.getNode(this.x + 1, this.y);
			if (!node.alreadyVisited && !node.isWall) {
				createNeighbor(node, grid);
				neighbors.add(node);
			}
		}

		if (this.y + 1 < grid.getCols() && this.y >= 0) {
			Node node = grid.getNode(this.x, this.y + 1);
			if (!node.alreadyVisited && !node.isWall) {
				createNeighbor(node, grid);
				neighbors.add(node);
			}
		}

		if (this.y - 1 >= 0 && this.y < grid.getCols()) {
			Node node = grid.getNode(this.x, this.y - 1);
			if (!node.alreadyVisited && !node.isWall) {
				createNeighbor(node, grid);
				neighbors.add(node);
			}
		}

		if (this.x - 1 >= 0 && this.x < grid.getRows()) {
			Node node = grid.getNode(this.x - 1, this.y);
			if (!node.alreadyVisited && !node.isWall) {
				createNeighbor(node, grid);
				neighbors.add(node);
			}
		}

		if (MyUtils.allowDiagonials) {
			if (this.x - 1 >= 0 && this.y - 1 >= 0) {
				Node node = grid.getNode(this.x - 1, this.y - 1);
				if (!node.alreadyVisited && !node.isWall) {
					createNeighbor(node, grid);
					neighbors.add(node);
				}
			}
			if (this.x - 1 >= 0 && this.y + 1 < grid.getCols()) {
				Node node = grid.getNode(this.x - 1, this.y + 1);
				if (!node.alreadyVisited && !node.isWall) {
					createNeighbor(node, grid);
					neighbors.add(node);
				}
			}
			if (this.x + 1 < grid.getRows() && this.y + 1 < grid.getCols()) {
				Node node = grid.getNode(this.x + 1, this.y + 1);
				if (!node.alreadyVisited && !node.isWall) {
					createNeighbor(node, grid);
					neighbors.add(node);
				}
			}

			if (this.x + 1 < grid.getRows() && this.y - 1 >= 0) {
				Node node = grid.getNode(this.x + 1, this.y - 1);
				if (!node.alreadyVisited && !node.isWall) {
					createNeighbor(node, grid);
					neighbors.add(node);
				}
			}

		}

		return neighbors;
	}

	public void createNeighbor(Node node, Grid grid) {
		node.setParent(this);
		node.setG(this.g + 1);
		node.setH(node.heuristic(grid));
		node.setF();
	}

	public double heuristic(Grid grid) {
		double distance;
		if (MyUtils.allowDiagonials) {
			distance = Math.hypot(Math.abs(this.x - grid.getFinish().getX()),
					Math.abs(this.y - grid.getFinish().getY()));
		} else {
			distance = Math.abs(this.x - grid.getFinish().getX()) + Math.abs(this.y - grid.getFinish().getY());
		}
		return distance;
	}

	public void draw(Graphics2D g, JPanel panel) {

		g.setColor(Color.black);
		if (isWall) {
			g.fillRect(x * size, y * size, size, size);
		}

		switch (type) {
		case START:
			g.setColor(Color.blue);
			break;
		case VISITED:
			g.setColor(Color.cyan);
			break;
		case CURRENT:
			g.setColor(Color.magenta);
			break;
		case FINISH:
			g.setColor(Color.red);
			break;
		case FRONTIER:
			g.setColor(Color.GREEN);
			break;
		case WALL:
			return;
		case PATH:
			g.setColor(Color.yellow);
			break;

		default:
			return;
		}
		g.setStroke(new BasicStroke(1.5f));
		g.fillRect(x * size, y * size, size, size);
		panel.revalidate();
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
			this.type = Type.START;
		} else {
			this.type = Type.DEFAULT;
		}
		this.isStart = isStart;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		if (isFinish) {
			this.type = Type.FINISH;
		} else {
			this.type = Type.DEFAULT;
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

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public double getF() {
		return f;
	}

	public void setF() {
		this.f = this.g + this.h;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
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
		if (isWall) {
			this.setType(Type.WALL);
		} else {
			this.setType(Type.DEFAULT);
		}
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
	public int compareTo(Node n2) {
		switch (MyUtils.algorithm) {
		case 2:
			if (this.equals(n2)) {
				return 0;
			}
			if (this.getH() > n2.getH()) {
				return 1;
			} else if (this.getH() < n2.getH()) {
				return -1;
			} else {
				return 1;
			}
		case 3:
			if (this.equals(n2)) {
				return 0;
			}
			if (this.getF() > n2.getF()) {
				return 1;
			} else if (this.getF() < n2.getF()) {
				return -1;
			} else {
				return 1;
			}
		default:
			break;
		}

		return 0;
	}

}
