package towerdef.model.entity.troop;

import javafx.geometry.Point2D;
import towerdef.model.entity.Entity;

public abstract class Troop extends Entity {

    private int mvSpeed;
    private Point2D destination;

    public Troop(int hp, int damage, int armor, int atkSpeed, int range, int mvSpeed, int x, int y) {
        super(hp, damage, armor, atkSpeed, range, x, y);
        this.mvSpeed = mvSpeed;
    }

    public Point2D getDestination() {
        return destination;
    }

    public void setDestination(Point2D destination) {
        this.destination = destination;
    }

    public int getMvSpeed() {
        return mvSpeed;
    }

    public void setMvSpeed(int mvSpeed) {
        this.mvSpeed = mvSpeed;
    }

    @Override
    public String toString() {
        return "" + super.toString();
    }
}