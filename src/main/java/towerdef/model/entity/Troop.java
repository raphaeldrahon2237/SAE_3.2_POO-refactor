package towerdef.model.entity;

public abstract class Troop extends Entity {
    private int mvSpeed;

    public Troop(int hp, int damage, int armor, int atkSpeed, double x, double y) {
        super(hp, damage, armor, atkSpeed, x, y);
        this.mvSpeed = 1;
    }

    public int getMvSpeed() {
        return mvSpeed;
    }

    public void setMvSpeed(int mvSpeed) {
        this.mvSpeed = mvSpeed;
    }
}