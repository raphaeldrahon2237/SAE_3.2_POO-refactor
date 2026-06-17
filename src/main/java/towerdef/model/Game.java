package towerdef.model;

import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import towerdef.model.entity.AttackType;
import towerdef.model.entity.Base;
import towerdef.model.entity.Entity;
import towerdef.model.entity.Projectile;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.Enemy;
import towerdef.model.entity.troop.Troop;
import towerdef.model.map.MapGrid;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    private GameEntities gameEntities;
    private Progression progression;
    private MapGrid mapGrid;
    private Base base;
    private Path path;
    private EnemySpawner enemySpawner;
    private TowerManager towerManager;
    private RangeChecker rangeChecker;
    private ProjectileManager projectileManager;
    private Combat combat;
    private String playerName = "ayoube";
    private boolean gameOver;
    private MiniQuestManager miniQuestManager;

    public Game() {
        gameEntities = new GameEntities();
        progression = new Progression(playerName);

        mapGrid = new MapGrid();
        base = new Base(16, 12, 250);
        path = new Path(mapGrid, base, gameEntities);

        rangeChecker = new RangeChecker(gameEntities, base);
        projectileManager = new ProjectileManager(gameEntities, rangeChecker, progression);
        towerManager = new TowerManager(mapGrid, gameEntities, progression);
        miniQuestManager = new MiniQuestManager();
        combat = new Combat(this, gameEntities, base, progression, miniQuestManager);
        enemySpawner = new EnemySpawner(mapGrid, base, new Random(), path, gameEntities, progression);
        gameOver = false;
    }



    public void updateStates(double deltaTime) {
        if (!gameOver) {
            progression.addSurvivalTime(deltaTime);
            progression.increaseScoreRate(enemySpawner.update(deltaTime));
            progression.updateMaxWaveReached(enemySpawner.getCurrentWave());

            combat.update(gameEntities.getEnemies(), gameEntities.getAllies(),deltaTime);

            if (!gameOver) {
                projectileManager.update(deltaTime);
                progression.updateScore(deltaTime);
                removeDeadEntities();
                updateBarrackRespawns();
            }
        }


    }

    public MapGrid getMapGrid() {
        return mapGrid;
    }

    public ObservableList<Entity> getAllies() {
        return gameEntities.getAllies();
    }

    public ObservableList<Enemy> getEnemies() {
        return gameEntities.getEnemies();
    }

    public ObservableList<Projectile> getProjectiles() {
        return gameEntities.getProjectiles();
    }

    public int getScore() {
        return progression.getScore();
    }

    public IntegerProperty scoreProperty() {
        return progression.scoreProperty();
    }

    public void addScore(int amount) {
        progression.addScore(amount);
    }

    public void updateScore(double deltaTime) {
        progression.updateScore(deltaTime);
    }

    public void resetScore() {
        progression.resetScore();
    }

    public int getBaseHp() {
        return base.getHp();
    }

    public int getBaseMaxHp() {
        return base.getMaxHp();
    }

    public int getBaseX() {
        return base.getX();
    }

    public int getBaseY() {
        return base.getY();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public GameStats getGameStats() {
        progression.updateMaxWaveReached(enemySpawner.getCurrentWave());
        return progression.getGameStats();
    }

    public int getCurrentWave() {
        return enemySpawner.getCurrentWave();
    }


    public int getGold() {
        return progression.getGold();
    }

    public IntegerProperty goldProperty() {
        return progression.goldProperty();
    }

    public IntegerProperty baseHpProperty(){
        return base.hpProperty();
    }

    public void damageBase(int damage) {
        combat.damageBase(damage);
    }

    public void removeDeadEntities() {
        combat.progressionReward(gameEntities.getEnemies());
        gameEntities.removeDeadEntities();

    }

    public boolean canPlaceTower(int x, int y) {
        return towerManager.canPlaceTower(x, y);
    }

    public boolean placeTower(int x, int y, int idTower) {
        return towerManager.placeTower(x, y, idTower);
    }

    public Tower getTowerByTypeId(int towerTypeId) {
        return towerManager.getTowerByTypeId(towerTypeId);
    }

    public Tower getTowerAt(int x, int y) {
        return towerManager.getTowerAt(x, y);
    }

    public boolean upgradeTower(Tower tower) {
        return towerManager.upgradeTower(tower);
    }

    public boolean sellTower(Tower tower) {
        return towerManager.sellTower(tower);
    }

    public void changeEnemiesDestination(Point2D newDestination) {
        for (Enemy enemy : gameEntities.getEnemies()) {
            enemy.setDestination(newDestination);
            path.assignMovement(enemy);
        }
    }

    public Enemy findEnemyInRange(double x, double y, int range) {
        return rangeChecker.findEnemyInRange(x, y, range);
    }

    public ArrayList<Enemy> findEnemiesInRange(double x, double y, int range) {
        return rangeChecker.findEnemiesInRange(x, y, range);
    }

    public Troop findAlliedTroopInRange(double x, double y, int range) {
        return rangeChecker.findAlliedTroopInRange(x, y, range);
    }

    public boolean isBaseInRange(Enemy enemy) {
        return rangeChecker.isBaseInRange(enemy);
    }

    public boolean isTowerInRange(double x, double y, int range) {
        return rangeChecker.isTowerInRange(x, y, range);
    }

    public boolean isAlliedTroopInRange(double x, double y, int range) {
        return rangeChecker.isAlliedTroopInRange(x, y, range);
    }

    public Tower findTowerInRange(double x, double y, int range) {
        return rangeChecker.findTowerInRange(x, y, range);
    }

    public Point2D computePreshotTile(double sourceX, double sourceY, Enemy target) {
        return projectileManager.computePreshotTile(sourceX, sourceY, target);
    }

    public void createProjectile(Entity source, Entity target, Point2D impactTile, AttackType projectileType) {
        projectileManager.createProjectile(source, target, impactTile, projectileType);
    }
    public void updateBarrackRespawns() {
        towerManager.updateBarrackRespawns(getCurrentWave());
    }


    public MiniQuestManager getMiniQuestManager() {
        return miniQuestManager;
    }
}
