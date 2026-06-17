package towerdef.model.entity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import towerdef.model.RangeChecker;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.Enemy;

import java.util.ArrayList;
import java.util.List;

import static towerdef.model.entity.AttackType.AOE;
import static towerdef.model.entity.AttackType.MAGIC;

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
    private AttackType projectyleType;

    public Projectile(List<Point2D> trajectory, Entity target, Entity source, double speed, AttackType  projectyleType) {
        this.trajectory = new ArrayList<>(trajectory);
        this.target = target;
        this.source = source;
        this.speed = speed;
        this.currentTileIndex.set(0);
        this.progressToNextTile.set(0.0);
        updateCoordinates();
        this.finished = trajectory.size() <= 1;
        this.projectyleType = projectyleType;
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
        if(this.finished){
            if(projectyleType==AOE) dealDamageInAoe(rangeChecker);
            else dealDamage();
        }
    }

    public void dealDamage(){
        if (target instanceof Enemy enemy && source instanceof Tower tower) {
            enemy.setLastDamagingTower(tower);
        }

        if(projectyleType==MAGIC) target.takeMagicDamage(getDamage());
        else target.takeDamage(source.getDamage());
    }

    public void dealDamageInAoe(RangeChecker rangeChecker){
        ArrayList<Enemy> enemiesImpacted;
        Point2D impactTile = this.getImpactTile();
        double impactX = impactTile.getX();
        double impactY = impactTile.getY();

        enemiesImpacted= rangeChecker.findEnemiesInRange(impactX,impactY, 2);

        for(Enemy enemy: enemiesImpacted) {
            if (source instanceof Tower tower) {
                enemy.setLastDamagingTower(tower);
            }

            enemy.takeDamage(source.getDamage());
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
}
