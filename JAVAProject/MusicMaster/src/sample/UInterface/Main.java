package sample.UInterface;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Main class.
 */
public class Main extends Application {

    @FXML
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
            AnchorPane root = myLoader.load();
            MenuController menuController = myLoader.getController();
            menuController.setPrevStage(primaryStage);
            Scene scene = new Scene(root,600,400);
            scene.getStylesheets().add(getClass().getResource("menu.css").toExternalForm());
            primaryStage.resizableProperty().setValue(Boolean.FALSE);
            primaryStage.setTitle("MusicMaster");
            primaryStage.setScene(scene);
            primaryStage.show();

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                    System.exit(0);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * main function
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

