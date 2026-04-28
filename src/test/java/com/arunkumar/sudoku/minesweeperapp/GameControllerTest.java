package com.arunkumar.sudoku.minesweeperapp;

import com.arunkumar.sudoku.minesweeperapp.controller.GameController;
import com.arunkumar.sudoku.minesweeperapp.model.SudokuBoard;
import com.arunkumar.sudoku.minesweeperapp.service.SudokuService;
import com.arunkumar.sudoku.minesweeperapp.view.ConsoleView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Scanner;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private SudokuService service;

    @Mock
    private ConsoleView view;

    @InjectMocks
    private GameController controller;

    private SudokuBoard board;

    @BeforeEach
    void setup() {
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
    void shouldAcceptValidMove() {
        String result = controller.handleMove(board, "A3 4");

        assertEquals("Move accepted.", result);
        assertEquals(4, board.getCell(0,2).getValue());
    }

    @Test
    void shouldRejectPrefilledCell() {
        String result = controller.handleMove(board, "A1 6");

        assertEquals("Invalid move. Pre-filled cell.", result);
    }

    @Test
    void shouldClearCell() {
        board.getCell(0,2).setValue(4);

        String result = controller.handleMove(board, "A3 clear");

        assertEquals("Cell cleared.", result);
        assertEquals(0, board.getCell(0,2).getValue());
    }

    @Test
    void shouldRejectInvalidNumber() {
        String result = controller.handleMove(board, "A3 15");

        assertEquals("Invalid number. Must be 1–9.", result);
    }

    @Test
    void shouldHandleInvalidFormat() {
        String result = controller.handleMove(board, "wrong input");

        assertEquals("Invalid input format.", result);
    }

    @Test
    void shouldStartGameAndExitOnQuit() {

        int[][] puzzle = new int[9][9];
        SudokuBoard board = new SudokuBoard(puzzle);

        when(service.generatePuzzle()).thenReturn(board);

        Scanner scanner = new Scanner("quit\n");

        controller.startGame(scanner);

        verify(view).printWelcome();
        verify(view,
                atLeastOnce()).printBoard(board);
        verify(service).generatePuzzle();
    }
}