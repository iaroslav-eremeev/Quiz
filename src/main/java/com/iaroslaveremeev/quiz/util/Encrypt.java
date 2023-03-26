package com.iaroslaveremeev.quiz.util;

import com.iaroslaveremeev.quiz.model.Category;
import com.iaroslaveremeev.quiz.model.Difficulty;
import com.iaroslaveremeev.quiz.model.Question;

public class Encrypt {

    // Encrypts a string using a Caesar cipher
    public static String encrypt(String input, int shift) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) (base + (c - base + shift) % 26);
            }
            else if (Character.isDigit(c)) {
                c = (char) ('0' + (c - '0' + shift) % 10);
            }
            encrypted.append(c);
        }
        return encrypted.toString();
    }

    // Decrypts a string using a Caesar cipher
    public static String decrypt(String input, int shift) {
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) (base + (c - base - shift + 26) % 26);
            }
            else if (Character.isDigit(c)) {
                c = (char) ('0' + (c - '0' - shift + 10) % 10);
            }
            decrypted.append(c);
        }
        return decrypted.toString();
    }

    // Encrypts a question using a Caesar cipher
    public static Question encryptQuestion(Question question, int shift) {
        Category category = question.getCategory();
        String type = question.getType();
        Difficulty difficulty = question.getDifficulty();
        String encryptedQuestion = encrypt(question.getQuestion(), shift);
        String encryptedCorrectAnswer = encrypt(question.getCorrect_answer(), shift);
        String[] encryptedIncorrectAnswers = new String[question.getIncorrect_answers().length];
        for (int i = 0; i < question.getIncorrect_answers().length; i++) {
            encryptedIncorrectAnswers[i] = encrypt(question.getIncorrect_answers()[i], shift);
        }
        return new Question(category, type, difficulty, encryptedQuestion, encryptedCorrectAnswer, encryptedIncorrectAnswers);
    }

    // Decrypts a question using a Caesar cipher
    public static Question decryptQuestion(Question encryptedQuestion, int shift) {
        String decryptedQuestion = decrypt(encryptedQuestion.getQuestion(), shift);
        String decryptedCorrectAnswer = decrypt(encryptedQuestion.getCorrect_answer(), shift);
        String[] decryptedIncorrectAnswers = new String[encryptedQuestion.getIncorrect_answers().length];
        for (int i = 0; i < encryptedQuestion.getIncorrect_answers().length; i++) {
            decryptedIncorrectAnswers[i] = decrypt(encryptedQuestion.getIncorrect_answers()[i], shift);
        }
        return new Question(encryptedQuestion.getCategory(), encryptedQuestion.getType(),
                encryptedQuestion.getDifficulty(), decryptedQuestion,
                decryptedCorrectAnswer, decryptedIncorrectAnswers);
    }
}
