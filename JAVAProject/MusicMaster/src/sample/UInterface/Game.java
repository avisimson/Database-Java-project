package sample.UInterface;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Game extends Application {

    @FXML
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            GridPane root = (GridPane) FXMLLoader.load(getClass().getResource("Game.fxml"));
            Scene scene = new Scene(root,613,407);
            scene.getStylesheets().add(getClass().getResource("Game.css").toExternalForm());
            primaryStage.setTitle("Game");
            primaryStage.setScene(scene);
            Thread gameThread = new GameThread();
            gameThread.start();
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}