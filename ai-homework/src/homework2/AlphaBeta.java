package homework2;

import java.util.List;

/**
 * Implements Minimax with Alpha-Beta pruning.
 */
public class AlphaBeta {

    /**
     * Minimax with Alpha-Beta pruning.
     * 
     * @param board current board state
     * @return best move (row, col) or null if terminal
     */
    public static int[] minimaxAB(Board board) {
        if (GameRules.terminal(board)) {
            return null;
        }

        char currentPlayer = GameRules.player(board);
        List<int[]> actions = GameRules.actions(board);
        
        int[] bestMove = null;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        if (currentPlayer == 'X') {
            int bestValue = Integer.MIN_VALUE;
            for (int[] action : actions) {
                Board newBoard = GameRules.result(board, action);
                int value = minimaxValueAB(newBoard, alpha, beta, false);
                if (value > bestValue) {
                    bestValue = value;
                    bestMove = action;
                }
                alpha = Math.max(alpha, bestValue);
                if (beta <= alpha) {
                    break;
                }
            }
        } else {
            int bestValue = Integer.MAX_VALUE;
            for (int[] action : actions) {
                Board newBoard = GameRules.result(board, action);
                int value = minimaxValueAB(newBoard, alpha, beta, true);
                if (value < bestValue) {
                    bestValue = value;
                    bestMove = action;
                }
                beta = Math.min(beta, bestValue);
                if (beta <= alpha) {
                    break;
                }
            }
        }

        return bestMove;
    }

    /**
     * Recursive minimax value computation with alpha-beta pruning.
     * 
     * @param board current board state
     * @param alpha best value for Max player
     * @param beta best value for Min player
     * @param isMaximizing true if current player is X (Max)
     * @return minimax value
     */
    private static int minimaxValueAB(Board board, int alpha, int beta, boolean isMaximizing) {
        if (GameRules.terminal(board)) {
            Integer util = GameRules.utility(board);
            return util != null ? util : 0;
        }

        List<int[]> actions = GameRules.actions(board);
        
        if (isMaximizing) {
            int maxValue = Integer.MIN_VALUE;
            for (int[] action : actions) {
                Board newBoard = GameRules.result(board, action);
                int value = minimaxValueAB(newBoard, alpha, beta, false);
                maxValue = Math.max(maxValue, value);
                alpha = Math.max(alpha, maxValue);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxValue;
        } else {
            int minValue = Integer.MAX_VALUE;
            for (int[] action : actions) {
                Board newBoard = GameRules.result(board, action);
                int value = minimaxValueAB(newBoard, alpha, beta, true);
                minValue = Math.min(minValue, value);
                beta = Math.min(beta, minValue);
                if (beta <= alpha) {
                    break;
                }
            }
            return minValue;
        }
    }
}

