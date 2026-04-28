package com.arunkumar.sudoku.minesweeperapp;

import com.arunkumar.sudoku.minesweeperapp.model.SudokuBoard;
import com.arunkumar.sudoku.minesweeperapp.service.SudokuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SudokuServiceTest {

    private SudokuService service;
    private SudokuBoard board;

    @BeforeEach
    void setup() {
        service = new SudokuService();

        int[][] puzzle = {
                {5,3,0,0,7,0,0,0,0},
                {6,0,0,1,9,5,0,0,0},
                {0,9,8,0,0,0,0,6,0},
                {8,0,0,0,6,0,0,0,3},
                {4,0,0,8,0,3,0,0,1},
                {7,0,0,0,2,0,0,0,6},
                {0,6,0,0,0,0,2,8,0},
                {0,0,0,4,1,9,0,0,5},
                {0,0,0,0,8,0,0,7,9}
        };

        board = new SudokuBoard(puzzle);
    }

    @Test
    void shouldGeneratePuzzleWithEmptyCells() {
        SudokuBoard board = service.generatePuzzle();

        assertNotNull(board);

        long emptyCells = countEmptyCells(board);

        // Expect at least some empty cells
        assertTrue(emptyCells > 0, "Puzzle should have empty cells");
    }

    @Test
    void shouldGenerate9x9Board() {
        SudokuBoard board = service.generatePuzzle();

        assertEquals(9, board.getGrid().length);
        assertEquals(9, board.getGrid()[0].length);
    }

    @Test
    void shouldNotBeFullyEmpty() {
        SudokuBoard board = service.generatePuzzle();

        long filledCells = countFilledCells(board);

        assertTrue(filledCells > 0, "Puzzle should not be completely empty");
    }



    // 🔧 Helper methods
    private long countEmptyCells(SudokuBoard board) {
        return java.util.Arrays.stream(board.getGrid())
                .flatMap(row -> java.util.Arrays.stream(row))
                .filter(cell -> cell.getValue() == 0)
                .count();
    }

    private long countFilledCells(SudokuBoard board) {
        return java.util.Arrays.stream(board.getGrid())
                .flatMap(row -> java.util.Arrays.stream(row))
                .filter(cell -> cell.getValue() != 0)
                .count();
    }

    @Test
    void shouldAllowValidMove() {
        boolean result = service.isValidMove(board, 0, 2, 4);
        assertTrue(result);
    }

    @Test
    void shouldRejectInvalidRowMove() {
        boolean result = service.isValidMove(board, 0, 2, 5);
        assertFalse(result);
    }

    @Test
    void shouldRejectInvalidColumnMove() {
        boolean result = service.isValidMove(board, 2, 0, 6);
        assertFalse(result);
    }

    @Test
    void shouldRejectInvalidSubgridMove() {
        boolean result = service.isValidMove(board, 1, 1, 9);
        assertFalse(result);
    }

    @Test
    void shouldDetectRowViolation() {
        board.getCell(0, 2).setValue(3);

        Optional<String> result = service.checkBoard(board);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains("Row A"));
    }

    @Test
    void shouldDetectColumnViolation() {
        board.getCell(2, 0).setValue(5);

        Optional<String> result = service.checkBoard(board);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains("Column 1"));
    }

    @Test
    void shouldReturnNoViolations() {
        Optional<String> result = service.checkBoard(board);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldProvideHint() {
        String hint = service.getHint(board);
        assertTrue(hint.contains("Hint:"));
    }

    @Test
    void shouldDetectCompleteBoard() {
        SudokuBoard solved = new SudokuBoard(serviceSolution());
        assertTrue(service.isComplete(solved));
    }

    private int[][] serviceSolution() {
        return new int[][]{
                {5,3,4,6,7,8,9,1,2},
                {6,7,2,1,9,5,3,4,8},
                {1,9,8,3,4,2,5,6,7},
                {8,5,9,7,6,1,4,2,3},
                {4,2,6,8,5,3,7,9,1},
                {7,1,3,9,2,4,8,5,6},
                {9,6,1,5,3,7,2,8,4},
                {2,8,7,4,1,9,6,3,5},
                {3,4,5,2,8,6,1,7,9}
        };
    }


}