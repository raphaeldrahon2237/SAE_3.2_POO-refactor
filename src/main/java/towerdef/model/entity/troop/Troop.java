package towerdef.model.entity.troop;

import javafx.geometry.Point2D;
import towerdef.model.entity.Entity;

public abstract class Troop extends Entity {

    private int moveSpeed;
    private int baseMooveSpeed;
    private Point2D destination;

    public Troop(int hp, int damage, int armor, int attackSpeed, int range, int mvSpeed, int x, int y) {
        super(hp, damage, armor, attackSpeed, range, x, y);
        this.moveSpeed = mvSpeed;
        this.baseMooveSpeed = mvSpeed;
    }

    public Point2D getDestination() {
        return destination;
    }

    public void setDestination(Point2D destination) {
        this.destination = destination;
    }

    public int getMooveSpeed() {
        return moveSpeed;
    }

    public void setMooveSpeed(int mooveSpeed) {
        this.moveSpeed = mooveSpeed;
    }

    public int getBaseMooveSpeed() {
        return baseMooveSpeed;
    }

    public void resetMooveSpeed() {
        this.moveSpeed = baseMooveSpeed;
    }

    @Override
    public String toString() {
        return "" + super.toString();
    }
}