package com.iaroslaveremeev.quiz.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.quiz.dto.ResponseResult;
import com.iaroslaveremeev.quiz.model.Question;
import com.iaroslaveremeev.quiz.model.Quiz;
import com.iaroslaveremeev.quiz.util.URLHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class QuestionRepository {
    private List<Question> questions;
    public QuestionRepository() {
    }

    // Method to download the list of questions from the API
    public void downloadQuestions(Quiz quiz) throws IOException {
        try (InputStream inputStream = URLHelper.getData("https://opentdb.com/api.php?" +
                "amount=" + quiz.getNumberOfQuestions() + "&category=" + quiz.getCategory().getId() +
                "&difficulty=" + quiz.getDifficulty().name().toLowerCase(), "GET")){
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseResult<List<Question>> responseResult = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            if (responseResult.getResponse_code() == 0){
                this.questions = responseResult.getResults();
            }
            else {
                throw new IOException("There are no questions from this category");
            }
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionRepository that = (QuestionRepository) o;
        return Objects.equals(questions, that.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questions);
    }


}
