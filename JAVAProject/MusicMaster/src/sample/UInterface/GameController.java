package sample.UInterface;
import DataBase.Search;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Timer;
import java.util.Random;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.lang.Thread;



import static javafx.fxml.FXMLLoader.load;

public class GameController {
    public static final int timeOfTurn = 15;
    int timeLeftForTurn = timeOfTurn;
    Timeline time = new Timeline();
    public static final int comboOfcorrectAnswersForExtraLife = 10;
    int countDown = 3;
    Timer timer = new Timer();
    int score = 0;
    int life = 3;
    int correctAnswer = 0;
    int combo = 0;
    Search search = new Search();

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

    }


    @FXML
    /**
     * This function is activated when the
     * user has pressed the answer button
     */
    protected void answer(ActionEvent e) {
        String id =((Button)e.getSource()).getId();
        System.out.println("press answer " + id);
        String correctId = "btnAnswer" + correctAnswer;

        if(correctId.equals(id)) {
            System.out.println("correct answer");
            combo++;
            if(combo == comboOfcorrectAnswersForExtraLife) {
                life++;
                lifeLabel.setText("Life = " + life);
                combo = 0;
            }
        } else {
            life--;
            lifeLabel.setText("Life = " + life);
        }
    }

    /**
     * start the game
     */
    public void startGame() {
        //COUNTDOWN BEFORE GAME

        //disable buttons.
        progressBar.setVisible(false);
        btnAnswer1.setDisable(true);
        btnAnswer2.setDisable(true);
        btnAnswer3.setDisable(true);
        btnAnswer4.setDisable(true);

        //show 3-2-1 timer.
        countDownLabel.setVisible(true);
        time.setCycleCount(Timeline.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                countDown--;
                countDownLabel.setText(String.valueOf(countDown));
                if(countDown == 0) { //hide countdown.
                    countDownLabel.setVisible(false);
                }
            }
        });

        //START GAME

        //enable buttons
        progressBar.setVisible(true);
        btnAnswer1.setDisable(false);
        btnAnswer2.setDisable(false);
        btnAnswer3.setDisable(false);
        btnAnswer4.setDisable(false);


        System.out.println("game over");

        playOneTurn();

        //System.out.println("game over");


        //game over
        /*GameOver gameOver= new GameOver();
        Stage stage = (Stage) answer1.getScene().getWindow();
        try {
            gameOver.start(stage);

        } catch (Exception e) {

        }*/

    }

    private void playOneTurn(){
        System.out.println("playOneTurn");

        //pick random correct answer.
        Random rand = new Random();
        correctAnswer = rand.nextInt(4)+1;
        System.out.println("correctAnswer is: " + correctAnswer);
        String songId;
        songId = search.searchSong("omer adam");
        if(songId != null) {
            youTubePlayer.getEngine().load(
                    "http://www.youtube.com/watch/"+ songId + "?autoplay=1"
            );
        } else {
            // search for different song
        }

    }


    @FXML
    public void initialize() {
        btnAnswer1.setOnAction(e->answer(e));
        btnAnswer2.setOnAction(e->answer(e));
        btnAnswer3.setOnAction(e->answer(e));
        btnAnswer4.setOnAction(e->answer(e));
        startGame();
    }
}
