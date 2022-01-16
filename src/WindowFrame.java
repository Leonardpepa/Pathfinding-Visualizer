import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowFrame extends JFrame {

	private static final long serialVersionUID = -4280772482528512323L;

	private JPanel container;

	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;

	public WindowFrame() {

		container = new JPanel();
		GridPanel gridPanel = new GridPanel();

		container.add(gridPanel);

		this.setContentPane(container);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.pack();
//		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

}