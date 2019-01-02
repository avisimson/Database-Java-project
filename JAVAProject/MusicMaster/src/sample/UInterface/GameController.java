package sample.UInterface;
import Logic.GameLogic;
import Logic.Question;
import Logic.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


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

    public void startGame(Question[] questions){
        this.gameLogic.startGame(questions);
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
        else if("song".equals(propertyName)){

            Song song = (Song)e.getNewValue();
            String songId = song.getSongYoutubeId();
            float endOfFadeIn = song.getEndOfFadeIn();
            int minutes = (int)endOfFadeIn;
            double seconds = endOfFadeIn - Math.floor(endOfFadeIn);
            seconds = seconds + 60 * minutes;
            System.out.println("song id in game controoler: " +songId);
            System.out.println("in function songId: " + "http://www.youtube.com/watch/"+ songId + "?autoplay=1");
            if(songId != null) {
                String url = "https://www.youtube.com/watch?v="+ songId + "&autoplay=1" + "&t=" +seconds + "s";
                System.out.println("url is: " + url);
                youTubePlayer.getEngine().load(url);
            } else {
                // search for different song
            }
        } else if ("answers".equals(propertyName)){
            String[] answers = (String[])e.getNewValue();
            btnAnswer1.setText(answers[0]);
            btnAnswer2.setText(answers[1]);
            btnAnswer3.setText(answers[2]);
            btnAnswer4.setText(answers[3]);
        }
    }
}
