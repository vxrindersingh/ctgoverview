package com.example.backupctg;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;

public class settingsScene extends basescene {

    Label title = new Label("SETTINGS");
    Label bgcl, sound;
    Button yesbtn, nobtn, bkbtn, resetColorbtn,resetscore;
    ColorPicker colorPicker;
    mainMenuScene mainm = new mainMenuScene();
    private static final String HOVERED_yesBUTTON_STYLE = "-fx-border-radius: 10px; -fx-border-color: green;-fx-font-size: 30px;";
    private static final String IDLE_allBUTTON_STYLE = "-fx-background-color: transparent; -fx-font-size: 30px;";
    private static final String HOVERED_noBUTTON_STYLE = "-fx-border-radius: 10px; -fx-border-color: red;-fx-font-size: 30px;";

    public void show(Stage stage) throws IOException {


        ImageView bktomain = new ImageView(new Image(getClass().getResourceAsStream("back-button.png")));
        bktomain.setFitHeight(80);
        bktomain.setFitWidth(80);
        bkbtn = new Button("", bktomain);
        bkbtn.setLayoutX(10);
        bkbtn.setLayoutY(10);
        bkbtn.setBackground(Background.fill(Color.TRANSPARENT));
        bkbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    mainm.show(stage);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        root.getChildren().add(bkbtn);


        title.setTextFill(Color.WHITE);
        title.setLayoutY(100);
        title.setLayoutX(340);
        title.setFont(new Font("consolas", 60));
        root.getChildren().add(title);

        bgcl = new Label("Background-colour");
        bgcl.setTextFill(Color.WHITE);
        bgcl.setLayoutX(50);
        bgcl.setLayoutY(400);
        bgcl.setFont(new Font("consolas", 30));
        root.getChildren().add(bgcl);

        sound = new Label("Sound");
        sound.setTextFill(Color.WHITE);
        sound.setLayoutX(250);
        sound.setLayoutY(250);
        sound.setFont(new Font("consolas", 30));
        root.getChildren().add(sound);

        yesbtn = new Button("YES");
        yesbtn.setTextFill(Color.GREEN);
        yesbtn.setLayoutX(450);
        yesbtn.setLayoutY(240);
        yesbtn.setFont(new Font("consolas", 30));
        yesbtn.setBackground(Background.fill(Color.TRANSPARENT));
        yesbtn.setOnMouseEntered(e -> yesbtn.setStyle(HOVERED_yesBUTTON_STYLE));
        yesbtn.setOnMouseExited(e -> yesbtn.setStyle(IDLE_allBUTTON_STYLE));
        yesbtn.setStyle(IDLE_allBUTTON_STYLE);
        root.getChildren().add(yesbtn);

        nobtn = new Button("NO");
        nobtn.setTextFill(Color.RED);
        nobtn.setLayoutX(700);
        nobtn.setLayoutY(240);
        nobtn.setFont(new Font("consolas", 30));
        nobtn.setBackground(Background.fill(Color.TRANSPARENT));
        nobtn.setOnMouseEntered(e -> nobtn.setStyle(HOVERED_noBUTTON_STYLE));
        nobtn.setOnMouseExited(e -> nobtn.setStyle(IDLE_allBUTTON_STYLE));
        nobtn.setStyle(IDLE_allBUTTON_STYLE);
        root.getChildren().add(nobtn);

        resetColorbtn = new Button("reset Colour");
        resetColorbtn.setTextFill(Color.RED);
        resetColorbtn.setLayoutX(700);
        resetColorbtn.setLayoutY(390);
        resetColorbtn.setFont(new Font("consolas", 30));
        resetColorbtn.setBackground(Background.fill(Color.TRANSPARENT));
        resetColorbtn.setOnMouseEntered(e -> resetColorbtn.setStyle(HOVERED_noBUTTON_STYLE));
        resetColorbtn.setOnMouseExited(e -> resetColorbtn.setStyle(IDLE_allBUTTON_STYLE));
        resetColorbtn.setStyle(IDLE_allBUTTON_STYLE);
        resetColorbtn.setOnAction(event -> {
            try {
                PrintWriter pw = new PrintWriter("colorstore.txt");
                pw.println("#13a8ed");
                pw.close();
                Label userwarning= new Label("please return to main menu colour has been reset");
                userwarning.setLayoutX(250);
                userwarning.setLayoutY(500);
                userwarning.setTextFill(Color.WHITE);
                userwarning.setFont(new Font("consolas", 20));
                userwarning.setBackground(Background.fill(Color.TRANSPARENT));
                root.getChildren().add(userwarning);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        root.getChildren().add(resetColorbtn);


        String savedColor = readColorFromFile();

        colorPicker = new ColorPicker(Color.web(savedColor));
        colorPicker.setLayoutX(470);
        colorPicker.setLayoutY(410);
        root.getChildren().add(colorPicker);

        resetscore= new Button("reset score");
        resetscore.setFont(new Font("consolas",40));
        resetscore.setTextFill(Color.RED);
        resetscore.setBackground(Background.fill(Color.TRANSPARENT));
        resetscore.setOnMouseEntered(e -> resetscore.setStyle(HOVERED_noBUTTON_STYLE));
        resetscore.setOnMouseExited(e -> resetscore.setStyle(IDLE_allBUTTON_STYLE));
        resetscore.setStyle(IDLE_allBUTTON_STYLE);
        resetscore.setLayoutX(370);
        resetscore.setLayoutY(550);
        root.getChildren().add(resetscore);
        resetscore.setOnAction(actionEvent -> {
            try {
                PrintWriter pw = new PrintWriter("highscore.txt");//finds file
                pw.println(0);//writes hex colour in file
                pw.close();//closes file
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });


        stage.setTitle("CAR TRAFFIC GAME - Settings");
        super.show(stage);

        colorPicker.setOnAction(event -> {
            Color selectedColor = colorPicker.getValue();//gets value from colour picker
            super.scene.setFill(selectedColor);//changes current scene colour
            String hexColor = String.format("#%02X%02X%02X",
                    (int) (selectedColor.getRed() * 255),
                    (int) (selectedColor.getGreen() * 255),
                    (int) (selectedColor.getBlue() * 255));//converts selected colour to hex format
            try {
                PrintWriter pw = new PrintWriter("colorstore.txt");//finds file
                pw.println(hexColor);//writes hex colour in file
                pw.close();//closes file
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        });

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
