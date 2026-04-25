# Sudoku CLI Game – Spring Boot (MVC + TDD)

A command-line based Sudoku game built using **Spring Boot**, following **MVC architecture**, and developed with **Test-Driven Development (TDD)** principles.

---

## Features

* Play Sudoku in the command line
* Auto-generated puzzle with pre-filled cells
* Enter values into cells (e.g., `A3 4`)
* Clear a cell (e.g., `C5 clear`)
* Get hints (reveals correct value)
* Validate current grid (row, column, subgrid rules)
* Prevent modification of pre-filled cells
* Detect game completion

---

## Architecture

This project follows the **MVC (Model-View-Controller)** pattern:

```
com.example.sudoku
│
├── controller       → Handles user input & game flow
├── service          → Business logic (validation, generation)
├── model            → Data structures (Board, Cell)
├── view             → Console output
└── MineSweeperAppApplication → Entry point
```

---

## Tech Stack

* Java 17+
* Spring Boot 3.x
* JUnit 5
* Mockito
* Maven / Gradle

---

## How to Run

### Using IntelliJ

1. Open the project
2. Run `MineSweeperAppApplication.java`

### Using Terminal

#### Maven:

```
mvn spring-boot:run
```

#### Gradle:

```
./gradlew bootRun
```

---

## Game Instructions

### Commands

| Command    | Description                       |
| ---------- | --------------------------------- |
| `A3 4`     | Place number 4 in row A, column 3 |
| `C5 clear` | Clear the cell                    |
| `hint`     | Reveal a correct value            |
| `check`    | Validate current board            |
| `quit`     | Exit the game                     |

---

### Example Gameplay

```
Welcome to Sudoku!

    1 2 3 4 5 6 7 8 9
  A 5 3 _ _ 7 _ _ _ _
  B 6 _ _ 1 9 5 _ _ _
  ...

Enter command (e.g., A3 4, C5 clear, hint, check):
A3 4

Move accepted.
```

---

## Testing (TDD Approach)

* Unit tests written using **JUnit 5**
* Mocking with **Mockito**
* Tested components:

    * SudokuService (core logic)
    * GameController (command handling)
    * Puzzle generation

### Run Tests

```
mvn test
```

or

```
./gradlew test
```

---

## Design Highlights

* Clean separation using MVC
* Stream API used for validation logic
* Testable design (Scanner injected)
* Command parsing and validation
* Modular and extensible

---

## Known Limitations

* Puzzle generation is random (not difficulty-based)
* CLI-based (no UI yet)
* Basic hint system (not intelligent solving)

---

## Future Enhancements

* Web UI (React + Spring Boot REST API)
* Sudoku solver (Backtracking / AI)
* Difficulty levels (Easy / Medium / Hard)
* Save/load game state
* Leaderboard

---

## Interview Talking Points

* Why MVC? → Separation of concerns
* Why Spring Boot? → DI, scalability, testability
* Why Streams? → Cleaner validation logic
* TDD approach → Writing testable and maintainable code

---

## Author

Arun Kumar

---
