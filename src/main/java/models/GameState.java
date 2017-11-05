package models;

public enum GameState {
    WAITING_FOR_GAME_START("Waiting for game to start"),
    X_IS_MAKING_MOVE("X is making a move"),
    O_IS_MAKING_MOVE("O is making a move"),
    X_WIN("X has won"),
    O_WIN("O has won"),
    DRAW("This is a draw");

    private String value;

    GameState(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
