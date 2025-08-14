package com.task.minesweeperGame;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.task.minesweeperGame.model.Grid;
import com.task.minesweeperGame.viewmodel.MinesweeperViewModel;

import static org.junit.jupiter.api.Assertions.*;

class MinesweeperViewModelTest {

    private MinesweeperViewModel vm;

    @BeforeEach
    void setUp() {
        vm = new MinesweeperViewModel();
    }

    @Test
    void testStartNewGameInitializesCorrectly() {
        vm.startNewGame(5, 3);
        assertNotNull(vm.getTerrain(), "Board should be initialized");
        assertEquals(5, vm.getTerrain().getSize(), "Board size should be correct");
        assertFalse(vm.isGameOver(), "Game should not be over at start");
        assertFalse(vm.isWin(), "Game should not be won at start");
    }

    @Test
    void testRevealCellReducesRemainingCount() {
        vm.startNewGame(4, 1);
        int before = countRevealedCells();
        vm.revealGrid(0, 0);
        int after = countRevealedCells();
        assertTrue(after > before, "Revealing should increase revealed cells count");
    }

    @Test
    void testRevealMineEndsGame() {
        vm.startNewGame(3, 9); // all cells are mines except one
        // Find a mine and reveal it
        Grid[][] grid = vm.getTerrain().getGrid();
        outer:
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid.length; c++) {
                if (grid[r][c].isMine()) {
                    vm.revealGrid(r, c);
                    break outer;
                }
            }
        }
        assertTrue(vm.isGameOver(), "Game should be over if a mine is revealed");
    }

    @Test
    void testWinConditionTriggers() {
        vm.startNewGame(2, 2); // Only 1 mine
        // Reveal all safe cells
        Grid[][] grid = vm.getTerrain().getGrid();
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid.length; c++) {
                if (!grid[r][c].isMine()) {
                    vm.revealGrid(r, c);
                }
            }
        }
        assertTrue(vm.isWin(), "Win should trigger after revealing all safe cells");
    }

    @Test
    void testRevealCellOutOfBoundsDoesNotCrash() {
        vm.startNewGame(3, 1);
        assertDoesNotThrow(() -> vm.revealGrid(99, 99));
        assertDoesNotThrow(() -> vm.revealGrid(-1, 0));
    }

    private int countRevealedCells() {
        return (int) java.util.Arrays.stream(vm.getTerrain().getGrid())
                .flatMap(java.util.Arrays::stream)
                .filter(Grid::isRevealed)
                .count();
    }
}

