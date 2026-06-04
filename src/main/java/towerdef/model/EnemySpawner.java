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

    private MapGrid mapGrid;
    private Base base;
    private Random random;
    private Path path;
    private GameEntities gameEntities;
    private double difficultyTimer;
    private double spawnInterval;
    private double spawnTimer;

    public EnemySpawner(MapGrid mapGrid, Base base, Random random, Path path, GameEntities gameEntities) {
        this.mapGrid = mapGrid;
        this.base = base;
        this.random = random;
        this.path = path;
        this.gameEntities = gameEntities;
        difficultyTimer = 0.0;
        spawnTimer = 0.0;
        spawnInterval = 10;
    }

    public int update(double deltaTime) {
        int scoreRateIncrease = 0;

        difficultyTimer += deltaTime;
        spawnTimer += deltaTime;

        if (difficultyTimer >= 30.0) {
            difficultyTimer -= 30.0;
            scoreRateIncrease++;

            if (spawnInterval - 1 > 0.0) {
                spawnInterval -= 1;
            }
        }

        if (spawnTimer >= spawnInterval) {
            spawnTimer -= spawnInterval;
            spawnEnemies(1);
        }

        return scoreRateIncrease;
    }

    public void spawnEnemies(int number) {
        List<Point2D> walkableEdgeTiles = getWalkableEdgeTiles(mapGrid);

        for (int i = 0; i < number; i++) {
            Point2D tile = walkableEdgeTiles.get(random.nextInt(walkableEdgeTiles.size()));

            Wolf wolf = new Wolf((int) tile.getX(), (int) tile.getY());
            wolf.setDestination(new Point2D(base.getX(), base.getY()));

            spawnEnemy(wolf);
        }
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
