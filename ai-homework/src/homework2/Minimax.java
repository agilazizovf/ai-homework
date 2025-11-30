package homework2;

import java.util.List;

/**
 * Implements the Minimax algorithm for adversarial search.
 */
public class Minimax {

    /**
     * Plain Minimax algorithm (used as oracle for 3×3).
     * 
     * @param board current board state
     * @return best move (row, col) or null if terminal
     */
    public static int[] minimax(Board board) {
        if (GameRules.terminal(board)) {
            return null;
        }

        char currentPlayer = GameRules.player(board);
        List<int[]> actions = GameRules.actions(board);
        
        int[] bestMove = null;
        int bestValue = currentPlayer == 'X' ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int[] action : actions) {
            Board newBoard = GameRules.result(board, action);
            int value = minimaxValue(newBoard);
            
            if (currentPlayer == 'X') {
                if (value > bestValue) {
                    bestValue = value;
                    bestMove = action;
                }
            } else {
                if (value < bestValue) {
                    bestValue = value;
                    bestMove = action;
                }
            }
        }

        return bestMove;
    }

    /**
     * Recursive minimax value computation.
     */
    private static int minimaxValue(Board board) {
        if (GameRules.terminal(board)) {
            Integer util = GameRules.utility(board);
            return util != null ? util : 0;
        }

        char currentPlayer = GameRules.player(board);
        List<int[]> actions = GameRules.actions(board);
        
        if (currentPlayer == 'X') {
            int maxValue = Integer.MIN_VALUE;
            for (int[] action : actions) {
                Board newBoard = GameRules.result(board, action);
                maxValue = Math.max(maxValue, minimaxValue(newBoard));
            }
            return maxValue;
        } else {
            int minValue = Integer.MAX_VALUE;
            for (int[] action : actions) {
                Board newBoard = GameRules.result(board, action);
                minValue = Math.min(minValue, minimaxValue(newBoard));
            }
            return minValue;
        }
    }
}

