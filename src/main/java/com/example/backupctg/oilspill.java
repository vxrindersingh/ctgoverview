package com.example.backupctg;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class oilspill extends sprite{
    private Random random = new Random();
    private int previousLane = -1, locationChooser,width=250,height=210;

    public oilspill() {
        super.node=createimage();
        int[] laneXPositions = {410, 600, 800, 985};

        do {
            locationChooser = random.nextInt(laneXPositions.length);
        } while (locationChooser == previousLane);

        previousLane = locationChooser;

        super.node.setLayoutX(laneXPositions[locationChooser]);
        super.node.setLayoutY(-230);
    }
    public ImageView createimage(){
        ImageView oilspill = new ImageView(new Image(getClass().getResourceAsStream("oilspill.png")));
        oilspill.setFitHeight(210);
        oilspill.setFitWidth(250);
        oilspill.setPreserveRatio(true);
        return oilspill;
    }
    public void moveDown() {
        super.node.setLayoutY(super.node.getLayoutY() + speed);
    }

    public void setPlayerCarSpeed(double playerCarSpeed) {
            // Adjust the speed of the opponent car based on the player car's speed
            speed=playerCarSpeed;
    }
    public Rectangle getBoundingBox() {
        return new Rectangle(super.node.getLayoutX()+10, super.node.getLayoutY()+10
                ,120,140);
    }


}
