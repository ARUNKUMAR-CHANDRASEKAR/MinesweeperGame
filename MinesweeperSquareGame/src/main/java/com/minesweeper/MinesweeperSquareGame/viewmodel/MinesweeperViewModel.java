package com.minesweeper.MinesweeperSquareGame.viewmodel;

import java.util.*;
import java.util.stream.*;

import com.minesweeper.MinesweeperSquareGame.model.Grid;
import com.minesweeper.MinesweeperSquareGame.model.Terrain;

 

public class MinesweeperViewModel {
    private Terrain terrain;
    private int remainingToReveal;
    private boolean gameOver;
    private boolean win;

    public void startNewGame(int size, int mines) {
        terrain = new Terrain(size, mines);
        remainingToReveal = size * size - mines;
        gameOver = false;
        win = false;
    }

    public Terrain getBoard() { return terrain; }
    public boolean isGameOver() { return gameOver; }
    public boolean isWin() { return win; }

    public void revealCell(int row, int col) {
    	
        try {
            Grid grid = terrain.getGrid()[row][col];
            if (grid.isRevealed() || gameOver) return;

            if (grid.isMine()) {
                gameOver = true;
                return;
            }

            floodReveal(grid);

            if (remainingToReveal == 0) win = true;

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.printf("Invalid input! Rows: A-%c, Columns: 0-%d%n",
                    'A' + terrain.getSize() - 1, terrain.getSize() - 1);
        }
    }


    private void floodReveal(Grid start) {
        Queue<Grid> queue = new ArrayDeque<>();
        markCell(start);
        queue.add(start);

        int[] dr = {-1,-1,-1,0,0,1,1,1};
        int[] dc = {-1,0,1,-1,1,-1,0,1};

        while (!queue.isEmpty()) {
            Grid current = queue.poll();
            if (current.getAdjacentMines() != 0) continue;

            Arrays.stream(IntStream.range(0, 8).mapToObj(k -> new int[]{current.getRow() + dr[k], current.getCol() + dc[k]}).toArray(int[][]::new))
                .filter(pos -> inBounds(pos[0], pos[1]))
                .map(pos -> terrain.getGrid()[pos[0]][pos[1]])
                .filter(c -> !c.isRevealed() && !c.isMine())
                .forEach(c -> {
                    markCell(c);
                    if (c.getAdjacentMines() == 0) queue.add(c);
                });
        }
    }

    private void markCell(Grid grid) {
        if (!grid.isRevealed()) {
            grid.reveal();
            remainingToReveal--;
        }
    }

    private boolean inBounds(int r, int c) {
        return r >= 0 && r < terrain.getSize() && c >= 0 && c < terrain.getSize();
    }
}

