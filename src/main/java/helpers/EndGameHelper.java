package helpers;

import models.Coordinates;
import models.FieldState;
import models.Grid;
import models.Player;

public class EndGameHelper {
    public static boolean hasCurrentPlayerWon(Grid grid, Coordinates coordinates, Player currentPlayer) {
        FieldState currentPlayerFieldState = FieldState.valueOf(currentPlayer.getPlayersMark().toString());
        int sum = 0;
        int row = coordinates.getRow();
        int col = coordinates.getCol();
        // Check vertical line
        for (int i = 0; i < 5; ++i) {
            if (grid.getFieldState(i, col) == currentPlayerFieldState) {
                ++sum;
            }
        }
        if (sum == 5) {
            return true;
        }

        sum = 0;
        // Check horizontal line
        for (int i = 0; i < 5; ++i) {
            if (grid.getFieldState(row, i) == currentPlayerFieldState) {
                ++sum;
            }
        }

        if (sum == 5) {
            return true;
        }

        sum = 0;
        // Check 1st diagonal
        for(int i = 0; i < 5; ++i) {
            if (grid.getFieldState(i, i) == currentPlayerFieldState) {
                ++sum;
            }
        }

        if (sum == 5) {
            return true;
        }

        sum = 0;
        // Check 2nd diagonal
        for(int i = 0; i < 5; ++i) {
            if (grid.getFieldState(4-i, i) == currentPlayerFieldState) {
                System.out.println(4-i);

                ++sum;
            }
        }

        return sum == 5;
    };

    // This is run after hasCurrentPlayerWon - so if nobody won this tells if it is draw
    public static boolean isDraw(Grid grid) {
        return grid.getNumOfFieldsTaken() == 25;
    }
}
