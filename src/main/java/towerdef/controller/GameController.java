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
    private Button buttonArtillery;

    @FXML
    private Button buttonShockwave;

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
    private EntListener alliesListener;
    private EntListener enemiesListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Game();
        game = new Game();

        scoreLabel.textProperty().bind(game.scoreProperty().asString("Score : %d"));
        goldLabel.textProperty().bind(game.goldProperty().asString("Gold : %d"));
        TerrainView.renderMap(tilePane, game.getMapGrid().getMap());

        initListeners();

        initKeyboardControls();
        initButtonControls();
        initTowerPlacementControls();

        game.setInitialState();
        initDestinationTest();
        initAnimation();
    }

    private void initListeners() {
        alliesListener=new EntListener(gamePane);
        enemiesListener=new EntListener(gamePane);
        game.getAllies().addListener(alliesListener);
        game.getEnemies().addListener(enemiesListener);

        projectileListener = new ProjectileListener(gamePane);
        game.getProjectiles().addListener(projectileListener);
    }

    private void initButtonControls(){
        buttonArcherTower.setOnAction(event -> {
            towerSelected=1;
            System.out.println("Placement mode activated: Archer Tower");});
        buttonBarracks.setOnAction(event -> {
            towerSelected = 3;
            System.out.println("Placement mode activated: Barracks");});
        buttonArtillery.setOnAction(event -> {
            towerSelected = 2;
            System.out.println("Placement mode activated: Artillery");
        });
        buttonShockwave.setOnAction(event -> {
            towerSelected = 5;
            System.out.println("Placement mode activated: Shockwave");
        });
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
                case T:
                    towerSelected=2;
                    System.out.println("Placement mode activated: Artillery");
                    break;
                case S:
                    towerSelected=5;
                    System.out.println("Placement mode activated: Shockwave");
                    break;
                case E:
                    destinationTestMode = true;
                    System.out.println("Destination test activated: Destination test mode");
                    break;



                case ESCAPE:
                    towerSelected=0;
                    System.out.println("Placement annulé");
                    break;
            }
        });

        gamePane.requestFocus();
    }

    private void initTowerPlacementControls() {
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

                System.out.println("Destination test changed : " + x + ", " + y);

                gamePane.requestFocus();

                event.consume();
            }


        });
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

                    if (game.isGameOver()) {
                        gameLoop.stop();
                        return;
                    }

                    alliesListener.animations(deltaTime);
                    enemiesListener.animations(deltaTime);

                    time++;
                }
        );

        gameLoop.getKeyFrames().add(keyFrame);
        gameLoop.play();
    }

    public void onClose() {
        game.resetScore();
    }
}
