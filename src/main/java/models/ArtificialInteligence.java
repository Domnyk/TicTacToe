package models;

import java.util.Vector;

public interface ArtificialInteligence {
    public Coordinates makeMove(Board board);

    public Vector<Board> generateMoves(Board board);
}
