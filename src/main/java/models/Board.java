package models;

import helpers.diagonalLinesHelper;

import java.util.Arrays;

public class Board {
    private FieldState[][] grid;
    private int numOfFieldsTaken;
    private Coordinates lastMoveCoordinates;
    private static final int NUM_OF_FIELDS_IN_LINE = 5;
    private static final int NUM_OF_ROWS = 5;
    private static final int NUM_OF_COLS = 5;
    private static final int NUM_OF_FIELDS = NUM_OF_ROWS * NUM_OF_COLS;
    private static final int MIN_NUM_OF_FIELDS_TAKEN_REQUIRE_TO_WIN = 5;

    public Board() {
        grid = new FieldState[NUM_OF_ROWS][NUM_OF_COLS];
        numOfFieldsTaken = 0;

        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
                grid[i][j] = FieldState.EMPTY;
            }
        }
    }

    public Board(Board board) {
        grid = new FieldState[NUM_OF_ROWS][NUM_OF_COLS];
        numOfFieldsTaken = board.getNumOfFieldsTaken();
        lastMoveCoordinates = board.getLastMoveCoordinates();

        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
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

    private boolean isWinOnLIne(FieldState currentPlayerFieldState, Coordinates beginCoordinates, Coordinates endCoordinates) {
        int rowChange = Integer.compare(endCoordinates.getRow(), beginCoordinates.getRow());
        int colChange = Integer.compare(endCoordinates.getCol(), beginCoordinates.getCol());
        int score = 0;

        for (int i = beginCoordinates.getRow(), j = beginCoordinates.getCol();
             i != (endCoordinates.getRow() + rowChange) || j != (endCoordinates.getCol() + colChange); i += rowChange, j += colChange) {

            if (getFieldState(i, j) == currentPlayerFieldState) {
                ++score;
            }

            if (score == 3) {
                return true;
            }

            if (getFieldState(i, j) != currentPlayerFieldState && score != 0) {
                score = 0;
            }

        }

        return false;
    }

    public GameState evaluateGameState(Player currentPlayer) {
        GameState continueGameState = currentPlayer.getPlayersMark() == Mark.O ? GameState.X_IS_MAKING_MOVE : GameState.O_IS_MAKING_MOVE;

        if (numOfFieldsTaken < MIN_NUM_OF_FIELDS_TAKEN_REQUIRE_TO_WIN) {
            return continueGameState;
        }

        FieldState currentPlayerFieldState = FieldState.valueOf(currentPlayer.getPlayersMark().toString());
        GameState winGameState = currentPlayer.getPlayersMark() == Mark.O ? GameState.O_WIN : GameState.X_WIN;
        int lastMoveRowLine = lastMoveCoordinates.getRow();
        int lastMoveColLine = lastMoveCoordinates.getCol();
        Coordinates beginCoordinates, endCoordinates;

        // Vertical check
        beginCoordinates = new Coordinates(0, lastMoveColLine);
        endCoordinates = new Coordinates(4, lastMoveColLine);
        if (isWinOnLIne(currentPlayerFieldState, beginCoordinates, endCoordinates) ) {
            return winGameState;
        }

        // Horizontal check
        beginCoordinates = new Coordinates(lastMoveRowLine, 0);
        endCoordinates = new Coordinates(lastMoveRowLine, 4);
        if (isWinOnLIne(currentPlayerFieldState, beginCoordinates, endCoordinates) ) {
            return winGameState;
        }


        // Diagonals check
        Coordinates[] winLinesCoordinates = diagonalLinesHelper.getDiagonalLinesCoordinates();
        for (int i = 0; i < 20; i += 2) {
            if (isWinOnLIne(currentPlayerFieldState, winLinesCoordinates[i], winLinesCoordinates[i+1])) {
                return winGameState;
            }
        }

        // At this point it can be either draw or game continues
        if (numOfFieldsTaken == NUM_OF_FIELDS) {
            return GameState.DRAW;
        }

        // At this point game continues
        return continueGameState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        return Arrays.deepEquals(getGrid(), board.getGrid());
    }
}