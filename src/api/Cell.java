package api;

import static api.CellType.BLANK;
import static api.CellType.BORDER;
import static api.CellType.MINE;

public class Cell {

	private CellType type;

	private BoardLocation loc;

	private boolean covered;

	private boolean flagged;
	// if cell is a mine, numMines = -1
	private int numMines;

	public Cell(int x, int y) {
		this.type = BLANK;
		numMines = 0;
		loc = new BoardLocation(x, y);
		covered = true;
	}

	public Cell(CellType type, int x, int y) {
		if (type == MINE) {
			flagged = false;
			this.type = MINE;
			numMines = -1;
			loc = new BoardLocation(x, y);
			covered = true;
		}
	}

	public Cell(int n, int x, int y) {
		flagged = false;
		this.type = BORDER;
		numMines = n;
		loc = new BoardLocation(x, y);
		covered = true;
	}

	public int getNumMines() {
		return numMines;
	}

	public void incrementNumMines() {
		this.numMines++;
	}

	public boolean isCovered() {
		return this.covered;
	}

	public void setUncoved() {
		if (this.covered) {
			MineGame.NUM_UNCOVERED_CELLS++;
			this.covered = false;
		}

	}

	public void setUnFlagged() {
		this.flagged = false;
	}

	public void setFlagged() {
		this.flagged = true;
	}

	public boolean isFlagged() {
		return this.flagged;
	}

	public void setType(CellType type) {
		this.type = type;

		if (type == MINE) {
			this.numMines = -1;
		} else if (type == BLANK) {
			this.numMines = 0;
		} else {
			this.numMines = 0;
		}

	}

	public CellType getType() {
		return this.type;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		Cell other = (Cell) obj;
		return this.type == other.type && this.loc == other.loc && this.numMines == other.numMines;
	}

}
