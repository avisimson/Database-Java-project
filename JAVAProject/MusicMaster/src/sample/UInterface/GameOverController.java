package sample.UInterface;

import Logic.HighScoreLogic;
import Logic.HighScores;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * JavaFX FXML Controller of GameOver.fxml.
 */
public class GameOverController implements Initializable {
    //members
    @FXML
    private Label score;
    @FXML
    private Button backToMenu;
    @FXML
    private TextField username;

    private Stage prevStage;
    private HighScoreLogic highScoreLogic = new HighScoreLogic();
    private HighScores newHighScore;

    /**
     * This function returns to the main menu screen
     */
    @FXML
    protected void goToMain() {
        //update the name of the user that played
        newHighScore.setUserName(username.getText());
        //send the details about the user to the table in the DB of High Score.
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
     * This function updates the score of the player
     * @param finalScore is the final score of the player
     */
    public void setGameScore(int finalScore) {
        //show the score on the screen
        score.setText("Final Score: " + finalScore);
        //update the score of the user in the high score object
        newHighScore = new HighScores("",finalScore);
    }
    /**
     * This function sets the stage of the screen
     * @param stage is update stage
     */
    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }

    /**
     * This function initializes the screen of game over
     * @param location is parameter of this function
     * @param resources is parameter of this function
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //disable the button until a name is entered.
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
     * This function checks if a user name was entered
     * @return true if user name is null , else false.
     */
    private boolean checkIfNull() {
        if(username.getText().isEmpty()){ return true;}
        return false;
    }


}
