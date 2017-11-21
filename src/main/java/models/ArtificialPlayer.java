package models;

import helpers.GridHelper;
import helpers.ScoreHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.Vector;

public class ArtificialPlayer extends Player implements ArtificialInteligence {
    private int aiTreeDepth;
    private static final int numOfRows = 5;
    private static final int numOfCols = 5;
    private static final Logger logger = LogManager.getLogger("Application");

    public ArtificialPlayer(Mark playersMark, int aiTreeDepth) {
        super(playersMark);

        this.aiTreeDepth = aiTreeDepth;
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public Vector<Board> generateMoves(Board board) {
        Vector<Board> possibleMoves = new Vector<>();

        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                FieldState fieldState = board.getFieldState(i, j);
                if (fieldState != FieldState.EMPTY) {
                    continue;
                }

                Board possibleBoard = new Board(board);
                possibleBoard.setFieldState(new Coordinates(i, j), this.getPlayersMark().toFieldState());
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
        // logger.info("alphaBeta is running. Tree depths is: " + aiTreeDepth);

        if (aiTreeDepth == 0 || isTerminalBoardState(board)) {
            return board;
        }

        Vector<Board> possibleBoards = generateMoves(board);
        int index = 0;

        // if maximizing player
        if (isMaximazingPlayer) {
            int bestScore = -100;
            for (int i = 0; i < possibleBoards.size(); ++i) {
                Board tmpBoard = alphaBeta(possibleBoards.elementAt(i), aiTreeDepth - 1, alpha, beta, false);

                bestScore = Math.max(bestScore, ScoreHelper.calculateScore(tmpBoard));
                index = (bestScore == ScoreHelper.calculateScore(tmpBoard)) ? i : index;

                alpha = Math.max(alpha, bestScore);
                if (beta <= alpha) {
                    break;
                }
            }

            logger.info("AlphaBeta is returning this grid: ");
            GridHelper.printGrid(possibleBoards.elementAt(index));
            return possibleBoards.elementAt(index);
        } else {
            int bestScore = 100;
            for (int i = 0; i < possibleBoards.size(); ++i) {
                Board tmpBoard = alphaBeta(possibleBoards.elementAt(i), aiTreeDepth - 1, alpha, beta, true);

                bestScore = Math.min(bestScore, ScoreHelper.calculateScore(tmpBoard));
                index = (bestScore == ScoreHelper.calculateScore(tmpBoard)) ? i : index;

                beta = Math.min(beta, bestScore);
                if (beta <= alpha) {
                    break;
                }
            }
            logger.info("AlphaBeta is returning this grid: ");
            GridHelper.printGrid(possibleBoards.elementAt(index));
            return possibleBoards.elementAt(index);
        }
    }

    @Override
    public Coordinates makeMove(Board board) {
        Board bestMove = alphaBeta(board, aiTreeDepth, -100, 100, getPlayersMark() == Mark.X);

        /* int row = 0;
        int col = 0;

        do {

            row = new Random().nextInt(5);
            col = new Random().nextInt(5);

        } while (board.getFieldState(row, col) == FieldState.X || board.getFieldState(row, col) == FieldState.O);
        */


        // GridHelper.printGrid(bestMove);

        return bestMove.getLastMoveCoordinates();
    }
}
