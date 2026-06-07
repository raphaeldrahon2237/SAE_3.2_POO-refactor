package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;

public class Earthquake extends Tower {

    private int slowSpeed;

    public Earthquake(int x, int y, int slowSpeed) {
        super(40, 0, 4, 5, 2, x, y, 25, AttackType.SHOCKWAVE, 0);
        this.slowSpeed = slowSpeed;
    }

    public int getSlowSpeed() {
        return slowSpeed;
    }
}
