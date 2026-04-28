package com.arunkumar.sudoku.minesweeperapp;

import com.arunkumar.sudoku.minesweeperapp.controller.GameController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class MineSweeperAppApplication implements CommandLineRunner {

  @Autowired
  private GameController gameController;


    public static void main(String[] args) {
        SpringApplication.run(MineSweeperAppApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        gameController.startGame(scanner);
    }
}