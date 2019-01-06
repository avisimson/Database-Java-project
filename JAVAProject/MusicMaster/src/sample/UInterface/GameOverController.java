package sample.UInterface;

import Logic.HighScoreLogic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameOverController {

    @FXML
    private Label score;
    private Stage prevStage;
    private HighScoreLogic highScoreLogic = new HighScoreLogic();


    @FXML
    protected void goToMain() {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
            GridPane root = myLoader.load();
            MenuController menuController = myLoader.getController();
            menuController.setPrevStage(prevStage);
            Scene scene = new Scene(root,prevStage.getScene().getWidth(),prevStage.getScene().getHeight());
            scene.getStylesheets().add(getClass().getResource("menu.css").toExternalForm());
            prevStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void setGameScore(int finalScore) { score.setText("Final Score: " + finalScore);}
    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }
}
