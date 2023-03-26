package com.iaroslaveremeev.quiz.model;

import com.iaroslaveremeev.quiz.repositories.QuestionRepository;
import com.iaroslaveremeev.quiz.util.Encrypt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Quiz {

    private int numberOfQuestions;
    private Category category;
    private Difficulty difficulty;
    private List<Question> questions;

    public Quiz() {
    }

    public Quiz(int numberOfQuestions, Category category, Difficulty difficulty, List<Question> questions) {
        this.numberOfQuestions = numberOfQuestions;
        this.category = category;
        this.difficulty = difficulty;
        this.questions = questions;
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void downloadQuestions() throws IOException {
        QuestionRepository questionRepository = new QuestionRepository();
        this.questions = questionRepository.downloadQuestions(this);
    }

    // Encrypt questions from the quiz
    public void encryptQuestions(int shift){
        ArrayList<Question> encryptedQuestions = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            Question encryptedQuestion = Encrypt.encryptQuestion(questions.get(i), shift);
            encryptedQuestions.add(encryptedQuestion);
        }
        this.questions = encryptedQuestions;
    }

    // Decrypt questions from the quiz
    public void decryptQuestions(int shift){
        ArrayList<Question> decryptedQuestions = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            Question decryptedQuestion = Encrypt.decryptQuestion(questions.get(i), shift);
            decryptedQuestions.add(decryptedQuestion);
        }
        this.questions = decryptedQuestions;
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
