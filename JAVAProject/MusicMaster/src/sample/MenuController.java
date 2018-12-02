package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Set;

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
