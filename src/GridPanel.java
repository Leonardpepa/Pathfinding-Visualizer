import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GridPanel extends JPanel {

	private static final long serialVersionUID = 3728396685715564240L;
	private int size = Node.getSize();

	private int rows;
	private int cols;
	private Grid grid;

	public GridPanel() {
		this.setSize(700, 700);
		this.setPreferredSize(new Dimension(700, 700));
		this.setFocusable(true);
		rows = this.getHeight() / size;
		cols = this.getWidth() / size;
		
		grid = new Grid(rows, cols);
	}

	@Override
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		grid.drawGrid(g);
	}

}
