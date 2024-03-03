package com.example.backupctg;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class sprite {
    Node node;

    double speed;

    public Rectangle getBoundingBox() {
        return new Rectangle(0, 0
                ,0,0);
    }
    public void setspeed(double itemspeed) {
        this.speed = itemspeed;
    }

}
