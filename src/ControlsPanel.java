import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControlsPanel extends JPanel {

	private JButton search;
	private JButton reset;
	private JButton resetPath;
	private JButton generate;
	private JComboBox<String> algorithms;
//	private JComboBox<String> mazes;
	private JSlider size;
	private JSlider delay;
	private JLabel sizeLabel;
	private JLabel delayLabel;
	private JCheckBox allowDiagonials;

	private String[] algorithmsName = { "Breadth first search", "Depth first search", "Best first search",
			"A* search" };

	public ControlsPanel(int width, int height, GridPanel gridPanel) {
		this.setPreferredSize(new Dimension(width, height));
		this.setFocusable(true);
		this.setLayout(null);

		search = new JButton("Visualize");
		search.setBounds(25, 20, 150, 30);
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (MyUtils.solving) {
					return;
				}
				gridPanel.resetPath();
				
				MyUtils.solving = true;
				Algorithm algo = new Algorithm(gridPanel.getGrid(), gridPanel);
				algo.start();
			}
		});
		reset = new JButton("Reset grid");
		reset.setBounds(25, 120, 150, 30);
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MyUtils.solving = false;

				gridPanel.resetGrid();
				gridPanel.revalidate();
				gridPanel.repaint();
			}
		});

		resetPath = new JButton("Reset path");
		resetPath.setBounds(25, 170, 150, 30);
		resetPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (MyUtils.solving) {
					return;
				}
				gridPanel.resetPath();
				gridPanel.repaint();
			}
		});
		setBounds(25, 70, 150, 30);
		generate = new JButton("Generate maze");
		generate.setBounds(25, 70, 150, 30);
		generate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (MyUtils.solving) {
					return;
				}
				gridPanel.clearWalls();

				Grid grid = gridPanel.getGrid();
				Random rand = new Random(System.currentTimeMillis());

				for (int i = 0; i < grid.getRows(); i++) {
					for (int j = 0; j < grid.getCols(); j++) {
						Node node = grid.getNode(i, j);
						if (!node.isStart() && !node.isFinish() && rand.nextFloat() > 0.7) {
							node.setWall(true);
						}
					}
				}

			}
		});

		algorithms = new JComboBox<>(algorithmsName);
		algorithms.setSelectedIndex(0);
		algorithms.setBounds(25, 220, 150, 30);

		algorithms.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (MyUtils.solving) {
					algorithms.setSelectedIndex(MyUtils.algorithm);
					return;
				}
				MyUtils.algorithm = algorithms.getSelectedIndex();
			}
		});

		allowDiagonials = new JCheckBox("Allow diagonial moves");
		allowDiagonials.setBounds(25, 260, 200, 20);
		allowDiagonials.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(MyUtils.solving) {
					if (MyUtils.allowDiagonials) {
						allowDiagonials.setSelected(true);
					} else {
						allowDiagonials.setSelected(false);
					}
					return;
				}
				MyUtils.allowDiagonials = allowDiagonials.isSelected();
			}
		});

		sizeLabel = new JLabel("Size: 20x20");
		sizeLabel.setBounds(30, 300, 150, 20);

		size = new JSlider(20, 60, Node.size);
		size.setMajorTickSpacing(10);
		size.setMinorTickSpacing(10);
		size.setBounds(25, 325, 150, 20);

		size.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (MyUtils.solving) {
					return;
				}

				int cellSize = size.getValue() / 10 * 10;

				if (cellSize != Node.size) {
					gridPanel.updateGrid(cellSize);
					sizeLabel.setText("Size: " + gridPanel.getGrid().getRows() + "x" + gridPanel.getGrid().getCols());
				}
			}
		});

		delayLabel = new JLabel("Delay: 30ms");
		delayLabel.setBounds(30, 365, 150, 20);

		delay = new JSlider(0, 100, MyUtils.delay);
		delay.setMajorTickSpacing(10);
		delay.setMinorTickSpacing(10);

		delay.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				MyUtils.delay = delay.getValue();
				delayLabel.setText("Delay: " + MyUtils.delay + "ms");
			}
		});

		delay.setBounds(25, 390, 150, 20);

		this.add(search);
		this.add(reset);
		this.add(generate);
		this.add(algorithms);
		this.add(sizeLabel);
		this.add(size);
		this.add(delayLabel);
		this.add(delay);
		this.add(resetPath);
		this.add(allowDiagonials);

	}

	private static final long serialVersionUID = -7860009947400256170L;
}
