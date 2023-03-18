package com.iaroslaveremeev.quiz.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Category {

    private int id;
    private String name;

    public Category() {
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name) throws IOException {
        List<Category> categories = getCategories();
        for (Category category : categories) {
            if (category.getName().equals(name)) {
                this.id = category.getId();
                this.name = category.getName();
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Method to download the list of categories from the API
    public List<Category> getCategories() throws IOException {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader("https://opentdb.com/api_category.php"))) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(bufferedReader, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
