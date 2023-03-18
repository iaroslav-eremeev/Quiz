package com.iaroslaveremeev.quiz.controllers;

import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import com.iaroslaveremeev.quiz.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainController {
    @FXML
    public Button loadFromFile;
    @FXML
    public Button loadFromInternet;
    public Preferences prefs;

    public void initialize() throws IOException {
    }

    @FXML
    public void loadFromInternet(ActionEvent actionEvent) {
    }

    public void loadFromFile(ActionEvent actionEvent) throws BackingStoreException {
        FileChooser fileChooser = new FileChooser();
        if (prefs.nodeExists("filePath")) {
            fileChooser.setInitialDirectory(new File(prefs.get("filePath", "")));
        } else {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        }
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "JSON and CSV files", "*.json", "*.JSON", "*.csv", "*.CSV"));
        File file = fileChooser.showOpenDialog(null);
        try {
            if (file != null) {
                prefs = Preferences.userRoot().node("filePath");
                prefs.put("filePath", file.getAbsolutePath());
                Stage gameStage = Main.openWindow("/game.fxml");
                if (gameStage != null) {
                    gameStage.show();
                    Stage close = (Stage) this.loadFromFile.getScene().getWindow();
                    close.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
