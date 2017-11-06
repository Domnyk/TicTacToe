package models;

import java.util.Arrays;

public class Grid {
    private FieldState[][] grid;
    private int numOfFieldsTaken;

    public Grid() {
        grid = new FieldState[5][5];
        numOfFieldsTaken = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = FieldState.EMPTY;
            }
        }
    }

    public void printGrid() {
        System.out.println("--------");
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; j++) {
                System.out.printf("%s ", grid[i][j]);
            }
            System.out.print("\n");
        }
    }

    public int getNumOfFieldsTaken() {
        return numOfFieldsTaken;
    }

    public FieldState[][] getGrid() {
        return grid;
    }

    public void setGrid(FieldState[][] grid) {
        this.grid = grid;
    }

    public FieldState getFieldState(Coordinates coordinates) {
        int row = coordinates.getRow();
        int col = coordinates.getCol();

        return grid[row][col];
    }

    public FieldState getFieldState(int row, int col) {
        return grid[row][col];
    }

    public void setFieldState(Coordinates coordinates, FieldState newFieldState) {
        int row = coordinates.getRow();
        int col = coordinates.getCol();

        grid[row][col] = newFieldState;
        ++numOfFieldsTaken;
    }

    public void updateGridWithMark(Coordinates coordinates, FieldState newFieldState) {
        FieldState fs = getFieldState(coordinates);

        if (fs == FieldState.EMPTY) {
            setFieldState(coordinates, newFieldState);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grid grid = (Grid) o;

        return Arrays.deepEquals(getGrid(), grid.getGrid());
    }
}