package sample.UInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class MenuController {

    private Stage prevStage;

    @FXML
    private Button settingsButton;
    @FXML
    private Button highScoreButton;
    @FXML
    private Button endButton;

    @FXML
    protected void highScoreTable() {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("HighScores.fxml"));
            AnchorPane root = myLoader.load();
            HighScoreController highScoreController = myLoader.getController();
            highScoreController.setPrevStage(prevStage);
            Scene scene = new Scene(root,prevStage.getScene().getWidth(),prevStage.getScene().getHeight());
            scene.getStylesheets().add(getClass().getResource("HighScore.css").toExternalForm());
            prevStage.setScene(scene);
        } catch(Exception e) {
            e.printStackTrace();
        }
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
