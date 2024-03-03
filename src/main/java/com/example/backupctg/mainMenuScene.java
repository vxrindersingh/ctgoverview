package com.example.backupctg;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class mainMenuScene {
    int scwidth = 1000;
    int scheight = 700;

    Scene scene;
    Group root = new Group();
    Label title = new Label("CAR TRAFFIC GAME");
    Button play, about, settings;
    private static final String HOVERED_playBUTTON_STYLE = "-fx-border-radius: 10px; -fx-border-color: green;-fx-font-size: 50px;";
    private static final String IDLE_allBUTTON_STYLE = "-fx-background-color: transparent; -fx-font-size: 50px;";
    private static final String HOVERED_aboutBUTTON_STYLE = "-fx-border-radius: 10px; -fx-border-color: red;-fx-font-size: 50px;";

    private static final String HOVERED_settingsicBUTTON_STYLE = "-fx-border-radius: 10px; -fx-border-color: white; -fx-background-color: transparent;";

    public void show(Stage stage) throws FileNotFoundException {

        title.setTextFill(Color.WHITE);
        title.setLayoutY(180);
        title.setLayoutX(150);
        title.setFont(new Font("consolas", 80));
        root.getChildren().add(title);


        ImageView playicon = new ImageView(new Image(getClass().getResourceAsStream("playicon.png")));
        playicon.setFitWidth(50);
        playicon.setFitHeight(50);
        play = new Button(" PLAY", playicon);
        play.setTextFill(Color.GREEN);
        play.setLayoutX(360);
        play.setLayoutY(290);
        play.setBackground(Background.fill(Color.TRANSPARENT));
        play.setOnMouseEntered(e -> play.setStyle(HOVERED_playBUTTON_STYLE));
        play.setOnMouseExited(e -> play.setStyle(IDLE_allBUTTON_STYLE));
        play.setStyle(IDLE_allBUTTON_STYLE);
        root.getChildren().add(play);

        about = new Button("ABOUT");
        about.setTextFill(Color.RED);
        about.setLayoutX(370);
        about.setLayoutY(390);
        about.setBackground(Background.fill(Color.TRANSPARENT));
        about.setOnMouseEntered(e -> about.setStyle(HOVERED_aboutBUTTON_STYLE));
        about.setOnMouseExited(e -> about.setStyle(IDLE_allBUTTON_STYLE));
        about.setStyle(IDLE_allBUTTON_STYLE);
        root.getChildren().add(about);

        ImageView settingsicon = new ImageView(new Image(getClass().getResourceAsStream("settingsicon.png")));
        playicon.setFitWidth(50);
        playicon.setFitHeight(50);
        settings = new Button("", settingsicon);
        settings.setLayoutX(10);
        settings.setLayoutY(630);
        settings.setOnMouseEntered(e -> settings.setStyle(HOVERED_settingsicBUTTON_STYLE));
        settings.setOnMouseExited(e -> settings.setStyle("-fx-background-color: transparent;"));
        settings.setStyle("-fx-background-color: transparent;");
        root.getChildren().add(settings);

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
        about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    aboutScene aboutsc = new aboutScene();
                    aboutsc.show(stage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainGame maingame = new mainGame();
                try {
                    maingame.start(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        Label highscore=new Label("HIGH SCORE:"+readhighscore());
        highscore.setFont(new Font("consolas",40));
        highscore.setLayoutX(300);
        highscore.setLayoutY(550);
        highscore.setTextFill(Color.rgb(185,179,28));
        root.getChildren().add(highscore);

        String savedColor = readColorFromFile();


        scene = new Scene(root, scwidth, scheight);
        stage.setTitle("CAR TRAFFIC GAME - Main Menu");
        stage.setScene(scene);
        scene.setFill(Color.web(savedColor));
        stage.setResizable(false);
        stage.show();


    }

    private String readColorFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("colorstore.txt"))) {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private String readhighscore() {
        try (BufferedReader br = new BufferedReader(new FileReader("highscore.txt"))) {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
