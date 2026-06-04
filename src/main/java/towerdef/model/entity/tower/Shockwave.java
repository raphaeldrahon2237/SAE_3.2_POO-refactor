package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;

public class Shockwave extends Tower {

    private int slowSpeed;

    public Shockwave(int x, int y, int slowSpeed) {
        super(40, 0, 4, 5, 4, x, y, 10, AttackType.SHOCKWAVE, 0);
        this.slowSpeed = slowSpeed;
    }

    public int getSlowSpeed() {
        return slowSpeed;
    }
}