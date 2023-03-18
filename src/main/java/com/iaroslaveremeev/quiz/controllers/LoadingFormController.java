package com.iaroslaveremeev.quiz.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.prefs.Preferences;

public class LoadingFormController {
    @FXML
    public TextField numberOfQuestions;
    @FXML
    public ComboBox selectedCategory;
    @FXML
    public ComboBox selectedDifficulty;
    Preferences prefs;

    public void startGame(ActionEvent actionEvent) {
    }

    public void saveQuiz(ActionEvent actionEvent) {
        int numberOfQuestions = Integer.parseInt(this.numberOfQuestions.getText());
        String selectedCategory = this.selectedCategory.getValue().toString();
        String selectedDifficulty = this.selectedDifficulty.getValue().toString();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(prefs.get("dirPath", "")));

    }
}
