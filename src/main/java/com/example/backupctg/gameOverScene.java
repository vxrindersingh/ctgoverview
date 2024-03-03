package com.example.backupctg;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class gameOverScene {

    int scwidth = 1000;
    int scheight = 700;

    Scene scene;
    Group root = new Group();
    Button settings, mainmenu;

    private final String HOVERED_settingsicBUTTON_STYLE = " -fx-border-color: white; -fx-background-color: transparent;";

    public void show(Stage stage) throws FileNotFoundException {

        Label title = new Label("GAME OVER");
        title.setTextFill(Color.BLACK);
        title.setLayoutY(150);
        title.setLayoutX(250);
        title.setFont(new Font("consolas", 100));
        root.getChildren().add(title);

        Text score = new Text();
        score.setText("score: " + scoreFromfile());
        score.setFont(new Font("consolas", 50));
        score.setLayoutX(340);
        score.setLayoutY(360);
        root.getChildren().add(score);

        mainmenu = new Button("main menu");
        mainmenu.setLayoutX(350);
        mainmenu.setLayoutY(400);
        mainmenu.setFont(new Font("consolas", 40));
        mainmenu.setOnMouseEntered(e -> mainmenu.setStyle(HOVERED_settingsicBUTTON_STYLE));
        mainmenu.setOnMouseExited(e -> mainmenu.setStyle("-fx-background-color: transparent;"));
        mainmenu.setStyle("-fx-background-color: transparent;");
        mainmenu.setOnAction(actionEvent -> {
            try {
                mainMenuScene mm = new mainMenuScene();
                mm.show(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
        root.getChildren().add(mainmenu);

        ImageView settingsicon = new ImageView(new Image(getClass().getResourceAsStream("settingsicon.png")));
        settings = new Button("", settingsicon);
        settings.setLayoutX(10);
        settings.setLayoutY(630);
        settings.setOnMouseEntered(e -> settings.setStyle(HOVERED_settingsicBUTTON_STYLE));
        settings.setOnMouseExited(e -> settings.setStyle("-fx-background-color: transparent;"));
        settings.setStyle("-fx-background-color: transparent;");
        settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    settingsScene setting = new settingsScene();
                    setting.show(stage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        root.getChildren().add(settings);


        scene = new Scene(root, scwidth, scheight);
        stage.setTitle("CAR TRAFFIC GAME - GAME OVER");
        stage.setScene(scene);
        scene.setFill(Color.RED);
        stage.setResizable(false);
        scene.getWindow().centerOnScreen();
        stage.show();

    }

    private String scoreFromfile() {
        try (BufferedReader br = new BufferedReader(new FileReader("playedscore.txt"))) {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
