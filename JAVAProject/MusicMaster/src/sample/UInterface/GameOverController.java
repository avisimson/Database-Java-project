package sample.UInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameOverController {

    @FXML
    private Button backToMenu;

    Stage prevStage;

    @FXML
    protected void goToMain() {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
            GridPane root = myLoader.load();
            MenuController menuController = myLoader.getController();
            menuController.setPrevStage(prevStage);
            Scene scene = new Scene(root,prevStage.getScene().getWidth(),prevStage.getScene().getHeight());
            scene.getStylesheets().add(getClass().getResource("menuApplication.css").toExternalForm());
            prevStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }
}
