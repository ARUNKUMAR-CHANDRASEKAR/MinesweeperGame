package com.example.sudoku.minesweeperapp.model;

public class Cell {
    private int value;
    private boolean prefilled;

    public Cell(int value, boolean prefilled) {
        this.value = value;
        this.prefilled = prefilled;
    }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }

    public boolean isPrefilled() { return prefilled; }
}