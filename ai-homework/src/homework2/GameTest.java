package homework2;

import java.util.Arrays;

/**
 * Comprehensive tests for the Generalized Tic-Tac-Toe implementation.
 */
public class GameTest {
    
    public static void main(String[] args) {
        System.out.println("Running Game Tests...\n");
        
        int passed = 0;
        int failed = 0;
        
        // Test 1: Initial state
        if (testInitialState()) {
            System.out.println("✓ Test 1: Initial state");
            passed++;
        } else {
            System.out.println("✗ Test 1: Initial state");
            failed++;
        }
        
        // Test 2: Player alternation
        if (testPlayerAlternation()) {
            System.out.println("✓ Test 2: Player alternation");
            passed++;
        } else {
            System.out.println("✗ Test 2: Player alternation");
            failed++;
        }
        
        // Test 3: Actions
        if (testActions()) {
            System.out.println("✓ Test 3: Actions");
            passed++;
        } else {
            System.out.println("✗ Test 3: Actions");
            failed++;
        }
        
        // Test 4: Result
        if (testResult()) {
            System.out.println("✓ Test 4: Result");
            passed++;
        } else {
            System.out.println("✗ Test 4: Result");
            failed++;
        }
        
        // Test 5: Win detection (3x3)
        if (testWinDetection3x3()) {
            System.out.println("✓ Test 5: Win detection (3x3)");
            passed++;
        } else {
            System.out.println("✗ Test 5: Win detection (3x3)");
            failed++;
        }
        
        // Test 6: Terminal state
        if (testTerminal()) {
            System.out.println("✓ Test 6: Terminal state");
            passed++;
        } else {
            System.out.println("✗ Test 6: Terminal state");
            failed++;
        }
        
        // Test 7: Utility
        if (testUtility()) {
            System.out.println("✓ Test 7: Utility");
            passed++;
        } else {
            System.out.println("✗ Test 7: Utility");
            failed++;
        }
        
        // Test 8: Minimax optimality (3x3)
        if (testMinimaxOptimality()) {
            System.out.println("✓ Test 8: Minimax optimality (3x3)");
            passed++;
        } else {
            System.out.println("✗ Test 8: Minimax optimality (3x3)");
            failed++;
        }
        
        // Test 9: Alpha-Beta equivalence (3x3)
        if (testAlphaBetaEquivalence()) {
            System.out.println("✓ Test 9: Alpha-Beta equivalence (3x3)");
            passed++;
        } else {
            System.out.println("✗ Test 9: Alpha-Beta equivalence (3x3)");
            failed++;
        }
        
        // Test 10: Depth-limited search
        if (testDepthLimitedSearch()) {
            System.out.println("✓ Test 10: Depth-limited search");
            passed++;
        } else {
            System.out.println("✗ Test 10: Depth-limited search");
            failed++;
        }
        
        System.out.println("\nResults: " + passed + " passed, " + failed + " failed");
    }
    
    private static boolean testInitialState() {
        Board board = GameRules.initialState(3, 3);
        return board.getM() == 3 && board.getK() == 3 && board.getMoveCount() == 0;
    }
    
    private static boolean testPlayerAlternation() {
        Board board = GameRules.initialState(3, 3);
        char p1 = GameRules.player(board);
        board.set(0, 0, p1);
        char p2 = GameRules.player(board);
        return p1 == 'X' && p2 == 'O';
    }
    
    private static boolean testActions() {
        Board board = GameRules.initialState(3, 3);
        var actions = GameRules.actions(board);
        if (actions.size() != 9) return false;
        
        board.set(0, 0, 'X');
        actions = GameRules.actions(board);
        return actions.size() == 8;
    }
    
    private static boolean testResult() {
        Board board = GameRules.initialState(3, 3);
        Board newBoard = GameRules.result(board, new int[]{1, 1});
        return newBoard.get(1, 1) == 'X' && board.get(1, 1) == ' ';
    }
    
    private static boolean testWinDetection3x3() {
        Board board = GameRules.initialState(3, 3);
        // Horizontal win
        board.set(0, 0, 'X');
        board.set(0, 1, 'X');
        board.set(0, 2, 'X');
        if (GameRules.winner(board) != 'X') return false;
        
        board = GameRules.initialState(3, 3);
        // Vertical win
        board.set(0, 0, 'O');
        board.set(1, 0, 'O');
        board.set(2, 0, 'O');
        if (GameRules.winner(board) != 'O') return false;
        
        board = GameRules.initialState(3, 3);
        // Diagonal win
        board.set(0, 0, 'X');
        board.set(1, 1, 'X');
        board.set(2, 2, 'X');
        return GameRules.winner(board) == 'X';
    }
    
    private static boolean testTerminal() {
        Board board = GameRules.initialState(3, 3);
        if (GameRules.terminal(board)) return false;
        
        board.set(0, 0, 'X');
        board.set(0, 1, 'X');
        board.set(0, 2, 'X');
        return GameRules.terminal(board);
    }
    
    private static boolean testUtility() {
        Board board = GameRules.initialState(3, 3);
        board.set(0, 0, 'X');
        board.set(0, 1, 'X');
        board.set(0, 2, 'X');
        Integer util = GameRules.utility(board);
        return util != null && util == 1;
    }
    
    private static boolean testMinimaxOptimality() {
        Board board = GameRules.initialState(3, 3);
        int[] move = Minimax.minimax(board);
        if (move == null) return false;
        
        // Center move is optimal for first move in 3x3
        return move[0] == 1 && move[1] == 1;
    }
    
    private static boolean testAlphaBetaEquivalence() {
        Board board = GameRules.initialState(3, 3);
        int[] minimaxMove = Minimax.minimax(board);
        int[] abMove = AlphaBeta.minimaxAB(board);
        
        if (minimaxMove == null || abMove == null) return false;
        return Arrays.equals(minimaxMove, abMove);
    }
    
    private static boolean testDepthLimitedSearch() {
        Board board = GameRules.initialState(4, 3);
        int[] move = DepthLimitedSearch.search(board, 3);
        return move != null && move.length == 2;
    }
}

