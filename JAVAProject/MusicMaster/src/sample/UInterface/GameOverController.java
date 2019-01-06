package sample.UInterface;

import Logic.HighScoreLogic;
import Logic.HighScores;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GameOverController implements Initializable {

    @FXML
    private Label score;
    private Stage prevStage;
    @FXML
    private Button backToMenu;
    private HighScoreLogic highScoreLogic = new HighScoreLogic();
    @FXML
    private TextField username;
    private HighScores newHighScore;


    @FXML
    protected void goToMain() {
        newHighScore.setUserName(username.getText());
        highScoreLogic.setHighScoreTable(newHighScore);
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
    public void setGameScore(int finalScore) {
        score.setText("Final Score: " + finalScore);
        newHighScore = new HighScores("",finalScore);
    }
    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //disable button until name entered.
        backToMenu.setDisable(true);

        username.setId("username");
        username.setPromptText("enter your name");
        username.setFocusTraversable(false);

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            backToMenu.setDisable(true);
            username.setId("username");
            username.setPromptText("enter your name");
            username.setFocusTraversable(false);

            if(!checkIfNull()){
                backToMenu.setDisable(false);
            }
        });
    }

    private boolean checkIfNull() {
        if(username.getText().isEmpty()){ return true;}
        return false;
    }


}
