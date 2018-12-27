package sample.UInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameOverController {



    @FXML
    private Button backToMenu;

    @FXML
    protected void goToMain() {
        Menu menu = new Menu();
        try {
            Stage stage = (Stage) backToMenu.getScene().getWindow();

            menu.start(stage);
        } catch (Exception e) {

        }
    }
}
