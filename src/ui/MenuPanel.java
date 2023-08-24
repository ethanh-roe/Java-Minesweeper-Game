package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import api.MineGame;

public class MenuPanel extends JPanel {

	private static int time;

	private static int flags;

	private static boolean done;
	MineGame game;

	static JLabel timeDisplay;

	static JLabel flagDisplay;

	public MenuPanel() {
		game = GamePanel.game;
		time = 0;
		flags = MineGame.NUM_MINES;
		done = GamePanel.done;

		String[] difficulties = { "Easy", "Medium", "Hard" };
		JComboBox difficultyList = new JComboBox(difficulties);
		difficultyList.setPreferredSize(new Dimension(35, 25));
		difficultyList.addActionListener(new DifficultyListListener());

		JButton resetButton = new JButton("↻");
		resetButton.setPreferredSize(new Dimension(35, 25));
		resetButton.addActionListener(new ResetButtonListener());

		timeDisplay = new JLabel(time / 60 + ":0" + time % 60, SwingConstants.CENTER);
		timeDisplay.setFont(new Font("Serif", Font.PLAIN, 30));
		timeDisplay.setPreferredSize(new Dimension(85, 50));
		timeDisplay.setOpaque(true);
		timeDisplay.setBackground(Color.black);
		timeDisplay.setForeground(Color.red);

		flagDisplay = new JLabel("" + flags, SwingConstants.CENTER);
		flagDisplay.setFont(new Font("Serif", Font.PLAIN, 30));
		flagDisplay.setPreferredSize(new Dimension(85, 50));
		flagDisplay.setOpaque(true);
		flagDisplay.setBackground(Color.black);
		flagDisplay.setForeground(Color.red);

		JButton helpButton = new JButton("?");
		helpButton.setPreferredSize(new Dimension(15, 15));
		helpButton.addActionListener(new HelpButtonListener());

		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridLayout(2, 1));
		middlePanel.add(resetButton);
		middlePanel.add(difficultyList);

		this.add(helpButton);
		this.add(flagDisplay);
		this.add(middlePanel);
		this.add(timeDisplay);

	}

	public static void addFlag() {
		flagDisplay.setText(Integer.toString(++flags));

	}

	public static void removeFlag() {
		flagDisplay.setText(Integer.toString(--flags));
	}

	@Override
	public void paintComponent(Graphics g) {
		((Graphics2D) g).setBackground(Color.black);

		g.drawRect(0, 0, WIDTH, HEIGHT);
	}

	public static void startGame() {
		done = false;
		timer.start();
	}

	public static void endGame() {
		done = true;
		flags = MineGame.NUM_MINES;
		time = 0;
		GamePanel.firstClick = true;
		timer.restart();

		MineGame.NUM_UNCOVERED_CELLS = 0;
	}

	static Timer timer = new Timer(1000, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!done) {
				time++;
				if (time % 60 < 10) {
					timeDisplay.setText(time / 60 + ":0" + time % 60);
				} else {
					timeDisplay.setText(time / 60 + ":" + time % 60);
				}

			}

		}
	});

	public class ResetButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GamePanel.game.resetGame();
			GamePanel.gamePanel.repaint();
			flagDisplay.setText(Integer.toString(flags));
			timeDisplay.setText(time / 60 + ":0" + time % 60);
			endGame();

		}
	}

	public class HelpButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(GamePanel.gamePanel,
					"Minesweeper is a game where the goal is to locate a number of randomly placed 'mines' in the shortest amount of time possible by clicking on 'safe' squares.\n"
							+ "If the player clicks on a mine the game ends."
							+ "Otherwise a number between 1 and 8 is shown which represents the amount of mines that border that particular\n"
							+ "square. Squares with no number dont border a mine. If the player suspects a square is occupied by a mine they have the option of 'flagging' that square.\n"
							+ "It is not necessary to place a flag on every mine to win the game. You only need to uncover all the squares that aren't mines. However, marking squares\n"
							+ "suspected of being mine can help keep track of how may more mines there are." + "\n\n"
							+ "The UI above the grid of squares represents the following-\n"
							+ "? - Opens up the info panel.\n"
							+ "The label on the left represents the number of mines on the board and decreases as you place flags\n"
							+ "↻ - Resets the game\n"
							+ "The label on the right displays the amount of time you have spent playing" + "\n\n"
							+ "For info on common patterns and tactics for minesweeper visit \n"
							+ "minesweepergame.com/strategy/how-to-play-minesweeper",
					"Info", JOptionPane.PLAIN_MESSAGE);

		}
	}

	public class DifficultyListListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			MineGame.DIFFICULTY = cb.getSelectedIndex() + 1;
			Main.createNewFrame(new MineGame(MineGame.DIFFICULTY));


		}
	}

}
