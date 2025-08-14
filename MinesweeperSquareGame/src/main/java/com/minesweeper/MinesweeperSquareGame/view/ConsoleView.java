package com.minesweeper.MinesweeperSquareGame.view;


import java.util.Scanner;
import java.util.stream.IntStream;

import com.minesweeper.MinesweeperSquareGame.model.Grid;
import com.minesweeper.MinesweeperSquareGame.model.Terrain;

/*
 * ITS UI PORTION
 * 
 * IN THIS VIEW CLASS WE CAN PREPARE THE RESULT WITH UI
 */

 
import com.minesweeper.MinesweeperSquareGame.viewmodel.MinesweeperViewModel;
 

public class ConsoleView {
    private final MinesweeperViewModel vm;
    private final Scanner sc = new Scanner(System.in);

    public ConsoleView(MinesweeperViewModel vm) { this.vm = vm; }

    public void start() {
        while (true) {
            System.out.println("\nWelcome to Minesweeper!");
            System.out.print("\nEnter the size of the grid (e.g. 4 for a 4x4 grid): ");
            int size = sc.nextInt();
            System.out.print("\nEnter the number of mines to place on the grid (maximum is 35% of the total squares): ");
            int mines = sc.nextInt();
            sc.nextLine();

            vm.startNewGame(size, mines);

            while (!vm.isGameOver() && !vm.isWin()) {
                printBoard();
                System.out.print("Enter row letter and column number (e.g., C 3) or 'q' to quit: ");
                String input = sc.nextLine().trim();
                if ("q".equalsIgnoreCase(input)) break;

                String[] parts = input.split("\\s+");
                if (parts.length != 2) {
                    System.out.println("Invalid format! Please enter letter and number.");
                    continue;
                }
                int row = parts[0].toUpperCase().charAt(0) - 'A';
                int col;
                try { col = Integer.parseInt(parts[1]); } catch (Exception e) { continue; }
                vm.revealCell(row, col);
            }

            System.out.println(vm.isWin() ? "🎉 You cleared the minefield!" : "💥 BOOM! You hit a mine!");
            printBoard(true);

            System.out.print("Press ENTER to play again or 'q' to quit: ");
            String choice = sc.nextLine();
            if ("q".equalsIgnoreCase(choice)) break;
        }

        System.out.println("Thanks for playing!");
    }

    private void printBoard() { printBoard(false); }

    private void printBoard(boolean revealAll) {
    	System.out.println("Here is your minefield:'");
        Terrain terrain = vm.getBoard();
        System.out.print("    ");
        IntStream.range(0, terrain.getSize()).forEach(c -> System.out.printf("%2d ", c));
        System.out.println("\n    " + "---".repeat(terrain.getSize()));

        IntStream.range(0, terrain.getSize()).forEach(r -> {
            System.out.printf(" %c | ", (char)('A' + r));
            IntStream.range(0, terrain.getSize()).forEach(c -> {
                Grid grid = terrain.getGrid()[r][c];
                char ch = revealAll || grid.isRevealed()
                        ? (grid.isMine() ? '*' : (grid.getAdjacentMines() == 0 ? '■' : (char)('0' + grid.getAdjacentMines())))
                        : '■';
                System.out.printf("%2s ", ch);
            });
            System.out.println();
        });
        System.out.println();
    }
}

