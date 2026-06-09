package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;

public class Barrack extends Tower {
    public Barrack(int x, int y){
        super(45, 10, 5, 2, 5, x, y, AttackType.NORMAL, 1, 30, 1, 25, 5, 5, 1, 2);
    }

    public String toString() {
        return "Barracks: "+super.toString();
    }
}
