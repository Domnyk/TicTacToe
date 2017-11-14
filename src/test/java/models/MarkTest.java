package models;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MarkTest {
    @Test
    public void toStringTest() {
        Mark markO = Mark.O;
        Mark markX = Mark.X;
        String expectedMarkOString = "O";
        String expectedMarkXString = "X";

        String actualMarkOString = markO.toString();
        String actualMarkXString = markX.toString();

        assertEquals(expectedMarkOString, actualMarkOString);
        assertEquals(expectedMarkXString, actualMarkXString);
    }

    @Test
    public void toFieldStateTest() {
        Mark markO = Mark.O;
        Mark markX = Mark.X;
        FieldState expectedFieldStateForMarkO = FieldState.O;
        FieldState expectedFieldStateForMarkX = FieldState.X;

        FieldState actualFieldStateForMarkO = markO.toFieldState();
        FieldState actualFieldStateForMarkX = markX.toFieldState();

        assertEquals(expectedFieldStateForMarkO, actualFieldStateForMarkO);
        assertEquals(expectedFieldStateForMarkX, actualFieldStateForMarkX);
    }
}
