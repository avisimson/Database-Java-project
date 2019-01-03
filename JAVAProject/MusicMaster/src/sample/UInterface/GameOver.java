package sample.UInterface;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.application.Application;


import static javafx.fxml.FXMLLoader.load;

public class GameOver extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{

        try {
            AnchorPane root = (AnchorPane) load(getClass().getResource("GameOver.fxml"));
            Scene scene = new Scene(root,613,407);
            scene.getStylesheets().add(getClass().getResource("GameOver.css").toExternalForm());
            primaryStage.setTitle("Game Over");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
