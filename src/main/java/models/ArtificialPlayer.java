package models;

import helpers.ScoreHelper;
import java.util.Vector;

public class ArtificialPlayer extends Player implements ArtificialIntelligence {
    private int aiTreeDepth;
    private static final int NUM_OF_ROWS = 5;
    private static final int NUM_OF_COLS = 5;
    private static final int ALPHA_INIT_VALUE = -100;
    private static final int BETA_INIT_VALUE = 100;

    public ArtificialPlayer(Mark playersMark, int aiTreeDepth) {
        super(playersMark);
        this.aiTreeDepth = aiTreeDepth;
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public Vector<Board> generateMoves(Board board, Mark playersMark) {
        Vector<Board> possibleMoves = new Vector<>();

        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
                FieldState fieldState = board.getFieldState(i, j);
                if (fieldState != FieldState.EMPTY) {
                    continue;
                }

                Board possibleBoard = new Board(board);
                possibleBoard.setFieldState(new Coordinates(i, j), playersMark.toFieldState());
                possibleMoves.add(possibleBoard);
            }
        }

        return possibleMoves;
    }

    private boolean isTerminalBoardState(Board board) {
        GameState gameState = board.evaluateGameState(this);

        return (gameState == GameState.DRAW || gameState == GameState.O_WIN || gameState == GameState.X_WIN);
    }

    private Board alphaBeta(Board board, int aiTreeDepth, int alpha, int beta, boolean isMaximazingPlayer) {
        if (aiTreeDepth == 0 || isTerminalBoardState(board)) {
            return board;
        }

        Vector<Board> possibleMoves = generateMoves(board, isMaximazingPlayer ? Mark.X : Mark.O);
        int indexOfBestMove = 0;


        if (isMaximazingPlayer) {
            for (int i = 0; i < possibleMoves.size(); ++i) {
                Board newMove = alphaBeta(possibleMoves.elementAt(i), aiTreeDepth - 1, alpha, beta, false);
                int newMoveScore = ScoreHelper.calculateScore(newMove);

                if ( newMoveScore > alpha ) {
                    alpha = newMoveScore;
                    indexOfBestMove = i;
                }

                if (beta <= alpha) {
                    break;
                }
            }

            return possibleMoves.elementAt(indexOfBestMove);
        } else {
            for (int i = 0; i < possibleMoves.size(); ++i) {
                Board newMove = alphaBeta(possibleMoves.elementAt(i), aiTreeDepth - 1, alpha, beta, true);
                int newMoveScore = ScoreHelper.calculateScore(newMove);

                if ( newMoveScore < beta ) {
                    beta = newMoveScore;
                    indexOfBestMove = i;
                }

                if (beta <= alpha) {
                    break;
                }
            }
            return possibleMoves.elementAt(indexOfBestMove);
        }
    }

    @Override
    public Coordinates makeMove(Board board) {
        Board bestMove = alphaBeta(board, aiTreeDepth, ALPHA_INIT_VALUE, BETA_INIT_VALUE, getPlayersMark() == Mark.X);

        return bestMove.getLastMoveCoordinates();
    }
}
