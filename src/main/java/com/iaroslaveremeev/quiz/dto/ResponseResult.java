package com.iaroslaveremeev.quiz.dto;

public class ResponseResult<T> {
    private int response_code;
    private T results;

    public ResponseResult() {
    }

    public ResponseResult(int response_code, T results) {
        this.response_code = response_code;
        this.results = results;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
