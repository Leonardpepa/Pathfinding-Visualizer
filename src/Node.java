import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Objects;

public class Node {
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

	private boolean isWall = false;
	private boolean alreadyVisited;

	private static int size = 30; // default size
	private Node parent;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public LinkedList<Node> getNeighbors(Node[][] nodes) {
		return null;
	}

	public void draw(Graphics2D g) {

		if (type == -1) {
			return;
		}

		if (type == 0) {
			g.setColor(Color.green);
		} else if (type == 1) {
			g.setColor(Color.cyan);
		} else if (type == 2) {
			g.setColor(Color.MAGENTA);
		} else if (type == 3) {
			g.setColor(Color.red);
		} else if (type == 4) {
			g.setColor(Color.yellow);
		}
		g.fillRect(x * size, y * size, size, size);

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

}
