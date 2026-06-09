package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;

public class Earthquake extends Tower {

    private int slowSpeed;

    public Earthquake(int x, int y, int slowSpeed) {
        super(30, 5, 0, 1, 2, x, y, AttackType.AOE, 5, 30, 1, 30, 5, 5, 1, 2);
        this.slowSpeed = slowSpeed;
    }

    public int getSlowSpeed() {
        return slowSpeed;
    }
}
