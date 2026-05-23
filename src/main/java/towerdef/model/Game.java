package towerdef.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import towerdef.model.core.AStar;
import towerdef.model.core.Movement;
import towerdef.model.entity.Entity;
import towerdef.model.entity.Recruit;
import towerdef.model.entity.Troop;
import towerdef.model.map.MapGrid;

import java.util.List;

public class Game {

    private ObservableList<Entity> entities;
    private ObservableList<Troop> prePool;
    private MapGrid mapGrid;

    public Game() {
        entities = FXCollections.observableArrayList();
        prePool = FXCollections.observableArrayList();
        mapGrid = new MapGrid();
    }

    public MapGrid getMapGrid() {
        return mapGrid;
    }

    public void setInitialState() {
        Recruit recruit = new Recruit(0, 2);
        recruit.setDestination(new Point2D(16, 12));
        prePool.add(recruit);
    }

    public void addTroopFromPrePool(Troop troop) {
        assignMovement(troop);

        if (!entities.contains(troop)) {
            entities.add(troop);
        }
    }

    private void assignMovement(Troop troop) {
        Point2D source = new Point2D(troop.getX(), troop.getY());
        AStar aStar = new AStar(mapGrid, source, troop.getDestination());
        List<Point2D> path = aStar.getPath();

        Movement movement = new Movement(troop, path);
        troop.setMovement(movement);
        movement.start();
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

    public ObservableList<Troop> getPrePool() {
        return prePool;
    }

    public void updateStates(double deltaTime) {
        for (Entity entity : entities) {
            if (entity instanceof Troop) {
                ((Troop) entity).getMovement().update(deltaTime);
            }
        }
    }
}
