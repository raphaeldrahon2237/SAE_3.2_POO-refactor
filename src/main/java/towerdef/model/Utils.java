package towerdef.model;

import javafx.geometry.Point2D;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.Enemy;

import java.util.ArrayList;
import java.util.List;

public final class Utils {

    private Utils() {
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;

        return Math.sqrt(dx * dx + dy * dy);
    }

    public static boolean isInRange(double sourceX, double sourceY, double targetX, double targetY, int range) {
        return distance(sourceX, sourceY, targetX, targetY) <= range;
    }

    public static Point2D toTilePosition(double x, double y) {
        return new Point2D(Math.round(x), Math.round(y));
    }

    public static Point2D computePreshotTile(double sourceX, double sourceY, Enemy target, double projectileSpeed) {
        Point2D impactTile = toTilePosition(target.getX(), target.getY());
        double projectileTravelTime = computeProjectileTravelTime(sourceX, sourceY, impactTile, projectileSpeed);
        Point2D predictedPosition = target.predictPositionAfter(projectileTravelTime);

        return toTilePosition(predictedPosition.getX(), predictedPosition.getY());
    }

    public static double computeProjectileTravelTime(double sourceX, double sourceY, Point2D impactTile, double projectileSpeed) {
        List<Point2D> trajectory = computeLineTiles(
                (int) sourceX,
                (int) sourceY,
                (int) impactTile.getX(),
                (int) impactTile.getY()
        );

        return (trajectory.size() - 1) / projectileSpeed;
    }

    public static Enemy findClosestEnemyInRange(List<Enemy> enemies, double x, double y, int range) {
        Enemy closestEnemy = null;
        double closestDistance = Double.MAX_VALUE;

        for (Enemy enemy : enemies) {
            double distance = distance(enemy.getX(), enemy.getY(), x, y);

            if (enemy.isAlive() && distance <= range && distance < closestDistance) {
                closestEnemy = enemy;
                closestDistance = distance;
            }
        }

        return closestEnemy;
    }

    public static Tower findClosestTowerInRange(List<Tower> towers, double x, double y, int range) {
        Tower closestTower = null;
        double closestDistance = Double.MAX_VALUE;

        for (Tower tower : towers) {
            double distance = distance(tower.getX(), tower.getY(), x, y);

            if (tower.isAlive() && distance <= range && distance < closestDistance) {
                closestTower = tower;
                closestDistance = distance;
            }
        }

        return closestTower;
    }

    public static ArrayList<Enemy> findEnemiesInRange(List<Enemy> enemies, double x, double y, int range) {
        ArrayList<Enemy> enemiesInRange = new ArrayList<>();

        for (Enemy enemy : enemies) {
            if (enemy.isAlive() && isInRange(enemy.getX(), enemy.getY(), x, y, range)) {
                enemiesInRange.add(enemy);
            }
        }

        return enemiesInRange;
    }

    public static List<Point2D> computeLineTiles(int startX, int startY, int targetX, int targetY) {
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
}
