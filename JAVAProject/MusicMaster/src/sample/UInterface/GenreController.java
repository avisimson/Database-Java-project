package sample.UInterface;

import Logic.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.CheckBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * JavaFX FXML Controller of Genre.fxml.
 */
public class GenreController implements Initializable {
    private Stage prevStage;
    private List<Artist> artistList = new LinkedList<>();
    private List<Genre> genreList = new LinkedList<>();
    private GenreLogic genreLogic = new GenreLogic();
    private CheckBox checkBoxes[];
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label error;

    /**
     * This function initializes the genre choosing screen.
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        // get the list of all the genres
        genreList = genreLogic.getListOfGenres();
        checkBoxes = new CheckBox[genreList.size()];

        ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
            private int activeCount = 0;
            public void changed(ObservableValue<? extends Boolean> o, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    activeCount++;
                    error.setText("");
                    if (activeCount == 3) {
                        // disable unselected CheckBoxes
                        for (CheckBox cb : checkBoxes) {
                            if (!cb.isSelected()) {
                                cb.setDisable(true);
                            }
                        }
                    }
                } else {
                    if (activeCount == 3) {
                        // reenable CheckBoxes
                        for (CheckBox cb : checkBoxes) {
                            cb.setDisable(false);

                        }
                    }
                    activeCount--;
                }
            }
        };

        //place check boxes
        int col = 45;
        int row = 0;
        for(int i = 0; i < genreList.size(); i++) {
            CheckBox cb = new CheckBox(genreList.get(i).getGenreName());
            cb.selectedProperty().addListener(listener);
            checkBoxes[i] = cb;
            cb.setMnemonicParsing(false);
            anchorPane.getChildren().add(i,cb);
            anchorPane.getChildren().get(i).setStyle("-fx-font-size: 12.5px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: black;");
            anchorPane.getChildren().get(i).setLayoutX(38 + row*38);
            anchorPane.getChildren().get(i).setLayoutY(30 + col);

            row = row + 3;
            if (((i +1) % 5 == 0))
            {
                row = 0;
                col = col + 40;
            }
        }
    }

    /**
     * This function happens when the player clicks the ok button.
     */
    @FXML
    protected void ok() {
        List<Genre> genreChoose = new LinkedList<>();
        int j = 0;
        //get the selected genres
        for (int i = 0; i < genreList.size(); i++) {
            CheckBox cb = checkBoxes[i];
            if(cb.isSelected()) {
                for(Genre g : genreList) {
                    if(g.getGenreName().equals(cb.getText())) {
                        genreChoose.add(j,g);
                        j++;
                    }
                }
            }
        }
        // the user didn't choose genres
        if(genreChoose.size() == 0) {
            error.setText("Please choose at least one genre");
            return;
        }
        // get the artists that sing in those genres
        artistList = genreLogic.getArtistsByGenre(genreChoose);
        // go to game screen
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
            myLoader.setController(new GameController(artistList,genreChoose));
            GridPane root =  myLoader.load();
            GameController gameController = myLoader.getController();
            gameController.setPrevStage(prevStage);
            Scene scene = new Scene(root,prevStage.getScene().getWidth(),prevStage.getScene().getHeight());
            scene.getStylesheets().add(getClass().getResource("Game.css").toExternalForm());
            prevStage.setScene(scene);
            gameController.startGame();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set the previous stage.
     * @param stage - previous stage.
     */
    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }
}
