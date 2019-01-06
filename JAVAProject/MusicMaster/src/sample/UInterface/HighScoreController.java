package sample.UInterface;

import Logic.HighScoreLogic;
import Logic.HighScores;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
/**
 * class that manage the table of high score
 */
public class HighScoreController implements Initializable {
    //members
    @FXML
    private AnchorPane anchorPane;
    private Stage prevStage;
    private List<HighScores> highScoresList = new LinkedList<>();
    private HighScoreLogic highScoreLogic = new HighScoreLogic();
    /**
     * function that initialize the screen of High score table
     * @param location is parameter of this function
     * @param resources is parameter of this function
     */
    public void initialize(URL location, ResourceBundle resources) {
        //get the 10 best results of players that play in the game
        highScoresList = highScoreLogic.getList10BestScores();
        //order in screen
        int col = 100;
        int row = 200;
        for(int i = 0; i < highScoresList.size(); i++) {
            //get the name of user
            Label username = new Label(highScoresList.get(i).getUserName());
            anchorPane.getChildren().add(i,username);
            anchorPane.getChildren().get(i).setLayoutX(row);
            anchorPane.getChildren().get(i).setLayoutY(col);
            anchorPane.getChildren().get(i).setStyle("-fx-font-size: 12.5px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: black;");
            //get the score of user
            Label score = new Label();
            score.setText(""+ highScoresList.get(i).getScore());
            anchorPane.getChildren().add(i,score);
            anchorPane.getChildren().get(i).setStyle("-fx-font-size: 12.5px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: black;");
            anchorPane.getChildren().get(i).setLayoutX(row + 100);
            anchorPane.getChildren().get(i).setLayoutY(col);
            col = col + 30;
        }
    }
    /**
     * function that return the game to open screen
     */
    @FXML
    protected void goToMain() {
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
     * function that update the stage that screen to user
     * @param stage is update stage
     */
    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }
}
