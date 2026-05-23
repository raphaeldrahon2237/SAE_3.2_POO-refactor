package towerdef.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import towerdef.View.ViewTerrain;
import towerdef.model.Game;
import towerdef.model.core.Movement;
import towerdef.model.entity.*;
import towerdef.model.entity.Recruit;
import towerdef.model.map.MapGrid;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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
    private Map<Entity, Node> entityViews = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Game();

        ViewTerrain.renderMap(tilePane, game.getMapGrid().getMap());

        initEntityListener();

        game.setInitialState();

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


    private void initEntityListener() {
        game.getEntities().addListener((ListChangeListener<Entity>) change -> {
            while (change.next()) {

                if (change.wasAdded()) {
                    for (Entity entity : change.getAddedSubList()) {
                        createEntityView(entity);
                    }
                }

                if (change.wasRemoved()) {
                    for (Entity entity : change.getRemoved()) {
                        removeEntityView(entity);
                    }
                }
            }
        });
    }

    private void createEntityView(Entity entity) {
        Circle circle = new Circle();

        circle.setRadius(10);
        circle.setFill(Color.BLUE);

        circle.centerXProperty().bind(
                entity.xProperty().multiply(TILESIZE).add(TILESIZE / 2.0)
        );

        circle.centerYProperty().bind(
                entity.yProperty().multiply(TILESIZE).add(TILESIZE / 2.0)
        );

        entityViews.put(entity, circle);
        gamePane.getChildren().add(circle);
    }

    private void removeEntityView(Entity entity) {
        Node node = entityViews.remove(entity);

        if (node != null) {
            gamePane.getChildren().remove(node);
        }
    }

}

