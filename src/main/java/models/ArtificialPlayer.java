package models;

import java.util.Random;

public class ArtificialPlayer extends Player implements ArtificialInteligence {
    private int aiTreeDepth;

    public ArtificialPlayer(Mark playersMark, int aiTreeDepth) {
        super(playersMark);

        this.aiTreeDepth = aiTreeDepth;
    }

    @Override
    public boolean isHuman() {
        return false;
    }


    @Override
    public Coordinates makeMove(Grid grid) {
        int row = 0;
        int col = 0;

        do {
            // System.out.println("Old values: " + row + " " + col);

            row = new Random().nextInt(5);
            col = new Random().nextInt(5);

        } while (grid.getFieldState(row, col) == FieldState.X || grid.getFieldState(row, col) == FieldState.O);

        // System.out.println("New Coordinates: ");
        // System.out.printf("%d %d\n", row, col);

        return new Coordinates(row, col);
    }
}
