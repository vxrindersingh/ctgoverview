package com.example.backupctg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class mapGeneration {
    private Image roadImage=new Image(getClass().getResourceAsStream("road.jpg"));
    private double roadPositionY = 0;
    private double scrollSpeed = 1; // Adjust speed as needed

        public void generateMap(GraphicsContext gc, double canvasWidth, double canvasHeight,double userCarSpeed) {
            double adjustedSpeed = calculateAdjustedSpeed(userCarSpeed);
            setScrollSpeed(adjustedSpeed);

            // Draw the scrolling road
            gc.drawImage(roadImage, 0, roadPositionY, canvasWidth, canvasHeight);
            gc.drawImage(roadImage, 0, roadPositionY - roadImage.getHeight(), canvasWidth, canvasHeight);
            gc.drawImage(roadImage, 0, roadPositionY + roadImage.getHeight(), canvasWidth, canvasHeight);

            // Update the road position for scrolling effect
            roadPositionY += scrollSpeed;

            // Wrap around the road image when reaching the top edge
            if (roadPositionY >= roadImage.getHeight()) {
                roadPositionY -= roadImage.getHeight();
            }
         }
         public void setScrollSpeed(double speed){//sets scroll speed
            this.scrollSpeed=speed;
         }

         public double getScrollSpeed(){
            return scrollSpeed;
         }
    private double calculateAdjustedSpeed(double userCarSpeed) {//calculation for scrolling speed
        return userCarSpeed * 0.3;
    }


}
