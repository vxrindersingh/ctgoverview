package com.example.backupctg;

import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class playerCar extends sprite {

    private double x;
    private double maxSpeed = 80;
    private double roadStartX = 400;
    private double roadEndX = 1000;
    private Label speedLabel;


    private Node rec;

    public playerCar() {

        super.node = createimage();

        speedLabel = new Label("Speed: 0");
        speedLabel.setTextFill(Color.BLACK);
        speedLabel.setFont(new Font(60));
        speedLabel.setLayoutX(50);
        speedLabel.setLayoutY(30);

    }

    public ImageView createimage() {
        ImageView car = new ImageView(new Image(getClass().getResourceAsStream("lambo.png")));
        car.setFitHeight(270);
        car.setFitWidth(260);
        car.setLayoutY(520);
        car.setLayoutX(605);
        car.setPreserveRatio(true);
        return car;
    }
    private void updateX(double newX) {
        x = newX;
    }

    public void moveLeft() {
        double newX = super.node.getLayoutX() - (speed + 5);//sets new x value
        if (isWithinRoadBounds(newX)) {// checks if x value is thin the road
            super.node.setLayoutX(newX);//makes car node move to x value
            updateX(newX);//updates x value
        }
    }
    public void moveRight() {
        double newX = super.node.getLayoutX() + speed + 5;//sets new x value
        if (isWithinRoadBounds(newX)) {// checks if x value is thin the road
            super.node.setLayoutX(newX);//makes car node move to x value
            updateX(newX);//updates x value
        }
    }

    public void increaseSpeed() {
        speed = Math.min(speed + 4, maxSpeed); // Increase speed by 4, but not exceeding maxSpeed
        updateSpeedLabel();//updates in game speed monitor
    }

    public void decreaseSpeed() {
        speed = Math.max(speed - 4, 8); // Decrease speed by 4, but not going below 0 (minimum speed)
        updateSpeedLabel();//updates in game speed monitor
    }

    private boolean isWithinRoadBounds(double newX) {
        if (speed < 50) {
            return newX >= roadStartX && newX <= roadEndX;
        } else {
            return newX >= roadStartX - 10 && newX <= roadEndX + 15;
        }
    }

    public void handleKeyPress(KeyCode keyCode) {//procedure handles key input
        switch (keyCode) {
            case LEFT:
                moveLeft();
                break;
            case A:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
            case D:
                moveRight();
                break;
            case UP:
                increaseSpeed();
                break;
            case DOWN:
                decreaseSpeed();
                break;
            case W:
                increaseSpeed();
                break;
            case S:
                decreaseSpeed();
                break;
        }
    }

    public Node getSpeedLabel() {
        return speedLabel;
    }

    public void updateSpeedLabel() {
        speedLabel.setText("Speed: " + String.format("%.2f", speed));
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(x, super.node.getLayoutY() + 2
                , 130, 260);
    }
    public void rotateCar() {
        RotateTransition tiltRight = new RotateTransition(Duration.seconds(0.4), super.node);
        tiltRight.setByAngle(-40); // Rotate the car by 360 degrees

        RotateTransition tiltLeft = new RotateTransition(Duration.seconds(0.4), super.node);
        tiltLeft.setByAngle(80); // Rotate the car by 360 degrees

        RotateTransition center = new RotateTransition(Duration.seconds(0.4), super.node);
        center.setByAngle(-40); // Rotate the car by 360 degrees


        tiltRight.setOnFinished(event -> tiltLeft.play());
        tiltLeft.setOnFinished(event -> center.play());

        tiltRight.play();
    }
}
