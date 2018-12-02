package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Menu extends Application {

    @FXML
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            GridPane root = (GridPane) FXMLLoader.load(getClass().getResource("menu.fxml"));
            Scene scene = new Scene(root, 544, 366);
            scene.getStylesheets().add(getClass().getResource("menuApplication.css").toExternalForm());
            primaryStage.setTitle("Menu");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        launch(args);
    }
}

