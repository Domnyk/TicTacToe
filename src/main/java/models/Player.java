package models;

public abstract class Player {
    private Mark playersMark;

    public Player(Mark playersMark) {
        this.playersMark = playersMark;
    }
    public Mark getPlayersMark() {
        return playersMark;
    }

    public abstract boolean isHuman();
}