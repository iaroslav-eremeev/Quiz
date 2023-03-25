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
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class LoadingFormController {
    @FXML
    public TextField numberOfQuestions;
    @FXML
    public ComboBox<String> selectedCategory;
    @FXML
    public ComboBox<String> selectedDifficulty;
    public Preferences prefs;

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
        Difficulty selectedDifficulty = Difficulty.valueOf(this.selectedDifficulty.getValue().toLowerCase());
        Quiz quiz = new Quiz(numberOfQuestions, selectedCategory, selectedDifficulty, new ArrayList<>());
        quiz.setQuestions();
        // Save the quiz to a file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(Preferences.userRoot().node("dirPath").get("dirPath", "")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "JSON files", "*.json", "*.JSON"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "CSV files", "*.csv", "*.CSV"));
        File file = fileChooser.showSaveDialog(null);
        try {
            if (file != null) {
                prefs = Preferences.userRoot().node("dirPath");
                prefs.put("dirPath", file.getAbsolutePath());
                ObjectMapper objectMapper = new ObjectMapper();
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                    // Check if user chose json file extension
                    if (file.getName().endsWith(".json") || file.getName().endsWith(".JSON")) {
                        objectMapper.writeValue(bufferedWriter, quiz);
                    }
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
