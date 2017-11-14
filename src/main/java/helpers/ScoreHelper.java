package helpers;

import models.Coordinates;
import models.FieldState;
import models.Grid;
import models.Player;

public class ScoreHelper {
    public static int calculateScore(Grid grid, Player currentPlayer) {
        int score = 0;

        // Rows
        for (int i = 0; i < 5; ++i) {
            score += calculateRowScore(grid, i, currentPlayer);
        }

        // Cols
        for (int i = 0; i < 5; ++i) {
            score += calculateColScore(grid, i, currentPlayer);
        }

        // First diagonal
        score += calculateFirstDiagonalScore(grid, currentPlayer);

        // Second diagonal
        score += calculateSecondDiagonalScore(grid, currentPlayer);

        return score;
    }

    // Only for vertical or horizontal line
    private static int calculateLineScore(Grid grid, Coordinates beginCoordinates, Coordinates endCoordinates, Player currentPlayer) {
        int score = 0;
        int currentPlayerNumOfFields = 0;
        int oppositePlayerNumOfFields = 0;

        /*
            Integer.compare will return:
            -1 if endCoordinates.getRow() < beginCoordinates.getRow()
            0 if endCoordinates.getRow() == beginCoordinates.getRow()
            1 if endCoordinates.getRow() > beginCoordinates.getRow()
         */
        int rowChange = Integer.compare(endCoordinates.getRow(), beginCoordinates.getRow());
        int colChange = Integer.compare(endCoordinates.getCol(), beginCoordinates.getCol());
        FieldState currentPlayerFieldState = currentPlayer.getPlayersMark().toFieldState();

        for(int i = beginCoordinates.getRow(), j = beginCoordinates.getCol(); i < 5 && j < 5; i += rowChange, j += colChange) {
            FieldState fieldState = grid.getFieldState(i, j);
            if (fieldState == FieldState.EMPTY) {
                continue;
            }

            if (fieldState == currentPlayerFieldState) {
                ++currentPlayerNumOfFields;
            } else {
                ++oppositePlayerNumOfFields;
            }
        }

        // Return 0 if at least 1 our mark and 1 enemy's
        if (currentPlayerNumOfFields > 0 && oppositePlayerNumOfFields > 0) {
            return 0;
        }

        // Each our mark gives +1 point
        score += currentPlayerNumOfFields;

        // Each enemy mark gives -1 point
        score -= oppositePlayerNumOfFields;

        return score;
    }

    private static int calculateRowScore(Grid grid, int rowNum, Player currentPlayer) {
        return calculateLineScore(grid, new Coordinates(rowNum, 0), new Coordinates(rowNum, 4), currentPlayer);
    }

    private static int calculateColScore(Grid grid, int colNum, Player currentPlayer) {
        return calculateLineScore(grid, new Coordinates(0, colNum), new Coordinates(4, colNum), currentPlayer);
    }

    // First diagonal - top left to bottom right
    private static int calculateFirstDiagonalScore(Grid grid, Player currentPlayer) {
        int score = 0;
        int currentPlayerNumOfFields = 0;
        int oppositePlayerNumOfFields = 0;

        FieldState currentPlayerFieldState = currentPlayer.getPlayersMark().toFieldState();

        for(int i = 0; i < 5 ; i += 1) {
            FieldState fieldState = grid.getFieldState(i, i);
            if (fieldState == FieldState.EMPTY) {
                continue;
            }

            if (fieldState == currentPlayerFieldState) {
                ++currentPlayerNumOfFields;
            } else {
                ++oppositePlayerNumOfFields;
            }
        }

        // Return 0 if at least 1 our mark and 1 enemy's
        if (currentPlayerNumOfFields > 0 && oppositePlayerNumOfFields > 0) {
            return 0;
        }

        // Each our mark gives +1 point
        score += currentPlayerNumOfFields;

        // Each enemy mark gives -1 point
        score -= oppositePlayerNumOfFields;

        return score;
    }

    // First diagonal - top left to bottom right
    private static int calculateSecondDiagonalScore(Grid grid, Player currentPlayer) {
        int score = 0;
        int currentPlayerNumOfFields = 0;
        int oppositePlayerNumOfFields = 0;

        FieldState currentPlayerFieldState = currentPlayer.getPlayersMark().toFieldState();

        for(int i = 4; i >= 0 ; i -= 1) {
            FieldState fieldState = grid.getFieldState(4-i, i);
            if (fieldState == FieldState.EMPTY) {
                continue;
            }

            if (fieldState == currentPlayerFieldState) {
                ++currentPlayerNumOfFields;
            } else {
                ++oppositePlayerNumOfFields;
            }
        }

        // Return 0 if at least 1 our mark and 1 enemy's
        if (currentPlayerNumOfFields > 0 && oppositePlayerNumOfFields > 0) {
            return 0;
        }

        // Each our mark gives +1 point
        score += currentPlayerNumOfFields;

        // Each enemy mark gives -1 point
        score -= oppositePlayerNumOfFields;

        return score;
    }
}
