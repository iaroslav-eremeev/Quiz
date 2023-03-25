package com.iaroslaveremeev.quiz.controllers;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
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
    public Quiz quiz;

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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json", "*.JSON"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv", "*.CSV"));
        File file = fileChooser.showOpenDialog(null);
        try {
            if (file != null) {
                prefs.put("dirPath", file.getAbsolutePath());
                // If JSON file chosen
                if (file.getName().endsWith(".json") || file.getName().endsWith(".JSON")) {
                    // Read a Quiz object from json file
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        Quiz quiz = objectMapper.readValue(file, Quiz.class);
                        // Check if the quiz is valid
                        if (quiz == null) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("The quiz file is unreadable");
                            alert.setContentText("The quiz file you chose is unreadable. Please choose another one.");
                            alert.showAndWait();
                            return;
                        }
                        // If the quiz is valid, set it as a current quiz
                        this.quiz = quiz;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // If CSV file chosen
                else if (file.getName().endsWith(".csv") || file.getName().endsWith(".CSV")) {
                    try (CSVReader csvReader = new CSVReader(new FileReader(file))){
                        List<String[]> rows = csvReader.readAll();
                        csvReader.close();
                        String[] firstRow = rows.get(1);
                        this.quiz = new Quiz();
                        quiz.setNumberOfQuestions(Integer.parseInt(firstRow[0]));
                        quiz.setCategory(new Category(firstRow[1]));
                        quiz.setDifficulty(Difficulty.valueOf(firstRow[2]));
                        List<Question> questions = new ArrayList<>();
                        for (int i = 2; i < rows.size(); i++) {
                            String[] row = rows.get(i);
                            String question = row[3];
                            String correctAnswer = row[4];
                            String[] incorrectAnswers = new String[3];
                            System.arraycopy(row, 5, incorrectAnswers, 0, 3);
                            Question q = new Question(quiz.getCategory(), null, quiz.getDifficulty(),
                                    question, correctAnswer, incorrectAnswers);
                            questions.add(q);
                        }
                        quiz.setQuestions(questions);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // Запихнуть игру из файла в gameStage
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
