package com.iaroslaveremeev.quiz.model;

import com.iaroslaveremeev.quiz.repositories.CategoryRepository;

import java.io.IOException;
import java.util.Objects;

public class Category {

    private int id;
    private String name;

    public Category() {
    }

    public Category(String name) throws IOException {
        CategoryRepository categoryRepository = new CategoryRepository();
        categoryRepository.downloadCategories();
        for (Category category : categoryRepository.getCategories()) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
