package ui;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import api.*;
import api.MineGame;

public class GamePanel extends JPanel {

	public static boolean done = false;

	public static boolean firstClick = true;

	public static final int SIZE = 30;

	public static final Color BACKGROUND_COLOR = Color.lightGray;

	public static final Color UNCOVERED_CELL_COLOR = Color.gray;

	public static final Color FLAGGED_COLOR = Color.green;

	public static final Color GRID_COLOR = Color.black;

	public static final int TEXT_FONT = 12;

	public static MineGame game;

	public static GamePanel gamePanel;

	public GamePanel(MineGame game, MenuPanel menuPanel) {
		gamePanel = this;
		GamePanel.game = game;
		addMouseListener(new MyMouseListener());
	}

	public void paintComponent(Graphics g) {
		g.setColor(GamePanel.BACKGROUND_COLOR);
		g.fillRect(0, 0, game.getCols() * SIZE, game.getRows() * SIZE);

		((Graphics2D) g).setStroke(new BasicStroke(1));
		for (int row = 0; row < game.getRows(); row++) {
			for (int col = 0; col < game.getCols(); col++) {

				int x = GamePanel.SIZE * col;
				int y = GamePanel.SIZE * row;
				((Graphics2D) g).setStroke(new BasicStroke(1));

				Cell c = game.getCell(row, col);
				if (c.isCovered()) {
					g.setColor(GamePanel.GRID_COLOR);
					g.drawRect(x, y, GamePanel.SIZE - 1, GamePanel.SIZE - 1);
					if (c.isFlagged()) {
						paintFlagged(((Graphics2D) g), row, col);
					}
				} else if (!c.isCovered()) {
					if (c.getNumMines() == -1) {
						paintMine(((Graphics2D) g), row, col);
					} else if (c.getNumMines() > 0) {
						paintBorderCell(((Graphics2D) g), row, col, c.getNumMines());
					} else {
						paintUncoveredCell(((Graphics2D) g), row, col);
					}

				}

			}
		}
	}

	private void paintUncoveredCell(Graphics g, int row, int col) {

		int x = GamePanel.SIZE * col;
		int y = GamePanel.SIZE * row;
		g.setColor(GamePanel.UNCOVERED_CELL_COLOR);
		g.fillRect(x, y, GamePanel.SIZE, GamePanel.SIZE);

		g.setColor(GamePanel.GRID_COLOR);
		g.drawRect(x, y, GamePanel.SIZE - 1, GamePanel.SIZE - 1);

	}

	private void paintBorderCell(Graphics g, int row, int col, int num) {

		int x = GamePanel.SIZE * col;
		int y = GamePanel.SIZE * row;

		int centX = ((x) + (x + GamePanel.SIZE)) / 2;
		int centY = ((y) + (y + GamePanel.SIZE)) / 2;
		String str = "";
		str += num;
		g.setColor(GamePanel.UNCOVERED_CELL_COLOR);
		g.fillRect(x, y, GamePanel.SIZE, GamePanel.SIZE);
		g.setColor(GamePanel.GRID_COLOR);
		g.drawRect(x, y, GamePanel.SIZE - 1, GamePanel.SIZE - 1);

		g.setColor(getBorderColor(num));
		g.drawString(str, centX - 4, centY + 4);
	}

	private void paintFlagged(Graphics g, int row, int col) {
		int x = GamePanel.SIZE * col;
		int y = GamePanel.SIZE * row;
		g.setColor(Color.green);
		g.fillRect(x, y, GamePanel.SIZE, GamePanel.SIZE);

	}

	private void paintMine(Graphics g, int row, int col) {
		int x = GamePanel.SIZE * col;
		int y = GamePanel.SIZE * row;
		g.setColor(Color.red);
		g.fillRect(x, y, GamePanel.SIZE, GamePanel.SIZE);

		g.setColor(GamePanel.GRID_COLOR);
		g.drawRect(x, y, GamePanel.SIZE - 1, GamePanel.SIZE - 1);
	}

	public static void winGameDialog() {
		MenuPanel.endGame();
		Object[] options = { "Play Again", "Exit" };
		int result = JOptionPane.showOptionDialog(gamePanel,
				"You Win! Congratulations\n" + "Would you like to exit? Or play again?", "Winner Winner Chicken Dinner",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
		dialogHandler(result);
	}

	public static void loseGameDialog() {
		Object[] options = { "Play Again", "Exit" };
		int result = JOptionPane.showOptionDialog(gamePanel,
				"You clicked on a mine ☹.\n" + "Would you like to exit? Or play again?", "*Sad Trumpet Noises*",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
		dialogHandler(result);
	}

	private static void dialogHandler(int result) {
		if (result == JOptionPane.YES_OPTION) {

		} else if (result == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
	}

	private Color getBorderColor(int n) {
		switch (n) {
		case 1:
			return Color.cyan;
		case 2:
			return Color.green;
		case 3:
			return Color.pink;
		case 4:
			return Color.blue;
		case 5:
			return Color.red;
		case 6:
			return Color.cyan;
		case 7:
			return Color.magenta;
		case 8:
			return Color.black;
		}
		return null;
	}

	private class MyMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (!done) {
					int row = e.getY() / GamePanel.SIZE;
					int col = e.getX() / GamePanel.SIZE;
					if (firstClick) {
						MenuPanel.startGame();
						game.checkForFirstClick(row, col);

					} else {
						game.revealCell(row, col);

					}

				}
			} else if (SwingUtilities.isRightMouseButton(e)) {
				if (!done) {
					int row = e.getY() / GamePanel.SIZE;
					int col = e.getX() / GamePanel.SIZE;
					if (game.getCell(row, col).isFlagged()) {

						game.removeFlagged(row, col);
					} else {
						game.setFlagged(row, col);
					}

				}
			}
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}
}
