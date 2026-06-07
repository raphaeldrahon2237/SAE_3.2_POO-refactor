package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;

public class Artillery extends Tower {
    public Artillery(int x, int y){
        super(40, 10, 4, 2, 7, x, y, 10, AttackType.AOE, 2);
    }
}
