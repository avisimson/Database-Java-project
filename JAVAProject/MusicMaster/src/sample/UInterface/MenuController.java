package sample.UInterface;

import DataBase.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private Button settingsButton;
    @FXML
    private Button startButton;
    @FXML
    private Button endButton;
    @FXML
    protected void openSettings() {

    }
    @FXML
    protected void startGame() {
        ChooseGenre chooseGenre = new ChooseGenre();
        Stage stage = (Stage) startButton.getScene().getWindow();
        try {
            chooseGenre.start(stage);
        } catch (Exception e) {

        }
    }
    @FXML
    private void closeButtonAction(){
        //get a handle of the stage
        Stage stage = (Stage) endButton.getScene().getWindow();
        //close the stage
        stage.close();
    }

}
