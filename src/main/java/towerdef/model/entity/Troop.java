package towerdef.model.entity;

public abstract class Troop extends Entity {
    private int mvSpeed;

    public Troop(int hp, int damage, int armor, int atkSpeed){
        super(hp, damage, armor, atkSpeed);
    }
}
