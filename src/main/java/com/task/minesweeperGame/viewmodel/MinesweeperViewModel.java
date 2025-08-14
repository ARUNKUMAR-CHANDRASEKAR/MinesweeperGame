package com.task.minesweeperGame.viewmodel;

import java.util.*;
import com.task.minesweeperGame.model.Grid;
import com.task.minesweeperGame.model.Terrain;

/**
 * 
 * 
 * ViewModel class for the Minesweeper game following the MVVM pattern.
 * Handles game logic, grid revealing, win/loss detection, and flood fill.
 * 
 * 
 */
public class MinesweeperViewModel {
    private Terrain terrain;           // The game terrain containing the grid and mines
    private int remainingToReveal;     // Number of non-mine grids left to reveal
    private boolean gameOver;          // Flag to indicate if the game is over
    private boolean win;               // Flag to indicate if the player has won

    /**
     * 
     * Starts a new game by initializing the terrain with the given size and mines.
     * 
     */
    public void startNewGame(int size, int mines) {
        terrain = new Terrain(size, mines);
        remainingToReveal = size * size - mines;
        gameOver = false;
        win = false;
    }

    public Terrain getTerrain() { return terrain; }
    public boolean isGameOver() { return gameOver; }
    public boolean isWin() { return win; }

    /**
     * 
     * Reveals the grid at (row, col). 
     * 
     */
    public void revealGrid(int row, int col) {
        try {
            Grid grid = terrain.getGrid()[row][col];

            // Ignore already revealed cells or if the game is over
            if (grid.isRevealed() || gameOver) return;

            // If the cell contains a mine, game ends
            if (grid.isMine()) {
                gameOver = true;
                return;
            }

            // Reveal empty cells recursively using flood fill
            floodReveal(grid);

            // If all safe cells are revealed, player wins
            if (remainingToReveal == 0) win = true;

        } catch (ArrayIndexOutOfBoundsException e) {
            // Inform user about valid row/column range
            System.out.printf("Invalid input! Rows: A-%c, Columns: 0-%d%n",
                    'A' + terrain.getSize() - 1, terrain.getSize() - 1);
        }
    }

    /**
     * Flood fill to reveal all connected cells with zero adjacent mines.
     * Uses a queue to avoid recursion and stack overflow.
     */
    private void floodReveal(Grid start) {
        Queue<Grid> queue = new ArrayDeque<>();

        // Start by revealing the initial cell
        markCell(start);
        queue.add(start);

        // Relative positions to check all 8 neighbors of a cell
        int[] mineRows = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] mineCols = {-1, 0, 1, -1, 1, -1, 0, 1};

        while (!queue.isEmpty()) {
            Grid current = queue.poll();

            // If current grid has adjacent mines, do not reveal its neighbors
            if (current.getAdjacentMines() != 0) continue;

            // Check all 8 neighbors
            for (int k = 0; k < 8; k++) {
                int newRow = current.getRow() + mineRows[k];
                int newCol = current.getCol() + mineCols[k];

                // Skip out-of-bounds positions
                if (!terrainBounds(newRow, newCol)) continue;

                Grid neighbor = terrain.getGrid()[newRow][newCol];

                // Skip already revealed grids and mines
                if (neighbor.isRevealed() || neighbor.isMine()) continue;

                // Reveal the neighbor
                markCell(neighbor);

                // If neighbor has no adjacent mines, add to queue to continue flood fill
                if (neighbor.getAdjacentMines() == 0) {
                    queue.add(neighbor);
                }
            }
        }
    }

    /**
     * Reveals a grid and decreases the remainingToReveal counter.
     */
    private void markCell(Grid grid) {
        if (!grid.isRevealed()) {
            grid.reveal();
            remainingToReveal--;
        }
    }

    /**
     * Checks whether the given row and column are within terrain bounds.
     */
    private boolean terrainBounds(int r, int c) {
        return r >= 0 && r < terrain.getSize() && c >= 0 && c < terrain.getSize();
    }
}
