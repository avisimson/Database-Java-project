package sample.UInterface;
/**
 * Created by Yakir Pinchas and Avi Simson on 08/01/18.
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.CheckBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
public class GenreController {
    @FXML
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
    private CheckBox reggae;
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
    private CheckBox jungle;
    @FXML
    private CheckBox GenreArray[] = {hip_hop,rock,ccm,post_grunge,pop,house,jazz,rap,reggae,salsa,chill_out,
            metal,dance,funk,trance,gospel,track,honky_tonk,dubstep,disco,meditation,blues,trip_hop,chinese,flamenco,
            stand_up,ballad,samba, wave,jungle};

    private ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
        private int activeCount = 0;
        public void changed(ObservableValue<? extends Boolean> o, Boolean oldValue, Boolean newValue) {
            if (newValue) {
                System.out.println(activeCount);
                activeCount++;
                if (activeCount == 3) {
                    // disable unselected CheckBoxes
                    for (CheckBox cb : GenreArray) {
                        if (!cb.isSelected()) {
                            cb.setDisable(true);
                        }
                    }
                }
            } else {
                if (activeCount == 3) {
                    // reenable CheckBoxes
                    for (CheckBox cb : GenreArray) {
                        cb.setDisable(false);
                    }
                }
                activeCount--;
            }
        }
    };
    @FXML
    protected void ok() throws IOException  {
        Game game= new Game();
        Stage stage = (Stage) OkButton.getScene().getWindow();
        try {
            game.start(stage);

        } catch (Exception e) {

        }
    }
}
