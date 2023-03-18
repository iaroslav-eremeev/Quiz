package com.iaroslaveremeev.quiz.model;

import java.util.Arrays;
import java.util.Objects;

public class Question {
    private Category category;
    private String type;
    private Difficulty difficulty;
    private String question;
    private String correct_answer;
    private String[] incorrect_answers;

    public Question() {
    }

    public Question(Category category, String type, Difficulty difficulty, String question,
                    String correct_answer, String[] incorrect_answers) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answers = incorrect_answers;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String[] getIncorrect_answers() {
        return incorrect_answers;
    }

    public void setIncorrect_answers(String[] incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return Objects.equals(category, question1.category) && Objects.equals(type, question1.type) && difficulty == question1.difficulty && Objects.equals(question, question1.question) && Objects.equals(correct_answer, question1.correct_answer) && Arrays.equals(incorrect_answers, question1.incorrect_answers);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(category, type, difficulty, question, correct_answer);
        result = 31 * result + Arrays.hashCode(incorrect_answers);
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "category=" + category +
                ", type='" + type + '\'' +
                ", difficulty=" + difficulty +
                ", question='" + question + '\'' +
                ", correct_answer='" + correct_answer + '\'' +
                ", incorrect_answers=" + Arrays.toString(incorrect_answers) +
                '}';
    }
}
