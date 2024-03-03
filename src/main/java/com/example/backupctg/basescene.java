package com.example.backupctg;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class basescene {
    int scwidth = 1000;
    int scheight = 700;

    Scene scene;
    Group root = new Group();

    public void show(Stage stage) throws IOException {

        String savedColor = readColorFromFile();
        Scene scene = new Scene(root, scwidth, scheight);
        scene.setFill(Color.web(savedColor));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        this.scene=scene;

    }
    private String readColorFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("colorstore.txt"))) {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
