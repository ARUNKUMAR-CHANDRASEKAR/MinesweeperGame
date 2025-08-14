package com.task.minesweeperGame.model;

/**
 * Represents a single square [Grid] in the Minesweeper game board.
 * 
 * 
 * Each Grid object stores its position (row, column), whether it contains a
 * mine, whether it has been revealed, and the number of mines adjacent to it.
 *
 * - This class is part of the Model layer in the MVVM architecture.
 */
public class Grid {

	private final int row;

	private final int col;

	private boolean revealed;

	private boolean mine;

	private int adjacentMines;

	/**
	 * Creates a grid cell at the specified position.
	 *
	 * @param row the row index of the cell
	 * @param col the column index of the cell
	 */
	public Grid(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public boolean isRevealed() {
		return revealed;
	}

	public void reveal() {
		revealed = true;
	}

	public boolean isMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public int getAdjacentMines() {
		return adjacentMines;
	}

	public void setAdjacentMines(int count) {
		adjacentMines = count;
	}
}
