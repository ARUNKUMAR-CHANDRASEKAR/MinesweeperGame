package com.minesweeper.MinesweeperSquareGame.model;

public class Grid {
    private final int row;
    private final int col;
    private boolean revealed;
    private boolean mine;
    private int adjacentMines;

    public Grid(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public boolean isRevealed() { return revealed; }
    public void reveal() { revealed = true; }
    public boolean isMine() { return mine; }
    public void setMine(boolean mine) { this.mine = mine; }
    public int getAdjacentMines() { return adjacentMines; }
    public void setAdjacentMines(int count) { adjacentMines = count; }
}

