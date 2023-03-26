package com.iaroslaveremeev.quiz.controllers;

import com.iaroslaveremeev.quiz.model.Question;
import com.iaroslaveremeev.quiz.model.Quiz;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameFormController {
    @FXML
    private TabPane tabPane;
    private Quiz quiz;
    private List<Tab> questions;
    private List<ToggleGroup> answers;

    public void initialize(){

    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        makeQuestionTabs();
    }

    private void makeQuestionTabs() {
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            Question question = quiz.getQuestions().get(i);
            Tab tab = new Tab();
            tab.setText("Question " + (i+1));
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            vBox.setPadding(new Insets(20, 20, 20, 20));
            Label questionText = new Label(question.getQuestion());
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
            questions.add(tab);
        }
        Tab resultsTab = new Tab();
        resultsTab.setText("Results");
        questions.add(resultsTab);
        tabPane.getTabs().addAll(questions);
    }
}
