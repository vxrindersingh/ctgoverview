package com.example.backupctg;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class trafficCars extends sprite{
    private int previousLane = -1, locationChooser;
    private Random random = new Random();
    private double playerCarSpeed, previousPlayerCarSpeed=0;

    public trafficCars() {


        super.node = createimage();
        // Set the initial position of the opponent car
        int[] laneXPositions = {420, 610, 810, 995};

        do {
            locationChooser = random.nextInt(laneXPositions.length);
        } while (locationChooser == previousLane);

        previousLane = locationChooser;

        super.node.setLayoutX(laneXPositions[locationChooser]);
        super.node.setLayoutY(-230);
    }

    private ImageView createimage(){
        Image carImage = getRandomCarImage();
        // Create an ImageView to display the car image
        ImageView imageView = new ImageView(carImage);
        imageView.setFitWidth(250);
        imageView.setFitHeight(250);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public void setTrafficcarspeed(double trafficcarspeed) {
        this.speed = trafficcarspeed;
    }

    public void moveDown() {
        super.node.setLayoutY(super.node.getLayoutY() + speed);
    }

    // Generate a random car image
    private Image getRandomCarImage() {
        int randomCarIndex = random.nextInt(2) + 1;
        String carImagePath = "car" + randomCarIndex + ".png";
        return new Image(getClass().getResourceAsStream(carImagePath));
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(super.node.getLayoutX()+7, super.node.getLayoutY(), 100, 215);
    }
    public void setPlayerCarSpeed(double playerCarSpeed) {
        this.playerCarSpeed = playerCarSpeed;

        if (playerCarSpeed != previousPlayerCarSpeed) {
            this.playerCarSpeed = playerCarSpeed;
            // Adjust the speed of the opponent car based on the player car's speed
            adjustSpeed();
            // Update the previous speed of the player car
            previousPlayerCarSpeed = playerCarSpeed;
        }
    }

    // Method to adjust the speed of the opponent car based on the player car's speed
    private void adjustSpeed() {

        if (playerCarSpeed > previousPlayerCarSpeed) {
            // Player car speed increased, decrease opponent car speed
            speed = speed + random.nextDouble((2) + 1);
        } else {
            // Player car speed decreased or stayed the same, increase opponent car speed
            speed = speed-random.nextDouble((10) + 4);
        }
    }
}
