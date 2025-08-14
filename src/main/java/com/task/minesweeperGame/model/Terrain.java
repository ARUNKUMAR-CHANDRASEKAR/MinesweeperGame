package com.task.minesweeperGame.model;

import java.util.*;
import java.util.stream.*;

/**
 * - Represents the entire Minesweeper game board (terrain) containing all
 * grids.
 * 
 * - The terrain is initialized with a given size (NxN) and a specified number
 * of mines. It manages the placement of mines and the calculation of the number
 * of adjacent mines for each grid.
 * 
 *
 * This class is part of the Model layer in the MVVM architecture.
 */
public class Terrain {

	/** The width/height of the terrain (square grid). */
	private final int size;

	/** The total number of mines placed on the terrain. */
	private final int totalMines;

	/** 2D array of grid grids representing the terrain. */
	private final Grid[][] grid;

	/**
	 * Creates a new game terrain.
	 *
	 * @param size       the size of the terrain (NxN)
	 * @param totalMines the number of mines to place
	 */
	public Terrain(int size, int totalMines) {
		this.size = size;
		this.totalMines = totalMines;

		// Create a 2D grid of Grid objects
		grid = IntStream.range(0, size)
				.mapToObj(r -> IntStream.range(0, size).mapToObj(c -> new Grid(r, c)).toArray(Grid[]::new))
				.toArray(Grid[][]::new);

		placeMines();
		computeAdjacents();
	}

	/**
	 * Randomly places mines on the terrain. Ensures that no grid gets more than one
	 * mine.
	 */
	private void placeMines() {
		Random rnd = new Random();

		// Repeat until all mines are placed
		IntStream.iterate(0, i -> i < totalMines, i -> i + 1).forEach(i -> {
			while (true) {
				int r = rnd.nextInt(size);
				int c = rnd.nextInt(size);
				Grid gridObj = grid[r][c];

				if (!gridObj.isMine()) {
					gridObj.setMine(true);
					break; // successfully placed a mine in this iteration
				}
			}
		});
	}

	/**
	 * - Calculates the number of adjacent mines for each grid. 
	 * - Skips calculation for grids that already contain a mine.
	 */
	private void computeAdjacents() {
		// Relative positions of all 8 neighboring grids
		int[] mineRows = { -1, -1, -1, 0, 0, 1, 1, 1 };
		int[] mineCols = { -1, 0, 1, -1, 1, -1, 0, 1 };

		IntStream.range(0, size).forEach(r -> IntStream.range(0, size).forEach(c -> {
			Grid gridObj = grid[r][c];
			if (gridObj.isMine())
				return; 

			// Count adjacent mines
			long count = IntStream.range(0, 8).mapToObj(k -> new int[] { r + mineRows[k], c + mineCols[k] })
					.filter(pos -> terrainBounds(pos[0], pos[1])).map(pos -> grid[pos[0]][pos[1]]).filter(Grid::isMine)
					.count();

			gridObj.setAdjacentMines((int) count);
		}));
	}

	/**
	 * Checks if the given coordinates are inside the terrain boundaries.
	 *
	 * @param r row index
	 * @param c column index
	 * @return true if the coordinates are within bounds, false otherwise
	 */
	private boolean terrainBounds(int r, int c) {
		return r >= 0 && r < size && c >= 0 && c < size;
	}

	/**
	 * Gets the full 2D grid array representing the terrain.
	 *
	 * @return the grid array
	 */
	public Grid[][] getGrid() {
		return grid;
	}

	/**
	 * Gets the size of the terrain.
	 *
	 * @return the terrain size (NxN)
	 */
	public int getSize() {
		return size;
	}
}
