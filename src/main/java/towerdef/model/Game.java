package towerdef.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import towerdef.model.core.AStar;
import towerdef.model.core.Movement;
import towerdef.model.entity.*;
import towerdef.model.entity.tower.*;
import towerdef.model.entity.troop.Recruit;
import towerdef.model.entity.troop.Troop;
import towerdef.model.entity.troop.Wolf;
import towerdef.model.map.MapGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private ObservableList<Entity> entities;
    private ObservableList<Entity> allies;
    private ObservableList<Entity> enemies;
    private ObservableList<Troop> troops;
    private ObservableList<Tower> towers;
    private MapGrid mapGrid;
    private Random random;

    public Game() {
        entities = FXCollections.observableArrayList();
        allies = FXCollections.observableArrayList();
        enemies = FXCollections.observableArrayList();
        troops = FXCollections.observableArrayList();
        towers = FXCollections.observableArrayList();
        mapGrid = new MapGrid();
        random = new Random();
        Base base = new Base(12, 16, 100);
    }

    public MapGrid getMapGrid() {
        return mapGrid;
    }

    public void setInitialState() {
        Recruit recruit = new Recruit(0, 2);
        recruit.setDestination(new Point2D(16, 12));
        spawnAlly(recruit);
        Wolf wolf = new Wolf(17, 32);
        spawnEnemy(wolf);
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

    private void addTower(Tower tower) {
        towers.add(tower);
        allies.add(tower);
        entities.add(tower);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public ObservableList<Entity> getEntities() {
        return entities;
    }

    private void updateMovements(double deltaTime) {
        for (Troop troop : troops) {
            if (troop.getMovement() != null) {
                troop.getMovement().update(deltaTime);
            }
        }
    }

    public void spawnTroop(Troop troop) {
        spawnAlly(troop);
    }

    private void spawnAlly(Troop troop) {
        assignMovement(troop);
        addAlly(troop);
    }

    private void spawnEnemy(Troop troop) {
        addEnemy(troop);
    }


    private List<Point2D> getWalkableEdgeTiles() {
        List<Point2D> spawnTiles = new ArrayList<>();

        for (int x = 0; x < mapGrid.getWidth(); x++) {
            addSpawnTileIfWalkable(spawnTiles, x, 0);
            addSpawnTileIfWalkable(spawnTiles, x, mapGrid.getHeight() - 1);
        }

        for (int y = 1; y < mapGrid.getHeight() - 1; y++) {
            addSpawnTileIfWalkable(spawnTiles, 0, y);
            addSpawnTileIfWalkable(spawnTiles, mapGrid.getWidth() - 1, y);
        }

        return spawnTiles;
    }

    private void addSpawnTileIfWalkable(List<Point2D> spawnTiles, int x, int y) {
        if (mapGrid.isWalkable(x, y)) {
            spawnTiles.add(new Point2D(x, y));
        }
    }


    public void updateStates(double deltaTime) {
        updateTargets();
        updateAttacks(deltaTime);
        updateMovements(deltaTime);
    }

    private void updateTargets() {
        updateTargets(allies);
        updateTargets(enemies);
    }

    private void updateTargets(ObservableList<Entity> entities) {
        for (Entity entity : entities) {
            entity.setTarget(findNearestTarget(entity));
        }
    }

    private void updateAttacks(double deltaTime) {
        for (Entity ally : allies) {
            //TODO
        }

        for (Entity enemy : enemies) {
            //TODO
        }
    }

    private Entity findNearestTarget(Entity entity) {
        if (enemies.contains(entity)) {
            return fixNearestTargetInList(entity, allies);
        }

        if (allies.contains(entity)) {
            return fixNearestTargetInList(entity, enemies);
        }

        return null;
    }

    private Entity fixNearestTargetInList(Entity entity, ObservableList<Entity> targets) {
        Entity nearestTarget = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Entity target : targets) {
            double distance = manhattanDistance(entity, target);

            if (distance <= entity.getRange() && distance < nearestDistance) {
                nearestDistance = distance;
                nearestTarget = target;
            }
        }

        return nearestTarget;
    }

    private double manhattanDistance(Entity first, Entity second) {
        return Math.abs(first.getX() - second.getX())
                + Math.abs(first.getY() - second.getY());
    }

    //modif
    public boolean canPlaceTower(int x, int y){
        if(!mapGrid.isInside(x,y)) {
            return false;
        }
        if (mapGrid.isWalkable(x, y)){
            return false;
        }
        for (Tower tower : towers){
            if(tower.getX() == x && tower.getY() == y){
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
        addTower(tower);

        return true;
    }

    public boolean placeTower(int x,int y, int idTower){
        if(!canPlaceTower(x,y)){
            return false;
        }
        EntityType entityType = EntityType.fromId(idTower);
        if (entityType == null) {
            return false;
        }

        Tower tower = null;

        switch (entityType){
            case ARCHERTOWER ->{
                 tower = new ArcherTower(x,y);
            }
            case MAGE ->{
                 tower = new Mage(x,y);
            }
            case BARRACK ->  {
                 tower = new Barrack(x,y);
            }
            case ARTILLERY -> {
                 tower = new Artillery(x,y);
            }
            case SHOCKWAVE -> {
                 tower = new Shockwave(x,y);
            }
        }

        if (tower != null) {
            addTower(tower);
            return true;
        }

        return true;
    }

    private void addAlly(Troop troop) {
        allies.add(troop);
        troops.add(troop);
        entities.add(troop);
    }

    private void addEnemy(Troop troop) {
        enemies.add(troop);
        troops.add(troop);
        entities.add(troop);
    }

    public void changeTroopsDestination(Point2D newDestination) {
        for (Troop troop : troops) {
            troop.setDestination(newDestination);
            assignMovement(troop);
        }
    }

}
