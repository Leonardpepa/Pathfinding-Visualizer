import java.awt.Graphics2D;

public class Grid {

	private int rows, cols;
	private Node[][] grid;

	public Grid(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		grid = new Node[rows][cols];
		initialiseGrid();
	}

	public void initialiseGrid() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				grid[i][j] = new Node(i, j);
			}
		}
	}

	public void drawGrid(Graphics2D g) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				g.drawRect(i * Node.getSize(), j * Node.getSize(), Node.getSize(), Node.getSize());
				grid[i][j].draw(g);
			}
		}
	}

	public void printGrid() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				System.out.println("i: " + i + " j: " + j);
			}
		}
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public Node getNode(int x, int y) {
		return grid[x][y];
	}

	public Node[][] getGrid() {
		return grid;
	}

	public void setGrid(Node[][] grid) {
		this.grid = grid;
	}

}
