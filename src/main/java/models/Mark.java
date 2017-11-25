package models;

public enum Mark {
    X("X"),
    O("O");

    private String value;

    Mark(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public FieldState toFieldState () {
        if (this.value.equals("X")) {
            return FieldState.X;
        } else {
            return FieldState.O;
        }
    }
}
