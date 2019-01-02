package sample.UInterface;

import Logic.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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

public class GenreController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    Stage prevStage;
    private List<Artist> artistList = new LinkedList<>();
    private List<Song> songsList = new LinkedList<>();
    private List<Genre> genreList = new LinkedList<>();
    private String threeConfusionAns[] = new String[3];

    private GenreLogic genreLogic = new GenreLogic();
    private CheckBox checkBoxes[];
    private String specificSong;
    private String specificArtist;
    private Questions[] questions = new Questions[20];
    public void initialize(URL location, ResourceBundle resources) {
        genreList = genreLogic.getListOfGenres();
        checkBoxes = new CheckBox[genreList.size()];

        ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
            private int activeCount = 0;
            private int i = 0;
            public void changed(ObservableValue<? extends Boolean> o, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    activeCount++;
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
                   // System.out.println(activeCount);
                }
            }
        };

        int col = 45;
        int row = 0;
       for(int i = 0; i < genreList.size(); i++) {
           CheckBox cb = new CheckBox(genreList.get(i).getGenreName());
           cb.selectedProperty().addListener(listener);
           checkBoxes[i] = cb;
           cb.setMnemonicParsing(false);
           anchorPane.getChildren().add(i,cb);
           anchorPane.getChildren().get(i).setStyle("-fx-font-size: 12px;" +
                   "-fx-font-weight: bold;" +
                   "-fx-text-fill: white;");
           anchorPane.getChildren().get(i).setLayoutX(20 + row*35);
           anchorPane.getChildren().get(i).setLayoutY(20 + col);

           row = row + 3;
           if (((i +1) % 5 == 0))
           {
               row = 0;
               col = col + 50;
           }
       }
    }
    @FXML
    protected void ok() {
        List<Genre> genreChoose = new LinkedList<>();
        int j = 0;
        //loop for get the selected genre
        for (int i = 0; i < genreList.size(); i++) {
            CheckBox cb = checkBoxes[i];
            if(cb.isSelected()) {
                for(Genre g : genreList) {
                    if(g.getGenreName().equals(cb.getText())) {
                        genreChoose.add(j,g);
                      //  System.out.println(genreChoose.get(j).getGenreName());
                        j++;
                    }
                }
            }
        }
        artistList = genreLogic.getArtistsByGenre(genreChoose);
       // specificArtist = genreLogic.getFilterOneArtist(artistList);
       // songsList = genreLogic.getFilterSong(specificArtist);
       // specificSong = genreLogic.getFilterSpesificSong(songsList);
       // threeConfusionAns = genreLogic.getThreeConfusionAns(specificArtist);
        questions = genreLogic.Create20Questions(artistList);
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
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
    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }
}
