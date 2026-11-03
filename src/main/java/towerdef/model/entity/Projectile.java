package towerdef.model.entity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import towerdef.model.RangeChecker;
import towerdef.model.entity.impact.ImpactStrategy;

import java.util.ArrayList;
import java.util.List;

public class Projectile {

    private List<Point2D> trajectory;
    private IntegerProperty currentTileIndex = new SimpleIntegerProperty();
    private DoubleProperty progressToNextTile = new SimpleDoubleProperty();
    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();
    private double speed;
    private Entity target;
    private Entity source;
    private boolean finished;
    private final ImpactStrategy impactStrategy;

    public Projectile(List<Point2D> trajectory, Entity target, Entity source,
                      double speed, ImpactStrategy impactStrategy) {
        this.trajectory = new ArrayList<>(trajectory);
        this.target = target;
        this.source = source;
        this.speed = speed;
        this.currentTileIndex.set(0);
        this.progressToNextTile.set(0.0);
        updateCoordinates();
        this.finished = trajectory.size() <= 1;
        this.impactStrategy = impactStrategy;
    }

    public void act(double deltaTime, RangeChecker rangeChecker) {

        progressToNextTile.set(progressToNextTile.get() + speed * deltaTime);

        while (progressToNextTile.get() >= 1.0 && !finished) {
            progressToNextTile.set(progressToNextTile.get() - 1.0);
            currentTileIndex.set(currentTileIndex.get() + 1);

            if (currentTileIndex.get() == trajectory.size() - 1) {
                finished = true;
            }
        }
        updateCoordinates();
        if (finished) {
            impactStrategy.applyImpact(this, rangeChecker);
        }
    }

    private void updateCoordinates() {
        Point2D current = getCurrentTile();
        Point2D next = getNextTile();
        double progress = progressToNextTile.get();

        x.set(current.getX() + (next.getX() - current.getX()) * progress);
        y.set(current.getY() + (next.getY() - current.getY()) * progress);
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

    public Point2D getImpactTile() {
        return trajectory.get(trajectory.size() - 1);
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

    public int getDamage() {
        return source.getDamage();
    }

    public Entity getSource() {
        return source;
    }

    public Entity getTarget() {
        return target;
    }
}
