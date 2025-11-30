package homework2;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board state for generalized Tic-Tac-Toe.
 */
public class Board {
    private final char[][] grid;
    private final int m;
    private final int k;
    private int moveCount;

    /**
     * Creates a new board with specified dimensions and win condition.
     * 
     * @param m board size (m×m)
     * @param k win condition (k-in-a-row)
     */
    public Board(int m, int k) {
        this.m = m;
        this.k = k;
        this.grid = new char[m][m];
        this.moveCount = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    /**
     * Copy constructor for creating a new board from an existing one.
     */
    public Board(Board other) {
        this.m = other.m;
        this.k = other.k;
        this.moveCount = other.moveCount;
        this.grid = new char[m][m];
        for (int i = 0; i < m; i++) {
            System.arraycopy(other.grid[i], 0, this.grid[i], 0, m);
        }
    }

    /**
     * Gets the board size.
     */
    public int getM() {
        return m;
    }

    /**
     * Gets the win condition.
     */
    public int getK() {
        return k;
    }

    /**
     * Gets the number of moves made.
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * Gets the character at the specified position.
     */
    public char get(int row, int col) {
        return grid[row][col];
    }

    /**
     * Sets the character at the specified position.
     */
    public void set(int row, int col, char player) {
        if (grid[row][col] != ' ') {
            throw new IllegalArgumentException("Position already occupied");
        }
        grid[row][col] = player;
        moveCount++;
    }

    /**
     * Checks if a position is empty.
     */
    public boolean isEmpty(int row, int col) {
        return grid[row][col] == ' ';
    }

    /**
     * Checks if the board is full.
     */
    public boolean isFull() {
        return moveCount == m * m;
    }

    /**
     * Returns a string representation of the board.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            sb.append("|");
            for (int j = 0; j < m; j++) {
                sb.append(grid[i][j]).append("|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns a copy of the internal grid.
     */
    public char[][] getGrid() {
        char[][] copy = new char[m][m];
        for (int i = 0; i < m; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, m);
        }
        return copy;
    }
}

