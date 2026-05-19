package towerdef.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import towerdef.View.ViewTerrain;
import towerdef.model.Core.Deplacement;
import towerdef.model.entity.Recruit;
import towerdef.model.map.MapGrid;

import java.net.URL;
import java.util.ResourceBundle;

import static towerdef.model.map.TileType.TILESIZE;

public class GameController implements Initializable {

    @FXML
    private Pane gamePane;

    @FXML
    private TilePane tilePane;

    private DoubleProperty xProperty = new SimpleDoubleProperty();
    private DoubleProperty yProperty = new SimpleDoubleProperty();
    private Timeline gameLoop;
    private MapGrid mapGrid;
    private int temps;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapGrid = new MapGrid();
        int[][] map = mapGrid.getMap();
        ViewTerrain.renderMap(tilePane, map);

        Recruit recruit = new Recruit(1, 0);

        Circle circle = new Circle(10);
        circle.setFill(Color.BLUE);

        circle.centerXProperty().bind(
                recruit.xProperty().multiply(TILESIZE).add(TILESIZE / 2.0)
        );

        circle.centerYProperty().bind(
                recruit.yProperty().multiply(TILESIZE).add(TILESIZE / 2.0)
        );

        gamePane.getChildren().add(circle);

        Deplacement deplacement = new Deplacement(recruit, mapGrid.getChemin(), 0.35);
        deplacement.start();
        gamePane.getChildren().add(cible);
    }

    private void initAnimation() {
        gameLoop = new Timeline();
        temps=0;
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                // on définit le FPS (nbre de frame par seconde)
                Duration.seconds(0.017),
                // on définit ce qui se passe à chaque frame
                // c'est un eventHandler d'ou le lambda
                (ev ->{
                    if(temps==100){
                        System.out.println("fini");
                        gameLoop.stop();
                    }
                    else if (temps%5==0){
                        System.out.println("un tour");
                    }
                    temps++;
                })
        );
        gameLoop.getKeyFrames().add(kf);
    }
}

