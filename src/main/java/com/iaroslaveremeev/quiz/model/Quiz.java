package com.iaroslaveremeev.quiz.model;

import java.util.List;
import java.util.Locale;

public class Quiz {

    private int numberOfQuestions;
    private Category category;
    private Difficulty difficulty;

    public Quiz(int numberOfQuestions, Category category, Difficulty difficulty) {
        this.numberOfQuestions = numberOfQuestions;
        this.category = category;
        this.difficulty = difficulty;
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
}
