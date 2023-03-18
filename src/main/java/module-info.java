module com.iaroslaveremeev.quiz {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.prefs;
    requires com.fasterxml.jackson.databind;

    opens com.iaroslaveremeev.quiz to javafx.fxml;
    exports com.iaroslaveremeev.quiz;
    exports com.iaroslaveremeev.quiz.controllers;
    opens com.iaroslaveremeev.quiz.controllers to javafx.fxml;
}