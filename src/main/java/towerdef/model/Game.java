package towerdef.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import towerdef.model.core.AStar;
import towerdef.model.core.Movement;
import towerdef.model.entity.*;
import towerdef.model.map.MapGrid;

import java.util.List;

public class Game {

    private ObservableList<Entity> entities;
    //private ObservableList<Troop> prePool;
    private MapGrid mapGrid;

    public Game() {
        entities = FXCollections.observableArrayList();
        //prePool = FXCollections.observableArrayList();
        mapGrid = new MapGrid();
    }

    public MapGrid getMapGrid() {
        return mapGrid;
    }

    public void setInitialState() {
        Recruit recruit = new Recruit(0, 2);

        recruit.setDestination(new Point2D(16, 12));
        //modif
        spawnTroop(recruit);
        // prePool.add(recruit);
    }

//    public void addTroopFromPrePool(Troop troop) {
//        assignMovement(troop);
//
//        if (!entities.contains(troop)) {
//            entities.add(troop);
//        }
//    }

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

//   public ObservableList<Troop> getPrePool() {
//        return prePool;
//    }

    public void updateStates(double deltaTime) {
        for (Entity entity : entities) {
            //modif
            if (entity instanceof Troop troop && troop.getMovement() != null){
                troop.getMovement().update(deltaTime);
            }
        }
    }
    //modif
    public void spawnTroop(Troop troop) {
        assignMovement(troop);

        if (!entities.contains(troop)) {
            entities.add(troop);
        }
    }

    //modif
    public boolean canPlaceTower(int x, int y){
        if(!mapGrid.isInside(x,y)) {
            return false;
        }
        if (mapGrid.isWalkable(x, y)){
            return false;
        }
        for (Entity entity : entities){
            if(entity instanceof Tower && entity.getX() == x && entity.getY() == y){
                return false;
            }
        }
        return true;
    }
    //modif
    public boolean placeArcherTower(int x, int y) {
        if (!canPlaceTower(x, y)) {
            return false;
        }

        ArcherTower tower = new ArcherTower(x, y);
        entities.add(tower);

        return true;
    }

    //modif
    public void changeTroopsDestination(Point2D newDestination) {
        for (Entity entity : entities) {
            if (entity instanceof Troop) {
                Troop troop = (Troop) entity;

                troop.setDestination(newDestination);
                assignMovement(troop);
            }
        }
    }

}
