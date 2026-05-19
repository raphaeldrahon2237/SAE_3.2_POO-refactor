package towerdef.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MapGrid mapGrid = new MapGrid();
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
    }
}

