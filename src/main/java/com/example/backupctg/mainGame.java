package com.example.backupctg;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class mainGame extends Application {
    Group root = new Group();
    int scwidth = 1550, scheight = 800;
    private playerCar car = new playerCar();
    private List<trafficCars> trafficCarslist = new ArrayList<>();
    private List<oilspill> oilSpillist = new ArrayList<>();
    private List<roadblock> roadblockList = new ArrayList<>();
    Random random = new Random();
    private long startTime;
    private boolean gameOver = false;
    private int score;
    private Stage stage;
    private Timeline trafficSpawnTimeline, obstacleSpwanTimeline;
    private mapGeneration mapGenerator = new mapGeneration();
    private Canvas canvas = new Canvas(scwidth, scheight);

    private Label scoreLabel = new Label("score: 0");
    private Label scoreDeductionLabel = new Label();


    @Override
    public void start(Stage stage) throws IOException {

        root.getChildren().add(canvas);
        root.getChildren().add(car.node);

        scoreLabel.setFont(new Font("consolas", 45));
        scoreLabel.setLayoutX(1155);
        scoreLabel.setLayoutY(20);
        scoreLabel.setTextFill(Color.BLACK);
        root.getChildren().add(scoreLabel);
        root.getChildren().add(car.getSpeedLabel());

        scoreDeductionLabel.setLayoutY(70);
        scoreDeductionLabel.setLayoutX(1300);
        scoreDeductionLabel.setTextFill(Color.RED);
        scoreDeductionLabel.setFont(new Font("consolas", 40));
        scoreDeductionLabel.setVisible(false);
        root.getChildren().add(scoreDeductionLabel);

        Scene scene = new Scene(root, scwidth, scheight);
        scene.setOnKeyPressed(event -> car.handleKeyPress(event.getCode()));
        stage.setScene(scene);
        stage.setTitle("CAR TRAFFIC GAME");
        stage.setResizable(false);
        scene.getWindow().centerOnScreen();
        stage.show();

        this.stage = stage;

        startGameLoop();

        startTime = System.nanoTime();

        trafficSpawnTimeline = createtrafficSpawnTimeline();
        obstacleSpwanTimeline = createobstacleSpawnTimeline();
    }

    private void startGameLoop() {
        // Start the game loop, e.g., using AnimationTimer
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                // Check if the user car speed is more than 0
                if (car.speed > 0 && !trafficSpawnTimeline.getStatus().equals(Timeline.Status.RUNNING)) {
                    trafficSpawnTimeline.play(); // Start spawning opponent cars
                    obstacleSpwanTimeline.play();
                    car.moveRight();
                }

                if (!gameOver) {
                    moveTrafficcars();
                    checkCollisions();
                    updateScore();
                    mapGenerator.generateMap(canvas.getGraphicsContext2D(), scwidth, scheight, car.speed);//this line allows the map to move
                    //during the gameloop
                    updateObstacles();
                    updateOpponentCars();


                }

            }
        }.start();
    }

    private Timeline createtrafficSpawnTimeline() {
        trafficSpawnTimeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> spawnOpponentCars())
        );
        trafficSpawnTimeline.setCycleCount(Timeline.INDEFINITE);
        return trafficSpawnTimeline;
    }

    private Timeline createobstacleSpawnTimeline() {
        obstacleSpwanTimeline = new Timeline(
                new KeyFrame(Duration.seconds(6.5), event -> spawnobstacles())
        );
        obstacleSpwanTimeline.setCycleCount(Timeline.INDEFINITE);
        return obstacleSpwanTimeline;
    }

    private void spawnOpponentCars() {
        int[] carsSpawn = {1, 2, 3, 4};
        int choosenocars = random.nextInt(2);
        for (int i = 0; i < carsSpawn[choosenocars]; i++) {
            spawnOpponent();
        }
    }

    private void spawnOpponent() {
        if (!gameOver) {
            trafficCars trafficCar = new trafficCars();
            trafficCar.setTrafficcarspeed(generateRandomSpeed());
            this.trafficCarslist.add(trafficCar);
            root.getChildren().add(trafficCar.node);
        }
    }

    private void spawnobstacles() {
        if (!gameOver) {
            int chooseobstacle = random.nextInt(2) + 1;
            if (chooseobstacle == 1) {
                oilspill oil = new oilspill();
                oil.setspeed(mapGenerator.getScrollSpeed());
                this.oilSpillist.add(oil);
                root.getChildren().add(oil.node);
            } else {
                roadblock rb = new roadblock();
                rb.setspeed(mapGenerator.getScrollSpeed());
                this.roadblockList.add(rb);
                root.getChildren().add(rb.node);
            }
        }
    }

    private double generateRandomSpeed() {
        double trafficspeed;
        do {
            trafficspeed = random.nextDouble((car.speed / 1.8) + 1);
        } while (trafficspeed < 2);
        return trafficspeed;
    }

    private void moveTrafficcars() {
        for (trafficCars traffic : trafficCarslist) {
            if (traffic.node.getLayoutY() < 0) {
                traffic.setTrafficcarspeed(traffic.speed);
                traffic.moveDown();
            } else {
                traffic.moveDown();
            }
        }
        for (oilspill oil : oilSpillist) {
            if (oil.node.getLayoutY() < 0) {
                oil.moveDown();
            } else {
                oil.moveDown();
            }
        }
        for (roadblock rb : roadblockList) {
            if (rb.node.getLayoutY() < 0) {
                rb.moveDown();
            } else {
                rb.moveDown();
            }
        }
    }

    private void checkCollisions() {
        Rectangle playerBoundingBox = car.getBoundingBox();

        for (trafficCars traffic : trafficCarslist) {
            Rectangle opponentBoundingBox = traffic.getBoundingBox();

            if (playerBoundingBox.intersects(opponentBoundingBox.getBoundsInLocal())) {
                handleCollision();
            }
        }
    }

    private void handleScore() {
        if (score < 10000) {
            score = score - 1000;
            scoreDeductionLabel.setText("-1000");
            showScoreDeductionLabel();
        } else if (score < 100000 && score > 10000) {
            score = score - 10000;
            scoreDeductionLabel.setText("-10000");
            showScoreDeductionLabel();
        } else {
            score = score - 30000;
            scoreDeductionLabel.setText("-30000");
            showScoreDeductionLabel();
        }
    }
    private void showScoreDeductionLabel() {
        scoreDeductionLabel.setVisible(true);

        // Start a timeline to hide the label after 2 seconds
        Timeline hideLabelTimeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> {
                    scoreDeductionLabel.setVisible(false);
                })
        );
        hideLabelTimeline.play();
    }

    private void handleCollision() {
        gameOver = true;
        storehighscore();
        stopGame();
    }

    private void stopGame() {
        stage.close();
        try {
            gameOverScene gOs = new gameOverScene();
            gOs.show(stage);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void storehighscore() {
        int hsc = Integer.parseInt(scoreFromfile());
        if (score > hsc) {
            try {
                PrintWriter pw = new PrintWriter("highscore.txt");//finds file
                pw.println(score);//writes score in file
                pw.close();//closes file
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            PrintWriter pw = new PrintWriter("playedscore.txt");//finds file
            pw.println(score);//writes score in file
            pw.close();//closes file
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private String scoreFromfile() {
        try (BufferedReader br = new BufferedReader(new FileReader("highscore.txt"))) {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateScore() {
        long elapsedTime = System.nanoTime() - startTime;
        int timeInSeconds = (int) (elapsedTime / 1_000_000_000); // Convert nanoseconds to seconds

        // Adjust the scoring formula based on your preferences
        if (car.speed < 30) {
            score = score + (int) ((car.speed * 0.9));
        } else {
            score = score + (int) (timeInSeconds + (car.speed * 1.2));
        }
        scoreLabel.setText("Score: " + String.format(String.valueOf(score)));

    }

    private void updateOpponentCars() {
        Iterator<trafficCars> iterator = trafficCarslist.iterator();
        while (iterator.hasNext()) {
            trafficCars traffic = iterator.next();
            // Check if opponent car is off-screen, you may need to adjust these conditions
            if (traffic.node.getLayoutY() > 800 || traffic.node.getLayoutY() < -230) {
                root.getChildren().remove(traffic.node);// Remove traffic image car from the scene or perform cleanup logic
                iterator.remove();//removes node of traffic car
            }
        }
        for (trafficCars opponentCar : trafficCarslist) {
            opponentCar.setPlayerCarSpeed(car.speed);
        }

    }

    private void updateObstacles() {
        Rectangle playerBoundingBox = car.getBoundingBox();
        Iterator<oilspill> iterator = oilSpillist.iterator();
        while (iterator.hasNext()) {
            oilspill oil = iterator.next();
            Rectangle oilBoundingbox = oil.getBoundingBox();
            // Check if opponent car is off-screen, you may need to adjust these conditions
            if (oil.node.getLayoutY() > 800 || oil.node.getLayoutY() < -230) {
                root.getChildren().remove(oil.node);// Remove traffic image car from the scene or perform cleanup logic
                iterator.remove();//removes node of traffic car
            }
            if (playerBoundingBox.intersects(oilBoundingbox.getBoundsInLocal())) {
                root.getChildren().remove(oil.node);
                car.rotateCar();
                iterator.remove();
            }


        }
        Iterator<roadblock> iterator2 = roadblockList.iterator();
        while (iterator2.hasNext()) {
            roadblock rb = iterator2.next();
            Rectangle rbBoundingbox = rb.getBoundingBox();
            // Check if opponent car is off-screen, you may need to adjust these conditions
            if (rb.node.getLayoutY() > 800 || rb.node.getLayoutY() < -230) {
                root.getChildren().remove(rb.node);// Remove traffic image car from the scene or perform cleanup logic
                iterator2.remove();//removes node of traffic car
            }
            if (playerBoundingBox.intersects(rbBoundingbox.getBoundsInLocal())) {
                root.getChildren().remove(rb.node);
                iterator2.remove();
                handleScore();
            }
        }
        for (roadblock rb : roadblockList) {
            rb.setPlayerCarSpeed(mapGenerator.getScrollSpeed());
        }
        for (oilspill olsp : oilSpillist) {
            olsp.setPlayerCarSpeed(mapGenerator.getScrollSpeed());
        }

    }

}
