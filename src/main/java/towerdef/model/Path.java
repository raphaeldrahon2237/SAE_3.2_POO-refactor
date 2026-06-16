package towerdef.model;

import javafx.geometry.Point2D;
import towerdef.model.core.AStar;
import towerdef.model.entity.Base;
import towerdef.model.entity.troop.Enemy;
import towerdef.model.map.MapGrid;

import java.util.List;

public class Path {

    private MapGrid mapGrid;
    private Base base;
    private AStar[] aStarList;
    private GameEntities gameEntities;

    public Path(MapGrid mapGrid, Base base, GameEntities gameEntities) {
        this.mapGrid = mapGrid;
        this.base = base;
        this.gameEntities = gameEntities;
        aStarList = createPaths();
    }

    public boolean assignMovement(Enemy enemy) {
        List<Point2D> path = getCachedPath(enemy);

        if (path == null) {
            Point2D source = new Point2D(enemy.getX(), enemy.getY());
            AStar aStar = new AStar(mapGrid, source, enemy.getDestination(), gameEntities);
            path = aStar.getPath();
        }

        enemy.setPath(path);
        enemy.startMovement();

        return !path.isEmpty();
    }

    private AStar[] createPaths() {
        List<Point2D> spawnPoints = EnemySpawner.getWalkableEdgeTiles(mapGrid);
        AStar[] aStarList = new AStar[spawnPoints.size()];
        Point2D baseTile = new Point2D(base.getX(), base.getY());

        for (int i = 0; i < aStarList.length; i++) {
            aStarList[i] = new AStar(mapGrid, spawnPoints.get(i), baseTile, gameEntities);
        }

        return aStarList;
    }

    private List<Point2D> getCachedPath(Enemy enemy) {
        if (!gameEntities.getTowers().isEmpty()) {
            return null;
        }

        if (!new Point2D(base.getX(), base.getY()).equals(enemy.getDestination())) {
            return null;
        }

        for (AStar aStar : aStarList) {
            if (aStar.getSource().getX() == enemy.getX() && aStar.getSource().getY() == enemy.getY()) {
                return aStar.getPath();
            }
        }

        return null;
    }
}
