package com.iaroslaveremeev.quiz;

import com.iaroslaveremeev.quiz.controllers.ControllerData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    private static Scene scene;
    public static void main(String[] args) {
        launch();
    }
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"), 315, 215);
        stage.setTitle("Quiz");
        stage.setScene(scene);
        stage.show();
    }
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static <T> Stage openWindow(String name, T data) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(name));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(loader.load()));
            if(data != null) {
                ControllerData<T> controller = loader.getController();
                controller.initData(data);
            }
            return stage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //TODO Заняться Гейм-стейджем

}