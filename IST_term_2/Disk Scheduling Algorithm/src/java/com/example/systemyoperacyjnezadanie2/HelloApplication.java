package com.example.systemyoperacyjnezadanie2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainFrame.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        stage.getIcons().add(new Image(getClass().getResourceAsStream("file:com/example/systemyoperacyjnezadanie2/data.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}