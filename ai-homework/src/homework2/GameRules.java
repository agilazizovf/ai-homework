package homework2;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the game rules for generalized Tic-Tac-Toe.
 */
public class GameRules {

    /**
     * Creates the initial board state.
     * 
     * @param m board size (default 3)
     * @param k win condition (default 3)
     * @return new empty board
     */
    public static Board initialState(int m, int k) {
        return new Board(m, k);
    }

    /**
     * Determines whose turn it is.
     * X moves first, then O alternates.
     * 
     * @param board current board state
     * @return 'X' or 'O'
     */
    public static char player(Board board) {
        return board.getMoveCount() % 2 == 0 ? 'X' : 'O';
    }

    /**
     * Returns all legal moves (empty positions) on the board.
     * 
     * @param board current board state
     * @return list of (row, col) pairs
     */
    public static List<int[]> actions(Board board) {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < board.getM(); i++) {
            for (int j = 0; j < board.getM(); j++) {
                if (board.isEmpty(i, j)) {
                    moves.add(new int[]{i, j});
                }
            }
        }
        return moves;
    }

    /**
     * Returns the new board state after applying an action.
     * 
     * @param board current board state
     * @param action (row, col) move to make
     * @return new board state
     */
    public static Board result(Board board, int[] action) {
        Board newBoard = new Board(board);
        char currentPlayer = player(board);
        newBoard.set(action[0], action[1], currentPlayer);
        return newBoard;
    }

    /**
     * Checks if there's a winner and returns the winning player.
     * 
     * @param board current board state
     * @return 'X', 'O', or null if no winner
     */
    public static Character winner(Board board) {
        int m = board.getM();
        int k = board.getK();
        char[][] grid = board.getGrid();

        // Check rows
        for (int i = 0; i < m; i++) {
            char winner = checkLine(grid, i, 0, 0, 1, m, k);
            if (winner != ' ') {
                return winner;
            }
        }

        // Check columns
        for (int j = 0; j < m; j++) {
            char winner = checkLine(grid, 0, j, 1, 0, m, k);
            if (winner != ' ') {
                return winner;
            }
        }

        // Check main diagonals (top-left to bottom-right)
        for (int startRow = 0; startRow <= m - k; startRow++) {
            char winner = checkLine(grid, startRow, 0, 1, 1, m, k);
            if (winner != ' ') {
                return winner;
            }
        }
        for (int startCol = 1; startCol <= m - k; startCol++) {
            char winner = checkLine(grid, 0, startCol, 1, 1, m, k);
            if (winner != ' ') {
                return winner;
            }
        }

        // Check anti-diagonals (top-right to bottom-left)
        for (int startRow = 0; startRow <= m - k; startRow++) {
            char winner = checkLine(grid, startRow, m - 1, 1, -1, m, k);
            if (winner != ' ') {
                return winner;
            }
        }
        for (int startCol = k - 1; startCol < m - 1; startCol++) {
            char winner = checkLine(grid, 0, startCol, 1, -1, m, k);
            if (winner != ' ') {
                return winner;
            }
        }

        return null;
    }

    /**
     * Helper method to check a line for k-in-a-row.
     * Uses a sliding window approach.
     * 
     * @param grid the board grid
     * @param startRow starting row
     * @param startCol starting column
     * @param deltaRow row direction (1, 0, or -1)
     * @param deltaCol column direction (1, 0, or -1)
     * @param m board size
     * @param k required consecutive count
     * @return winning player or ' ' if none
     */
    private static char checkLine(char[][] grid, int startRow, int startCol,
                                  int deltaRow, int deltaCol, int m, int k) {
        int row = startRow;
        int col = startCol;
        int length = 0;
        
        while (row >= 0 && row < m && col >= 0 && col < m) {
            length++;
            row += deltaRow;
            col += deltaCol;
        }
        
        if (length < k) {
            return ' ';
        }
        
        row = startRow;
        col = startCol;
        
        for (int i = 0; i <= length - k; i++) {
            char first = grid[row][col];
            if (first == ' ') {
                row += deltaRow;
                col += deltaCol;
                continue;
            }
            
            boolean allMatch = true;
            for (int j = 1; j < k; j++) {
                int checkRow = row + j * deltaRow;
                int checkCol = col + j * deltaCol;
                if (grid[checkRow][checkCol] != first) {
                    allMatch = false;
                    break;
                }
            }
            
            if (allMatch) {
                return first;
            }
            
            row += deltaRow;
            col += deltaCol;
        }
        
        return ' ';
    }

    /**
     * Checks if the game is in a terminal state (win, loss, or draw).
     * 
     * @param board current board state
     * @return true if game is over
     */
    public static boolean terminal(Board board) {
        return winner(board) != null || board.isFull();
    }

    /**
     * Returns the utility value for a terminal state.
     * +1 if X wins, -1 if O wins, 0 if draw.
     * 
     * @param board current board state
     * @return utility value or null if not terminal
     */
    public static Integer utility(Board board) {
        if (!terminal(board)) {
            return null;
        }
        
        Character winner = winner(board);
        if (winner == null) {
            return 0; // Draw
        }
        return winner == 'X' ? 1 : -1;
    }
}

