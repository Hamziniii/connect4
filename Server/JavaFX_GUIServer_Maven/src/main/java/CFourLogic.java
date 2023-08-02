import javafx.util.Pair;

import java.util.ArrayList;

public class CFourLogic {
    static CFourInfo c = new CFourInfo();

    static int checkWin() {
        if(playerWin(c.turn % 2))
            return c.turn % 2;
        return -1;
    }

    static boolean playerWin(int player) {
        // horizontal check
        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 7 - 3; j++)
                if((c.board[i][j] == player) && (c.board[i][j + 1] == player) && (c.board[i][j + 2] == player) && (c.board[i][j + 3] == player)) {
                    c.board[i][j] = -3;
                    c.board[i][j + 1] = -3;
                    c.board[i][j + 2] = -3;
                    c.board[i][j + 3] = -3;

                    return true;
                }

        // vertical check
        for(int i = 0; i < 6 - 3; i++)
            for(int j = 0; j < 7; j++)
                if((c.board[i][j] == player) && (c.board[i + 1][j] == player) && (c.board[i + 2][j] == player) && (c.board[i + 3][j] == player)) {
                    c.board[i][j] = -3;
                    c.board[i + 1][j] = -3;
                    c.board[i + 2][j] = -3;
                    c.board[i + 3][j] = -3;

                    return true;
                }

        // diagonal going down
        for(int i = 3; i < 6; i++)
            for(int j = 0; j < 7 - 3; j++)
                if((c.board[i][j] == player) && (c.board[i - 1][j + 1] == player) && (c.board[i - 2][j + 2] == player) && (c.board[i - 3][j + 3] == player)) {
                    c.board[i][j] = -3;
                    c.board[i - 1][j + 1] = -3;
                    c.board[i - 2][j + 2] = -3;
                    c.board[i - 3][j + 3] = -3;

                    return true;
                }

        // diagonal going up
        for(int i = 3; i < 6; i++)
            for(int j = 3; j < 7; j++)
                if((c.board[i][j] == player) && (c.board[i - 1][j - 1] == player) && (c.board[i - 2][j - 2] == player) && (c.board[i - 3][j - 3] == player))  {
                    c.board[i][j] = -3;
                    c.board[i - 1][j - 1] = -3;
                    c.board[i - 2][j - 2] = -3;
                    c.board[i - 3][j - 3] = -3;

                    return true;
                }

        return false;
    }

    static int[][] validMoves() {
        int[][] board = c.board;

        for(int j = 0; j < 7; j++)
            if(board[5][j] == -2)
                board[5][j] = -1;

        for(int j = 0; j < 7; j++)
            for(int i = 0; i < 5; i++)
                if(board[i][j] == -1)
                    // we already have a valid move in this column, can only be 1 valid move per column
                    break;
                else if(board[i][j] == -2 && board[i + 1][j] > -1) {
                    board[i][j] = -1;
                    break;
                    // we found a valid position, so we can move to the next column
                }
        return board;
    }

    static int[][] noValidMoves() {
        int[][] board = c.board;

        for(int j = 0; j < 7; j++)
            for(int i = 0; i < 6; i++)
                if(board[i][j] == -1)
                    board[i][j] = -2;

        return board;
    }

    static int[][] currentBoard(int player) {
        if(c.turn % 2 == player && !c.gameEnded)
            return validMoves();
        else
            return noValidMoves();
    }

    static CFourInfo currentCFI(int player) {
        CFourInfo cfi = c;
        cfi.currentPlayer = player;
        cfi.board = currentBoard(player);
        System.out.println(cfi.currentPlayer);
        debugDisplayBoard(cfi.board);
        return cfi;
    }

    static void debugDisplayBoard(int[][] board) {
        if(board == null)
            board = c.board;

        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 7; j++)
                System.out.print(board[i][j] + " ");
            System.out.println("");
        }
        System.out.println("------");
    }

    static int[][] createBoard() {
        int[][] board = new int[][]{
                {-2, -2, -2, -2, -2, -2, -2},
                {-2, -2, -2, -2, -2, -2, -2},
                {-2, -2, -2, -2, -2, -2, -2},
                {-2, -2, -2, -2, -2, -2, -2},
                {-2, -2, -2, -2, -2, -2, -2},
                {-1, -1, -1, -1, -1, -1, -1},
        };
        return board;
    }

    static void resetBoard() {
        c = new CFourInfo();
    }

    static boolean allTied() {
        int[][] board = c.board;

        // If every piece is a player piece then it's a tie
        for(int j = 0; j < 7; j++)
            for(int i = 0; i < 6; i++)
                if(board[i][j] < 0)
                    return false;

        return true;
    }
}
