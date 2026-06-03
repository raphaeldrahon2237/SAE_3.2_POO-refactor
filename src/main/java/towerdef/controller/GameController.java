package towerdef.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import towerdef.view.TerrainView;
import towerdef.model.Game;

import java.net.URL;
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

    private int towerSelected=0;
    private boolean destinationTestMode = false;

    private Timeline gameLoop;
    private int time;
    private long lastFrameTime = 0;
    private double deltaTime;
    private Game game;
    private ProjectileListener projectileListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Game();
        game = new Game();

        scoreLabel.textProperty().bind(game.scoreProperty().asString("Score : %d"));
        goldLabel.textProperty().bind(game.goldProperty().asString("Gold : %d"));
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
        game.getAllies().addListener(new EntListener(gamePane));
        game.getEnemies().addListener(new EntListener(gamePane));

        projectileListener = new ProjectileListener(gamePane);
        game.getProjectiles().addListener(projectileListener);
    }

    private void initButtons(){
        buttonArcherTower.setOnAction(event -> {
            towerSelected=1;
            System.out.println("Placement mode activated: Archer Tower");});
        buttonBarracks.setOnAction(event -> {
            towerSelected = 3;
            System.out.println("Placement mode activated: Barracks");});
    }

    private void initKeyboardControls() {
        gamePane.setFocusTraversable(true);

        gamePane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                    towerSelected=1;
                    System.out.println("Placement mode activated: Archer Tower");
                    break;
                case B:
                    towerSelected=3;
                    System.out.println("Placement mode activated: Barracks");
                    break;
                case E:
                    destinationTestMode = true;
                    System.out.println("Destination test activated: Destination test mode");
                    break;



                case ESCAPE:
                    towerSelected=0;
                    System.out.println("Placement annulÃ©");
                    break;
            }
        });

        gamePane.requestFocus();
    }

    private void initTowerPlacement() {
        gamePane.setOnMouseClicked(event -> {

            if(towerSelected!=0) {
                int x = (int) (event.getX() / TILESIZE);
                int y = (int) (event.getY() / TILESIZE);

                boolean placed = false;
                placed = game.placeTower(x, y, towerSelected);

                if (placed) {
                    System.out.println("Tower placed in : " + x + ", " + y);
                    game.addScore(10);
                    towerSelected=0;
                } else {
                    System.out.println("Tower placement impossible : " + x + ", " + y);
                }

                gamePane.requestFocus();
            }
        });
    }

    private void initDestinationTest() {
        gamePane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {


            if (destinationTestMode){
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


    public void onClose() {
        game.resetScore();
    }
}
