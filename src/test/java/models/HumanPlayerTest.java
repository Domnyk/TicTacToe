package models;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HumanPlayerTest {
    @Test
    public void constructorTest() {
        Mark expectedHumanPlayerMark = Mark.X;
        HumanPlayer humanPlayer = new HumanPlayer(expectedHumanPlayerMark);

        Mark actualHumanPlayerMark = humanPlayer.getPlayersMark();

        assertEquals(expectedHumanPlayerMark, actualHumanPlayerMark);
    }

    @Test
    public void isHumanTest() {
        HumanPlayer humanPlayer = new HumanPlayer(Mark.X);

        assertEquals(true, humanPlayer.isHuman());
    }
}
