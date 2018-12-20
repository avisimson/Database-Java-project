package sample.UInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class ChooseGenre extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

        try {
            AnchorPane root = (AnchorPane) load(getClass().getResource("Genre.fxml"));
            Scene scene = new Scene(root,613,407);
            scene.getStylesheets().add(getClass().getResource("genre.css").toExternalForm());
            primaryStage.setTitle("Choose Genre");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
