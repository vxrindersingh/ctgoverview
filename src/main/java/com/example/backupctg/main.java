package com.example.backupctg;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {
    mainMenuScene menu = new mainMenuScene();

    @Override
    public void start(Stage stage) throws IOException {

        menu.show(stage);

    }

    public static void main(String[] args) {
        launch();
    }
}