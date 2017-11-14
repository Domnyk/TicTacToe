package models;

public class Coordinates  {
    private Integer row;
    private Integer col;

    public Coordinates(Integer row, Integer col) {
        this.row = row;
        this.col = col;
    }

    /* public Coordinates(Coordinates coordinates) {
        if (coordinates == null) return;

        this.row = coordinates.getRow();
        this.col = coordinates.getCol();
    } */

    public Integer getRow() {
        return row;
    }

    public Integer getCol() {
        return col;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public void setCol(Integer col) {
        this.col = col;
    }
}