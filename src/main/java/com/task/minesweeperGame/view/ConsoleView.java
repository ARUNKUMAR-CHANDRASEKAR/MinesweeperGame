package com.task.minesweeperGame.view;

import java.util.Scanner;
import java.util.stream.IntStream;

import com.task.minesweeperGame.model.Grid;
import com.task.minesweeperGame.model.Terrain;
import com.task.minesweeperGame.viewmodel.MinesweeperViewModel;

/**
 * 
 * 
 * Console-based user interface for the Minesweeper game.
 * <p>
 * This class handles all player interactions and display formatting
 * for the game in a text-based console environment.
 * 
 * Here We follows the MVVM pattern, where the {@link MinesweeperViewModel}
 * manages the game logic and state, while this view class is
 * responsible only for input/output presentation.
 * </p>
 *
 * 
 *
 * Dependencies:
 * <ul>
 *     <li>{@link MinesweeperViewModel} - game state and logic.</li>
 *     <li>{@link Terrain} - the current minefield layout.</li>
 *     <li>{@link Grid} - individual square data.</li>
 * </ul>
 */
public class ConsoleView {

    /** ViewModel instance to handle game logic. */
    private final MinesweeperViewModel vm;

    /** Scanner to capture player input from console. */
    private final Scanner sc = new Scanner(System.in);

    /**
     * Constructs a new ConsoleView for the given ViewModel.
     *
     * @param vm the MinesweeperViewModel to be used for game logic
     */
    public ConsoleView(MinesweeperViewModel vm) {
        this.vm = vm;
    }

    /**
     * 
     * Main game loop for the console version of Minesweeper.
     * 
     *     Board display after each move.
     *     Processing of user input.
     *     End game and replay prompts.
     *     
     */
    public void start() {while (true) {
        System.out.println("\nWelcome to Minesweeper!");

        int size = 0;
        int mines = 0;

        try {
            System.out.print("\nEnter the size of the grid (e.g. 4 for a 4x4 grid): ");
            size = sc.nextInt();

            System.out.print("\nEnter the number of mines to place on the grid (maximum is 35% of the total squares): ");
            mines = sc.nextInt();
            sc.nextLine(); 
        } catch (Exception e) {
            sc.nextLine(); 
            System.out.println("Invalid input! Please enter numerical values only.");
            
            System.out.print("Press any key to play again or 'q' to quit: ");
            String choice = sc.nextLine();
            if ("q".equalsIgnoreCase(choice)) break;
            else continue; // restart loop
        }

        vm.startNewGame(size, mines);

        while (!vm.isGameOver() && !vm.isWin()) {
            printTerrain();
            System.out.print("Select a square to reveal (e.g. A 1): ");
            String input = sc.nextLine().trim();
            if ("q".equalsIgnoreCase(input)) break;

            String[] parts = input.split("\\s+");
            if (parts.length != 2) {
                System.out.println("Invalid format! Please enter letter and number.");
                continue;
            }

            int row = parts[0].toUpperCase().charAt(0) - 'A';
            int col;
            try {
                col = Integer.parseInt(parts[1]);
            } catch (Exception e) {
                System.out.println("Invalid number! Please enter a valid column.");
                continue;
            }
            vm.revealGrid(row, col);
        }

        System.out.println(vm.isWin()
                ? "Congratulations, you have won the game!"
                : "Oh no, you detonated a mine! Game over.");

        System.out.print("Press any key to play again or 'q' to quit: ");
        String choice = sc.nextLine();
        if ("q".equalsIgnoreCase(choice)) break;
    }

    // If Pressed 'q' the below statement will be display
    System.out.println("Thanks for playing!");
}

    /**
     * Renders the minefield to the console without revealing hidden mines.
     */
    private void printTerrain() {
        printTerrain(false);
    }

    /**
     * Renders the minefield to the console.
     *
     * @param revealAll if true, all mines and numbers are shown (used at game end);
     *                  if false, only revealed Grids are shown.
     */
    private void printTerrain(boolean revealAll) {
        System.out.println("Here is your minefield:");
        Terrain terrain = vm.getTerrain();

        // Print column headers
        System.out.print("    ");
        IntStream.range(0, terrain.getSize()).forEach(c -> System.out.printf("%2d ", c));
        System.out.println("\n    " + "---".repeat(terrain.getSize()));

        // Print each row
        IntStream.range(0, terrain.getSize()).forEach(r -> {
            System.out.printf(" %c | ", (char) ('A' + r));
            IntStream.range(0, terrain.getSize()).forEach(c -> {
                Grid grid = terrain.getGrid()[r][c];
                char ch = revealAll || grid.isRevealed()
                        ? (grid.isMine() ? '*' :
                           (grid.getAdjacentMines() == 0 ? '■' :
                           (char) ('0' + grid.getAdjacentMines())))
                        : '■';
                System.out.printf("%2s ", ch);
            });
            System.out.println();
        });
        System.out.println();
    }
}
