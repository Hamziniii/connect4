import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;

public class CFourInfo implements Serializable {
    int turn = 0;
    int playerCount = 0;
    int currentPlayer = -1;
    ArrayList<Pair<Integer, Integer>> moveList = new ArrayList<>();
    Pair<Integer, Integer> lastMove;
    int[][] board = new int[][]{
            {-2, -2, -2, -2, -2, -2, -2},
            {-2, -2, -2, -2, -2, -2, -2},
            {-2, -2, -2, -2, -2, -2, -2},
            {-2, -2, -2, -2, -2, -2, -2},
            {-2, -2, -2, -2, -2, -2, -2},
            {-1, -1, -1, -1, -1, -1, -1},
    };
    int winner = -1;
    boolean playAgain = false;
    boolean gameEnded = false;
    boolean gameStarted = false;
}
