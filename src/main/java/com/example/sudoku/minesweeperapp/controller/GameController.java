package com.example.sudoku.minesweeperapp.controller;

import com.example.sudoku.minesweeperapp.model.SudokuBoard;
import com.example.sudoku.minesweeperapp.service.SudokuService;
import com.example.sudoku.minesweeperapp.view.ConsoleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class GameController {

    @Autowired
    private SudokuService service;

    @Autowired
    private ConsoleView view;

    public void startGame(Scanner scanner) {

        SudokuBoard board = service.generatePuzzle();

        view.printWelcome();
        view.printBoard(board);

        while (true) {

            // ALWAYS print board first
            view.printBoard(board);

            // ALWAYS print prompt after board
            System.out.print("\nEnter command (e.g., A3 4, C5 clear, hint, check, quit): ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("quit")) {
                System.out.println("Exiting game...");
                break;
            }

            if (input.equalsIgnoreCase("hint")) {
                System.out.println(service.getHint(board));
                continue;
            }

            if (input.equalsIgnoreCase("check")) {
                var result = service.checkBoard(board);
                System.out.println(result.orElse("No rule violations detected."));
                continue;
            }

            handleMove(board, input);

            if (service.isComplete(board)) {
                view.printBoard(board);
                System.out.println("🎉 You have successfully completed the Sudoku puzzle!");
                break;
            }
        }
    }

    public String handleMove(SudokuBoard board, String input) {
        try {
            String[] parts = input.trim().split(" ");

            if (parts.length < 2) {
                return "Invalid input format.";
            }

            String pos = parts[0].toUpperCase();

            int row = pos.charAt(0) - 'A';
            int col = Integer.parseInt(pos.substring(1)) - 1;

            // ✅ Check bounds
            if (row < 0 || row > 8 || col < 0 || col > 8) {
                return "Invalid position.";
            }

            // ✅ Check prefilled FIRST
            if (board.getCell(row, col).isPrefilled()) {
                return "Invalid move. Pre-filled cell.";
            }

            // ✅ CLEAR
            if (parts[1].equalsIgnoreCase("clear")) {
                board.getCell(row, col).setValue(0);
                return "Cell cleared.";
            }

            // ✅ Parse number
            int num = Integer.parseInt(parts[1]);

            if (num < 1 || num > 9) {
                return "Invalid number. Must be 1–9.";
            }

            board.getCell(row, col).setValue(num);

            return "Move accepted.";

        } catch (Exception e) {
            return "Invalid input format.";
        }
    }
}