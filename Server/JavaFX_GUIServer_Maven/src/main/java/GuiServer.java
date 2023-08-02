import java.util.HashMap;
import java.util.Objects;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiServer extends Application {
    Scene startScene;
    BorderPane startPane;
    Server serverConnection;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        primaryStage.setTitle("C4 Server");

//		this.serverChoice.setOnAction(e->{ primaryStage.setScene(sceneMap.get("server"));
//											primaryStage.setTitle("This is the Server");
//				serverConnection = new Server(data -> {
//					Platform.runLater(()->{
//						listItems.getItems().add(data.toString());
//					});
//
//				});
//
//		});


//		this.clientChoice.setOnAction(e-> {primaryStage.setScene(sceneMap.get("client"));
//											primaryStage.setTitle("This is a client");
//											clientConnection = new Client(data->{
//							Platform.runLater(()->{listItems2.getItems().add(data.toString());
//											});
//							});
//
//											clientConnection.start();
//		});


        startScene = new Scene(new VBox(), 600, 600);

//		b1.setOnAction(e->{clientConnection.send(c1.getText()); c1.clear();});

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        FXMLLoader loader1 = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/FXML/ServerMain.fxml")));
        Parent root = loader1.load();
        ServerMain s = loader1.getController();

        FXMLLoader loader2 = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/FXML/ServerStats.fxml")));
        Parent root2 = loader2.load();
        ServerStats s2 = loader2.getController();

        s.server_button.setOnAction(e -> {
            startScene.setRoot(root2);

            serverConnection = new Server(data -> {
                Platform.runLater(() -> {
                    s2.log.getItems().add(0, data);
                });
            }, l -> {
                Platform.runLater(() -> {
                    s2.players_connected.setText("players connected: " + serverConnection.clients.size());
                    s2.winner.setText("winner: " + (CFourLogic.c.winner == -1 ? "n/a" : CFourLogic.c.winner == 0 ? "Player 0" : "Player 1"));
                    s2.playing_again.setText("playing again: " + CFourLogic.c.playAgain);
//                    s2.start_game.setDisable(CFourLogic.c.playerCount > 1);
                });
            });

            serverConnection.port = Integer.parseInt(s.port_number.getText());
            serverConnection.server.start();
        });
//		root.getStylesheets().add("/styles/styles.css");
        startScene.setRoot(root);


        primaryStage.setScene(startScene);
        primaryStage.show();
        root.requestFocus();

    }
}
