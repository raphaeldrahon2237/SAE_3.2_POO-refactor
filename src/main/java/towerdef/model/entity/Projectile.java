package towerdef.model.entity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Projectile {

    private List<Point2D> trajectory;
    private IntegerProperty currentTileIndex = new SimpleIntegerProperty();
    private DoubleProperty progressToNextTile = new SimpleDoubleProperty();
    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();
    private int aoe;
    private double speed;
    private int damage;
    private Entity target;
    private boolean finished;

    public Projectile(List<Point2D> trajectory, Entity target, int damage, double speed,  int aoe) {
        this.trajectory = new ArrayList<>(trajectory);
        this.target = target;
        this.damage = damage;
        this.speed = speed;
        this.currentTileIndex.set(0);
        this.progressToNextTile.set(0.0);
        updateCoordinates();
        this.finished = trajectory.size() <= 1;
        this.aoe = aoe;
    }

    public void updatePosition(double deltaTime) {

        progressToNextTile.set(progressToNextTile.get() + speed * deltaTime);

        while (progressToNextTile.get() >= 1.0 && !finished) {
            progressToNextTile.set(progressToNextTile.get() - 1.0);
            currentTileIndex.set(currentTileIndex.get() + 1);

            if (currentTileIndex.get() == trajectory.size() - 1) {
                finished = true;
            }
        }

        updateCoordinates();
    }

    public Point2D getCurrentTile() {
        return trajectory.get(currentTileIndex.get());
    }

    public Point2D getNextTile() {
        if (currentTileIndex.get() + 1 >= trajectory.size()) {
            return getCurrentTile();
        }

        return trajectory.get(currentTileIndex.get() + 1);
    }

    public DoubleProperty getProgressToNextTile() {
        return progressToNextTile;
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public boolean isFinished() {
        return finished;
    }

    public Entity getTarget() {
        return target;
    }

    public int getDamage() {
        return damage;
    }

    public List<Point2D> getTrajectory() {
        return trajectory;
    }

    public int getAoe() {
        return aoe;
    }

    private void updateCoordinates() {
        Point2D current = getCurrentTile();
        Point2D next = getNextTile();
        double progress = progressToNextTile.get();

        x.set(current.getX() + (next.getX() - current.getX()) * progress);
        y.set(current.getY() + (next.getY() - current.getY()) * progress);
    }
}
