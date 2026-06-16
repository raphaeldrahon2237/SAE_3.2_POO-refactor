package towerdef.model;

import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import towerdef.model.entity.*;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.tower.TowerFactory;
import towerdef.model.entity.troop.Enemy;
import towerdef.model.map.MapGrid;
import towerdef.model.quest.MiniQuest;
import towerdef.model.quest.MiniQuestManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private static final double PROJECTILE_SPEED = 8.0;

    private GameEntities gameEntities;
    private Economy economy;
    private MapGrid mapGrid;
    private Base base;
    private Path path;
    private EnemySpawner enemySpawner;
    private TowerPlacement towerPlacement;
    private GameStats gameStats;
    private String playerName = "ayoube";
    private boolean gameOver;
    private MiniQuestManager miniQuestManager;
    public Game() {
        gameEntities = new GameEntities();
        economy = new Economy(playerName);
        gameStats = new GameStats();

        mapGrid = new MapGrid();
        base = new Base(16, 12, 250);
        path = new Path(mapGrid, base, gameEntities);
        enemySpawner = new EnemySpawner(mapGrid, base, new Random(), path, gameEntities, gameStats);
        towerPlacement = new TowerPlacement(mapGrid, gameEntities, economy);
        gameOver = false;
        miniQuestManager = new MiniQuestManager();


    }

    public void runGame() {
        removeDeadEntities();
    }

    public void updateStates(double deltaTime) {
        if (!gameOver) {
            gameStats.addSurvivalTime(deltaTime);
            economy.increaseScoreRate(enemySpawner.update(deltaTime));
            gameStats.updateMaxWaveReached(enemySpawner.getCurrentWave());

            updateAlliesAttackTimers(deltaTime);
            updateEnemiesAttackTimers(deltaTime);

            alliesAttackEnemies();
            enemiesAttack();

            if (!gameOver) {
                updateMovements(deltaTime);
                updateProjectiles(deltaTime);
                updateScore(deltaTime);

                removeDeadEntities();
            }
        }
    }

    private void updateEnemiesAttackTimers(double deltaTime) {
        for(Enemy enemy:gameEntities.getEnemies()){
            enemy.updateAttackTimer(deltaTime);
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
        return economy.getScore();
    }

    public IntegerProperty scoreProperty() {
        return economy.scoreProperty();
    }

    public void addScore(int amount) {
        economy.addScore(amount);
    }

    public void updateScore(double deltaTime) {
        economy.updateScore(deltaTime);
    }

    public void resetScore() {
        economy.resetScore();
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

    public GameStats getGameStats() {
        gameStats.updateMaxWaveReached(enemySpawner.getCurrentWave());
        return gameStats;
    }

    public int getCurrentWave() {
        return enemySpawner.getCurrentWave();
    }

    public int getEnemiesRemainingInWave() {
        return enemySpawner.getEnemiesRemainingInWave();
    }

    public int getGold() {
        return economy.getGold();
    }

    public IntegerProperty goldProperty() {
        return economy.goldProperty();
    }

    public IntegerProperty baseHpProperty(){
        return base.hpProperty();
    }

    public void damageBase(int damage) {
        if (!gameOver) {
            int baseHpBeforeAttack = base.getHp();
            base.takeDamage(damage);
            gameStats.addDamageTakenByBase(baseHpBeforeAttack - base.getHp());

            if (base.isDestroyed()) {
                gameOver = true;
            }
        }
    }

    public void addGold(int amount) {
        economy.addGold(amount);
    }

    public void spendGold(int amount) {
        economy.spendGold(amount);
    }

    public void removeDeadEntities() {

        for (Enemy enemy : new ArrayList<>(gameEntities.getEnemies())) {
            if (!enemy.isAlive()) {
                economy.addGold(enemy.getReward());
                economy.addScore(10);
                gameStats.enemyKilled();
            }
        }

        gameEntities.removeDeadEntities();
    }

    public boolean canPlaceTower(int x, int y) {
        return towerPlacement.canPlaceTower(x, y);
    }


    public boolean placeTower(int x, int y, int idTower) {
        boolean placed = towerPlacement.placeTower(x, y, idTower);

        if (placed) {
            gameStats.towerPlaced();
        }

        return placed;
    }

    public Tower getTowerByTypeId(int towerTypeId) {
        EntityType type = EntityType.fromId(towerTypeId);
        if (type != null) {
            return TowerFactory.createTower(type, 0, 0);
        }
        return null;
    }

    public Tower getTowerAt(int x, int y) {
        Tower selectedTower = null;

        for (Tower tower : gameEntities.getTowers()) {
            if (tower.isAlive() && (int) tower.getX() == x && (int) tower.getY() == y) {
                selectedTower = tower;
            }
        }

        return selectedTower;
    }

    public boolean upgradeTower(Tower tower) {
        boolean upgraded = false;

        if (tower != null && tower.isAlive() && tower.levelProperty().get() < 5 && economy.getGold() >= tower.getUpgradeCost()) {
            economy.spendGold(tower.getUpgradeCost());
            tower.upgrade();
            upgraded = true;
        }

        return upgraded;
    }

    public boolean sellTower(Tower tower) {
        boolean sold = false;

        if (tower != null && tower.isAlive()) {
            economy.addGold(tower.getSellValue());
            gameEntities.removeTower(tower);
            sold = true;
        }

        return sold;
    }

    public void changeEnemiesDestination(Point2D newDestination) {
        for (Enemy enemy : gameEntities.getEnemies()) {
            enemy.setDestination(newDestination);
            path.assignMovement(enemy);
        }
    }

    /*
    public void applyAreaDamage(Projectile projectile) {
        Point2D impactTile = projectile.getImpactTile();
        double impactX = impactTile.getX();
        double impactY = impactTile.getY();

        for (Enemy enemy : gameEntities.getEnemies()) {
            if (enemy.isAlive()) {
                double dx = enemy.getX() - impactX;
                double dy = enemy.getY() - impactY;
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance <= projectile.getAoe()) {
                    enemy.takeDamage(projectile.getDamage());
                }
            }
        }
    }*/

    private void updateAlliesAttackTimers(double deltaTime) {
        for (Entity ally : gameEntities.getAllies()) {
            ally.updateAttackTimer(deltaTime);
        }
    }


   /* public void applyEarthquake (Earthquake earthquakeTower) {
        earthquakeTower.incrementAttackCounter();
        ArrayList<Enemy> enemiesInRange= new ArrayList<>();
        for (Enemy enemy : gameEntities.getEnemies()) {
            if (enemy.isAlive()) {
                double dx = enemy.getX() - earthquakeTower.getX();
                double dy = enemy.getY() - earthquakeTower.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance <= earthquakeTower.getRange()) {
                    enemy.takeDamage(earthquakeTower.getDamage());
                    enemy.setMooveSpeed(earthquakeTower.getSlowSpeed());
                }
                else {
                    enemy.resetMooveSpeed();
                }
            }
        }

    }

    */

    public void alliesAttackEnemies() {
        for (Entity ally : gameEntities.getAllies()) {
            ally.attack(this);
            }
        }




    public Enemy findEnemyInRange(double x, double y, int range) {
        return Utils.findClosestEnemyInRange(gameEntities.getEnemies(), x, y, range);
    }

    public ArrayList<Enemy> findEnemiesInRange(double x, double y, int range) {
        return Utils.findEnemiesInRange(gameEntities.getEnemies(), x, y, range);
    }

    private void updateMovements(double deltaTime) {
        for (Enemy enemy : gameEntities.getEnemies()) {
            if (!enemy.isInCombat(this)) {
                enemy.updateMovement(deltaTime);
            }
        }
    }
    private void enemiesAttack() {
        for (Enemy enemy : gameEntities.getEnemies()) {
            enemy.resetAttacking();
            enemy.attack(this);
        }
    }

    public boolean isBaseInRange(Enemy enemy) {
        return Utils.isInRange(base.getX(), base.getY(), enemy.getX(), enemy.getY(), enemy.getRange());
    }

    public boolean isTowerInRange(double x, double y, int range) {
        return findTowerInRange(x ,y ,range) != null;
    }

    public Tower findTowerInRange(double x, double y, int range) {
        return Utils.findClosestTowerInRange(gameEntities.getTowers(), x, y, range);
    }

    private void updateProjectiles(double deltaTime) {
        for (Projectile projectile : new ArrayList<>(gameEntities.getProjectiles())) {

            projectile.act(deltaTime, this);

            if(projectile.isFinished()) gameEntities.removeProjectile(projectile);
        }
    }

    public Point2D computePreshotTile(double sourceX, double sourceY, Enemy target) {
        return Utils.computePreshotTile(sourceX, sourceY, target, PROJECTILE_SPEED);
    }

    public void createProjectile(Entity source, Entity target, Point2D impactTile, AttackType projectileType) {
        List<Point2D> trajectory = Utils.computeLineTiles(
                (int) source.getX(),
                (int) source.getY(),
                (int) impactTile.getX(),
                (int) impactTile.getY()
        );

        Projectile projectile = new Projectile( trajectory, target, source, PROJECTILE_SPEED, projectileType);

        gameEntities.addProjectile(projectile);
    }
    public String getMiniQuestDisplayText() {
        return miniQuestManager.getDisplayText();
    }

    public MiniQuestManager getMiniQuestManager() {
        return miniQuestManager;
    }

    private void giveMiniQuestRewardIfCompleted(Enemy enemy) {
        MiniQuest completedQuest = miniQuestManager.updateOnEnemyKilled(enemy, enemy.getLastDamagingTower());

        if (completedQuest != null) {
            economy.addGold(completedQuest.getRewardGold());
            economy.addScore(completedQuest.getRewardScore());
            System.out.println("Mini-quete terminee : " + completedQuest.getTitle());
        }
    }
    private void applyDamageFromSource(Enemy enemy, Entity source, int damage) {
        if (source instanceof Tower tower) {
            enemy.setLastDamagingTower(tower);
        }

        enemy.takeDamage(damage);
    }
}
