package towerdef.model.entity.tower;

import towerdef.model.entity.AttackType;

public class Mage extends Tower {
    public Mage( int x, int y){
        super(35, 14, 1, 2, 5, x, y, AttackType.AOE, 1, 170, 1, 120, 5, 5, 3, 1);
    }
}
