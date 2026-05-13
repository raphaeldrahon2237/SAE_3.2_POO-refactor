package towerdef.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.TilePane;
import towerdef.View.ViewTerrain;
import towerdef.model.map.MapGrid;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private TilePane tilePane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MapGrid mapGrid = new MapGrid();
        int map[][] = mapGrid.getMap();
        ViewTerrain.renderMap(tilePane, map);

    }
}

