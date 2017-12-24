package helpers;

import models.Coordinates;

public class diagonalLinesHelper {
    private static Coordinates[] diagonalLinesCoordinates = {
            new Coordinates(0, 0), new Coordinates(4, 4),
            new Coordinates(4, 0), new Coordinates(0, 4),
            new Coordinates(2, 0), new Coordinates(4, 2),
            new Coordinates(1, 0), new Coordinates(4, 3),
            new Coordinates(0, 1), new Coordinates(3, 4),
            new Coordinates(0, 2), new Coordinates(2, 4),
            new Coordinates(2, 0), new Coordinates(0, 2),
            new Coordinates(3, 0), new Coordinates(0, 3),
            new Coordinates(4, 1), new Coordinates(1, 4),
            new Coordinates(4, 2), new Coordinates(2, 4),
    };


    public static Coordinates[] getDiagonalLinesCoordinates() {
        return diagonalLinesCoordinates;
    }
}
