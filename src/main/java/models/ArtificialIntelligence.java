package models;

import java.util.Vector;

public interface ArtificialIntelligence {
    Coordinates makeMove(Board board);

     Vector<Board> generateMoves(Board board, Mark playersMark);
}
