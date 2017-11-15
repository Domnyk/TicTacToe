package models;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArtificialPlayerTest {
    @Test
    public void constructorTest() {
        Mark expectedPlayersMark = Mark.X;
        int treeDepth = 10;

        ArtificialPlayer artificialPlayer = new ArtificialPlayer(expectedPlayersMark, treeDepth);
        Mark actualPlayersMark = artificialPlayer.getPlayersMark();

        assertEquals(expectedPlayersMark, actualPlayersMark);
    }

    @Test
    public void isHumanTest() {
        ArtificialPlayer artificialPlayer = new ArtificialPlayer(Mark.X, 10);

        assertEquals(false, artificialPlayer.isHuman());
    }
}
