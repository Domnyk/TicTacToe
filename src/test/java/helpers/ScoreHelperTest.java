package helpers;

import models.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ScoreHelperTest {
    /*
     * Grid:
     * E X E E E
     * E E E E E
     * E E E E E
     * E E E E E
     * E E E E E
     */
    @Test
    public void calculateScoreTest_HorizontalAndVerticalLineOnly_OneMark() {
        Board board = new Board();
        Player currentPlayer = new HumanPlayer(Mark.X);
        int expectedScore = 2;
        int actualScore;

        board.setFieldState(new Coordinates(0, 1), currentPlayer.getPlayersMark().toFieldState());
        actualScore = ScoreHelper.calculateScore(board);

        assertEquals(expectedScore, actualScore);
    }

    /*
     * Grid:
     * E X E E E
     * E E O E E
     * E E E E E
     * E E E E E
     * E E E E E
     */
    @Test
    public void calculateScoreTest_HorizontalAndVerticalLineOnly_TwoOppositeMarks() {
        Board board = new Board();
        Player currentPlayer = new HumanPlayer(Mark.X);
        Player oppositePlayer = new HumanPlayer(Mark.O);
        int expectedScore = 0;
        int actualScore;

        board.setFieldState(new Coordinates(0, 1), currentPlayer.getPlayersMark().toFieldState());
        board.setFieldState(new Coordinates(1, 2), oppositePlayer.getPlayersMark().toFieldState());
        actualScore = ScoreHelper.calculateScore(board);

        assertEquals(expectedScore, actualScore);
    }

    /*
     * Grid:
     * E X E X E
     * E E E E E
     * E E E E E
     * E E E E E
     * E E E E E
     */
    @Test
    public void calculateScoreTest_HorizontalAndVerticalLineOnly_TwoOurMarksInLine() {
        Board board = new Board();
        Player currentPlayer = new HumanPlayer(Mark.O);
        int expectedScore = -4;
        int actualScore;

        board.setFieldState(new Coordinates(0, 1), currentPlayer.getPlayersMark().toFieldState());
        board.setFieldState(new Coordinates(0, 3), currentPlayer.getPlayersMark().toFieldState());
        actualScore = ScoreHelper.calculateScore(board);

        assertEquals(expectedScore, actualScore);
    }

    /*
     * Grid:
     * X E E E E
     * E E E E E
     * E E O E E
     * E O E E E
     * E E E E X
     */
    @Test
    public void calculateScoreTest_DiagonalLines() {
        Board board = new Board();
        Player currentPlayer = new HumanPlayer(Mark.X);
        Player oppositePlayer = new HumanPlayer(Mark.O);
        int expectedScore = -2;
        int actualScore;

        board.setFieldState(new Coordinates(0, 0), currentPlayer.getPlayersMark().toFieldState());
        board.setFieldState(new Coordinates(4, 4), currentPlayer.getPlayersMark().toFieldState());
        board.setFieldState(new Coordinates(2, 2), oppositePlayer.getPlayersMark().toFieldState());
        board.setFieldState(new Coordinates(3, 1), oppositePlayer.getPlayersMark().toFieldState());
        actualScore = ScoreHelper.calculateScore(board);

        assertEquals(expectedScore, actualScore);
    }

}
