package models;

import org.junit.Test;

import java.util.Vector;

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

    @Test
    public void generateMovesTestWhenNoMoveAvailable() {
        ArtificialPlayer artificialPlayer = new ArtificialPlayer(Mark.X, 10);
        Board board = new Board();
        Vector<Board> expectedPossibleMoves = new Vector<>();

        /*
         * This isn't a legal grid setup. It is only for test purpose
         * Grid setup:
         *  X X X X X
         *  X X X X X
         *  X X X X X
         *  X X X X X
         *  X X X X X
         */
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board.setFieldState(new Coordinates(i, j), FieldState.X);
            }
        }

        Vector<Board> actualPossibleMoves = artificialPlayer.generateMoves(board);
        assertEquals(expectedPossibleMoves, actualPossibleMoves);
    }

    @Test
    public void generateMovesTestWhenMovesAvailable() {
        ArtificialPlayer artificialPlayer = new ArtificialPlayer(Mark.X, 10);
        Board board = new Board();
        Board possibleBoard1 = new Board();
        Board possibleBoard2 = new Board();
        Vector<Board> expectedPossibleMoves = new Vector<>();

        /*
         * Initial board setup
         * This isn't a legal grid setup. It is only for test purpose
         * Grid setup:
         *  X X X X X
         *  X X X X X
         *  X X X X X
         *  X X X X X
         *  X X X E E
         */
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                // Break so that last 2 fields are empty
                if (i == 4 && j == 3) {
                    break;
                }

                board.setFieldState(new Coordinates(i, j), FieldState.X);
            }
        }

        /*
         * Possible board number 1
         * This isn't a legal grid setup. It is only for test purpose
         * Grid setup:
         *  X X X X X
         *  X X X X X
         *  X X X X X
         *  X X X X X
         *  X X X X E
         */
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                // Break so that last field is empty
                if (i == 4 && j == 4) {
                    break;
                }

                possibleBoard1.setFieldState(new Coordinates(i, j), FieldState.X);
            }
        }

        /*
         * Possible board number 2
         * This isn't a legal grid setup. It is only for test purpose
         * Grid setup:
         *  X X X X X
         *  X X X X X
         *  X X X X X
         *  X X X X X
         *  X X X E X
         */
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                // Continue so that one before last field is empty
                if (i == 4 && j == 3) {
                    continue;
                }

                possibleBoard2.setFieldState(new Coordinates(i, j), FieldState.X);
            }
        }

        expectedPossibleMoves.add(possibleBoard1);
        expectedPossibleMoves.add(possibleBoard2);

        Vector<Board> actualPossibleMoves = artificialPlayer.generateMoves(board);
        assertEquals(expectedPossibleMoves, actualPossibleMoves);
    }
}
