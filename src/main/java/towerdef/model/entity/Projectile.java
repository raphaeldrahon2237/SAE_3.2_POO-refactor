package towerdef.model.entity;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Projectile {

    private List<Point2D> trajectory;
    private int currentTileIndex;
    private double progressToNextTile;
    private double speed;
    private int damage;
    private Entity target;
    private boolean finished;

    public Projectile(List<Point2D> trajectory, Entity target, int damage, double speed) {
        this.trajectory = new ArrayList<>();
        this.target = target;
        this.damage = damage;
        this.speed = speed;
        this.currentTileIndex = 0;
        this.progressToNextTile = 0.0;
        this.finished = trajectory.size() <= 1;
    }

    public void updatePosition(double deltaTime) {

        progressToNextTile += speed * deltaTime;

        while (progressToNextTile >= 1.0 && !finished) {
            progressToNextTile -= 1.0;
            currentTileIndex++;

            if (currentTileIndex == trajectory.size() - 1) {
                target.takeDamage(damage);
                finished = true;
            }
        }
    }

    public Point2D getCurrentTile() {
        return trajectory.get(currentTileIndex);
    }

    public Point2D getNextTile() {
        if (currentTileIndex + 1 >= trajectory.size()) {
            return getCurrentTile();
        }

        return trajectory.get(currentTileIndex + 1);
    }

    public double getProgressToNextTile() {
        return progressToNextTile;
    }

    public boolean isFinished() {
        return finished;
    }
}
