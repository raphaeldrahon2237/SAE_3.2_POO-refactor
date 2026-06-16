package towerdef.model;

import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import towerdef.model.entity.*;
import towerdef.model.entity.tower.Earthquake;
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
        miniQuestManager = new MiniQuestManager();

        mapGrid = new MapGrid();
        base = new Base(16, 12, 250);
        path = new Path(mapGrid, base, gameEntities);
        enemySpawner = new EnemySpawner(mapGrid, base, new Random(), path, gameEntities, gameStats);
        towerPlacement = new TowerPlacement(mapGrid, gameEntities, economy);
        gameOver = false;
    }

//    public void runGame() {
//        removeDeadEntities();
//    }

    public void updateStates(double deltaTime) {
        if (!gameOver) {
            gameStats.addSurvivalTime(deltaTime);
            economy.increaseScoreRate(enemySpawner.update(deltaTime));
            gameStats.updateMaxWaveReached(enemySpawner.getCurrentWave());
            removeDeadEntities();
            updateAlliesAttackTimers(deltaTime);
            updateEnemiesAttackTimers(deltaTime);

            alliesAttackEnemies();
            enemiesAttackAllies();
            enemiesAttackBase();

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
                giveMiniQuestRewardIfCompleted(enemy);
            }
        }

        gameEntities.removeDeadEntities();
    }
    private void applyDamageFromSource(Enemy enemy, Entity source, int damage) {
        if (source instanceof Tower tower) {
            enemy.setLastDamagingTower(tower);
        } else {
            enemy.setLastDamagingTower(null);
        }

        enemy.takeDamage(damage);
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
                    //enemy.takeDamage(projectile.getDamage());
                    applyDamageFromSource(enemy, projectile.getSource(), projectile.getDamage());
                }
            }
        }
    }

    private void updateAlliesAttackTimers(double deltaTime) {
        for (Entity ally : gameEntities.getAllies()) {
            ally.updateAttackTimer(deltaTime);
        }
    }

    public void applyEarthquake (Earthquake earthquakeTower) {
        earthquakeTower.incrementAttackCounter();
        for (Enemy enemy : gameEntities.getEnemies()) {
            if (enemy.isAlive()) {
                double dx = enemy.getX() - earthquakeTower.getX();
                double dy = enemy.getY() - earthquakeTower.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance <= earthquakeTower.getRange()) {
                    //enemy.takeDamage(earthquakeTower.getDamage());
                    applyDamageFromSource(enemy, earthquakeTower, earthquakeTower.getDamage());
                    enemy.setMooveSpeed(earthquakeTower.getSlowSpeed());
                }
                else {
                    enemy.resetMooveSpeed();
                }
            }
        }

    }

    public void alliesAttackEnemies() {
        for (Entity ally : gameEntities.getAllies()) {
            if (ally.canAttack() && ally.getDamage() >= 0) {
                switch (ally.getAttackType()) {
                    case EARTHQUAKE:
                        applyEarthquake((Earthquake) ally);
                        ally.resetAttackTimer();
                        break;

                    case NORMAL:
                    case AOE:
                        Enemy target = findEnemyInRange(ally);
                        if (target != null) {
                            shootProjectile(ally, target);
                            ally.resetAttackTimer();
                        }
                        break;
                }
            }
        }
    }

    private void enemiesAttackBase() {
        for (Enemy enemy : gameEntities.getEnemies()) {
            if (!gameOver && enemy.canAttack() && enemy.getDamage() > 0 && isBaseInRange(enemy)) {
                int baseHpBeforeAttack = base.getHp();
                base.takeDamage(enemy.getDamage());
                gameStats.addDamageTakenByBase(baseHpBeforeAttack - base.getHp());
                enemy.resetAttackTimer();

                if (base.isDestroyed()) {
                    gameOver = true;
                }
            }
        }
    }

    private Enemy findEnemyInRange(Entity source) {
        Enemy closestEnemy = null;
        double closestDistance = Double.MAX_VALUE;

        for (Enemy enemy : gameEntities.getEnemies()) {
            double dx = enemy.getX() - source.getX();
            double dy = enemy.getY() - source.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance <= source.getRange() && distance < closestDistance) {
                closestEnemy = enemy;
                closestDistance = distance;
            }
        }

        return closestEnemy;
    }
    private void updateMovements(double deltaTime) {
        for (Enemy enemy : gameEntities.getEnemies()) {
            if (!isEnemyInCombat(enemy) && !isBaseInRange(enemy)) {
                enemy.updateMovement(deltaTime);
            }
        }
    }

    private boolean isEnemyInCombat(Enemy enemy) {
        return findAllyInRange(enemy) != null;
    }

    private void enemiesAttackAllies() {
        for (Enemy enemy : gameEntities.getEnemies()) {
            if (enemy.canAttack() && enemy.getDamage() > 0) {
                Entity target = findAllyInRange(enemy);

                if (target != null) {
                    shootProjectile(enemy, target);
                    enemy.resetAttackTimer();
                }
            }
        }
    }

    private Entity findAllyInRange(Enemy enemy) {
        Entity closestAlly = null;
        double closestDistance = Double.MAX_VALUE;

        for (Entity ally : gameEntities.getAllies()) {
            boolean canBeAttacked = ally.isAlive();

            if (canBeAttacked) {
                double dx = ally.getX() - enemy.getX();
                double dy = ally.getY() - enemy.getY();

                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance <= enemy.getRange() && distance < closestDistance) {
                    closestAlly = ally;
                    closestDistance = distance;
                }
            }
        }

        return closestAlly;
    }

    private boolean isBaseInRange(Enemy enemy) {
        double dx = base.getX() - enemy.getX();
        double dy = base.getY() - enemy.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance <= enemy.getRange();
    }

//    private void updateProjectiles(double deltaTime) {
//        for (Projectile projectile : new ArrayList<>(gameEntities.getProjectiles())) {
//            projectile.updatePosition(deltaTime);
//
//            if (projectile.isFinished()) {
//                if (projectile.getTarget() != null) {
//                    if (projectile.getAoe() != 0) {
//                        applyAreaDamage(projectile);
//                    } else if (projectile.getTarget().isAlive()) {
//                        projectile.getTarget().takeDamage(projectile.getDamage());
//                    }
//                }
//                gameEntities.removeProjectile(projectile);
//            }
//        }
//    }
private void updateProjectiles(double deltaTime) {
    for (Projectile projectile : new ArrayList<>(gameEntities.getProjectiles())) {
        projectile.updatePosition(deltaTime);

        if (projectile.isFinished()) {
            if (projectile.getTarget() != null) {
                if (projectile.getAoe() != 0) {
                    applyAreaDamage(projectile);
                } else if (projectile.getTarget().isAlive()) {
                    if (projectile.getTarget() instanceof Enemy enemyTarget) {
                        applyDamageFromSource(enemyTarget, projectile.getSource(), projectile.getDamage());
                    } else {
                        projectile.getTarget().takeDamage(projectile.getDamage());
                    }
                }
            }
            gameEntities.removeProjectile(projectile);
        }
    }
}

    private void shootProjectile(Entity source, Entity target) {
        Point2D impactTile = computePreshotTile(source, target);
        source.incrementAttackCounter();
        gameStats.projectileFired();
        createProjectile(source, target, impactTile);
    }

    private Point2D computePreshotTile(Entity source, Entity target) {
        Point2D impactTile = toTilePosition(target.getX(), target.getY());

        if (target instanceof Enemy enemy) {
            double projectileTravelTime = computeProjectileTravelTime(source, impactTile);
            Point2D predictedPosition = enemy.predictPositionAfter(projectileTravelTime);
            impactTile = toTilePosition(predictedPosition.getX(), predictedPosition.getY());
        }

        return impactTile;
    }

    private double computeProjectileTravelTime(Entity source, Point2D impactTile) {
        List<Point2D> trajectory = computeLineTiles(
                (int) source.getX(),
                (int) source.getY(),
                (int) impactTile.getX(),
                (int) impactTile.getY()
        );

        return (trajectory.size() - 1) / PROJECTILE_SPEED;
    }

    private Point2D toTilePosition(double x, double y) {
        return new Point2D(Math.round(x), Math.round(y));
    }

    private void createProjectile(Entity source, Entity target, Point2D impactTile) {
        List<Point2D> trajectory = computeLineTiles(
                (int) source.getX(),
                (int) source.getY(),
                (int) impactTile.getX(),
                (int) impactTile.getY()
        );

        Projectile projectile = new Projectile(
                trajectory,
                target,
                source,
                source.getDamage(),
                PROJECTILE_SPEED,
                source.getZone()
        );

        gameEntities.addProjectile(projectile);
    }

    private List<Point2D> computeLineTiles(int startX, int startY, int targetX, int targetY) {
        List<Point2D> trajectory = new ArrayList<>();

        int dx = targetX - startX;
        int dy = targetY - startY;

        if (dx == 0 && dy == 0) {
            trajectory.add(new Point2D(startX, startY));
            return trajectory;
        }

        int absDx = Math.abs(dx);
        int absDy = Math.abs(dy);

        int stepX = Integer.compare(dx, 0);
        int stepY = Integer.compare(dy, 0);

        int x = startX;
        int y = startY;
        int error = 0;

        trajectory.add(new Point2D(x, y));

        if (absDy >= absDx) {
            while (x != targetX || y != targetY) {
                y += stepY;
                error += absDx;

                if (error >= absDy) {
                    x += stepX;
                    error -= absDy;
                }

                trajectory.add(new Point2D(x, y));
            }
        } else {
            while (x != targetX || y != targetY) {
                x += stepX;
                error += absDy;

                if (error >= absDx) {
                    y += stepY;
                    error -= absDx;
                }

                trajectory.add(new Point2D(x, y));
            }
        }

        return trajectory;
    }

    private boolean isColliding(Entity entity1, Entity entity2) {
        double dx = entity1.getX() - entity2.getX();
        double dy = entity1.getY() - entity2.getY();

        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance < 0.5;
    }
}
