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

    private static final double PROJECTILE_SPEED = 8.0;

    private ObservableList<Entity> allies;
    private ObservableList<Troop> enemies;
    private ObservableList<Tower> towers;
    private ObservableList<Projectile> projectiles;

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
        allies = FXCollections.observableArrayList();
        enemies = FXCollections.observableArrayList();
        towers = FXCollections.observableArrayList();
        projectiles = FXCollections.observableArrayList();

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

        updateTowerAttackTimers(deltaTime);
        attackEnemies();

        updateMovements(deltaTime);
        updateProjectiles(deltaTime);
        updateScore(deltaTime);

        removeDeadEntities();
    }

    public MapGrid getMapGrid() {
        return mapGrid;
    }

    public ObservableList<Entity> getAllies() {
        return allies;
    }

    public ObservableList<Troop> getEnemies() {
        return enemies;
    }

    public ObservableList<Projectile> getProjectiles() {
        return projectiles;
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

    public void removeDeadEntities() {
        allies.removeIf(ally -> !ally.isAlive());
        enemies.removeIf(enemy -> !enemy.isAlive());
    }

    public boolean canPlaceTower(int x, int y) {
        if (!mapGrid.isInside(x, y)) {
            return false;
        }

        if (mapGrid.isWalkable(x, y)) {
            return false;
        }

        for (Tower tower : towers) {
            if (tower.getX() == x && tower.getY() == y) {
                return false;
            }
        }

        return true;
    }

    public boolean placeTower(int x, int y, int idTower) {
        if (!canPlaceTower(x, y)) {
            return false;
        }

        EntityType entityType = EntityType.fromId(idTower);

        if (entityType == null) {
            return false;
        }

        Tower tower = null;

        switch (entityType) {
            case ARCHERTOWER -> {
                tower = new ArcherTower(x, y);
            }

            case MAGE -> {
                tower = new Mage(x, y);
            }

            case BARRACK -> {
                tower = new Barrack(x, y);

                Troop recruit = createRecruitNextToBarrack(x, y);

                if (recruit != null) {
                    spawnAlly(recruit);
                }
            }

            case ARTILLERY -> {
                tower = new Artillery(x, y);
            }

            case SHOCKWAVE -> {
                tower = new Shockwave(x, y);
            }
        }

        if (tower != null && gold >= tower.getCost()) {
            spendGold(tower.getCost());
            addTower(tower);
            return true;
        }

        return false;
    }

    public void changeEnemiesDestination(Point2D newDestination) {
        for (Troop enemy : enemies) {
            enemy.setDestination(newDestination);
            assignMovement(enemy);
        }
    }

    private void updateTowerAttackTimers(double deltaTime) {
        for (Tower tower : towers) {
            tower.updateAttackTimer(deltaTime);
        }
    }

    public void attackEnemies() {
        for (Tower tower : towers) {
            if (!tower.canAttack()) {
                continue;
            }

            if (tower.getDamage() <= 0) {
                continue;
            }

            Troop target = findEnemyInRange(tower);

            if (target != null) {
                System.out.println("Tower: " + tower + " attacking " + target);

                shootProjectile(tower, target);
                tower.resetAttackTimer();
            }
        }
    }

    private Troop findEnemyInRange(Tower tower) {
        Troop closestEnemy = null;
        double closestDistance = Double.MAX_VALUE;

        for (Troop enemy : enemies) {
            double dx = enemy.getX() - tower.getX();
            double dy = enemy.getY() - tower.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance <= tower.getRange() && distance < closestDistance) {
                closestEnemy = enemy;
                closestDistance = distance;
            }
        }

        return closestEnemy;
    }

    private void updateMovements(double deltaTime) {
        for (Troop enemy : enemies) {
            if (enemy.getMovement() != null) {
                enemy.getMovement().update(deltaTime);
            }
        }
    }

    private void updateProjectiles(double deltaTime) {
        for (Projectile projectile : new ArrayList<>(projectiles)) {
            projectile.updatePosition(deltaTime);

            if (projectile.isFinished()) {
                if (projectile.getTarget() != null && projectile.getTarget().isAlive()) {
                    projectile.getTarget().takeDamage(projectile.getDamage());
                }

                projectiles.remove(projectile);
            }
        }
    }

    private void spawnEnemies(int number) {
        List<Point2D> walkableEdgeTiles = getWalkableEdgeTiles();

        for (int i = 0; i < number; i++) {
            Point2D tile = walkableEdgeTiles.get(random.nextInt(walkableEdgeTiles.size()));

            Wolf wolf = new Wolf((int) tile.getX(), (int) tile.getY());
            wolf.setDestination(new Point2D(base.getX(), base.getY()));

            spawnEnemy(wolf);
        }
    }

    private void spawnAlly(Troop troop) {
        addAlly(troop);
    }

    private Troop createRecruitNextToBarrack(int x, int y) {
        int[][] directions = {
                {0, -1},
                {0, 1},
                {-1, 0},
                {1, 0}
        };

        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            if (mapGrid.isInside(newX, newY) && mapGrid.isWalkable(newX, newY)) {
                return new Recruit(newX, newY);
            }
        }

        return null;
    }

    private void spawnEnemy(Troop troop) {
        assignMovement(troop);
        addEnemy(troop);
    }

    private void addAlly(Troop troop) {
        allies.add(troop);
    }

    private void addEnemy(Troop troop) {
        enemies.add(troop);
    }

    private void addTower(Tower tower) {
        towers.add(tower);
        allies.add(tower);
    }

    private void assignMovement(Troop troop) {
        Point2D source = new Point2D(troop.getX(), troop.getY());

        AStar aStar = new AStar(mapGrid, source, troop.getDestination());
        List<Point2D> path = aStar.getPath();

        Movement movement = new Movement(troop, path);
        troop.setMovement(movement);
        movement.start();
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
                PROJECTILE_SPEED
        );

        projectiles.add(projectile);
    }

    private boolean isColliding(Entity entity1, Entity entity2) {
        double dx = entity1.getX() - entity2.getX();
        double dy = entity1.getY() - entity2.getY();

        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance < 0.5;
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