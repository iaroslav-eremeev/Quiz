package com.iaroslaveremeev.quiz.controllers;

import com.iaroslaveremeev.quiz.model.Question;
import com.iaroslaveremeev.quiz.model.Quiz;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private List<Tab> questions;
    private List<ToggleGroup> answers;

    @Override
    public void initData(Quiz quiz) throws IOException {
        this.quiz = quiz;
    }
    public void initialize() {
        Platform.runLater(this::makeQuestionTabs);
    }
    public void makeQuestionTabs() {
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
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
        Tab resultsTab = new Tab();
        resultsTab.setText("Results");
        this.questions.add(resultsTab);
        this.tabPane.getTabs().addAll(this.questions);
    }
}
