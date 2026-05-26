package towerdef.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import towerdef.model.entity.tower.ArcherTower;
import towerdef.view.TerrainView;
import towerdef.model.Game;
import towerdef.model.entity.Entity;

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

    private boolean archerTowerSelected = false;
    private boolean destinationTestMode = false;

    private Timeline gameLoop;
    private int time;
    private long lastFrameTime = 0;
    private double deltaTime;
    private Game game;
    private Map<Entity, Node> entityViews = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Game();

        TerrainView.renderMap(tilePane, game.getMapGrid().getMap());

        //modif
        initEntityListener();

        //modif
        initKeyboardControls();

        game.setInitialState();
        //modif
        initTowerPlacement();
        initDestinationTest();
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
    //modif
    private void initTowerPlacement() {
        gamePane.setOnMouseClicked(event -> {
            if (!archerTowerSelected) {
                return;
            }

            int x = (int) (event.getX() / TILESIZE);
            int y = (int) (event.getY() / TILESIZE);

            boolean placed = game.placeTower(x, y,1);

            if (placed) {
                System.out.println("ArcherTower placée en : " + x + ", " + y);
                archerTowerSelected = false;
            } else {
                System.out.println("Impossible de placer une tour ici : " + x + ", " + y);
            }

            gamePane.requestFocus();
        });
    }
    //modif
    private void createEntityView(Entity entity) {
        Circle circle = new Circle();

        if (entity instanceof ArcherTower) {
            circle.setRadius(14);
            circle.setFill(Color.RED);
        } else {
            circle.setRadius(10);
            circle.setFill(Color.BLUE);
        }

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

    private void initKeyboardControls() {
        gamePane.setFocusTraversable(true);

        gamePane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                    archerTowerSelected = true;
                    System.out.println("Mode placement ArcherTower activé");
                    break;
                case E:
                    destinationTestMode = true;
                    System.out.println("Mode test destination activé : clique sur une case");
                    break;



                case ESCAPE:
                    archerTowerSelected = false;
                    System.out.println("Placement annulé");
                    break;
            }
        });

        gamePane.requestFocus();
    }
    //modif
    private void initDestinationTest() {
        gamePane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            while (destinationTestMode){
                int x = (int) (event.getX() / TILESIZE);
                int y = (int) (event.getY() / TILESIZE);

                game.changeTroopsDestination(new Point2D(x, y));

                destinationTestMode = false;

                System.out.println("Destination test changée : " + x + ", " + y);

                gamePane.requestFocus();

                event.consume();
            }


        });
    }
}

