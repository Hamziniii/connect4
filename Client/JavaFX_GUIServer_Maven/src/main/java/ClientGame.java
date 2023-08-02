import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Pair;

public class ClientGame {
    public GridPane buttonGrid;
    public Text gridTitle;
    public Text subtitle;
    public ListView moveHistory;

    @FXML
    public void initialize() {
        GameButton gb;

        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 7; j++) {
                gb = new GameButton();
                gb.setData(new Pair<>(i, j), -3, -2);
                GridPane.setFillHeight(gb, true);
                GridPane.setFillWidth(gb, true);
                gb.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                buttonGrid.add(gb, j, i);
            }
//        buttonGrid
    }

    @FXML
    public void setButtonGrid(CFourInfo cfi) {
        int player = cfi.currentPlayer;
        GameButton gb;

        for(Node b : buttonGrid.getChildren()) {
            gb = (GameButton) b;
            gb.player = player;
            gb.value = cfi.board[GridPane.getRowIndex(b)][GridPane.getColumnIndex(b)];
            gb.update();
        }
    }

    @FXML
    public void resetButtonGrid() {
        GameButton gb;

        for(Node b : buttonGrid.getChildren()) {
            gb = (GameButton) b;
            gb.reset();
        }
    }
}
