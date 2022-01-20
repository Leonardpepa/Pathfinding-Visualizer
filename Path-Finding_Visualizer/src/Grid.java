import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Grid {

	private int rows, cols;
	private Node[][] grid;
	private Node start;
	private Node finish;

	public Grid(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		grid = new Node[rows][cols];
		initialiseGrid();
	}
	
	public Grid cloneGrid() {
		
		return null;
	}

	public void initialiseGrid() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid[i][j] = new Node(i, j);
			}
		}
		start = grid[0][0];
		start.setStart(true);
		finish = grid[rows - 1][cols - 1];
		finish.setFinish(true);
	}

	public void clearWalls() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid[i][j].setWall(false);
			}
		}
	}

	public void resetPath() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Node node = grid[i][j];
				node.setParent(null);
				node.setAlreadyVisited(false);
				node.setType(Type.DEFAULT);
				node.setG(0);
				node.setH(0);
				node.setF();
			}
		}
	}

	public void updateGrid(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		grid = new Node[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid[i][j] = new Node(i, j);
			}
		}
	}

	public void drawGrid(Graphics2D g, JPanel panel) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid[i][j].draw(g, panel);
				g.setColor(Color.black);
				g.setStroke(new BasicStroke(1.5f));
				g.drawRect(i * Node.getSize(), j * Node.getSize(), Node.getSize(), Node.getSize());
			}
		}
		start.draw(g, panel);
		finish.draw(g, panel);
		panel.revalidate();
		panel.repaint();
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

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		if (start.equals(finish)) {
			return;
		}
		this.start.setStart(false);
		this.start = start;
		this.start.setStart(true);
	}

	public Node getFinish() {
		return finish;
	}

	public void setFinish(Node finish) {
		if (start.equals(finish)) {
			return;
		}
		this.finish.setFinish(false);
		this.finish = finish;
		this.finish.setFinish(true);
	}

}
