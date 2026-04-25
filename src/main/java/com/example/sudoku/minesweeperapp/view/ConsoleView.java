package com.example.sudoku.minesweeperapp.view;

import com.example.sudoku.minesweeperapp.model.SudokuBoard;
import org.springframework.stereotype.Component;

@Component
public class ConsoleView {

    public void printWelcome() {
        System.out.println("Welcome to Sudoku!");
    }

    public void printBoard(SudokuBoard board) {
        System.out.println("\n    1 2 3 4 5 6 7 8 9");

        for (int i = 0; i < 9; i++) {
            System.out.print("  " + (char)('A' + i) + " ");
            for (int j = 0; j < 9; j++) {
                int val = board.getCell(i, j).getValue();
                System.out.print((val == 0 ? "_" : val) + " ");
            }
            System.out.println();
        }
    }
}