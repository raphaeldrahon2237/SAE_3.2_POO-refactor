package towerdef.model;

import javafx.geometry.Point2D;
import towerdef.model.entity.Base;
import towerdef.model.entity.troop.Enemy;
import towerdef.model.entity.troop.Wolf;
import towerdef.model.map.MapGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemySpawner {

    private static final int FIRST_WAVE_SIZE = 3;
    private static final int ENEMIES_ADDED_PER_WAVE = 1;
    private static final double INITIAL_TIME_BETWEEN_WAVES = 8.0;
    private static final double MIN_TIME_BETWEEN_WAVES = 3.0;
    private static final double WAVE_DELAY_DECREASE_PER_WAVE = 0.0;
    private static final double TIME_BETWEEN_ENEMIES = 1.0;
    private static final double MIN_TIME_BETWEEN_ENEMIES = 0.35;
    private static final double ENEMY_DELAY_DECREASE_PER_WAVE = 0.05;

    private MapGrid mapGrid;
    private Base base;
    private Random random;
    private Path path;
    private GameEntities gameEntities;
    private int currentWave;
    private int enemiesRemainingInWave;
    private double waveTimer;
    private double enemySpawnTimer;
    private boolean waveInProgress;

    public EnemySpawner(MapGrid mapGrid, Base base, Random random, Path path, GameEntities gameEntities) {
        this.mapGrid = mapGrid;
        this.base = base;
        this.random = random;
        this.path = path;
        this.gameEntities = gameEntities;
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
        if (gameEntities.getTowers().isEmpty()) {
            return;
        }

        List<Point2D> walkableEdgeTiles = getWalkableEdgeTiles(mapGrid);

        for (int i = 0; i < number; i++) {
            Point2D tile = walkableEdgeTiles.get(random.nextInt(walkableEdgeTiles.size()));

            Wolf wolf = new Wolf((int) tile.getX(), (int) tile.getY());
            wolf.setDestination(new Point2D(base.getX(), base.getY()));

            spawnEnemy(wolf);
        }
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public int getEnemiesRemainingInWave() {
        return enemiesRemainingInWave;
    }

    private void startNextWave() {
        currentWave++;
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

    private void spawnEnemy(Enemy enemy) {
        path.assignMovement(enemy);
        gameEntities.addEnemy(enemy);
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
