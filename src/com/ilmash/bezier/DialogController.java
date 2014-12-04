package com.ilmash.bezier;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by ilmash on 2014-12-03.
 */
public class DialogController {

    @FXML
    private Button button;
    @FXML
    private TextField duration;

    private AnimOptions animOptions;

    public void buttonAction() {
        Stage stage = (Stage) button.getScene().getWindow();
        animOptions.setDuration(Integer.parseInt(duration.getText()));
        stage.close();
        Main.getMainStage().show();
    }

    @FXML
    private void initialize() {
        animOptions = MainController.getAnimationOptions();
        duration.setText("" + animOptions.getDuration());
    }
}
