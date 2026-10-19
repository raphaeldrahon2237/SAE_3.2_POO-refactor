package towerdef.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import towerdef.view.TerrainView;
import towerdef.model.Game;
import towerdef.model.GameStats;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.tower.TowerCharacteristics;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.scene.layout.VBox;
import towerdef.model.quest.MiniQuest;

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
    private Button buttonEarthquake;

    @FXML
    private Button buttonMage;

    @FXML
    private Button buttonUpgrade;

    @FXML
    private Button buttonSell;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label goldLabel;

    @FXML
    private Label baseHpLabel;

    @FXML
    private Label waveLabel;

    @FXML
    private GridPane gameOverPane;

    @FXML
    private Label finalScoreLabel;

    @FXML
    private Label survivalTimeLabel;

    @FXML
    private Label maxWaveLabel;

    @FXML
    private Label enemiesKilledLabel;

    @FXML
    private Label towersPlacedLabel;

    @FXML
    private Label goldRemainingLabel;

    @FXML
    private Label towerInfoLabel;

    @FXML
    private VBox miniQuestBox;

    private int towerSelected=0;
    private boolean destinationTestMode = false;
    private Timeline gameLoop;
    private long lastFrameTime = 0;
    private double deltaTime;
    private Game game;
    private Tower selectedTower;
    private ProjectileListener projectileListener;
    private EntListener alliesListener;
    private EntListener enemiesListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Game();

        scoreLabel.textProperty().bind(game.scoreProperty().asString("Score : %d"));
        goldLabel.textProperty().bind(game.goldProperty().asString("Gold : %d"));
        baseHpLabel.textProperty().bind(game.baseHpProperty().asString("BaseHp : %d"));
        waveLabel.setText("Vague : " + game.getCurrentWave());
        TerrainView.renderMap(tilePane, game.getMapGrid().getMap());
        refreshMiniQuestDisplay();
        createBaseHealthBar();

        initListeners();

        initKeyboardControls();
        initButtonControls();
        initTowerPlacementControls();

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

    private void changeTowerSelected(int TowerId){
        towerSelected= TowerId;
        displayTowerInfos(TowerId);
    }

    private void initButtonControls() {
        buttonArcherTower.setOnAction(event -> {
            changeTowerSelected(1);
        });

        buttonArtillery.setOnAction(event -> {
            changeTowerSelected(2);
        });

        buttonBarracks.setOnAction(event -> {
            changeTowerSelected(3);
        });

        buttonMage.setOnAction(event -> {
            changeTowerSelected(4);
        });

        buttonEarthquake.setOnAction(event -> {
            changeTowerSelected(5);
        });

        buttonUpgrade.setOnAction(event -> upgradeSelectedTower());
        buttonSell.setOnAction(event -> sellSelectedTower());
    }

    private void displayTowerInfos(int towerId) {
        TowerCharacteristics characteristics = game.getTowerCharacteristics(towerId);
        if (characteristics != null) {
            towerInfoLabel.setText(
                    "Price : " + characteristics.getCost() + "\n" +
                    "Damage : " + characteristics.getDamage() + "\n" +
                    "Range : " + characteristics.getRange() + "\n" +
                    "Role : " + characteristics.getDescription()
            );
            towerInfoLabel.setVisible(true);
        }
    }

    private void initKeyboardControls() {
        gamePane.setFocusTraversable(true);

        gamePane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                    changeTowerSelected(1);
                    break;

                case T:
                    changeTowerSelected(2);
                    break;
                case B:
                    changeTowerSelected(3);
                    break;
                case  M:
                    changeTowerSelected(4);;
                    break;
                case S:
                    changeTowerSelected(5);
                    break;
                case E:
                    destinationTestMode = true;
                    System.out.println("Destination test activated: Destination test mode");
                    break;

                case ESCAPE:
                    towerSelected=0;
                    break;
            }
        });

        gamePane.requestFocus();
    }

    private void initTowerPlacementControls() {
        gamePane.setOnMouseClicked(event -> {
            int x = (int) (event.getX() / TILESIZE);
            int y = (int) (event.getY() / TILESIZE);

            if (event.getButton() == MouseButton.SECONDARY) {
                selectTower(x, y);
                event.consume();
            } else if(towerSelected!=0) {
                boolean placed;
                placed = game.placeTower(x, y, towerSelected);

                if (placed) {
                    System.out.println("Tower placed in : " + x + ", " + y);
                    towerSelected=0;
                } else {
                    System.out.println("Tower placement impossible : " + x + ", " + y);
                }

                gamePane.requestFocus();
            }
        });
    }

    private void selectTower(int x, int y) {
        selectedTower = game.getTowerAt(x, y);

        if (selectedTower!= null) {
            buttonUpgrade.setText("Upgrade (" + selectedTower.getUpgradeCost() + ")");
            buttonUpgrade.setDisable(false);
            buttonSell.setText("Sell (" + selectedTower.getSellValue() + ")");
            buttonSell.setDisable(false);
        } else {
            buttonUpgrade.setText("Upgrade");
            buttonUpgrade.setDisable(true);
            buttonSell.setText("Sell");
            buttonSell.setDisable(true);
        }

        gamePane.requestFocus();
    }

    private void upgradeSelectedTower() {
        if (game.upgradeTower(selectedTower)) {
            buttonUpgrade.setText("Upgrade (" + selectedTower.getUpgradeCost() + ")");
            buttonSell.setText("Sell (" + selectedTower.getSellValue() + ")");
        }
    }

    private void sellSelectedTower() {
        if (game.sellTower(selectedTower)) {
            selectedTower = null;
            buttonUpgrade.setText("Upgrade");
            buttonUpgrade.setDisable(true);
            buttonSell.setText("Sell");
            buttonSell.setDisable(true);
        }

        gamePane.requestFocus();
    }

    private void createBaseHealthBar() {
        double barWidth = TILESIZE;
        double barHeight = 5;

        Rectangle healthBarBack = new Rectangle(barWidth, barHeight);
        Rectangle healthBarFront = new Rectangle(barWidth, barHeight);

        healthBarBack.setFill(Color.DARKRED);
        healthBarFront.setFill(Color.LIMEGREEN);

        healthBarBack.setX(game.getBaseX() * TILESIZE + 2);
        healthBarBack.setY(game.getBaseY() * TILESIZE - 7);
        healthBarFront.xProperty().bind(healthBarBack.xProperty());
        healthBarFront.yProperty().bind(healthBarBack.yProperty());
        healthBarFront.widthProperty().bind(
                Bindings.createDoubleBinding(
                        () -> {
                            double ratio = (double) game.getBaseHp() / game.getBaseMaxHp();
                            ratio = Math.max(0, Math.min(1, ratio));
                            return barWidth * ratio;
                        },
                        game.baseHpProperty()
                )
        );

        gamePane.getChildren().addAll(healthBarBack, healthBarFront);
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

        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(0.017),
                ev -> {
                    long now = System.nanoTime();

                    if (lastFrameTime == 0) {
                       lastFrameTime = now;
                    } else {

                        deltaTime = (now - lastFrameTime) / 1_000_000_000.0;

                        lastFrameTime = now;

                        game.updateStates(deltaTime);
                        waveLabel.setText("Vague : " + game.getCurrentWave());
                        refreshMiniQuestDisplay();

                        if (game.isGameOver()) {
                            showGameOverScreen();
                        } else {

                            alliesListener.animations(deltaTime);
                            enemiesListener.animations(deltaTime);

                        }
                    }
                }
        );

        gameLoop.getKeyFrames().add(keyFrame);
        gameLoop.play();
    }

    public void showGameOverScreen() {
        GameStats stats = game.getGameStats();

        finalScoreLabel.setText(String.valueOf(game.getScore()));
        survivalTimeLabel.setText(formatTime(stats.getSurvivalTime()));
        maxWaveLabel.setText(String.valueOf(stats.getMaxWaveReached()));
        enemiesKilledLabel.setText(String.valueOf(stats.getEnemiesKilled()));
        towersPlacedLabel.setText(String.valueOf(stats.getTowersPlaced()));
        goldRemainingLabel.setText(String.valueOf(game.getGold()));

        gameOverPane.setVisible(true);
        gameLoop.stop();
    }

    @FXML
    private void restartGame() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/towerdef/game-view.fxml"));
        try {
            Parent root = loader.load();
            Scene currentScene = gameOverPane.getScene();
            Stage stage = (Stage) currentScene.getWindow();
            GameController controller = loader.getController();

            stage.setScene(new Scene(root, currentScene.getWidth(), currentScene.getHeight()));
            stage.setOnCloseRequest(event -> controller.onClose());
        }
        catch (IOException e) {
            throw new RuntimeException("Impossible de relancer la partie", e);
        }
    }

    @FXML
    private void quitGame() {
        onClose();
        Platform.exit();
    }

    private String formatTime(double timeInSeconds) {
        int totalSeconds = (int) Math.floor(timeInSeconds);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    // Updates the quest display.
    private void refreshMiniQuestDisplay() {
        if (miniQuestBox.getChildren().isEmpty()) {
            Label titleLabel = new Label("Mini-quest bonus");
            titleLabel.setWrapText(true);
            titleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
            miniQuestBox.getChildren().add(titleLabel);

            for (MiniQuest ignored : game.getMiniQuestManager().getQuests()) {
                Label questLabel = new Label();
                questLabel.setWrapText(true);
                miniQuestBox.getChildren().add(questLabel);
            }
        }

        for (int i = 0; i < game.getMiniQuestManager().getQuests().size(); i++) {
            MiniQuest quest = game.getMiniQuestManager().getQuests().get(i);
            Label questLabel = (Label) miniQuestBox.getChildren().get(i + 1);
            questLabel.setText(quest.getDisplayText());

            if (quest.isCompleted()) {
                questLabel.setStyle("-fx-text-fill: #5cff78; -fx-font-weight: bold;");
            } else {
                questLabel.setStyle("-fx-text-fill: white;");
            }
        }
    }

    public void onClose() {
        game.resetScore();
    }
}
