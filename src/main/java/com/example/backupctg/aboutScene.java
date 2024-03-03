package com.example.backupctg;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class aboutScene extends basescene {

    Label title = new Label("ABOUT");
    Button bkbtn;
    mainMenuScene menu = new mainMenuScene();


    public void show(Stage stage) throws IOException {

        title.setTextFill(Color.WHITE);
        title.setLayoutY(100);
        title.setLayoutX(400);
        title.setFont(new Font("consolas", 60));
        root.getChildren().add(title);

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
                    menu.show(stage);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        root.getChildren().add(bkbtn);

        stage.setTitle("CAR TRAFFIC GAME - About");
        super.show(stage);


    }

}
