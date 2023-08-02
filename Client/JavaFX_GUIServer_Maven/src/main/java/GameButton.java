import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Pair;

public class GameButton extends Button {

    Pair<Integer, Integer> index;
    int player;
    int value;

    void update() {
        if(this.value != -1) {
            this.setDisable(true);
            this.setOpacity(.3);
        }
        if(this.value == -1) {
            this.setDisable(false);
            this.setOpacity(1);
        }
        if(this.value == -3) {
            this.setDisable(true);
            this.setOpacity(1);
            if(Client.c.get().winner == player)
                this.setStyle(String.format("-fx-background-color: %s;", ClientMenu.getColor(ClientMenu.color1)));
            else
                this.setStyle(String.format("-fx-background-color: %s;", ClientMenu.getColor(ClientMenu.color2)));
        }

//        System.out.println(String.format("v:%d p:%d ( %d %d )", value, player, index.getKey(), index.getValue()));

        if(this.value == player)
            this.setStyle(String.format("-fx-background-color: %s;", ClientMenu.getColor(ClientMenu.color1)));
        else if(this.value > -1)
            this.setStyle(String.format("-fx-background-color: %s;", ClientMenu.getColor(ClientMenu.color2)));

        this.setOnAction(e -> {
            CFourInfo c = Client.c.get();
            debugDisplayBoard(c.board);
            c.turn++;
            c.board[index.getKey()][index.getValue()] = c.currentPlayer;
            c.lastMove = index;
            c.moveList.add(0, index);
            c.board = noValidMoves(c);
            debugDisplayBoard(c.board);
            System.out.println(String.format("clicked: ( %d %d )", index.getKey(), index.getValue()));
            Client.setV(c);
        });
    }
    void setData(Pair<Integer, Integer> index, int player, int value) {
        this.index = index;
        this.player = player;
        this.value = value;

        this.setText(String.format("(%d, %d)", 5 - index.getKey(), index.getValue()));

        this.update();
    }

    void reset() {
        this.setDisable(true);
        this.setOpacity(.3);
        this.setStyle("");
    }

    static int[][] noValidMoves(CFourInfo c) {
        int[][] board = c.board;

        for(int j = 0; j < 7; j++)
            for(int i = 0; i < 6; i++)
                if(board[i][j] == -1)
                    board[i][j] = -2;

        return board;
    }

    static void debugDisplayBoard(int[][] board) {
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 7; j++)
                System.out.print(board[i][j] + " ");
            System.out.println("");
        }
        System.out.println("------");
    }
}
