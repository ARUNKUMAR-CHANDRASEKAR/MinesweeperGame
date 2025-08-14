package com.task.minesweeperGame;


import org.junit.jupiter.api.Test;

import com.task.minesweeperGame.model.Terrain;

import static org.junit.jupiter.api.Assertions.*;

class TerrainTest {

    @Test
    void testBoardInitialization() {
        Terrain board = new Terrain(4, 2);
        assertEquals(7, board.getSize());
        assertNotNull(board.getGrid());
    }

    @Test
    void testMineCount() {
        int size = 4;
        int mineCount = 16;
        Terrain board = new Terrain(size, mineCount);

        long actualMines = java.util.Arrays.stream(board.getGrid())
                .flatMap(java.util.Arrays::stream)
                .count();

        assertEquals(mineCount, actualMines, "Number of mines placed should match requested");
    }
    
    @Test
    void testMineCount1() {
        int size = 4;
        int mineCount = 25;
        Terrain board = new Terrain(size, mineCount);

        long actualMines = java.util.Arrays.stream(board.getGrid())
                .flatMap(java.util.Arrays::stream)
                .count();

        assertEquals(mineCount, actualMines, "Number of mines placed should match requested");
    }

    @Test
    void testMineCount2() {
        int size = 4;
        int mineCount = 0;
        Terrain board = new Terrain(size, mineCount);

        long actualMines = java.util.Arrays.stream(board.getGrid())
                .flatMap(java.util.Arrays::stream)
                .count();

        assertEquals(mineCount, actualMines, "Number of mines placed should match requested");
    }

    @Test
    void testAdjacentMineNumbers() {
    	Terrain board = new Terrain(3, 1);
        boolean hasNonMineWithNumber = java.util.Arrays.stream(board.getGrid())
                .flatMap(java.util.Arrays::stream)
                .anyMatch(cell -> !cell.isMine() && cell.getAdjacentMines() >= 0);

        assertTrue(hasNonMineWithNumber, "Non-mine cells should have valid adjacent mine count");
    }
}

