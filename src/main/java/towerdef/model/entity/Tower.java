package towerdef.model.entity;

public abstract class Tower extends Entity {

    public Tower(int hp, int damage, int armor, int atkSpeed, int range, double x, double y) {
        super(hp, damage, armor, atkSpeed, range, x, y);
    }

}
