package com.minesweeper.MinesweeperSquareGame.view;

import com.minesweeper.MinesweeperSquareGame.viewmodel.MinesweeperViewModel;

/*
 * MINESWEEPER GAME
 * 
 * WE USED MVVM ARCHIT HERE
 * 
 * FROM THE MAIN CLASS WE WILL CALL VIEW CLASS WILL MOVE ON LOGIC 
 * 
 * 
 * 
 */
public class Minesweeper {
    public static void main(String[] args) {
        MinesweeperViewModel vm = new MinesweeperViewModel();
        new ConsoleView(vm).start();
    }
}

