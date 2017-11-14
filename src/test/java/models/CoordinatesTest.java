package models;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CoordinatesTest {
    @Test
    public void constructorTest() {
        int expectedRowNum = 3;
        int expectedColNum = 4;

        Coordinates coordinates = new Coordinates(expectedRowNum, expectedColNum);
        int actualRowNum = coordinates.getRow();
        int actualColNum = coordinates.getCol();

        assertEquals(expectedRowNum, actualRowNum);
        assertEquals(expectedColNum, actualColNum);
    }

    @Test
    public void getRowTest() {
        int expectedRowNum = 3;

        Coordinates coordinates = new Coordinates(expectedRowNum, 4);
        int actualRowNum1 = coordinates.getRow();
        int actualRowNum2 = coordinates.getRow();

        assertEquals(expectedRowNum, actualRowNum1);
        assertEquals(expectedRowNum, actualRowNum2);
        assertEquals(actualRowNum1, actualRowNum2);
    }

    @Test
    public void getColTest() {
        int expectedColNum = 3;

        Coordinates coordinates = new Coordinates(4, expectedColNum);
        int actualColNum1 = coordinates.getCol();
        int actualColNum2 = coordinates.getCol();

        assertEquals(expectedColNum, actualColNum1);
        assertEquals(expectedColNum, actualColNum2);
        assertEquals(actualColNum1, actualColNum2);
    }
}
