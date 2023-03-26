package com.iaroslaveremeev.quiz.controllers;

import com.iaroslaveremeev.quiz.model.Question;
import com.iaroslaveremeev.quiz.model.Quiz;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameFormController implements ControllerData<Quiz> {
    @FXML
    private TabPane tabPane;
    private Quiz quiz;
    private List<Tab> questions = new ArrayList<>();
    private List<ToggleGroup> answers = new ArrayList<>();

    @Override
    public void initData(Quiz quiz) throws IOException {
        this.quiz = quiz;
    }
    public void initialize() {
        Platform.runLater(this::makeTabs);
    }
    public void makeTabs() {
        makeQuestionTabs();
        makeResultTab();
    }

    private void makeQuestionTabs() {
        for (int i = 0; i < this.quiz.getQuestions().size(); i++) {
            Question question = this.quiz.getQuestions().get(i);
            Tab tab = new Tab();
            tab.setText("Question " + (i+1));
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            vBox.setPadding(new Insets(20, 20, 20, 20));
            Label questionText = new Label(question.getQuestion());
            questionText.setWrapText(true);
            vBox.getChildren().add(questionText);
            List<String> options = new ArrayList<>();
            options.add(question.getCorrect_answer());
            options.addAll(Arrays.asList(question.getIncorrect_answers()));
            // Shuffle options
            Collections.shuffle(options);
            ToggleGroup answerOptions = new ToggleGroup();
            this.answers.add(answerOptions);
            for (String option : options) {
                RadioButton radioButton = new RadioButton(option);
                radioButton.setToggleGroup(answerOptions);
                vBox.getChildren().add(radioButton);
            }
            tab.setContent(vBox);
            this.questions.add(tab);
        }
    }
    private void makeResultTab() {
        Tab resultsTab = new Tab();
        resultsTab.setText("Results");
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        Button checkButton = new Button("Check");
        checkButton.setOnAction(event -> {
            boolean allQuestionsAnswered = true;
            StringBuilder statisticsBuilder = new StringBuilder();
            int correctAnswers = 0;
            int totalQuestions = quiz.getQuestions().size();
            for (int i = 0; i < totalQuestions; i++) {
                ToggleGroup answerOptions = answers.get(i);
                if (answerOptions.getSelectedToggle() == null) {
                    allQuestionsAnswered = false;
                    break;
                } else {
                    String selectedOption = ((RadioButton) answerOptions.getSelectedToggle()).getText();
                    Question question = quiz.getQuestions().get(i);
                    if (selectedOption.equals(question.getCorrect_answer())) {
                        correctAnswers++;
                        statisticsBuilder.append("Q").append(i+1).append(": +\n");
                    } else {
                        statisticsBuilder.append("Q").append(i+1).append(": -\n");
                    }
                }
            }
            if (allQuestionsAnswered) {
                double percentage = (double) correctAnswers / totalQuestions * 100;
                statisticsBuilder.append("\nTotal: ").append(correctAnswers).append("/").append(totalQuestions)
                        .append(" (").append(String.format("%.2f", percentage)).append("%)");
                Label statisticsLabel = new Label(statisticsBuilder.toString());
                vBox.getChildren().add(statisticsLabel);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Not all questions were answered!");
                alert.showAndWait();
            }
        });
        vBox.getChildren().add(checkButton);
        resultsTab.setContent(vBox);
        this.questions.add(resultsTab);
        this.tabPane.getTabs().addAll(this.questions);
    }
}
