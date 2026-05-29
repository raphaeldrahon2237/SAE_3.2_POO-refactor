package towerdef.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import towerdef.model.core.AStar;
import towerdef.model.core.Movement;
import towerdef.model.entity.*;
import towerdef.model.entity.tower.*;
import towerdef.model.entity.troop.Troop;
import towerdef.model.entity.troop.Wolf;
import towerdef.model.map.MapGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    //private ObservableList<Entity> entities;//on peut pas avoir 5 listes d'éléments qui se répetent
    private ObservableList<Entity> allies;
    private ObservableList<Troop> enemies;
    //private ObservableList<Troop> troops;
    private ObservableList<Tower> towers;
    private MapGrid mapGrid;
    private int score;
    private String playerName = "ayoube";
    private Base base;
    private int scoreRate;
    private int gold;
    private double difficultyTimer;
    private double spawnInterval;
    private double spawnTimer;
    private Random random;

    public Game() {
        //entities = FXCollections.observableArrayList();
        allies = FXCollections.observableArrayList();
        enemies = FXCollections.observableArrayList();
        //troops = FXCollections.observableArrayList();
        towers = FXCollections.observableArrayList();
        mapGrid = new MapGrid();
        base = new Base(16, 12, 100);
        score = ScoreManager.loadScore(playerName);
        gold = 100;
        scoreRate = 1;
        difficultyTimer = 0.0;
        spawnTimer = 0.0;
        spawnInterval = 10;
        random = new Random();
    }

    public void setInitialState() {
        spawnEnemies(5);
    }

    public void runGame() {
        attackEnemies();
        removeDeadEntities();
    }
    public void updateStates(double deltaTime) {

        difficultyTimer += deltaTime;
        spawnTimer += deltaTime;

        if (difficultyTimer >= 30.0) {
            difficultyTimer -= 30.0;
            scoreRate += 1;

            if (spawnInterval - 1 > 0.0) {
                spawnInterval -= 1;
            }
        }

        if (spawnTimer >= spawnInterval) {
            spawnTimer -= spawnInterval;
            spawnEnemies(1);
        }

        //updateTargets();
        updateMovements(deltaTime);
        updateScore(deltaTime);
        //removeDeadEntities();
    }

    public MapGrid getMapGrid() {
        return mapGrid;
    }

    /*public ObservableList<Entity> getEntities() {
        return entities;
    }*/

    public ObservableList<Entity> getAllies() {
        return allies;
    }

    public ObservableList<Troop> getEnemies() {
        return enemies;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void updateScore(double deltaTime) {
        score += deltaTime * scoreRate;
    }

    public void resetScore() {
        score = 0;
        ScoreManager.saveScore(playerName, score);
    }

    public int getGold() {
        return gold;
    }

    public void addGold(int amount) {
        gold += amount;
    }

    public void spendGold(int amount) {
        gold -= amount;
    }

    /*public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }
    */
    public void removeDeadEntities() {
        allies.removeIf(ally -> !ally.isAlive());
        enemies.removeIf(enemy -> !enemy.isAlive());
    }



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

        if (tower != null && gold >= tower.getCost()) {
            spendGold(tower.getCost());
            addTower(tower);
            return true;
        }

        return true;
    }

    public void changeEnemiesDestination(Point2D newDestination) {
        for (Troop enemy: enemies) {
            enemy.setDestination(newDestination);
            assignMovement(enemy);
        }
    }

    public void attackEnemies() {
        for (Tower tower: towers) {
            for(Troop enemy:enemies) {
                if(enemy.getX()<= (tower.getX()+tower.getRange()) && enemy.getX()>= (tower.getX()-tower.getRange()) &&
                enemy.getY()<=(tower.getY()+tower.getRange()) && enemy.getY()>= (tower.getX()-tower.getRange())){
                    System.out.println("Tower: "+tower.toString()+" attacking "+enemy.toString());
                    enemy.takeDamage(tower.getDamage());
                }
            }

        }
    }

    /*
    private void updateTargets() {
        updateTargets(allies);
        updateTargets(enemies);
    }


     */
    /*
    private void updateTargets(ObservableList<Entity> entities) {
        for (Entity entity : entities) {
            entity.setTarget(findNearestTarget(entity));
        }
    }*/

    private void updateMovements(double deltaTime) {
        for (Troop enemy : enemies) {
            if (enemy.getMovement() != null) {
                enemy.getMovement().update(deltaTime);
            }
        }
    }

    /*
    private void updateAttacks(double deltaTime) {
        for (Entity ally : allies) {
            if (ally.getTarget() != null) {
                ally.attack();
            }
        }

        for (Entity enemy : enemies) {
            if (enemy.getTarget() != null) {
                enemy.attack();
            }
        }

        //removeDeadEntities();
    }*/

    /*
    private void updateCollisions() {
        for (Entity ally : allies) {
            for (Entity enemy : enemies) {
                if (isColliding(ally, enemy)) {
                    ally.setTarget(enemy);
                    enemy.setTarget(ally);
                    System.out.println("Collision entre un alliÃ© et un ennemi !");
                }
            }
        }
    }*/

    private void spawnEnemies(int number) {
        List<Point2D> walkableEdgeTiles = getWalkableEdgeTiles();

        for (int i = 0; i < number; i++) {
            Point2D tile = walkableEdgeTiles.get(random.nextInt(walkableEdgeTiles.size()));
            Wolf wolf = new Wolf((int) tile.getX(), (int) tile.getY());
            wolf.setDestination(new Point2D(base.getX(), base.getY())); //faire uen constante de point2d base
            spawnEnemy(wolf);
        }
    }

    private void spawnAlly(Troop troop) {
        assignMovement(troop);
        addAlly(troop);
    }

    private void spawnEnemy(Troop troop) {
        assignMovement(troop);
        addEnemy(troop);
    }

    private void addAlly(Troop troop) {
        allies.add(troop);
        //troops.add(troop);
        //entities.add(troop);
    }

    private void addEnemy(Troop troop) {
        enemies.add(troop);
        //troops.add(troop);
        //entities.add(troop);
    }

    private void addTower(Tower tower) {
        towers.add(tower);
        allies.add(tower);
        //entities.add(tower);
    }

    private void assignMovement(Troop troop) {
        Point2D source = new Point2D(troop.getX(), troop.getY());
        AStar aStar = new AStar(mapGrid, source, troop.getDestination());
        List<Point2D> path = aStar.getPath();

        Movement movement = new Movement(troop, path);
        troop.setMovement(movement);
        movement.start();
    }

    /*
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
    }*/

    private double manhattanDistance(Entity first, Entity second) {
        return Math.abs(first.getX() - second.getX())
                + Math.abs(first.getY() - second.getY());
    }

    private boolean isColliding(Entity entity1, Entity entity2) {
        double dx = entity1.getX() - entity2.getX();
        double dy = entity1.getY() - entity2.getY();

        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance < 0.5;
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

}
