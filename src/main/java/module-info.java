module com.iaroslaveremeev.quiz {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.prefs;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.opencsv;
    requires org.apache.commons.io;

    opens com.iaroslaveremeev.quiz to javafx.fxml, com.fasterxml.jackson.databind;
    exports com.iaroslaveremeev.quiz;
    exports com.iaroslaveremeev.quiz.dto;
    exports com.iaroslaveremeev.quiz.model;
    exports com.iaroslaveremeev.quiz.controllers;
    opens com.iaroslaveremeev.quiz.controllers to javafx.fxml;
}