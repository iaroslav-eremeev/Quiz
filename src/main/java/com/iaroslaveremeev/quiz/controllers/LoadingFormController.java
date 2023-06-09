package com.iaroslaveremeev.quiz.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.quiz.Main;
import com.iaroslaveremeev.quiz.model.Category;
import com.iaroslaveremeev.quiz.model.Difficulty;
import com.iaroslaveremeev.quiz.model.Question;
import com.iaroslaveremeev.quiz.model.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import com.opencsv.CSVWriter;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.prefs.Preferences;

public class LoadingFormController {
    @FXML
    public TextField numberOfQuestions;
    @FXML
    public ComboBox<String> selectedCategory;
    @FXML
    public ComboBox<String> selectedDifficulty;
    public Preferences prefs = Preferences.userRoot().node("quiz");

    public void initialize(){
        this.selectedCategory.getItems().addAll("Mythology", "Sports", "Geography", "Art");
        this.selectedDifficulty.getItems().addAll("Easy", "Medium", "Hard");
    }

    public Quiz newQuiz() throws IOException {
        int numberOfQuestions = Integer.parseInt(this.numberOfQuestions.getText());
        Category selectedCategory = new Category(this.selectedCategory.getValue());
        Difficulty selectedDifficulty = Difficulty.valueOf(this.selectedDifficulty.getValue().toLowerCase());
        Quiz quiz = new Quiz(numberOfQuestions, selectedCategory, selectedDifficulty, new ArrayList<>());
        quiz.downloadQuestions();
        return quiz;
    }

    public void startGame(ActionEvent actionEvent) throws IOException {
        Quiz quiz = newQuiz();
        Stage gameStage = Main.openWindow("game.fxml", quiz);
        if (gameStage != null) {
            gameStage.setTitle("Game");
            gameStage.show();
            Stage close = (Stage) this.numberOfQuestions.getScene().getWindow();
            close.close();
        }
    }

    public void saveQuiz(ActionEvent actionEvent) throws IOException {
        // Create a new quiz from the parameters entered by the user
        Quiz quiz = newQuiz();
        // Encrypt the quiz with a random key or with the old one already saved in the preferences
        int key;
        if (prefs.get("key", null) == null) {
            Random random = new Random();
            key = random.nextInt(9) + 1;
            prefs.put("key", String.valueOf(key));
        }
        else {
            key = Preferences.userRoot().node("key").getInt("key", 0);
        }
        quiz.encryptQuestions(key);
        // Save the quiz to a file
        FileChooser fileChooser = new FileChooser();
        if (prefs.get("dirPath", null) != null) {
            fileChooser.setInitialDirectory(new File(prefs.get("dirPath", "")));
        }
        fileChooser.setInitialDirectory(new File(Preferences.userRoot()
                .node("dirPath").get("dirPath", "user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "JSON files", "*.json", "*.JSON"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "CSV files", "*.csv", "*.CSV"));
        File file = fileChooser.showSaveDialog(null);
        try {
            if (file != null) {
                prefs.put("dirPath", file.getParent());
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    // Check if user chose .json file extension
                    if (file.getName().endsWith(".json") || file.getName().endsWith(".JSON")) {
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        objectMapper.writeValue(bufferedWriter, quiz);
                    }
                    // Check if user chose .csv file extension
                    else if (file.getName().endsWith(".csv") || file.getName().endsWith(".CSV")) {
                        CSVWriter csvWriter = new CSVWriter(fileWriter);
                        String[] header = {"numberOfQuestions", "category", "difficulty", "question", "correct_answer", "incorrect_answers"};
                        csvWriter.writeNext(header);
                        for (Question question : quiz.getQuestions()) {
                            String[] data = {String.valueOf(quiz.getNumberOfQuestions()), quiz.getCategory().getName(), quiz.getDifficulty().toString(), question.getQuestion(),
                                    question.getCorrect_answer(), String.join("|", question.getIncorrect_answers())};
                            csvWriter.writeNext(data);
                        }
                        csvWriter.close();
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
