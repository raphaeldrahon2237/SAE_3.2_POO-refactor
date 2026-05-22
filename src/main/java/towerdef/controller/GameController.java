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
import towerdef.model.Game;
import towerdef.model.core.Movement;
import towerdef.model.entity.Recruit;
import towerdef.model.map.MapGrid;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import static towerdef.model.map.TileType.TILESIZE;

public class GameController implements Initializable {

    @FXML
    private Pane gamePane;

    @FXML
    private TilePane tilePane;


    private Timeline gameLoop;
    private MapGrid mapGrid;
    private Recruit recruit;
    private Movement movement;
    private int time;
    private long lastFrameTime = 0;
    private double deltaTime;
    private Game game;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Game();
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

        movement = new Movement(recruit, mapGrid.getPath());
        movement.start();
        initAnimation();
    }



    private void initAnimation() {
        time = 0;

        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(0.017),
                ev -> {
                    long now = System.nanoTime();

                    if (lastFrameTime == 0) {
                        lastFrameTime = now;
                        return;
                    }

                    deltaTime = (now - lastFrameTime) / 1_000_000_000.0;

                    lastFrameTime = now;

                    game.updateStates(deltaTime);
                    time++;
                }
        );

        gameLoop.getKeyFrames().add(keyFrame);
        gameLoop.play();
    }


}

