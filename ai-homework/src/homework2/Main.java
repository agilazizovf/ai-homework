package homework2;

import java.util.Scanner;

/**
 * Main class for running the Generalized Tic-Tac-Toe game.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Generalized Tic-Tac-Toe (m×m, k-in-a-row)");
        System.out.print("Enter board size m (default 3): ");
        String mInput = scanner.nextLine().trim();
        int m = mInput.isEmpty() ? 3 : Integer.parseInt(mInput);
        
        System.out.print("Enter win condition k (default 3): ");
        String kInput = scanner.nextLine().trim();
        int k = kInput.isEmpty() ? 3 : Integer.parseInt(kInput);
        
        System.out.print("Choose algorithm (minimax/alphabeta/depthlimited, default alphabeta): ");
        String algoInput = scanner.nextLine().trim().toLowerCase();
        String algorithm = algoInput.isEmpty() ? "alphabeta" : algoInput;
        
        int depth = 4;
        if (algorithm.equals("depthlimited")) {
            System.out.print("Enter search depth (default 4): ");
            String depthInput = scanner.nextLine().trim();
            depth = depthInput.isEmpty() ? 4 : Integer.parseInt(depthInput);
        }
        
        System.out.print("Play as X or O? (default X): ");
        String playerInput = scanner.nextLine().trim().toUpperCase();
        char humanPlayer = playerInput.isEmpty() || playerInput.equals("X") ? 'X' : 'O';
        char aiPlayer = humanPlayer == 'X' ? 'O' : 'X';
        
        Board board = GameRules.initialState(m, k);
        boolean gameOver = false;
        
        while (!gameOver) {
            System.out.println("\n" + board);
            char currentPlayer = GameRules.player(board);
            
            if (currentPlayer == humanPlayer) {
                System.out.println("Your turn (" + humanPlayer + ")");
                System.out.print("Enter row and col (0-indexed, space-separated): ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                scanner.nextLine();
                
                if (row < 0 || row >= m || col < 0 || col >= m || !board.isEmpty(row, col)) {
                    System.out.println("Invalid move! Try again.");
                    continue;
                }
                
                board.set(row, col, humanPlayer);
            } else {
                System.out.println("AI's turn (" + aiPlayer + ")");
                int[] move = getAIMove(board, algorithm, depth);
                
                if (move == null) {
                    System.out.println("No moves available!");
                    break;
                }
                
                System.out.println("AI plays: (" + move[0] + ", " + move[1] + ")");
                board.set(move[0], move[1], aiPlayer);
            }
            
            if (GameRules.terminal(board)) {
                gameOver = true;
                System.out.println("\n" + board);
                Character winner = GameRules.winner(board);
                if (winner != null) {
                    System.out.println("Winner: " + winner);
                } else {
                    System.out.println("Draw!");
                }
            }
        }
        
        scanner.close();
    }
    
    private static int[] getAIMove(Board board, String algorithm, int depth) {
        switch (algorithm.toLowerCase()) {
            case "minimax":
                return Minimax.minimax(board);
            case "alphabeta":
                return AlphaBeta.minimaxAB(board);
            case "depthlimited":
                return DepthLimitedSearch.search(board, depth);
            default:
                return AlphaBeta.minimaxAB(board);
        }
    }
}
