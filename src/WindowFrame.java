import java.awt.Dimension;

import javax.swing.JFrame;

public class WindowFrame extends JFrame {

	private static final long serialVersionUID = -4280772482528512323L;
	
	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;

	public WindowFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

}