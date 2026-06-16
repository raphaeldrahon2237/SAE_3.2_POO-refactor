package towerdef.model;

import javafx.geometry.Point2D;
import towerdef.model.entity.Base;
import towerdef.model.entity.troop.*;
import towerdef.model.map.MapGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemySpawner {

    private static final int FIRST_WAVE_SIZE = 6;
    private static final int ENEMIES_ADDED_PER_WAVE = 2;
    private static final int BOSS_WAVE_INTERVAL = 10;
    private static final double INITIAL_TIME_BETWEEN_WAVES = 9.0;
    private static final double MIN_TIME_BETWEEN_WAVES = 5.0;
    private static final double WAVE_DELAY_DECREASE_PER_WAVE = 0.2;
    private static final double TIME_BETWEEN_ENEMIES = 0.9;
    private static final double MIN_TIME_BETWEEN_ENEMIES = 0.4;
    private static final double ENEMY_DELAY_DECREASE_PER_WAVE = 0.03;

    private MapGrid mapGrid;
    private Base base;
    private Random random;
    private Path path;
    private GameEntities gameEntities;
    private Progression progression;
    private int currentWave;
    private int enemiesRemainingInWave;
    private double waveTimer;
    private double enemySpawnTimer;
    private boolean waveInProgress;
    private boolean bossSpawnedThisWave = false;

    public EnemySpawner(MapGrid mapGrid, Base base, Random random, Path path, GameEntities gameEntities, Progression progression) {
        this.mapGrid = mapGrid;
        this.base = base;
        this.random = random;
        this.path = path;
        this.gameEntities = gameEntities;
        this.progression = progression;
        currentWave = 0;
        enemiesRemainingInWave = 0;
        waveTimer = 0.0;
        enemySpawnTimer = 0.0;
        waveInProgress = false;
    }

    public int update(double deltaTime) {
        int scoreRateIncrease = 0;

        if (gameEntities.getTowers().isEmpty()) {
            waveTimer = 0.0;
            enemySpawnTimer = 0.0;
            return scoreRateIncrease;
        }

        if (!waveInProgress) {
            waveTimer += deltaTime;

            if (waveTimer >= getTimeBetweenWaves()) {
                startNextWave();
                scoreRateIncrease++;
            }
        }

        if (waveInProgress) {
            enemySpawnTimer += deltaTime;

            while (enemySpawnTimer >= getEnemySpawnDelay() && enemiesRemainingInWave > 0) {
                enemySpawnTimer -= getEnemySpawnDelay();
                spawnEnemies(1);
                enemiesRemainingInWave--;
            }

            if (enemiesRemainingInWave == 0) {
                waveInProgress = false;
                waveTimer = 0.0;
            }
        }

        return scoreRateIncrease;
    }

    public void spawnEnemies(int number) {

        if (!gameEntities.getTowers().isEmpty()) {
            List<Point2D> walkableEdgeTiles = getWalkableEdgeTiles(mapGrid);

            for (int i = 0; i < number; i++) {

                Point2D tile = walkableEdgeTiles.get(
                        random.nextInt(walkableEdgeTiles.size()));

                Enemy enemy = createEnemyForCurrentWave((int) tile.getX(), (int) tile.getY());

                enemy.setDestination(new Point2D(base.getX(), base.getY()));

                spawnEnemy(enemy);
            }

            if (currentWave > 0 && currentWave % BOSS_WAVE_INTERVAL == 0 && !bossSpawnedThisWave) {

                System.out.println("BOSS WAVE !");

                Point2D tile = walkableEdgeTiles.get(
                        random.nextInt(walkableEdgeTiles.size()));

                Boss boss = new Boss((int) tile.getX(), (int) tile.getY());

                boss.setDestination(new Point2D(base.getX(), base.getY()));

                spawnEnemy(boss);

                bossSpawnedThisWave = true;
            }
        }
    }

    public int getCurrentWave()  {
        return currentWave;
    }
    public boolean isBossSpawnedThisWave() {
        return bossSpawnedThisWave;
    }

    public boolean hasBossAlive() {
        for (Enemy enemy : gameEntities.getEnemies()) {
            if (enemy instanceof Boss) {
                return true;
            }
        }
        return false;
    }

    public int getEnemiesRemainingInWave() {
        return enemiesRemainingInWave;
    }

    private void startNextWave() {
        currentWave++;

        bossSpawnedThisWave = false;

        enemiesRemainingInWave = FIRST_WAVE_SIZE + (currentWave - 1) * ENEMIES_ADDED_PER_WAVE;

        enemySpawnTimer = getEnemySpawnDelay();
        waveInProgress = true;
    }

    private double getEnemySpawnDelay() {
        double delay = TIME_BETWEEN_ENEMIES - (currentWave - 1) * ENEMY_DELAY_DECREASE_PER_WAVE;

        return Math.max(delay, MIN_TIME_BETWEEN_ENEMIES);
    }

    private double getTimeBetweenWaves() {
        double delay = INITIAL_TIME_BETWEEN_WAVES - currentWave * WAVE_DELAY_DECREASE_PER_WAVE;

        return Math.max(delay, MIN_TIME_BETWEEN_WAVES);
    }

    private Enemy createEnemyForCurrentWave(int x, int y) {
        int wave = Math.max(1, currentWave);
        int rand = random.nextInt(100);

        if (wave < 3) {
            return rand < 80 ? new Wolf(x, y) : new Archer(x, y);
        }

        if (wave < 5) {
            if (rand < 55) {
                return new Wolf(x, y);
            }
            if (rand < 90) {
                return new Archer(x, y);
            }
            return new ShadowSoldier(x, y);
        }

        if (wave < 8) {
            if (rand < 35) {
                return new Wolf(x, y);
            }
            if (rand < 65) {
                return new Archer(x, y);
            }
            if (rand < 85) {
                return new ShadowSoldier(x, y);
            }
            if (rand < 95) {
                return new ShadowArcher(x, y);
            }
            return new Catapult(x, y);
        }

        if (rand < 25) {
            return new Wolf(x, y);
        }
        if (rand < 45) {
            return new Archer(x, y);
        }
        if (rand < 65) {
            return new ShadowSoldier(x, y);
        }
        if (rand < 85) {
            return new ShadowArcher(x, y);
        }
        return new Catapult(x, y);
    }

    private void spawnEnemy(Enemy enemy) {
        path.assignMovement(enemy);
        gameEntities.addEnemy(enemy);
        progression.enemySpawned();
    }

    public static List<Point2D> getWalkableEdgeTiles(MapGrid mapGrid) {
        List<Point2D> spawnTiles = new ArrayList<>();

        for (int x = 0; x < mapGrid.getWidth(); x++) {
            addSpawnTileIfWalkable(spawnTiles, mapGrid, x, 0);
            addSpawnTileIfWalkable(spawnTiles, mapGrid, x, mapGrid.getHeight() - 1);
        }

        for (int y = 1; y < mapGrid.getHeight() - 1; y++) {
            addSpawnTileIfWalkable(spawnTiles, mapGrid, 0, y);
            addSpawnTileIfWalkable(spawnTiles, mapGrid, mapGrid.getWidth() - 1, y);
        }

        return spawnTiles;
    }

    private static void addSpawnTileIfWalkable(List<Point2D> spawnTiles, MapGrid mapGrid, int x, int y) {
        if (mapGrid.isWalkable(x, y)) {
            spawnTiles.add(new Point2D(x, y));
        }
    }
}
