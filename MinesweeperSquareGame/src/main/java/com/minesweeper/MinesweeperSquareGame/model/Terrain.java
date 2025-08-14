package com.minesweeper.MinesweeperSquareGame.model;

import java.util.*;
import java.util.stream.*;

public class Terrain {
    private final int size;
    private final int totalMines;
    private final Grid[][] grid;

    public Terrain(int size, int totalMines) {
        this.size = size;
        this.totalMines = totalMines;
        grid = IntStream.range(0, size)
                .mapToObj(r -> IntStream.range(0, size)
                        .mapToObj(c -> new Grid(r, c))
                        .toArray(Grid[]::new))
                .toArray(Grid[][]::new);

        placeMines();
        computeAdjacents();
    }

    private void placeMines() {
        Random rnd = new Random();
        IntStream.iterate(0, i -> i < totalMines, i -> i + 1).forEach(i -> {
            while (true) {
                int r = rnd.nextInt(size);
                int c = rnd.nextInt(size);
                Grid gridObj = grid[r][c];
                if (!gridObj.isMine()) {
                    gridObj.setMine(true);
                    break;
                }
            }
        });
    }

    private void computeAdjacents() {
        int[] dr = {-1,-1,-1,0,0,1,1,1};
        int[] dc = {-1,0,1,-1,1,-1,0,1};

        IntStream.range(0, size).forEach(r ->
            IntStream.range(0, size).forEach(c -> {
                Grid gridObj = grid[r][c];
                if (gridObj.isMine()) return;

                long count = IntStream.range(0, 8)
                        .mapToObj(k -> new int[]{r + dr[k], c + dc[k]})
                        .filter(pos -> inBounds(pos[0], pos[1]))
                        .map(pos -> grid[pos[0]][pos[1]])
                        .filter(Grid::isMine)
                        .count();
                gridObj.setAdjacentMines((int) count);
            })
        );
    }

    private boolean inBounds(int r, int c) { return r >= 0 && r < size && c >= 0 && c < size; }

    public Grid[][] getGrid() { return grid; }
    public int getSize() { return size; }
}
