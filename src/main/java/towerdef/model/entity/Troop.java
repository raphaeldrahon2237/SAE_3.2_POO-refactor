package towerdef.model.entity;

import javafx.geometry.Point2D;
import towerdef.model.core.Movement;

public abstract class Troop extends Entity {
    private int mvSpeed;
    private Point2D destination;
    private Movement movement;

    public Troop(int hp, int damage, int armor, int atkSpeed, int range, int x, int y) {
        super(hp, damage, armor, atkSpeed, range, x, y);
        this.mvSpeed = 1;
    }

    public Point2D getDestination() {
        return destination;
    }

    public void setDestination(Point2D destination) {
        this.destination = destination;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public int getMvSpeed() {
        return mvSpeed;
    }

    public void setMvSpeed(int mvSpeed) {
        this.mvSpeed = mvSpeed;
    }
}
