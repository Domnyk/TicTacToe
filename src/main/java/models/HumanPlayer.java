package models;

public class HumanPlayer extends Player {
    public HumanPlayer(Mark playersMark) {
        super(playersMark);
    }

    @Override
    public boolean isHuman() {
        return true;
    }
}
