package api;

import static api.CellType.BLANK;
import static api.CellType.BORDER;
import static api.CellType.MINE;

public class util {

	public static Cell[][] generateBoard(int rows, int cols, int mines) {
		Cell[][] board = new Cell[rows][cols];

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				board[r][c] = new Cell(r, c);
			}
		}

		int minesCount = 0;
		int rx;
		int ry;
		while (minesCount < mines) {
			rx = (int) (Math.random() * rows);
			ry = (int) (Math.random() * cols);

			if (board[rx][ry].getType().equals(BLANK)) {
				board[rx][ry] = new Cell(MINE, rx, ry);
				minesCount++;
			}
		}
		setBorderLocations(board, rows, cols);

		return board;
	}

	public static void setAllBorderCellsBlank(Cell[][] board) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c].getType().equals(BORDER)) {
					board[r][c] = new Cell(r, c);
				}
			}
		}
	}

	public static void setBorderLocations(Cell[][] board, int rows, int cols) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c].getType().equals(MINE)) {
					BoardLocation loc = new BoardLocation(r, c);
					BoardLocation[] neighbors = util.getNeighbors(loc);
					for (int i = 0; i < neighbors.length; i++) {

						if ((neighbors[i].x > -1 && neighbors[i].x < rows)
								&& (neighbors[i].y > -1 && neighbors[i].y < cols)) {
							if (!board[neighbors[i].getRow()][neighbors[i].getCol()].getType().equals(MINE)) {

								if (!board[neighbors[i].getRow()][neighbors[i].getCol()].getType().equals(BORDER)) {
									board[neighbors[i].getRow()][neighbors[i].getCol()] = new Cell(1,
											neighbors[i].getRow(), neighbors[i].getCol());

								} else {
									board[neighbors[i].getRow()][neighbors[i].getCol()].incrementNumMines();
								}
							}
						}
					}
				}
			}
		}
	}

	public static Cell[][] resetBoard(int rows, int cols, int mines) {
		return generateBoard(rows, cols, mines);
	}

	public static BoardLocation[] getNeighbors(BoardLocation loc) {
		BoardLocation[] arr = new BoardLocation[8];
		int row = loc.getRow();
		int col = loc.getCol();
		arr[0] = new BoardLocation(row - 1, col);
		arr[1] = new BoardLocation(row - 1, col + 1);
		arr[2] = new BoardLocation(row, col + 1);
		arr[3] = new BoardLocation(row + 1, col + 1);
		arr[4] = new BoardLocation(row + 1, col);
		arr[5] = new BoardLocation(row + 1, col - 1);
		arr[6] = new BoardLocation(row, col - 1);
		arr[7] = new BoardLocation(row - 1, col - 1);

		return arr;
	}

}
