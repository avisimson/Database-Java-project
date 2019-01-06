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
/**
 * class that manage the game when he is finish
 */
public class GameOverController implements Initializable {
    //members
    @FXML
    private Label score;
    private Stage prevStage;
    @FXML
    private Button backToMenu;
    private HighScoreLogic highScoreLogic = new HighScoreLogic();
    @FXML
    private TextField username;
    private HighScores newHighScore;
    /**
     * function that return the game to open screen
     */
    @FXML
    protected void goToMain() {
        //update the name of user that played
        newHighScore.setUserName(username.getText());
        //send the details about the user to table in DB of High Score.
        highScoreLogic.setHighScoreTable(newHighScore);
        //load the main screen
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

    /**
     * function that update the score of user that played
     * @param finalScore is the score of player in this game
     */
    public void setGameScore(int finalScore) {
        //present the score in screen
        score.setText("Final Score: " + finalScore);
        //update the score of user in object high score
        newHighScore = new HighScores("",finalScore);
    }
    /**
     * function that update the stage that screen to user
     * @param stage is update stage
     */
    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }

    /**
     * function that initialize the screen of game over
     * @param location is parameter of this function
     * @param resources is parameter of this function
     */
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
    /**
     * function that check is user name contain name or not
     * @return true if user name is null , else false.
     */
    private boolean checkIfNull() {
        if(username.getText().isEmpty()){ return true;}
        return false;
    }


}
