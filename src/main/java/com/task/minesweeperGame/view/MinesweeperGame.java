package com.task.minesweeperGame.view;

import java.util.Scanner;

import com.task.minesweeperGame.viewmodel.MinesweeperViewModel;

/**
 * 
 * 
 *  - Entry point for the Minesweeper game application.
 *  - Creating a {@link MinesweeperViewModel} instance to manage game state and logic.
 *  - Creating a {@link ConsoleView} instance to handle user interaction via the console.
 *     
 *     
 */

public class MinesweeperGame {
	
    final static Scanner sc = new Scanner(System.in);

    /**
     * 
     * Main method - starts the Minesweeper application.
     *
     */
    public static void main(String[] args) {
        MinesweeperViewModel vm = new MinesweeperViewModel();
        new ConsoleView(vm).start();
    }
}
