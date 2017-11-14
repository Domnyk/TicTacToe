package helpers;

import models.Board;
import models.FieldState;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GridHelper {
    private static final Logger logger = LogManager.getLogger("Application");

    public static void printGrid(Board board) {

        for(int i = 0; i < 5; ++i) {
            String str = "";
            for (int j = 0; j < 5; ++j) {
                FieldState fieldState = board.getFieldState(i, j);
                if (fieldState == FieldState.EMPTY) {
                    str += "E ";
                } else {
                    str += fieldState + " ";
                }
            }
            logger.info(str);
        }
    }
}
