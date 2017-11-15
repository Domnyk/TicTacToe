package models;

public class Coordinates  {
    private Integer row;
    private Integer col;

    public Coordinates(Integer row, Integer col) {
        this.row = row;
        this.col = col;
    }

    public Integer getRow() {
        return row;
    }
    public Integer getCol() {
        return col;
    }
}