package com.example.sudoku.minesweeperapp.service;

import com.example.sudoku.minesweeperapp.model.SudokuBoard;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.IntStream;

@Service
public class SudokuService {

    private final int[][] solution = {
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

    public SudokuBoard generatePuzzle() {
        int[][] puzzle = Arrays.stream(solution)
                .map(int[]::clone)
                .toArray(int[][]::new);

        Random rand = new Random();

        IntStream.range(0, 51).forEach(i -> {
            int r = rand.nextInt(9);
            int c = rand.nextInt(9);
            puzzle[r][c] = 0;
        });

        return new SudokuBoard(puzzle);
    }

    public boolean isValidMove(SudokuBoard board, int row, int col, int num) {

        // Row check
        boolean rowValid = IntStream.range(0, 9)
                .noneMatch(c -> board.getCell(row, c).getValue() == num);

        // Column check
        boolean colValid = IntStream.range(0, 9)
                .noneMatch(r -> board.getCell(r, col).getValue() == num);

        // Subgrid check
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;

        boolean boxValid = IntStream.range(startRow, startRow + 3)
                .boxed()
                .flatMap(r -> IntStream.range(startCol, startCol + 3)
                        .mapToObj(c -> board.getCell(r, c)))
                .noneMatch(cell -> cell.getValue() == num);

        return rowValid && colValid && boxValid;
    }

    public Optional<String> checkBoard(SudokuBoard board) {

        // Row check
        for (int i = 0; i < 9; i++) {
            Set<Integer> set = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                int val = board.getCell(i, j).getValue();
                if (val != 0 && !set.add(val)) {
                    return Optional.of("Number " + val + " already exists in Row " + (char)('A'+i));
                }
            }
        }

        // Column check
        for (int j = 0; j < 9; j++) {
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < 9; i++) {
                int val = board.getCell(i, j).getValue();
                if (val != 0 && !set.add(val)) {
                    return Optional.of("Number " + val + " already exists in Column " + (j+1));
                }
            }
        }

        // Box check
        for (int r = 0; r < 9; r += 3) {
            for (int c = 0; c < 9; c += 3) {
                Set<Integer> set = new HashSet<>();
                for (int i = r; i < r+3; i++) {
                    for (int j = c; j < c+3; j++) {
                        int val = board.getCell(i, j).getValue();
                        if (val != 0 && !set.add(val)) {
                            return Optional.of("Number " + val + " already exists in the same 3×3 subgrid.");
                        }
                    }
                }
            }
        }

        return Optional.empty();
    }

    public String getHint(SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getCell(i, j).getValue() == 0) {
                    return "Hint: Cell " + (char)('A'+i) + (j+1) +
                            " = " + solution[i][j];
                }
            }
        }
        return "No hints available.";
    }

    public boolean isComplete(SudokuBoard board) {
        return IntStream.range(0, 9)
                .allMatch(i -> IntStream.range(0, 9)
                        .allMatch(j ->
                                board.getCell(i,j).getValue() == solution[i][j]));
    }
}