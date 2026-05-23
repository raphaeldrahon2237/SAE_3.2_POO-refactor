package towerdef.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import towerdef.model.entity.Entity;
import towerdef.model.entity.Recruit;
import towerdef.model.map.MapGrid;


import java.util.ArrayList;

public class Game {

    private ObservableList<Entity> entities;
    private MapGrid mapGrid;

    public Game() {
        entities = FXCollections.observableArrayList();
        mapGrid = new MapGrid();
    }

    public MapGrid getMapGrid() {
        return mapGrid;
    }

    public void setInitialState() {
        Recruit recruit = new Recruit(0,0);
        entities.add(recruit);
    }

    public void removeDeadEntities() {
        entities.removeIf(entity -> !entity.isAlive());
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public ObservableList<Entity> getEntities() {
        return entities;
    }
    public void updateStates(double deltaTime) {
        //TODO
    }


}
