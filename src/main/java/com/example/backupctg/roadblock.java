package com.example.backupctg;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class roadblock extends sprite{
    private Random random = new Random();
    private int previousLane = -1, locationChooser;
    public roadblock() {
        super.node=createimage();

        int[] laneXPositions = {400, 590, 790, 975};

        do {
            locationChooser = random.nextInt(laneXPositions.length);
        } while (locationChooser == previousLane);

        previousLane = locationChooser;

        super.node.setLayoutX(laneXPositions[locationChooser]);
        super.node.setLayoutY(-230);
    }
    public ImageView createimage(){
        ImageView oilspill = new ImageView(new Image(getClass().getResourceAsStream("roadblock.png")));
        oilspill.setFitHeight(190);
        oilspill.setFitWidth(220);
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
        return new Rectangle(super.node.getLayoutX()+19, super.node.getLayoutY()+35
                ,140,140);//x,y,width,height
    }


}
