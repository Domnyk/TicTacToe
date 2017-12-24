package models;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BoardTest {
    private static final int NUM_OF_COLS = 5;
    private static final int NUM_OF_ROWS = 5;
    private static final int MIN_NUM_OF_FIELDS_REQ_TO_WIN = 3;

    @Test
    public void defaultConstructorTest() {
        Board board = new Board();

        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
                int rowNum = i;
                int colNum = j;
                FieldState actualFieldState = board.getFieldState(rowNum, colNum);

                assertEquals(FieldState.EMPTY, actualFieldState);
            }
        }
    }

    @Test
    public void copyConstructorTest() {
        Board board1 = new Board();
        int row1 = 0, col1 = 1, row2 = 4, col2 = 2;

        board1.setFieldState(new Coordinates(row1, col1), FieldState.X);
        board1.setFieldState(new Coordinates(row2, col2), FieldState.X);

        Board board2 = new Board(board1);

        assertEquals(board1, board2);
        assertEquals(board1.getFieldState(row1, col1), board2.getFieldState(row1, col1));
        assertEquals(board1.getFieldState(row2, col2), board2.getFieldState(row2, col2));
    }

    @Test
    public void getGridTest() {
        Board board = new Board();

        FieldState[][] grid1 = board.getGrid();
        FieldState[][] grid2 = board.getGrid();

        assertArrayEquals(grid1, grid2);
    }

    @Test
    public void getFieldStateTest() {
        Board board = new Board();
        int rowNum = 2;
        int colNum = 3;
        FieldState expectedFieldState = FieldState.EMPTY;

        FieldState actualFieldState1 = board.getFieldState(rowNum, colNum);
        FieldState actualFieldState2 = board.getFieldState(rowNum, colNum);

        assertEquals(expectedFieldState, actualFieldState1);
        assertEquals(expectedFieldState, actualFieldState2);
        assertEquals(actualFieldState1, actualFieldState2);
    }

    @Test
    public void setFieldStateTest() {
        Board board = new Board();
        int rowNum = 2;
        int colNum = 3;
        Coordinates coordinates = new Coordinates(rowNum, colNum);

        FieldState expectedFieldState1 = FieldState.X;
        board.setFieldState(coordinates, expectedFieldState1);
        assertEquals(expectedFieldState1, board.getFieldState(rowNum, colNum));

        FieldState expectedFieldState2 = FieldState.O;
        board.setFieldState(coordinates, expectedFieldState2);
        assertEquals(expectedFieldState2, board.getFieldState(rowNum, colNum));
    }

    @Test
    public void getNumOfFieldsTakenTest() {
        Board board = new Board();
        int rowNum = 2;
        int colNum = 3;
        int expectedNumOfFieldsTaken = 2;
        int actualNumOfFieldsTaken;
        Coordinates coordinates1 = new Coordinates(rowNum, colNum);
        Coordinates coordinates2 = new Coordinates(rowNum + 1, colNum + 1);

        board.setFieldState(coordinates1, FieldState.O);
        board.setFieldState(coordinates2, FieldState.X);
        actualNumOfFieldsTaken = board.getNumOfFieldsTaken();

        assertEquals(expectedNumOfFieldsTaken, actualNumOfFieldsTaken);
    }

    @Test
    public void evaluateGameStateTestWhenDraw() {
        Board board = new Board();
        Player currentPlayer = new HumanPlayer(Mark.X);
        GameState expectedGameState = GameState.DRAW;
        GameState actualGameState;

        /*
         * This isn't a legal grid setup. It is only for test purpose
         * Grid setup:
         *  X O X O X
         *  O X O X O
         *  O X O X O
         *  X O X O X
         *  X O X O X
         */
        // X part
        for (int i = 0; i < 5; i += 2) {
            board.setFieldState(new Coordinates(0, i), FieldState.X);
            board.setFieldState(new Coordinates(3, i), FieldState.X);
            board.setFieldState(new Coordinates(4, i), FieldState.X);
        }

        for (int i = 1; i < 5; i += 2) {
            board.setFieldState(new Coordinates(1, i), FieldState.X);
            board.setFieldState(new Coordinates(2, i), FieldState.X);
        }

        // O part
        for (int i = 1; i < 5; i += 2) {
            board.setFieldState(new Coordinates(0, i), FieldState.O);
            board.setFieldState(new Coordinates(3, i), FieldState.O);
            board.setFieldState(new Coordinates(4, i), FieldState.O);
        }
        for (int i = 0; i < 5; i += 2) {
            board.setFieldState(new Coordinates(1, i), FieldState.O);
            board.setFieldState(new Coordinates(2, i), FieldState.O);

        }

        actualGameState = board.evaluateGameState(currentPlayer);

        assertEquals(expectedGameState, actualGameState);
    }

    @Test
    public void evaluateGameStateTestWhenOWon() {
        Board board = new Board();
        Player currentPlayer = new HumanPlayer(Mark.O);
        GameState expectedGameState = GameState.O_WIN;
        GameState actualGameState;

        /*
         * This isn't a legal grid setup. It is only for test purpose
         * Grid setup:
         *  O E E E E
         *  E O E E E
         *  E E O E E
         *  E E E E E
         *  X X X X E
         */
        for (int i = 0; i < 4; ++i) {
            Coordinates coordinates = new Coordinates(4, i);
            board.setFieldState(coordinates, FieldState.X);
        }

        for (int i = 0; i < MIN_NUM_OF_FIELDS_REQ_TO_WIN; i++) {
            Coordinates coordinates = new Coordinates(i, i);
            board.setFieldState(coordinates, FieldState.O);
        }
        actualGameState = board.evaluateGameState(currentPlayer);
        assertEquals(expectedGameState, actualGameState);
    }

    @Test
    public void evaluateGameStateTestWhenXWonOnLongestDiagonal() {
        Board board = new Board();
        Player currentPlayer = new HumanPlayer(Mark.X);
        GameState expectedGameState = GameState.X_WIN;
        GameState actualGameState;

        /*
         * Grid setup:
         *  X E E E O
         *  E X E E O
         *  E E X E E
         *  E E E E E
         *  E E E E E
         */
        for (int i = 0; i < MIN_NUM_OF_FIELDS_REQ_TO_WIN; i++) {
            Coordinates coordinates = new Coordinates(i, i);
            board.setFieldState(coordinates, FieldState.X);
        }

        for (int i = 0; i < 2; ++i) {
            Coordinates coordinates = new Coordinates(i, 4);
            board.setFieldState(coordinates, FieldState.O);
        }
        actualGameState = board.evaluateGameState(currentPlayer);

        assertEquals(expectedGameState, actualGameState);
    }

    @Test
    public void evaluateGameStateTestWhenXWonOnDiagonal() {
        Board board = new Board();
        Player currentPlayer = new HumanPlayer(Mark.X);
        GameState expectedGameState = GameState.X_WIN;
        GameState actualGameState;

        /*
         * Grid setup:
         *  E E X E O
         *  E E E X O
         *  E E E E X
         *  E E E E E
         *  E E E E E
         */
        for (int i = 0; i < MIN_NUM_OF_FIELDS_REQ_TO_WIN; i++) {
            Coordinates coordinates = new Coordinates(i, i+2);
            board.setFieldState(coordinates, FieldState.X);
        }

        for (int i = 0; i < 2; ++i) {
            Coordinates coordinates = new Coordinates(i, 4);
            board.setFieldState(coordinates, FieldState.O);
        }
        actualGameState = board.evaluateGameState(currentPlayer);

        assertEquals(expectedGameState, actualGameState);
    }

    @Test
    public void evaluateGameStateTestWhenGameIsNotFinishedAndXShouldMove() {
        Board board = new Board();
        Player currentPlayer = new HumanPlayer(Mark.O);
        GameState expectedGameState = GameState.X_IS_MAKING_MOVE;
        GameState actualGameState;
        Coordinates lastMoveCoordinates = new Coordinates(0, 0);

        /*
         * This isn't a legal grid setup. It is only for test purpose
         * Grid setup:
         *  O E E E E
         *  E E E E E
         *  E E E E E
         *  E E E E E
         *  E E E E E
         */
        board.setFieldState(lastMoveCoordinates, FieldState.O);

        actualGameState = board.evaluateGameState(currentPlayer);

        assertEquals(expectedGameState, actualGameState);
    }

    @Test
    public void evaluateGameStateTestWhenGameIsNotFinishedAndOShouldMove() {
        Board board = new Board();
        Player currentPlayer = new HumanPlayer(Mark.X);
        GameState expectedGameState = GameState.O_IS_MAKING_MOVE;
        GameState actualGameState;
        Coordinates lastMoveCoordinates = new Coordinates(0, 0);

        /*
         * This isn't a legal grid setup. It is only for test purpose
         * Grid setup:
         *  X E E E E
         *  E E E E E
         *  E E E E E
         *  E E E E E
         *  E E E E E
         */
        board.setFieldState(lastMoveCoordinates, FieldState.X);

        actualGameState = board.evaluateGameState(currentPlayer);

        assertEquals(expectedGameState, actualGameState);
    }

    @Test
    public void evaluteGameStateWhenXHas3MarksInLineButNotWin() {
        Board board = new Board();
        Player currentPlayer = new HumanPlayer(Mark.X);
        GameState expectedGameState = GameState.O_IS_MAKING_MOVE;
        GameState actualGameState;

        /*
         * Grid setup:
         *  X E E E E
         *  E O E E E
         *  E E X E E
         *  E E E O E
         *  E E E E X
         */
        for (int i = 0; i < 5; i++) {
            Coordinates coordinates = new Coordinates(i, i);
            board.setFieldState(coordinates, FieldState.X);
        }


        for (int i = 1; i < 4; i += 2) {
            Coordinates coordinates = new Coordinates(i, i);
            board.setFieldState(coordinates, FieldState.O);
        }
        actualGameState = board.evaluateGameState(currentPlayer);

        assertEquals(expectedGameState, actualGameState);
    }
}
