package towerdef.model.entity.troop;

import javafx.geometry.Point2D;
import towerdef.model.entity.Entity;

public abstract class Troop extends Entity {

    private int mooveSpeed;
    private Point2D destination;

    public Troop(int hp, int damage, int armor, int atkSpeed, int range, int mvSpeed, int x, int y) {
        super(hp, damage, armor, atkSpeed, range, x, y);
        this.mooveSpeed = mvSpeed;
    }

    public Point2D getDestination() {
        return destination;
    }

    public void setDestination(Point2D destination) {
        this.destination = destination;
    }

    public int getMooveSpeed() {
        return mooveSpeed;
    }

    public void setMooveSpeed(int mooveSpeed) {
        System.out.println("speed before :"+ mooveSpeed);
        this.mooveSpeed = mooveSpeed;
        System.out.println("speed after :"+ mooveSpeed);
    }

    @Override
    public String toString() {
        return "" + super.toString();
    }
}