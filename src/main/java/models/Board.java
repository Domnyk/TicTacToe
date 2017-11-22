package models;

import java.util.Arrays;

public class Board {
    private FieldState[][] grid;
    private int numOfFieldsTaken;
    private Coordinates lastMoveCoordinates;
    private static final int numOfFieldsInLine = 5;
    private static final int numOfRows = 5;
    private static final int numOfCols = 5;
    private static final int numOfFields = numOfRows * numOfCols;

    public Board() {
        grid = new FieldState[numOfRows][numOfCols];
        numOfFieldsTaken = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = FieldState.EMPTY;
            }
        }
    }

    public Board(Board board) {
        grid = new FieldState[numOfRows][numOfCols];
        numOfFieldsTaken = board.getNumOfFieldsTaken();
        lastMoveCoordinates = board.getLastMoveCoordinates();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = board.getFieldState(i, j);
            }
        }
    }

    public FieldState[][] getGrid() {
        return grid;
    }

    public int getNumOfFieldsTaken() {
        return numOfFieldsTaken;
    }

    public Coordinates getLastMoveCoordinates() {
        return lastMoveCoordinates;
    }

    public FieldState getFieldState(int row, int col) {
        return grid[row][col];
    }

    public void setFieldState(Coordinates coordinates, FieldState newFieldState) {
        int row = coordinates.getRow();
        int col = coordinates.getCol();

        lastMoveCoordinates = coordinates;
        grid[row][col] = newFieldState;
        ++numOfFieldsTaken;
    }

    public GameState evaluateGameState(Player currentPlayer) {
        GameState continueGameState = currentPlayer.getPlayersMark() == Mark.O ? GameState.X_IS_MAKING_MOVE : GameState.O_IS_MAKING_MOVE;

        // Minimal num of marks on board so that one player could win is 9
        if (numOfFieldsTaken < 9) {
            return continueGameState;
        }

        FieldState currentPlayerFieldState = FieldState.valueOf(currentPlayer.getPlayersMark().toString());
        GameState winGameState = currentPlayer.getPlayersMark() == Mark.O ? GameState.O_WIN : GameState.X_WIN;
        int lastMoveRowLine = lastMoveCoordinates.getRow();
        int lastMoveColLine = lastMoveCoordinates.getCol();
        int sumOfPlayerFields = 0;

        // Check vertical line
        for (int i = 0; i < 5; ++i) {
            if (this.getFieldState(i, lastMoveColLine) == currentPlayerFieldState) {
                ++sumOfPlayerFields;
            }
        }
        if (isWin(sumOfPlayerFields)) {
            return winGameState;
        }

        sumOfPlayerFields = 0;
        // Check horizontal line
        for (int i = 0; i < 5; ++i) {
            if (getFieldState(lastMoveRowLine, i) == currentPlayerFieldState) {
                ++sumOfPlayerFields;
            }
        }
        if (isWin(sumOfPlayerFields)) {
            return winGameState;
        }

        sumOfPlayerFields = 0;
        // Check 1st diagonal
        for(int i = 0; i < 5; ++i) {
            if (this.getFieldState(i, i) == currentPlayerFieldState) {
                ++sumOfPlayerFields;
            }
        }
        if (isWin(sumOfPlayerFields)) {
            return winGameState;
        }

        sumOfPlayerFields = 0;
        // Check 2nd diagonal
        for(int i = 0; i < 5; ++i) {
            if (this.getFieldState(4-i, i) == currentPlayerFieldState) {
                ++sumOfPlayerFields;
            }
        }
        if (isWin(sumOfPlayerFields)) {
            return winGameState;
        }

        // At this point it can be either draw or game continues
        if (numOfFieldsTaken == numOfFields) {
            return GameState.DRAW;
        }

        // At this point game continues
        return continueGameState;
    }

    private boolean isWin(int sumOfPlayerFields) {
        return sumOfPlayerFields == numOfFieldsInLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        return Arrays.deepEquals(getGrid(), board.getGrid());
    }
}