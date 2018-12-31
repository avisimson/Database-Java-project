package sample.UInterface;

import DataBase.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static javafx.fxml.FXMLLoader.load;

public class MenuController {

    Stage prevStage;

    @FXML
    private Button settingsButton;
    @FXML
    private Button startButton;
    @FXML
    private Button endButton;

    @FXML
    protected void openSettings() {

    }
    @FXML
    protected void startGame() {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Genre.fxml"));
            AnchorPane root = myLoader.load();
            GenreController genreController = myLoader.getController();
            genreController.setPrevStage(prevStage);
            Scene scene = new Scene(root,prevStage.getScene().getWidth(),prevStage.getScene().getHeight());
            scene.getStylesheets().add(getClass().getResource("genre.css").toExternalForm());
            prevStage.setScene(scene);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void closeButtonAction(){
        //get a handle of the stage
        Stage stage = (Stage) endButton.getScene().getWindow();
        //close the stage
        stage.close();
    }

    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }

}
