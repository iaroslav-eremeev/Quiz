package com.iaroslaveremeev.quiz.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.quiz.model.Category;
import com.iaroslaveremeev.quiz.util.URLHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CategoryRepository {

    private List<Category> categories;

    public CategoryRepository() {
    }

    public void downloadCategories() {
        try (InputStream inputStream = URLHelper.getData("https://opentdb.com/api_category.php", "GET")){
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, List<Category>> allCategories = objectMapper.readValue(inputStream, new TypeReference<>() {});
            this.categories = allCategories.get("trivia_categories");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryRepository that = (CategoryRepository) o;
        return Objects.equals(categories, that.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categories);
    }

    @Override
    public String toString() {
        return "CategoryRepository{" +
                "categories=" + categories +
                '}';
    }
}
