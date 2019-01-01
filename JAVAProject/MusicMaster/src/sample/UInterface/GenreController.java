package sample.UInterface;
/**
 * Created by Yakir Pinchas and Avi Simson on 08/01/18.
 */
import DataBase.DBArtists;
import DataBase.DBConnection;
import DataBase.DBGenre;
import Logic.Genre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
public class GenreController implements Initializable {

    Stage prevStage;
   /* @FXML
    private Button OkButton;
    @FXML
    private CheckBox hip_hop;
    @FXML
    private CheckBox rock;
    @FXML
    private CheckBox ccm;
    @FXML
    private CheckBox post_grunge;
    @FXML
    private CheckBox pop;
    @FXML
    private CheckBox house;
    @FXML
    private CheckBox jazz;
    @FXML
    private CheckBox rap;
    @FXML
    private CheckBox raggae;
    @FXML
    private CheckBox salsa;
    @FXML
    private CheckBox chill_out;
    @FXML
    private CheckBox metal;
    @FXML
    private CheckBox dance;
    @FXML
    private CheckBox funk;
    @FXML
    private CheckBox trance;
    @FXML
    private CheckBox gospel;
    @FXML
    private CheckBox track;
    @FXML
    private CheckBox honky_tonk;
    @FXML
    private CheckBox dubstep;
    @FXML
    private CheckBox disco;
    @FXML
    private CheckBox meditation;
    @FXML
    private CheckBox blues;
    @FXML
    private CheckBox trip_hop;
    @FXML
    private CheckBox chinese;
    @FXML
    private CheckBox flamenco;
    @FXML
    private CheckBox stand_up;
    @FXML
    private CheckBox ballad;
    @FXML
    private CheckBox samba;
    @FXML
    private CheckBox wave;
    @FXML
    private CheckBox jungle;*/
    @FXML
    private AnchorPane anchorPane;
    private String artistList[] = new String[15];
    private List<Genre> genreList = new LinkedList<>();

    DBArtists conA = new DBArtists();
    DBGenre conG = new DBGenre();

    private CheckBox checkBoxes[];
    public void initialize(URL location, ResourceBundle resources) {
       /* CheckBox temp[] = {hip_hop,rock,ccm,post_grunge,pop,house,jazz,rap,raggae,salsa,chill_out,
                metal,dance,funk,trance,gospel,track,honky_tonk,dubstep,disco,meditation,blues,trip_hop,chinese,flamenco,
                stand_up,ballad,samba, wave,jungle};
        GenreArray = temp;*/
        genreList = conG.GenreList();
        //CheckBox GenreArray [] = con.GenreList();
       // final CheckBox[] checkBoxes = new CheckBox[GenreArray.length];
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
                            } else {

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
                    System.out.println(activeCount);
                }
            }
        };

       /* for (int i = 0; i < GenreArray.length; i++) {
            CheckBox cb = GenreArray[i];
            cb.selectedProperty().addListener(listener);
            checkBoxes[i] = cb;
        }*/
       //int j =0 ;
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
           if ((i % 5 == 0) && (i!= 0))
           {
               row = 0;
               col = col + 50;
           }
           //anchorPane.getChildren().get(i).setLayoutX(20 + i*20);
          // anchorPane.getChildren().get(i).setLayoutY(20 +j*20);
           //j++;
       }
    }
    @FXML
    protected void ok() {
        String GenreChoose[] = new String[3];
        int j = 0;
        //loop for get the selected genre
        for (int i = 0; i < genreList.size(); i++) {
            CheckBox cb = checkBoxes[i];
            if(cb.isSelected()) {
                GenreChoose[j] = cb.getText();
                System.out.println(GenreChoose[j]);
                j++;
            }
        }
        artistList = conA.FilterArtistByGenre(GenreChoose);
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
