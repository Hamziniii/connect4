import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ClientMenu {
    public ComboBox yourColor;
    public TextField ipAdd;
    public TextField portNum;
    public ComboBox oppColor;
    public Label subtitle;
    public Button joinBtn;

    public static String color1 = getColor("red"), color2 = getColor("green");
    public Label yourColorLabel;
    public Label oppColorLabel;
    public HBox oppC;
    public HBox yourC;

    @FXML
    public void initialize() {
        color1 = yourColor.getValue().toString();
        color2 = oppColor.getValue().toString();

        yourC.setStyle(String.format("-fx-background-color: %s;", getColor(color1)));
        oppC.setStyle(String.format("-fx-background-color: %s;", getColor(color2)));

        yourColor.valueProperty().addListener((ob, oV, nV) -> {
            color1 = nV.toString();
            yourC.setStyle(String.format("-fx-background-color: %s;", getColor(color1)));
        });
        oppColor.valueProperty().addListener((ob, oV, nV) -> {
            color2 = nV.toString();
            oppC.setStyle(String.format("-fx-background-color: %s;", getColor(color2)));
        });
    }

    static String getColor(String color) {
        switch (color) {
            case "red":
                return "#F33053";
            case "orange":
                return "#F38E30";
            case "yellow":
                return "#F3E030";
            case "green":
                return "#86F330";
            case "blue":
                return "#30D0F3";
            case "purple":
                return "#C030F3";
            default:
                return "#555555";
        }
    }
}
