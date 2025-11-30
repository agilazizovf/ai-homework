package homework2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Depth-limited search with heuristic evaluation and move ordering.
 */
public class DepthLimitedSearch {

    /**
     * Performs depth-limited minimax with alpha-beta pruning and heuristic evaluation.
     * 
     * @param board current board state
     * @param depth maximum search depth
     * @return best move (row, col) or null if terminal
     */
    public static int[] search(Board board, int depth) {
        if (GameRules.terminal(board)) {
            return null;
        }

        char currentPlayer = GameRules.player(board);
        List<int[]> actions = GameRules.actions(board);
        
        // Apply move ordering
        actions = orderMoves(board, actions);
        
        int[] bestMove = null;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        if (currentPlayer == 'X') {
            int bestValue = Integer.MIN_VALUE;
            for (int[] action : actions) {
                Board newBoard = GameRules.result(board, action);
                int value = minimaxValueAB(newBoard, depth - 1, alpha, beta, false);
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
                int value = minimaxValueAB(newBoard, depth - 1, alpha, beta, true);
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
     * Recursive minimax value computation with depth limit and alpha-beta pruning.
     */
    private static int minimaxValueAB(Board board, int depth, int alpha, int beta, boolean isMaximizing) {
        if (GameRules.terminal(board)) {
            Integer util = GameRules.utility(board);
            return util != null ? util * 1000 : 0;
        }

        if (depth == 0) {
            return HeuristicEvaluator.evaluate(board);
        }

        List<int[]> actions = GameRules.actions(board);
        actions = orderMoves(board, actions);
        
        if (isMaximizing) {
            int maxValue = Integer.MIN_VALUE;
            for (int[] action : actions) {
                Board newBoard = GameRules.result(board, action);
                int value = minimaxValueAB(newBoard, depth - 1, alpha, beta, false);
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
                int value = minimaxValueAB(newBoard, depth - 1, alpha, beta, true);
                minValue = Math.min(minValue, value);
                beta = Math.min(beta, minValue);
                if (beta <= alpha) {
                    break;
                }
            }
            return minValue;
        }
    }

    /**
     * Orders moves to improve alpha-beta pruning effectiveness.
     * Strategy: center first, then corners, then edges.
     * For tie-breaking, uses lexicographic order (row, col).
     */
    private static List<int[]> orderMoves(Board board, List<int[]> moves) {
        List<int[]> ordered = new ArrayList<>(moves);
        int m = board.getM();
        int center = m / 2;
        
        ordered.sort((a, b) -> {
            int priorityA = getMovePriority(a, m, center);
            int priorityB = getMovePriority(b, m, center);
            
            if (priorityA != priorityB) {
                return Integer.compare(priorityA, priorityB);
            }
            
            // Lexicographic tie-breaking
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });
        
        return ordered;
    }

    /**
     * Assigns priority to a move position.
     * Lower number = higher priority.
     * 0: center
     * 1: corners
     * 2: edges
     */
    private static int getMovePriority(int[] move, int m, int center) {
        int row = move[0];
        int col = move[1];
        
        boolean isCenter = (row == center || row == center - 1) && 
                          (col == center || col == center - 1);
        if (isCenter) {
            return 0;
        }
        
        boolean isCorner = (row == 0 || row == m - 1) && (col == 0 || col == m - 1);
        if (isCorner) {
            return 1;
        }
        
        return 2;
    }
}

