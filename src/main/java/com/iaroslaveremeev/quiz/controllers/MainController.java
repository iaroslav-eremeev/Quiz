package com.iaroslaveremeev.quiz.controllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.quiz.Main;
import com.iaroslaveremeev.quiz.model.Category;
import com.iaroslaveremeev.quiz.model.Difficulty;
import com.iaroslaveremeev.quiz.model.Question;
import com.iaroslaveremeev.quiz.model.Quiz;
import com.opencsv.CSVReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainController {
    public Button fromFileButton;
    public Button fromInternetButton;
    public CheckBox showCorrectAnswers;
    public Preferences prefs;
    public Quiz quiz;

    public void initialize() {
        prefs = Preferences.userRoot().node("quiz");
        prefs.put("dirPath", String.valueOf(new File(System.getProperty("user.dir"))));
    }
    @FXML
    public void loadFromInternet(ActionEvent actionEvent) throws IOException {
        prefs.putBoolean("showCorrectAnswer", showCorrectAnswers.isSelected());
        Stage loadingStage = Main.openWindow("loading.fxml", null);
        if (loadingStage != null) {
            loadingStage.setTitle("Loading questions from Internet");
            loadingStage.show();
            Stage close = (Stage) this.fromInternetButton.getScene().getWindow();
            close.close();
        }
    }

    public void loadFromFile(ActionEvent actionEvent) throws IOException {
        prefs.putBoolean("showCorrectAnswer", showCorrectAnswers.isSelected());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(prefs.get("dirPath", "")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json", "*.JSON"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv", "*.CSV"));
        File file = fileChooser.showOpenDialog(null);
        try {
            if (file != null) {
                prefs.put("dirPath", file.getAbsolutePath());
                // If JSON file chosen
                if (file.getName().endsWith(".json") || file.getName().endsWith(".JSON")) {
                    // Read a Quiz object from json file
                    ObjectMapper objectMapper = new ObjectMapper();
                    this.quiz = objectMapper.readValue(file, Quiz.class);
                    int decryptKey = Preferences.userRoot().node("key").getInt("key", 0);
                    this.quiz.decryptQuestions(decryptKey);
                }
                // If CSV file chosen
                else if (file.getName().endsWith(".csv") || file.getName().endsWith(".CSV")) {
                    try (CSVReader csvReader = new CSVReader(new FileReader(file))){
                        List<String[]> rows = csvReader.readAll();
                        csvReader.close();
                        String[] firstRow = rows.get(1);
                        Quiz quizCSV = new Quiz();
                        quizCSV.setNumberOfQuestions(Integer.parseInt(firstRow[0]));
                        quizCSV.setCategory(new Category(firstRow[1]));
                        quizCSV.setDifficulty(Difficulty.valueOf(firstRow[2]));
                        List<Question> questions = new ArrayList<>();
                        for (int i = 2; i < rows.size(); i++) {
                            String[] row = rows.get(i);
                            String question = row[3];
                            String correctAnswer = row[4];
                            String incorrectAnswersString = row[5];
                            String[] incorrectAnswers = incorrectAnswersString.split("\\|");
                            Question q = new Question(quizCSV.getCategory(), null, quizCSV.getDifficulty(),
                                    question, correctAnswer, incorrectAnswers);
                            questions.add(q);
                        }
                        quizCSV.setQuestions(questions);
                        this.quiz = quizCSV;
                        int decryptKey = Preferences.userRoot().node("key").getInt("key", 0);
                        this.quiz.decryptQuestions(decryptKey);
                    } catch (FileNotFoundException e) {e.printStackTrace();}
                }
                Stage gameStage = Main.openWindow("game.fxml", this.quiz);
                if (gameStage != null) {
                    gameStage.setTitle("Game");
                    gameStage.show();
                    Stage close = (Stage) this.fromFileButton.getScene().getWindow();
                    close.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The quiz file is unreadable");
            alert.setContentText("The quiz file you chose is unreadable. Please choose another one.");
            alert.showAndWait();
        }
    }
}
