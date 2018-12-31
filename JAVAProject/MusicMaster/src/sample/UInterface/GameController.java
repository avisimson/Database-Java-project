package sample.UInterface;
import DataBase.Search;
import Logic.GameLogic;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.Random;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.lang.Thread;



import static javafx.fxml.FXMLLoader.load;

public class GameController implements PropertyChangeListener{
    private GameLogic gameLogic;
    public static final int timeOfTurn = 15;
    Stage prevStage;

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label lifeLabel;
    @FXML
    private Label countDownLabel;
    @FXML
    private Button btnAnswer1;
    @FXML
    private Button btnAnswer2;
    @FXML
    private Button btnAnswer3;
    @FXML
    private Button btnAnswer4;
    @FXML
    private WebView youTubePlayer;

    /**
     * constructor
     */
    public GameController(){
        gameLogic = new GameLogic();
        gameLogic.addPropertyChangeListener(this);
    }

    public void startGame(){
        this.gameLogic.startGame();
    }


    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }

    @FXML
    /**
     * This function is activated when the
     * user has pressed the answer button
     */
    protected void answer(ActionEvent e){
        String id =((Button)e.getSource()).getId();
        System.out.println("press answer " + id);
        gameLogic.answer(id);
    }


    private void gameOver(){
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("GameOver.fxml"));
            AnchorPane root = myLoader.load();
            GameOverController gameOverController = myLoader.getController();
            gameOverController.setPrevStage(prevStage);
            Scene scene = new Scene(root,prevStage.getScene().getWidth(),prevStage.getScene().getHeight());
            scene.getStylesheets().add(getClass().getResource("GameOver.css").toExternalForm());
            prevStage.setScene(scene);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void initialize() {
        btnAnswer1.setOnAction(e->answer(e));
        btnAnswer2.setOnAction(e->answer(e));
        btnAnswer3.setOnAction(e->answer(e));
        btnAnswer4.setOnAction(e->answer(e));
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        String newValue = String.valueOf(e.getNewValue());

        if ("score".equals(propertyName)){
            scoreLabel.setText(String.valueOf("Score = " +  newValue));

        } else if ("life".equals(propertyName)){
            lifeLabel.setText(String.valueOf("life = " +  newValue));
            System.out.println("propertyChanged lifeLabel :");
        }
        else if("songId".equals(propertyName)){

            String songId = newValue;
            System.out.println("song id in game controoler: " +songId);
            System.out.println("in function songId: " + "http://www.youtube.com/watch/"+ songId + "?autoplay=1");
            if(songId != null) {
                youTubePlayer.getEngine().load(
                        "http://www.youtube.com/watch/"+ songId + "?autoplay=1"
                );
            } else {
                // search for different song
            }
        }
    }
}
