package UInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX FXML Controller of menu.fxml.
 */
public class MenuController {

    private Stage prevStage;
    @FXML
    private Button startButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button highScoreButton;
    @FXML
    private Button endButton;
    @FXML
    private Label WelcomeToMusicMaster;
    @FXML
    private Label NoInternet;

    @FXML
    /**
     * this function is activated when the user press on
     * highScoreTable button.
     */
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
    /**
     * this function is activated when the user press on
     * start game button.
     */
    protected void startGame() {
        if (netIsAvailable()) {
            goToGenreScreen();
            return;
        }
        NoInternet.setStyle("-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: red;" +
                "-fx-padding: 10 0 0 40;");
        NoInternet.setVisible(true);

    }

    /**
     * moving into genre pick before start the game.
     */
    private void goToGenreScreen() {
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
    /**
     * this function is activated when the user press on
     * exit button.
     */
    private void closeButtonAction(){
        //get a handle of the stage
        Stage stage = (Stage) endButton.getScene().getWindow();
        //close the stage
        stage.close();
    }

    /**
     * set prev sage.
     * @param stage
     */
    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }

    //returns true if there is a network connection- false if there isn't.
    private boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }
}
