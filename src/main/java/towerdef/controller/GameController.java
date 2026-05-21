package towerdef.controller;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import towerdef.View.ViewTerrain;
import towerdef.model.core.Movement;
import towerdef.model.entity.Recruit;
import towerdef.model.map.MapGrid;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

import static towerdef.model.map.TileType.TILESIZE;

public class GameController implements Initializable {

    @FXML
    private Pane gamePane;

    @FXML
    private TilePane tilePane;


    private Timeline gameLoop;
    private Timeline movementTimeline;
    private MapGrid mapGrid;
    private Recruit recruit;
    private Movement movement;
    private int time;
    private double secondsPerTile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapGrid = new MapGrid();
        int[][] map = mapGrid.getMap();
        ViewTerrain.renderMap(tilePane, map);

        recruit = new Recruit(1, 0);

        Circle circle = new Circle(10);
        circle.setFill(Color.BLUE);

        circle.centerXProperty().bind(
                recruit.xProperty().multiply(TILESIZE).add(TILESIZE / 2.0)
        );

        circle.centerYProperty().bind(
                recruit.yProperty().multiply(TILESIZE).add(TILESIZE / 2.0)
        );

        gamePane.getChildren().add(circle);

        secondsPerTile = 0.35;
        movement = new Movement(recruit, mapGrid.getPath());
        movement.start();
        initAnimation();
    }

    private void initAnimation() {
        gameLoop = new Timeline();
        time = 0;
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(
                // on definit le FPS (nbre de frame par seconde)
                Duration.seconds(0.017),
                // on definit ce qui se passe a chaque frame
                // c'est un eventHandler d'ou le lambda
                (ev -> {
                    updateMovement();
                    time++;
                })
        );  
        gameLoop.getKeyFrames().add(keyFrame);
        gameLoop.play();
    }

    private void updateMovement() {
        if (movementTimeline != null && movementTimeline.getStatus() == Animation.Status.RUNNING) {
            return;
        }

        Point2D target = movement.getNextTarget();

        if (target == null) {
            gameLoop.stop();
            return;
        }

        movementTimeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(secondsPerTile),

                        // On anime la propriete x de la troupe jusqu'au x de la cible
                        new KeyValue(
                                recruit.xProperty(),
                                target.getX(),
                                Interpolator.LINEAR
                        ),

                        // pareil pour Y
                        new KeyValue(
                                recruit.yProperty(),
                                target.getY(),
                                Interpolator.LINEAR
                        )
                )
        );

        movementTimeline.play();
    }
}

