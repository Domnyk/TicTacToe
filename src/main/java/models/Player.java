package models;

public class Player {
    private Mark playersMark;

    public Mark getPlayersMark() {
        return playersMark;
    }

    public Player(Mark playersMark) {
        this.playersMark = playersMark;
    }
}