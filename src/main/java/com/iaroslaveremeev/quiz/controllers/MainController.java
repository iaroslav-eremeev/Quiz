package com.iaroslaveremeev.quiz.controllers;

import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import com.iaroslaveremeev.quiz.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainController {
    public Button fromFileButton;
    public Button fromInternetButton;
    public CheckBox showCorrectAnswers;
    public Preferences prefs;

    public void initialize() {
        prefs = Preferences.userRoot().node("dirPath");
        prefs.put("dirPath", String.valueOf(new File(System.getProperty("user.dir"))));
    }
    @FXML
    public void loadFromInternet(ActionEvent actionEvent) throws IOException {
        Stage loadingStage = Main.openWindow("loading.fxml");
        if (loadingStage != null) {
            loadingStage.setTitle("Loading questions from Internet");
            loadingStage.show();
            Stage close = (Stage) this.fromInternetButton.getScene().getWindow();
            close.close();
        }
    }

    public void loadFromFile(ActionEvent actionEvent) throws BackingStoreException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(prefs.get("dirPath", "")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "JSON and CSV files", "*.json", "*.JSON", "*.csv", "*.CSV"));
        File file = fileChooser.showOpenDialog(null);
        try {
            if (file != null) {
                prefs.put("dirPath", file.getAbsolutePath());
                Stage gameStage = Main.openWindow("game.fxml");
                if (gameStage != null) {
                    gameStage.setTitle("Game");
                    gameStage.show();
                    Stage close = (Stage) this.fromFileButton.getScene().getWindow();
                    close.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
