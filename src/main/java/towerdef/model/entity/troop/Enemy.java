package towerdef.model.entity.troop;

public abstract class Enemy extends Troop {

    private int targetMode;

    public Enemy(int hp, int damage, int armor, int atkSpeed, int range, int mvSpeed, int x, int y,int targetMode) {
        super(hp, damage, armor, atkSpeed, range, mvSpeed, x, y);
        this.targetMode = targetMode;
    }


}
