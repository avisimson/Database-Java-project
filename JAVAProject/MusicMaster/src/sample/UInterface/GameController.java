package sample.UInterface;
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
import javafx.stage.Stage;
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

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label lifeLabel;
    @FXML
    private Label countDownLabel;
    @FXML
    private Button answer1;
    @FXML
    private Button answer2;
    @FXML
    private Button answer3;
    @FXML
    private Button answer4;
    @FXML
    protected void answer1() {
        if(correctAnswer == 1) {
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
    @FXML
    private void answer2() {
        if(correctAnswer == 2) {
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
    @FXML
    protected void answer3() {
        if(correctAnswer == 3) {
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
    @FXML
    protected void answer4() {
        if(correctAnswer == 4) {
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

    public void startGame() {
        //COUNTDOWN BEFORE GAME



        //disable buttons.
        progressBar.setVisible(false);
        answer1.setDisable(true);
        answer2.setDisable(true);
        answer3.setDisable(true);
        answer4.setDisable(true);
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
        answer1.setDisable(false);
        answer2.setDisable(false);
        answer3.setDisable(false);
        answer4.setDisable(false);


        //pick random correct answer.
        Random rand = new Random();

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

    }

    public GameController(){
        System.out.println("in constructor");
    }

    @FXML
    public void initialize() {
        System.out.println("in initialize");
        startGame();
    }
}
