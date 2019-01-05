package sample.UInterface;
import Logic.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;


import static javafx.fxml.FXMLLoader.load;

public class GameController implements PropertyChangeListener{
    private GameLogic gameLogic;
    private Stage prevStage;

    @FXML
    private Label timeLeft;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label lifeLabel;
    @FXML
    private Label answerResult;
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
    public GameController( List<Artist> artistList, List<Genre> genres){
        gameLogic = new GameLogic(genres);
        gameLogic.Create20Questions(artistList);
        gameLogic.addPropertyChangeListener(this);
        progressBar = new ProgressBar(0);
    }

    /**
     * starts the game from game logic.
     */
    public void startGame(){

        this.gameLogic.startGame();
    }


    /**
     * set stage of game screen on previous stage.
     * @param stage
     */
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


    /**
     when life is gone- go to game over screen.
     */
    private void gameOver() {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("GameOver.fxml"));
            AnchorPane root = myLoader.load();
            GameOverController gameOverController = myLoader.getController();
            gameOverController.setPrevStage(prevStage);
            gameOverController.setGameScore(gameLogic.getScore());
            Scene scene = new Scene(root,prevStage.getScene().getWidth(),prevStage.getScene().getHeight());
            scene.getStylesheets().add(getClass().getResource("GameOver.css").toExternalForm());
            prevStage.setScene(scene);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    /**
     * initialize buttons actions.
     */
    public void initialize() {
        btnAnswer1.setOnAction(e->answer(e));
        btnAnswer2.setOnAction(e->answer(e));
        btnAnswer3.setOnAction(e->answer(e));
        btnAnswer4.setOnAction(e->answer(e));
    }

    @Override
    /**
     * function excecutes when property changes in logic, and change screen accordingly.
     * @param e is the event that happened.
     */
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        String newValue = String.valueOf(e.getNewValue());

        switch(propertyName) {
            case "score": {
                scoreLabel.setText("Score = " +  newValue);
                break;
            }
            case "life": {
                lifeLabel.setText("Life = " +  newValue);
                break;
            }
            case "song": {
                Song song = (Song)e.getNewValue();
                String songId = song.getSongYoutubeId();
                float endOfFadeIn = song.getEndOfFadeIn();
                int minutes = (int)endOfFadeIn;
                double seconds = endOfFadeIn - Math.floor(endOfFadeIn);
                seconds = seconds + 60 * minutes;

                System.out.println("song id in game controoler: " +songId);

                String url = "https://www.youtube.com/watch?v="+ songId + "&autoplay=1" + "&t=" +seconds + "s";
                System.out.println("url is: " + url);
                youTubePlayer.getEngine().load(url);
                break;
            }
            case "game-over": {
                youTubePlayer.getEngine().load("");
                gameOver();
                break;
            }
            case "timer": {
                timeLeft.setText("Time: " + newValue);
                double timeOfTurn = (double)gameLogic.getTimeOfTurn();
                progressBar.setProgress(Double.valueOf(newValue)/timeOfTurn);
                if(((int)timeOfTurn) - 3 == Integer.parseInt(newValue)) {
                    answerResult.setText("");
                }
                break;
            }
            case "answers": {
                String[] answers = (String[])e.getNewValue();
                btnAnswer1.setText(answers[0]);
                btnAnswer2.setText(answers[1]);
                btnAnswer3.setText(answers[2]);
                btnAnswer4.setText(answers[3]);
                break;
            }
            case "AnswerResult": {
                if(newValue.equals("true")) {
                    answerResult.setText("Correct Answer !");
                    answerResult.setTextFill(Paint.valueOf("green"));
                } else {
                    answerResult.setText("Wrong Answer ");
                    answerResult.setTextFill(Paint.valueOf("red"));

                }
                break;
            }
            default: break;

        }
    }

}
