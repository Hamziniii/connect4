
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiServer extends Application{

	Scene startScene;
	Client clientConnection;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("C4 Client");

//		this.clientChoice.setOnAction(e-> {primaryStage.setScene(sceneMap.get("client"));
//											primaryStage.setTitle("This is a client");
//											clientConnection = new Client(data->{
//							Platform.runLater(()->{listItems2.getItems().add(data.toString());
//											});
//							});
//
//											clientConnection.start();
//		});
//

		startScene = new Scene(new VBox(), 600,600);

//		b1.setOnAction(e->{clientConnection.send(c1.getText()); c1.clear();});
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });


		FXMLLoader loader1 = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/FXML/ClientMenu.fxml")));
		Parent root = loader1.load();
		ClientMenu s = loader1.getController();

		FXMLLoader loader2 = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/FXML/ClientGame.fxml")));
		Parent root2 = loader2.load();
		ClientGame s2 = loader2.getController();

		FXMLLoader loader3 = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/FXML/ClientGameOver.fxml")));
		Parent root3 = loader3.load();
		ClientGameOver s3 = loader3.getController();

		s.joinBtn.setOnAction(a -> {
			clientConnection = new Client(data -> {
				System.out.println(data);
			}, data -> {
				CFourInfo c = Client.c.get();
				Platform.runLater(() -> {
					if(c.turn % 2 == c.currentPlayer)
						s2.gridTitle.setText("Your Turn");
					else
						s2.gridTitle.setText("Opponent Turn");

					AtomicInteger _i = new AtomicInteger();
					s2.moveHistory.getItems().setAll(c.moveList.stream().map(m -> String.format("client %d moved to (%d, %d)", _i.getAndIncrement() % 2, 5 - m.getKey(), m.getValue())).collect(Collectors.toList()));

					s2.setButtonGrid(c);
					clientConnection.send(c);
				});
			}, data -> {
				startScene.setRoot(root3);
				s3.quit.setOnAction(f -> {
					primaryStage.close();
					Platform.exit();
					System.exit(0);
				});

				s3.playAgain.setOnAction(f -> {
					clientConnection.playAgain();
					s3.playAgain.setDisable(true);
					s2.resetButtonGrid();
					s2.subtitle.setText("");
					System.out.println("RESETTING");
					s3.subtitle.setText("Waiting for players....");
				});
			});

			clientConnection.ip = s.ipAdd.getText();
			clientConnection.port = Integer.parseInt(s.portNum.getText());
			clientConnection.start();

			s.ipAdd.setDisable(true);
			s.portNum.setDisable(true);
			s.joinBtn.setDisable(true);
			s.subtitle.setText("waiting for players...");

			Client.c.addListener((o) -> {
				CFourInfo c = Client.c.get();

				if(c.gameStarted && !c.gameEnded)
					startScene.setRoot(root2);

				Platform.runLater(() -> {
					if(c.turn % 2 == c.currentPlayer)
						s2.gridTitle.setText("Your Turn");
					else
						s2.gridTitle.setText("Opponent Turn");

					AtomicInteger _i = new AtomicInteger(c.moveList.size() + c.currentPlayer + 1);
					s2.moveHistory.getItems().setAll(c.moveList.stream().map(m -> String.format("%s moved to (%d, %d)", _i.getAndDecrement() % 2 == 0 ? "You" : "Opponent", 5 - m.getKey(), m.getValue())).collect(Collectors.toList()));

					if(c.gameEnded) {
						s2.gridTitle.setText("GAME OVER");
						if(c.winner == -1)
							s2.subtitle.setText("TIED!!!");
						else
							if(c.winner == c.currentPlayer)
								s2.subtitle.setText("YOU WON!!!");
							else
								s2.subtitle.setText("YOU LOST!!!");
						s3.playAgain.setDisable(false);
						s3.subtitle.setText("");
					}

					s2.setButtonGrid(c);
				});
			});
		});
		 
		startScene.setRoot(root);
		primaryStage.setScene(startScene);
		primaryStage.show();
		
	}
}
