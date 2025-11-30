package homework2;

/**
 * Heuristic evaluation function for depth-limited search.
 * Evaluates board positions without reaching terminal states.
 */
public class HeuristicEvaluator {

    /**
     * Evaluates a board position from X's perspective.
     * Positive values favor X, negative favor O.
     * 
     * @param board current board state
     * @return heuristic value
     */
    public static int evaluate(Board board) {
        if (GameRules.terminal(board)) {
            Integer util = GameRules.utility(board);
            return util != null ? util * 1000 : 0;
        }

        int m = board.getM();
        int k = board.getK();
        char[][] grid = board.getGrid();

        int score = 0;

        // Evaluate rows
        for (int i = 0; i < m; i++) {
            score += evaluateLine(grid, i, 0, 0, 1, m, k);
        }

        // Evaluate columns
        for (int j = 0; j < m; j++) {
            score += evaluateLine(grid, 0, j, 1, 0, m, k);
        }

        // Evaluate main diagonals
        for (int startRow = 0; startRow <= m - k; startRow++) {
            score += evaluateLine(grid, startRow, 0, 1, 1, m, k);
        }
        for (int startCol = 1; startCol <= m - k; startCol++) {
            score += evaluateLine(grid, 0, startCol, 1, 1, m, k);
        }

        // Evaluate anti-diagonals
        for (int startRow = 0; startRow <= m - k; startRow++) {
            score += evaluateLine(grid, startRow, m - 1, 1, -1, m, k);
        }
        for (int startCol = k - 1; startCol < m - 1; startCol++) {
            score += evaluateLine(grid, 0, startCol, 1, -1, m, k);
        }

        return score;
    }

    /**
     * Evaluates a line (row, column, or diagonal) for potential.
     * 
     * @param grid the board grid
     * @param startRow starting row
     * @param startCol starting column
     * @param deltaRow row direction
     * @param deltaCol column direction
     * @param m board size
     * @param k required consecutive count
     * @return heuristic value for this line
     */
    private static int evaluateLine(char[][] grid, int startRow, int startCol,
                                    int deltaRow, int deltaCol, int m, int k) {
        int xCount = 0;
        int oCount = 0;
        int emptyCount = 0;

        int row = startRow;
        int col = startCol;
        int length = 0;

        while (row >= 0 && row < m && col >= 0 && col < m && length < k) {
            if (grid[row][col] == 'X') {
                xCount++;
            } else if (grid[row][col] == 'O') {
                oCount++;
            } else {
                emptyCount++;
            }
            length++;
            row += deltaRow;
            col += deltaCol;
        }

        if (length < k) {
            return 0;
        }

        if (xCount > 0 && oCount > 0) {
            return 0;
        }

        if (xCount == 0 && oCount == 0) {
            return 0;
        }

        int score = 0;
        if (xCount > 0) {
            score = (int) Math.pow(10, xCount);
        } else {
            score = -(int) Math.pow(10, oCount);
        }

        return score;
    }
}

