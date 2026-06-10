package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;

public class Earthquake extends Tower {

    private int slowSpeed;

    public Earthquake(int x, int y, int slowSpeed) {
        super(5,"Ralentit les ennemis proches et inflige des degats continus.",60, 4, 3, 1, 4, x, y, AttackType.EARTHQUAKE, 0, 260, 1, 180, 2, 10, 1, 1);
        this.slowSpeed = slowSpeed;
    }

    public int getSlowSpeed() {
        return slowSpeed;
    }
}
