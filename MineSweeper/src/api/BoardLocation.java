package api;

public class BoardLocation {
	int x;
	int y;

	public BoardLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getRow() {
		return this.x;
	}

	public int getCol() {
		return this.y;
	}

	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		BoardLocation other = (BoardLocation) obj;
		return x == other.x && y == other.y;
	}

}
