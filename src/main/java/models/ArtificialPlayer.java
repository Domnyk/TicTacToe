package models;

import java.util.Map;
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
    public Coordinates makeMove(Board board) {
        int row = 0;
        int col = 0;

        do {

            row = new Random().nextInt(5);
            col = new Random().nextInt(5);

        } while (board.getFieldState(row, col) == FieldState.X || board.getFieldState(row, col) == FieldState.O);


        return new Coordinates(row, col);
    }
}
