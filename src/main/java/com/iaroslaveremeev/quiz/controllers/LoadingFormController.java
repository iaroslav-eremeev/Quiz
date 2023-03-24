package com.iaroslaveremeev.quiz.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.quiz.model.Category;
import com.iaroslaveremeev.quiz.model.Difficulty;
import com.iaroslaveremeev.quiz.model.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.prefs.Preferences;

public class LoadingFormController {
    @FXML
    public TextField numberOfQuestions;
    @FXML
    public ComboBox<String> selectedCategory;
    @FXML
    public ComboBox<String> selectedDifficulty;
    Preferences prefs;

    public void initialize(){
        this.selectedCategory.getItems().addAll("Mythology", "Sports", "Geography", "Art");
        this.selectedDifficulty.getItems().addAll("Easy", "Medium", "Hard");
    }

    public void startGame(ActionEvent actionEvent) {
    }

    public void saveQuiz(ActionEvent actionEvent) throws IOException {
        // Create a new quiz from the parameters entered by the user
        int numberOfQuestions = Integer.parseInt(this.numberOfQuestions.getText());
        Category selectedCategory = new Category(this.selectedCategory.getValue());
        Difficulty selectedDifficulty = Difficulty.valueOf(this.selectedDifficulty.getValue());
        Quiz quiz = new Quiz(numberOfQuestions, selectedCategory, selectedDifficulty);
        quiz.setQuestions();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, quiz.toString());
        alert.show();
        // Save the quiz to a file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(prefs.get("dirPath", "")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "JSON and CSV files", "*.json", "*.JSON", "*.csv", "*.CSV"));
        File file = fileChooser.showSaveDialog(null);
        try {
            if (file != null) {
            prefs = Preferences.userRoot().node("dirPath");
            prefs.put("dirPath", file.getAbsolutePath());
            ObjectMapper objectMapper = new ObjectMapper();
            String filename = file.getName();
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename))) {
                objectMapper.writeValue(bufferedWriter, quiz);
            } catch (IOException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Quiz parameters are incorrect");
                errorAlert.show();
            }
            } else throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR, "File not found");
        errorAlert.show();
        }
        }
    }
