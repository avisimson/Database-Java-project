package sample;
/**
 * Created by Yakir Pinchas and Avi Simson on 08/01/18.
 */
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GenreController {

    ObservableList<String> colors = FXCollections.
            observableArrayList("BLACK", "WHITE", "BLUE", "YELLOW", "RED", "CYAN", "ORANGE"
            		, "GREEN", "BROWN");
    ObservableList<String> players = FXCollections.
            observableArrayList("player 1", "player 2");
    @FXML
    private Checkbox HipHop;
    @FXML
    private Checkbox Rap;
    @FXML
    private Checkbox Rock;
    @FXML
    private Checkbox Dance;
    @FXML
    private Button closeButton;
    @FXML
    private void initialize(){
        HipHop = new Checkbox("Hip Hop");
        Rap = new Checkbox("Rap");
        Rock = new Checkbox("Rock");
        Dance = new Checkbox("Dance");
        //need to add more ...
    }
    @FXML
    protected void ok() throws IOException  {
        Game game= new Game();
        Stage stage = (Stage) closeButton.getScene().getWindow();
        try {
            game.start(stage);
        } catch (Exception e) {

        }
    }
}
