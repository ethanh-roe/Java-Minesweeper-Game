package api;

import static api.CellType.*;

import ui.GamePanel;
import ui.MenuPanel;

public class MineGame {
	private int rows;

	private int cols;

	static int SMALL_NUM_MINES = 10;
	static int MEDIUM_NUM_MINES = 40;
	static int LARGE_NUM_MINES = 99;
	public static int NUM_UNCOVERED_CELLS = 0;
	public static int DIFFICULTY = 1;

	public static int NUM_MINES;

	private Cell[][] grid;

	public MineGame(int n) {
		if (n == 1) {
			rows = 9;
			cols = 9;
			NUM_MINES = SMALL_NUM_MINES;
			grid = util.generateBoard(rows, cols, NUM_MINES);
		} else if (n == 2) {
			rows = 16;
			cols = 16;
			NUM_MINES = MEDIUM_NUM_MINES;
			grid = util.generateBoard(rows, cols, NUM_MINES);
		} else if (n == 3) {
			rows = 16;
			cols = 30;
			NUM_MINES = LARGE_NUM_MINES;
			grid = util.generateBoard(rows, cols, NUM_MINES);
		}

	}

	public Cell getCell(int row, int col) {
		return grid[row][col];
	}

	public int getRows() {
		return this.rows;
	}

	public int getCols() {
		return this.cols;
	}

	public void endGame() {
		GamePanel.done = true;
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				if (grid[r][c].getType().equals(MINE)) {
					grid[r][c].setUncoved();
				}
			}
		}
		MenuPanel.endGame();
		GamePanel.loseGameDialog();

	}

	private void winGame() {
		GamePanel.done = true;
		GamePanel.winGameDialog();
	}

	public void resetGame() {
		GamePanel.done = false;
		grid = util.resetBoard(rows, cols, NUM_MINES);
	}

	public void revealCell(int row, int col) {
		Cell c = grid[row][col];
		if (GamePanel.done) {
			return;
		}
		if (c.isCovered()) {
			if (c.getType().equals(BLANK) || c.getType().equals(BORDER)) {
				revealNearbyEmptyCells(row, col);
			} else if (c.getType().equals(FLAGGED)) {
				return;
			} else if (c.getType().equals(MINE)) {
				endGame();
			}
		}
	}

	public void revealNearbyEmptyCells(int row, int col) {
		Cell c = grid[row][col];
		BoardLocation location = new BoardLocation(row, col);
		if (!c.isCovered()) {
			return;
		} else {
			if (c.getType().equals(BLANK) && !c.isFlagged()) {

				c.setUncoved();

				BoardLocation[] neighbors = util.getNeighbors(location);

				for (int i = 0; i < neighbors.length; i++) {

					if ((neighbors[i].x > -1 && neighbors[i].x < rows)
							&& (neighbors[i].y > -1 && neighbors[i].y < cols)) {

						c = grid[neighbors[i].getRow()][neighbors[i].getCol()];

						if (c.getType().equals(BLANK) && c.isCovered()) {

							revealNearbyEmptyCells(neighbors[i].getRow(), neighbors[i].getCol());

							c.setUncoved();

						} else if (c.getType().equals(BORDER) && c.isCovered()) {

							c.setUncoved();
						}
					}

				}

			} else if (c.getType().equals(BORDER) && !c.isFlagged()) {

				c.setUncoved();

			} else if (c.getType().equals(MINE)) {
				return;

			} else {
				return;
			}

		}
		if (checkWinCondition()) {
			revealAllCells();
			winGame();
		}

	}

	private boolean checkWinCondition() {
		return NUM_UNCOVERED_CELLS + NUM_MINES == rows * cols;

	}

	public void revealAllCells() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				Cell cell = grid[r][c];
				cell.setUncoved();
			}
		}
	}

	public void checkForFirstClick(int row, int col) {

		Cell c = grid[row][col];
		boolean moved = false;
		int column = 0;
		if (c.getType().equals(MINE)) {
			grid[row][col].setType(BLANK);

			while (!moved) {
				if (grid[0][column].getType().equals(MINE)) {
					column++;
				} else {
					grid[0][column].setType(MINE);
					moved = true;
					column = 0;
				}
			}

			util.setAllBorderCellsBlank(grid);
			util.setBorderLocations(grid, rows, cols);

		}
		revealCell(row, col);
		GamePanel.firstClick = false;
	}

	public void setFlagged(int row, int col) {
		Cell c = grid[row][col];
		c.setFlagged();
		MenuPanel.removeFlag();
	}

	public void removeFlagged(int row, int col) {
		Cell c = grid[row][col];
		c.setUnFlagged();
		MenuPanel.addFlag();

	}

}
