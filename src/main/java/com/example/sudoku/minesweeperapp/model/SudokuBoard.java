package com.example.sudoku.minesweeperapp.model;

import java.util.Arrays;

public class SudokuBoard {

    private Cell[][] grid = new Cell[9][9];

    public SudokuBoard(int[][] initial) {
        for (int i = 0; i < 9; i++) {
            int row = i;
            grid[i] = Arrays.stream(initial[i])
                    .mapToObj(val -> new Cell(val, val != 0))
                    .toArray(Cell[]::new);
        }
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public Cell[][] getGrid() {
        return grid;
    }
}