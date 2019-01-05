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

public class HighScoreController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    private Stage prevStage;
    private List<HighScores> highScoresList = new LinkedList<>();
    private HighScoreLogic highScoreLogic = new HighScoreLogic();

    public void initialize(URL location, ResourceBundle resources) {
        highScoresList = highScoreLogic.getList10BestScores();
        if(highScoresList.size() != 0) {
            System.out.println("OKAY");
        }
        //order in screen
        int col = 200;
        int row = 90;
        for(int i = 0; i < highScoresList.size(); i++) {
            Label username = new Label();
            username.setText(highScoresList.get(i).getUserName());
         /*   anchorPane.getChildren().add(i,username);
            anchorPane.getChildren().get(i).setLayoutX(row);
            anchorPane.getChildren().get(i).setLayoutY(col);
            Label score = new Label();
            username.setText(""+ highScoresList.get(i).getScore());
            anchorPane.getChildren().add(i,score);
            anchorPane.getChildren().get(i).setStyle("-fx-font-size: 12.5px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: black;");
            anchorPane.getChildren().get(i).setLayoutX(row + 100);
            anchorPane.getChildren().get(i).setLayoutY(col);*/
            col = col + 30;
        }
    }
    @FXML
    protected void goToMain() {
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

    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }
}
