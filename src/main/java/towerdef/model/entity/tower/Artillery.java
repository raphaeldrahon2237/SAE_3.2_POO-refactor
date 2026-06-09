package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;

public class Artillery extends Tower {
    public Artillery(int x, int y){
        super(40, 10, 4, 2, 2, x, y, AttackType.AOE, 1, 20, 1, 25, 5, 5, 1, 2);
    }
}
