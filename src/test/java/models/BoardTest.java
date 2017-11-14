package models;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BoardTest {
    private static final int numOfCols = 5;
    private static final int numOfRows = 5;

    @Test
    public void constructorTest() {
        Board board = new Board();

        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                int rowNum = i;
                int colNum = j;
                FieldState actualFieldState = board.getFieldState(rowNum, colNum);

                assertEquals(FieldState.EMPTY, actualFieldState);
            }
        }
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
        Coordinates coordinates1 = new Coordinates(rowNum, colNum);
        Coordinates coordinates2 = new Coordinates(rowNum + 1, colNum + 1);
        int expectedNumOfFieldsTaken = 2;

        board.setFieldState(coordinates1, FieldState.O);
        board.setFieldState(coordinates2, FieldState.X);
        int actualNumOfFieldsTaken = board.getNumOfFieldsTaken();

        assertEquals(expectedNumOfFieldsTaken, actualNumOfFieldsTaken);
    }

}
