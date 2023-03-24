package com.iaroslaveremeev.quiz.model;

import com.iaroslaveremeev.quiz.repositories.QuestionRepository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Quiz {

    private int numberOfQuestions;
    private Category category;
    private Difficulty difficulty;
    private List<Question> questions;

    public Quiz(int numberOfQuestions, Category category, Difficulty difficulty) {
        this.numberOfQuestions = numberOfQuestions;
        this.category = category;
        this.difficulty = difficulty;
    }

    public void setQuestions() throws IOException {
        QuestionRepository questionRepository = new QuestionRepository();
        questionRepository.downloadQuestions(this);
        this.questions = questionRepository.getQuestions();
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return numberOfQuestions == quiz.numberOfQuestions && Objects.equals(category, quiz.category) && difficulty == quiz.difficulty && Objects.equals(questions, quiz.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfQuestions, category, difficulty, questions);
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "numberOfQuestions=" + numberOfQuestions +
                ", category=" + category +
                ", difficulty=" + difficulty +
                ", questions=" + questions +
                '}';
    }
}
