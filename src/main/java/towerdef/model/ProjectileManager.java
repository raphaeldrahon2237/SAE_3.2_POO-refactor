package towerdef.model;

import javafx.geometry.Point2D;
import towerdef.model.entity.AttackType;
import towerdef.model.entity.Entity;
import towerdef.model.entity.Projectile;
import towerdef.model.entity.troop.Enemy;

import java.util.ArrayList;
import java.util.List;

public class ProjectileManager {

    private static final double PROJECTILE_SPEED = 8.0;

    private GameEntities gameEntities;
    private RangeChecker rangeChecker;
    private Progression progression;

    public ProjectileManager(GameEntities gameEntities, RangeChecker rangeChecker, Progression progression) {
        this.gameEntities = gameEntities;
        this.rangeChecker = rangeChecker;
        this.progression = progression;
    }

    public void update(double deltaTime) {
        for (Projectile projectile : new ArrayList<>(gameEntities.getProjectiles())) {
            projectile.act(deltaTime, rangeChecker);

            if (projectile.isFinished()) {
                gameEntities.removeProjectile(projectile);
            }
        }
    }

    public Point2D computePreshotTile(double sourceX, double sourceY, Enemy target) {
        Point2D impactTile = Utils.toTilePosition(target.getX(), target.getY());
        double projectileTravelTime = computeProjectileTravelTime(sourceX, sourceY, impactTile);
        Point2D predictedPosition = target.predictPositionAfter(projectileTravelTime);

        return Utils.toTilePosition(predictedPosition.getX(), predictedPosition.getY());
    }

    public void createProjectile(Entity source, Entity target, Point2D impactTile, AttackType projectileType) {
        List<Point2D> trajectory = computeLineTiles(
                (int) source.getX(),
                (int) source.getY(),
                (int) impactTile.getX(),
                (int) impactTile.getY()
        );

        Projectile projectile = new Projectile(trajectory, target, source, PROJECTILE_SPEED, projectileType);
        progression.projectileFired();
        gameEntities.addProjectile(projectile);
    }

    private double computeProjectileTravelTime(double sourceX, double sourceY, Point2D impactTile) {
        List<Point2D> trajectory = computeLineTiles(
                (int) sourceX,
                (int) sourceY,
                (int) impactTile.getX(),
                (int) impactTile.getY()
        );

        return (trajectory.size() - 1) / PROJECTILE_SPEED;
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
}
