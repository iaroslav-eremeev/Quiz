package com.iaroslaveremeev.quiz.controllers;

import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import com.iaroslaveremeev.quiz.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainController {
    @FXML
    public Button loadFromFile;
    @FXML
    public Button loadFromInternet;
    @FXML
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
            loadingStage.show();
            Stage close = (Stage) this.loadFromInternet.getScene().getWindow();
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
                    gameStage.show();
                    Stage close = (Stage) this.loadFromFile.getScene().getWindow();
                    close.close();
                }
            }
            //TODO save current path from fileChooser
            else {
                prefs.put("dirPath", String.valueOf(fileChooser.getInitialDirectory()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
