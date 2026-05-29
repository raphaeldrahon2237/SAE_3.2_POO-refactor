package towerdef.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import towerdef.model.entity.tower.ArcherTower;
import towerdef.view.EntityView;
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

    @FXML
    private Button buttonArcherTower;

    @FXML
    private Button buttonBarracks;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label goldLabel;

    private boolean archerTowerSelected = false;
    private boolean barracksSelected = false;
    private boolean destinationTestMode = false;

    private Timeline gameLoop;
    private int time;
    private long lastFrameTime = 0;
    private double deltaTime;
    private Game game;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Game();
        updateScoreLabel();
        updateGoldLabel();
        TerrainView.renderMap(tilePane, game.getMapGrid().getMap());

        initEntityListener();

        initKeyboardControls();
        initButtons();

        game.setInitialState();
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

                    game.runGame();
                    game.updateStates(deltaTime);
                    time++;
                }
        );

        gameLoop.getKeyFrames().add(keyFrame);
        gameLoop.play();
    }

    private void initEntityListener() {
        game.getAllies().addListener(new listenerEnt(gamePane));
        game.getEnemies().addListener(new listenerEnt(gamePane));
    }

    private void initButtons(){
        buttonArcherTower.setOnAction(event -> {
            archerTowerSelected = true;
            System.out.println("Placement mode activated: Archer Tower");});
        buttonBarracks.setOnAction(event -> {
            barracksSelected = true;
            System.out.println("Placement mode activated: Barracks");});
    }

    private void initKeyboardControls() {
        gamePane.setFocusTraversable(true);

        gamePane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                    archerTowerSelected = true;
                    System.out.println("Placement mode activated: Archer Tower");
                    break;
                case B:
                    barracksSelected = true;
                    System.out.println("Placement mode activated: Barracks");
                case E:
                    destinationTestMode = true;
                    System.out.println("Destination test activated: Destination test mode");
                    break;



                case ESCAPE:
                    archerTowerSelected = false;
                    System.out.println("Placement annulÃ©");
                    break;
            }
        });

        gamePane.requestFocus();
    }

    private void initTowerPlacement() {
        gamePane.setOnMouseClicked(event -> {
            if (!archerTowerSelected && !barracksSelected) {
                return;
            }

            int x = (int) (event.getX() / TILESIZE);
            int y = (int) (event.getY() / TILESIZE);

            boolean placed=false;
            if(archerTowerSelected) placed= game.placeTower(x, y,1);
            else if (barracksSelected) placed= game.placeTower(x, y,3);

            if (placed) {
                System.out.println("Tower placed in : " + x + ", " + y);
                game.addScore(10);
                updateScoreLabel();
                updateGoldLabel();
                archerTowerSelected = false;
                barracksSelected = false;
            } else {
                System.out.println("Tower placement impossible : " + x + ", " + y);
            }

            gamePane.requestFocus();
        });
    }

    private void initDestinationTest() {
        gamePane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            while (destinationTestMode){
                int x = (int) (event.getX() / TILESIZE);
                int y = (int) (event.getY() / TILESIZE);

                game.changeEnemiesDestination(new Point2D(x, y));

                destinationTestMode = false;

                System.out.println("Destination test changÃ©e : " + x + ", " + y);

                gamePane.requestFocus();

                event.consume();
            }


        });
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score : " + game.getScore());
    }
    private void updateGoldLabel() {
        goldLabel.setText("Gold : " + game.getGold());
    }
    public void onClose() {
        game.resetScore();
    }
}
