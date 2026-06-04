package towerdef.model;

import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import towerdef.model.entity.*;
import towerdef.model.entity.tower.Shockwave;
import towerdef.model.entity.troop.Enemy;
import towerdef.model.map.MapGrid;

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
    private String playerName = "ayoube";

    public Game() {
        gameEntities = new GameEntities();
        economy = new Economy(playerName);

        mapGrid = new MapGrid();
        base = new Base(16, 12, 100);
        path = new Path(mapGrid, base);
        enemySpawner = new EnemySpawner(mapGrid, base, new Random(), path, gameEntities);
        towerPlacement = new TowerPlacement(mapGrid, gameEntities, economy);
    }

    public void setInitialState() {
        enemySpawner.spawnEnemies(5);
    }

    public void runGame() {
        removeDeadEntities();
    }

    public void updateStates(double deltaTime) {
        economy.increaseScoreRate(enemySpawner.update(deltaTime));

        updateAlliesAttackTimers(deltaTime);
        updateEnemiesAttackTimers(deltaTime);

        alliesAttackEnemies();
        enemiesAttackAllies();

        updateMovements(deltaTime);
        updateProjectiles(deltaTime);
        updateScore(deltaTime);

        removeDeadEntities();
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

    public int getGold() {
        return economy.getGold();
    }

    public IntegerProperty goldProperty() {
        return economy.goldProperty();
    }

    public void addGold(int amount) {
        economy.addGold(amount);
    }

    public void spendGold(int amount) {
        economy.spendGold(amount);
    }

    public void removeDeadEntities() {
        gameEntities.removeDeadEntities();
    }

    public boolean canPlaceTower(int x, int y) {
        return towerPlacementService.canPlaceTower(x, y);
    }

    public boolean placeTower(int x, int y, int idTower) {
        return towerPlacementService.placeTower(x, y, idTower);
    }

    public void changeEnemiesDestination(Point2D newDestination) {
        for (Enemy enemy : gameEntities.getEnemies()) {
            enemy.setDestination(newDestination);
            path.assignMovement(enemy);
        }
    }

    private void updateAlliesAttackTimers(double deltaTime) {
        for (Entity ally : gameEntities.getAllies()) {
            ally.updateAttackTimer(deltaTime);
        }
    }

    public void applyShockwave (Shockwave shockWaveTower) {

        for (Enemy enemy : gameEntities.getEnemies()) {
            if (enemy.isAlive()) {
                double dx = enemy.getX() - shockWaveTower.getX();
                double dy = enemy.getY() - shockWaveTower.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance <= shockWaveTower.getRange()) {
                    enemy.takeDamage(shockWaveTower.getDamage());
                    enemy.setMooveSpeed(shockWaveTower.getSlowSpeed());
                }
            }
        }
    }

    public void alliesAttackEnemies() {
        for (Entity ally : gameEntities.getAllies()) {
            if (ally.canAttack() && ally.getDamage() >= 0) {
                switch (ally.getAttackType()) {
                    case SHOCKWAVE:
                        applyShockwave((Shockwave) ally);
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

    private void enemiesAttackAllies() {
        for (Enemy enemy : gameEntities.getEnemies()) {
            if (enemy.canAttack() && enemy.getDamage() > 0) {
                Entity target = findAllyInRange(enemy);

                if (target != null) {
                    System.out.println("Enemy: " + enemy + " attacking " + target);

                    shootProjectile(enemy, target);
                    enemy.resetAttackTimer();
                }
            }
        }
    }

    public void applyShockwave (Entity shockWaveTower) {

        for (Enemy enemy : gameEntities.getEnemies()) {
            if (enemy.isAlive()) {
                double dx = enemy.getX() - shockWaveTower.getX();
                double dy = enemy.getY() - shockWaveTower.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance <= shockWaveTower.getRange()) {
                    enemy.takeDamage(shockWaveTower.getDamage());
                    enemy.setMooveSpeed(1);
                }
            }
        }

    }

    private Entity findAllyInRange(Enemy enemy) {
        Entity closestAlly = null;
        double closestDistance = Double.MAX_VALUE;

        for (Entity ally : gameEntities.getAllies()) {
            boolean isRecruit = !gameEntities.getTowers().contains(ally);
            boolean canBeAttacked = ally.isAlive();

            if (isRecruit && canBeAttacked) {
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
            if (!isEnemyInCombat(enemy)) {
                enemy.updateMovement(deltaTime);
            }
        }
    }

    private boolean isEnemyInCombat(Enemy enemy) {
        for (Entity ally : gameEntities.getAllies()) {
            boolean isRecruitOrTroop = !gameEntities.getTowers().contains(ally);
            boolean canFight = ally.isAlive() && ally.getDamage() > 0;

            if (isRecruitOrTroop && canFight) {
                double dx = enemy.getX() - ally.getX();
                double dy = enemy.getY() - ally.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance <= ally.getRange()) {
                    return true;
                }
            }
        }

        return false;
    }

    private void updateProjectiles(double deltaTime) {
        for (Projectile projectile : new ArrayList<>(gameEntities.getProjectiles())) {
            projectile.updatePosition(deltaTime);

            if (projectile.isFinished()) {
                if (projectile.getTarget() != null) {
                    if (projectile.getAoe() != 0) {
                        applyAreaDamage(projectile);
                    } else if (projectile.getTarget().isAlive()) {
                        projectile.getTarget().takeDamage(projectile.getDamage());
                    }
                }
                gameEntities.removeProjectile(projectile);
            }
        }
    }

    public void applyAreaDamage(Projectile projectile) {
        double impactX = projectile.getTarget().getX();
        double impactY = projectile.getTarget().getY();

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
    }

    private void shootProjectile(Entity source, Entity target) {
        List<Point2D> trajectory = computeLineTiles(
                (int) source.getX(),
                (int) source.getY(),
                (int) target.getX(),
                (int) target.getY()
        );

        Projectile projectile = new Projectile(
                trajectory,
                target,
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
