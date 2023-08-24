package ui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import api.MineGame;

public class Main {
	static MenuPanel menuPanel;
	static GamePanel panel;
	static JPanel mainPanel;
	static JFrame frame;

	private static void create() {
		MineGame game;
		game = new MineGame(1);
		create(game);
	}

	private static void create(MineGame game) {
		menuPanel = new MenuPanel();
		panel = new GamePanel(game, menuPanel);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(menuPanel);
		mainPanel.add(panel);

		frame = new JFrame("Minesweeper");
		frame.getContentPane().add(mainPanel);

		Dimension d = new Dimension(game.getCols() * GamePanel.SIZE, game.getRows() * GamePanel.SIZE);
		panel.setPreferredSize(d);

		d = new Dimension(game.getCols() * GamePanel.SIZE, 2 * GamePanel.SIZE);
		menuPanel.setPreferredSize(d);

		frame.pack();

		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
	}

	public static void createNewFrame(MineGame game) {
		frame.getContentPane().removeAll();
		frame.repaint();

		menuPanel = new MenuPanel();
		panel = new GamePanel(game, menuPanel);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(menuPanel);
		mainPanel.add(panel);

		frame.getContentPane().add(mainPanel);

		Dimension d = new Dimension(game.getCols() * GamePanel.SIZE, game.getRows() * GamePanel.SIZE);
		panel.setPreferredSize(d);

		d = new Dimension(game.getCols() * GamePanel.SIZE, 2 * GamePanel.SIZE);
		menuPanel.setPreferredSize(d);

		frame.pack();

		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
	}

	public static void main(String[] args) {
		create();
	}

}
